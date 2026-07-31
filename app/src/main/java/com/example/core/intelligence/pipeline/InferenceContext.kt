package com.example.core.intelligence.pipeline

import com.example.core.graph.model.ExecutiveGraphContext
import com.example.core.intelligence.model.IntelligenceInsight

data class InferenceContext(
    val graphContext: ExecutiveGraphContext,
    val insights: List<IntelligenceInsight> = emptyList()
)

interface IntelligenceStage {
    suspend fun execute(context: InferenceContext): InferenceContext
}
