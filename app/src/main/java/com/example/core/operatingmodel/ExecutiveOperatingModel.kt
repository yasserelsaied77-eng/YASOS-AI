package com.example.core.operatingmodel

data class BusinessUnit(
    val id: String,
    val name: String,
    val description: String,
    val capabilities: List<String>, // Capability IDs
    val processes: List<String>, // Process IDs
    val roles: List<String>
)

data class Role(
    val id: String,
    val title: String,
    val responsibilities: List<String>,
    val decisionRights: List<String>
)

data class ExecutiveOperatingModel(
    val organizationName: String,
    val businessUnits: List<BusinessUnit>,
    val capabilities: List<String>, // Overall registry reference
    val processes: List<String>,
    val roles: List<Role>
)

class OperatingModelEngine {
    private var currentModel: ExecutiveOperatingModel? = null
    
    fun setModel(model: ExecutiveOperatingModel) {
        currentModel = model
    }
    
    fun getModel(): ExecutiveOperatingModel? = currentModel
}
