package com.example.core.process

enum class ProcessStepStatus {
    PENDING, IN_PROGRESS, COMPLETED, BLOCKED, REVIEW
}

data class ProcessStep(
    val id: String,
    val name: String,
    val description: String,
    val roleRequired: String,
    val status: ProcessStepStatus = ProcessStepStatus.PENDING
)

data class ExecutiveProcess(
    val id: String,
    val name: String,
    val capabilityId: String,
    val steps: List<ProcessStep>,
    val dependencies: List<String>
)

class ExecutiveProcessLibrary {
    private val processes = mutableMapOf<String, ExecutiveProcess>()

    fun registerProcess(process: ExecutiveProcess) {
        processes[process.id] = process
    }
    
    fun getProcess(id: String): ExecutiveProcess? = processes[id]
}
