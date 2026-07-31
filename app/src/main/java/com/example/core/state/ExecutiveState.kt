package com.example.core.state

data class ExecutiveStateSnapshot(
    val timestamp: Long = System.currentTimeMillis(),
    val currentGoals: List<String>,
    val currentRisks: List<String>,
    val currentOpportunities: List<String>,
    val currentConstraints: List<String>,
    val currentResources: List<String>,
    val currentDecisions: List<String>,
    val currentPriorities: List<String>,
    val currentEnergyLevel: Double, // 0.0 to 1.0
    val currentFocusArea: String?
)

class ExecutiveStateManager {
    private var currentState: ExecutiveStateSnapshot? = null

    fun updateState(newState: ExecutiveStateSnapshot) {
        currentState = newState
    }

    fun getState(): ExecutiveStateSnapshot {
        return currentState ?: ExecutiveStateSnapshot(
            currentGoals = emptyList(),
            currentRisks = emptyList(),
            currentOpportunities = emptyList(),
            currentConstraints = emptyList(),
            currentResources = emptyList(),
            currentDecisions = emptyList(),
            currentPriorities = emptyList(),
            currentEnergyLevel = 1.0,
            currentFocusArea = null
        )
    }
}
