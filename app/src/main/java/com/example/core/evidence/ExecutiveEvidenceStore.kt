package com.example.core.evidence

data class Evidence(
    val id: String,
    val description: String,
    val source: String,
    val confidence: Double,
    val date: Long,
    val owner: String,
    val references: List<String>,
    val isValid: Boolean,
    val reviewDate: Long?
)

class ExecutiveEvidenceStore {
    private val evidenceStore = mutableMapOf<String, Evidence>()
    
    fun recordEvidence(evidence: Evidence) {
        evidenceStore[evidence.id] = evidence
    }
    
    fun getEvidence(id: String): Evidence? = evidenceStore[id]
    
    fun getAllValidEvidence(): List<Evidence> = evidenceStore.values.filter { it.isValid }
}
