package com.example.core.llm

import com.example.core.model.ExecutiveContext
import com.example.domain.model.LifeInboxRecord

class GeminiProvider : LlmProvider {
    override suspend fun analyze(inboxRecord: LifeInboxRecord, baseContext: ExecutiveContext): ExecutiveContext {
        // Mock implementation for now, AI integration comes later
        return baseContext.copy(
            summary = "AI Analyzed: ${inboxRecord.rawContent.take(50)}",
            confidence = 0.90
        )
    }
}
