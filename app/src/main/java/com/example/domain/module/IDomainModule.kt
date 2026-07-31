package com.example.domain.module

import com.example.domain.model.DomainCode
import com.example.domain.model.MemoryVaultRecord
import com.example.domain.model.RouterAnalysisResult

data class DomainMetadata(
    val nameAr: String,
    val nameEn: String,
    val version: String
)

data class DomainProcessResult(
    val success: Boolean,
    val domainSpecificRecordId: String,
    // Using a Map or a subset of MemoryVaultRecord for simplicity in the client representation
    val extractedKnowledge: Map<String, Any>
)

interface IDomainModule {
    val code: DomainCode
    val metadata: DomainMetadata

    suspend fun processDomainRecord(
        inboxItemContent: String,
        inboxItemMetadata: Map<String, Any>,
        routerAnalysis: RouterAnalysisResult
    ): DomainProcessResult

    suspend fun getDomainContext(query: String): String
}
