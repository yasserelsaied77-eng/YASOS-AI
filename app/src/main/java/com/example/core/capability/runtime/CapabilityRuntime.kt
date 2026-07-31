package com.example.core.capability.runtime

import com.example.core.capability.ExecutiveCapability
import com.example.core.decision.DecisionSession
import com.example.core.decision.DecisionStatus
import java.util.UUID

class CapabilityRuntime(
    val capability: ExecutiveCapability
) {
    private val activeSessions = mutableListOf<DecisionSession>()

    fun startDecisionSession(contextDescription: String): DecisionSession {
        val session = DecisionSession(
            id = UUID.randomUUID().toString(),
            capabilityId = capability.id,
            contextDescription = contextDescription
        )
        activeSessions.add(session)
        return session
    }

    fun getActiveSessions(): List<DecisionSession> {
        return activeSessions.filter { it.status != DecisionStatus.COMPLETED }
    }
}

class CapabilityWorkspaceManager {
    private val activeRuntimes = mutableMapOf<String, CapabilityRuntime>()

    fun loadCapability(capability: ExecutiveCapability): CapabilityRuntime {
        val runtime = CapabilityRuntime(capability)
        activeRuntimes[capability.id] = runtime
        return runtime
    }

    fun getRuntime(capabilityId: String): CapabilityRuntime? {
        return activeRuntimes[capabilityId]
    }
}
