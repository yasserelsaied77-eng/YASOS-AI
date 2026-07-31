package com.example.core.metrics

data class CognitiveMetrics(
    // Operational Metrics
    var knowledgeGrowth: Int = 0,
    var decisionAccuracy: Double = 0.0,
    var recommendationAcceptance: Double = 0.0,
    var duplicatePreventionCount: Int = 0,
    var conflictDetectionRate: Double = 0.0,
    var averageDecisionTime: Long = 0L,
    var insightGenerationRate: Double = 0.0,
    
    // Knowledge Health Metrics
    var knowledgeReuse: Int = 0,
    var knowledgeFreshness: Double = 1.0, // 1.0 is completely fresh
    var knowledgeCoverage: Double = 0.0,
    var evidenceStrength: Double = 0.0,
    var policyUsage: Int = 0,
    var dnaStability: Double = 1.0,
    var relationshipDensity: Double = 0.0,
    var ontologyCompleteness: Double = 0.0,
    var vocabularyCoverage: Double = 0.0,
    var learningVelocity: Double = 0.0
)

class ExecutiveMetricsEngine {
    private var currentMetrics = CognitiveMetrics()
    
    fun recordInsightGenerated() {
        currentMetrics.insightGenerationRate += 1.0 
    }
    
    fun recordDuplicatePrevented() {
        currentMetrics.duplicatePreventionCount += 1
    }
    
    fun getMetrics(): CognitiveMetrics = currentMetrics
}
