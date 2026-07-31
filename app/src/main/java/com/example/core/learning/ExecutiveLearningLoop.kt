package com.example.core.learning

data class ExecutiveIdentity(
    val role: String,
    val coreValues: List<String>
)

data class ExecutiveVision(
    val longTermGoals: List<String>,
    val horizonYears: Int
)

data class ExecutivePrinciples(
    val nonNegotiables: List<String>
)

data class ExecutivePreferences(
    val riskTolerance: Double, // 0.0 to 1.0
    val speedVsAccuracy: Double // 0.0 to 1.0
)

data class ExecutiveBehavior(
    val actualRiskTaken: Double,
    val averageDecisionTime: Long
)

data class ExecutiveDna(
    val version: Int = 1,
    val identity: ExecutiveIdentity = ExecutiveIdentity("Executive", emptyList()),
    val vision: ExecutiveVision = ExecutiveVision(emptyList(), 5),
    val principles: ExecutivePrinciples = ExecutivePrinciples(emptyList()),
    val preferences: ExecutivePreferences = ExecutivePreferences(0.5, 0.5),
    val behavior: ExecutiveBehavior = ExecutiveBehavior(0.5, 0L),
    val lastUpdated: Long = System.currentTimeMillis()
)

data class DecisionOutcome(
    val decisionId: String,
    val successScore: Double, // 0.0 to 1.0
    val feedback: String,
    val timestamp: Long
)

class ExecutiveLearningLoop {
    fun processOutcome(outcome: DecisionOutcome, currentDna: ExecutiveDna): ExecutiveDna {
        // Evaluate the outcome, extract lessons, update DNA version based on real behavior
        val updatedBehavior = currentDna.behavior.copy(
            actualRiskTaken = (currentDna.behavior.actualRiskTaken + outcome.successScore) / 2
        )
        return currentDna.copy(
            version = currentDna.version + 1,
            behavior = updatedBehavior,
            lastUpdated = System.currentTimeMillis()
        )
    }
}
