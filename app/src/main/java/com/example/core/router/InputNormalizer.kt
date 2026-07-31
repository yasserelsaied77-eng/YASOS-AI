package com.example.core.router

import com.example.core.model.NormalizedInput
import com.example.domain.model.LifeInboxRecord

class InputNormalizer {
    fun normalize(inboxRecord: LifeInboxRecord): NormalizedInput {
        return NormalizedInput(
            originalId = inboxRecord.id,
            rawContent = inboxRecord.rawContent,
            normalizedContent = inboxRecord.rawContent.trim(),
            source = inboxRecord.rawPayload.sourceClient ?: "UNKNOWN",
            type = inboxRecord.sourceType.name
        )
    }
}
