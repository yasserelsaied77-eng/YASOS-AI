package com.example.core.intelligence.pipeline

class InferencePipeline(
    private val stages: List<IntelligenceStage>
) {
    suspend fun run(initialContext: InferenceContext): InferenceContext {
        var currentContext = initialContext
        for (stage in stages) {
            currentContext = stage.execute(currentContext)
        }
        return currentContext
    }
}
