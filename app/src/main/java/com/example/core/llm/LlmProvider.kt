package com.example.core.llm

import com.example.core.model.ExecutiveContext
import com.example.domain.model.LifeInboxRecord

interface LlmProvider {
    suspend fun analyze(inboxRecord: LifeInboxRecord, baseContext: ExecutiveContext): ExecutiveContext
}
