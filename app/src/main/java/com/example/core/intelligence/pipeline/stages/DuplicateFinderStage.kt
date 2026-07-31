package com.example.core.intelligence.pipeline.stages

import com.example.core.intelligence.pipeline.InferenceContext
import com.example.core.intelligence.pipeline.IntelligenceStage

class DuplicateFinderStage : IntelligenceStage {
    override suspend fun execute(context: InferenceContext): InferenceContext {
        // Mock implementation
        return context
    }
}
