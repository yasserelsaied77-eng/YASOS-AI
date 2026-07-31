package com.example.core.capability

import com.example.core.ontology.OntologyConcept

data class CapabilityMetadata(
    val owner: String,
    val inputs: List<OntologyConcept>,
    val outputs: List<OntologyConcept>,
    val metrics: List<String>,
    val dependencies: List<String>
)

data class ExecutiveCapability(
    val id: String,
    val name: String,
    val description: String,
    val metadata: CapabilityMetadata,
    val policies: List<String>, // IDs of policies
    val playbooks: List<String>, // IDs of playbooks
    val decisionModels: List<String>, // IDs of decision models
    val knowledgeAssets: List<String>, // IDs of knowledge assets
    val aiSkills: List<String>,
    val automations: List<String>
)

class CapabilityRegistry {
    private val capabilities = mutableMapOf<String, ExecutiveCapability>()

    fun register(capability: ExecutiveCapability) {
        capabilities[capability.id] = capability
    }

    fun getCapability(id: String): ExecutiveCapability? {
        return capabilities[id]
    }

    fun getAllCapabilities(): List<ExecutiveCapability> {
        return capabilities.values.toList()
    }
}
