package com.example.core.decision

data class DecisionCriteria(
    val id: String,
    val description: String,
    val weight: Double, // e.g. 0.0 to 1.0
    val threshold: Double // minimum required score
)

data class DecisionModel(
    val id: String,
    val name: String,
    val capabilityId: String,
    val criteria: List<DecisionCriteria>,
    val policies: List<String>,
    val risks: List<String>,
    val expectedOutcomeMetrics: List<String>
)

data class DecisionAlternative(
    val id: String,
    val description: String,
    val evidenceIds: List<String>,
    val predictedScores: Map<String, Double> // criteria ID -> score
)

data class SimulationResult(
    val alternativeId: String,
    val overallScore: Double,
    val impact: String,
    val risks: List<String>,
    val costs: Double,
    val benefits: String
)

class ExecutiveSimulationEngine {
    fun runSimulation(
        model: DecisionModel,
        alternatives: List<DecisionAlternative>
    ): List<SimulationResult> {
        // Evaluate alternatives against the model criteria, apply weights
        return alternatives.map { alt ->
            var totalScore = 0.0
            model.criteria.forEach { criteria ->
                val score = alt.predictedScores[criteria.id] ?: 0.0
                totalScore += score * criteria.weight
            }
            SimulationResult(
                alternativeId = alt.id,
                overallScore = totalScore,
                impact = "Simulated impact for \${alt.description}",
                risks = emptyList(), // Would be derived from evidence and model
                costs = 0.0,
                benefits = "Simulated benefits"
            )
        }.sortedByDescending { it.overallScore }
    }
}
