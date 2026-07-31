package com.example.core.decision

import com.example.core.learning.ExecutiveDna
import com.example.core.learning.DecisionOutcome
import com.example.core.evidence.Evidence

enum class DecisionStatus {
    INITIATED, GATHERING_EVIDENCE, SIMULATING, AWAITING_APPROVAL, EXECUTING, REVIEWING, COMPLETED
}

data class DecisionSession(
    val id: String,
    val capabilityId: String,
    val contextDescription: String,
    var status: DecisionStatus = DecisionStatus.INITIATED,
    val evidence: MutableList<Evidence> = mutableListOf(),
    val alternatives: MutableList<DecisionAlternative> = mutableListOf(),
    var simulationResults: List<SimulationResult> = emptyList(),
    var finalDecisionAlternativeId: String? = null,
    var outcome: DecisionOutcome? = null
) {
    fun addEvidence(ev: Evidence) {
        evidence.add(ev)
    }

    fun addAlternative(alt: DecisionAlternative) {
        alternatives.add(alt)
    }

    fun runSimulations(engine: ExecutiveSimulationEngine, model: DecisionModel) {
        status = DecisionStatus.SIMULATING
        simulationResults = engine.runSimulation(model, alternatives)
        status = DecisionStatus.AWAITING_APPROVAL
    }

    fun approveDecision(alternativeId: String) {
        finalDecisionAlternativeId = alternativeId
        status = DecisionStatus.EXECUTING
    }

    fun completeDecision(outcomeRecord: DecisionOutcome) {
        outcome = outcomeRecord
        status = DecisionStatus.REVIEWING
    }
}
