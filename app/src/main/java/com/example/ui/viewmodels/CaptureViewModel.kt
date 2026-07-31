package com.example.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.di.ServiceLocator
import com.example.core.model.ExecutiveContext
import com.example.data.database.entities.ExecutiveContextEntity
import com.example.data.database.entities.LifeInboxEntity
import com.example.domain.model.InboxRawPayload
import com.example.domain.model.InboxSourceType
import com.example.domain.model.InboxStatus
import com.example.domain.model.LifeInboxRecord
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

sealed class CaptureState {
    object Idle : CaptureState()
    object Processing : CaptureState()
    data class Success(val context: ExecutiveContext) : CaptureState()
    data class Error(val message: String) : CaptureState()
}

class CaptureViewModel(application: Application) : AndroidViewModel(application) {
    private val inboxRepo = ServiceLocator.provideInboxRepository(application)
    private val executiveKernel = ServiceLocator.provideExecutiveKernel(application)

    private val _uiState = MutableStateFlow<CaptureState>(CaptureState.Idle)
    val uiState: StateFlow<CaptureState> = _uiState.asStateFlow()

    private val _recentCaptures = MutableStateFlow<List<ExecutiveContext>>(emptyList())
    val recentCaptures: StateFlow<List<ExecutiveContext>> = _recentCaptures.asStateFlow()

    fun processInput(rawText: String, sourceType: InboxSourceType = InboxSourceType.TEXT) {
        if (rawText.isBlank()) return

        viewModelScope.launch {
            _uiState.value = CaptureState.Processing
            try {
                // 1. Capture -> InboxRecord
                val inboxRecord = LifeInboxRecord(
                    id = UUID.randomUUID().toString(),
                    sourceType = sourceType,
                    rawContent = rawText,
                    rawPayload = InboxRawPayload(sourceClient = "android_capture"),
                    status = InboxStatus.PENDING,
                    errorLog = null,
                    receivedAt = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).format(Date()),
                    processedAt = null
                )

                // 2. Repository.saveInbox()
                val inboxEntity = LifeInboxEntity(
                    id = inboxRecord.id,
                    sourceType = inboxRecord.sourceType.name,
                    rawContent = inboxRecord.rawContent,
                    rawPayloadJson = "{}", // Mocked for now
                    status = inboxRecord.status.name,
                    errorLog = inboxRecord.errorLog,
                    receivedAt = inboxRecord.receivedAt,
                    processedAt = inboxRecord.processedAt
                )
                inboxRepo.insert(inboxEntity)
                
                // 3. Delegate to Executive Kernel
                val aiContext = executiveKernel.processInput(inboxRecord, rawText)
                
                // Update state and history
                _uiState.value = CaptureState.Success(aiContext)
                _recentCaptures.value = listOf(aiContext) + _recentCaptures.value

                // Reset to idle after a short delay to allow new inputs
                kotlinx.coroutines.delay(3000)
                if (_uiState.value is CaptureState.Success) {
                    _uiState.value = CaptureState.Idle
                }
            } catch (e: Exception) {
                _uiState.value = CaptureState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
}
