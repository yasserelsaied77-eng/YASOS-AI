package com.example.core.intelligence.pipeline.stages

import com.example.core.intelligence.pipeline.InferenceContext
import com.example.core.intelligence.pipeline.IntelligenceStage
import com.example.core.intelligence.model.InsightType
import com.example.core.intelligence.model.IntelligenceInsight
import com.example.core.graph.model.NodeType
import java.util.UUID

class PatternAnalyzerStage : IntelligenceStage {
    override suspend fun execute(context: InferenceContext): InferenceContext {
        val newInsights = mutableListOf<IntelligenceInsight>()
        val decisions = context.graphContext.related.filter { it.type == NodeType.DECISION }
        if (decisions.size >= 2) {
            newInsights.add(
                IntelligenceInsight(
                    id = UUID.randomUUID().toString(),
                    type = InsightType.PATTERN,
                    title = "Recurring Decisions",
                    description = "Found multiple decisions in this context. A pattern is emerging.",
                    relatedNodes = decisions,
                    confidence = 0.85,
                    timestamp = System.currentTimeMillis()
                )
            )
        }
        return context.copy(insights = context.insights + newInsights)
    }
}
