package com.example.core.governance

import com.example.core.ontology.OntologyConcept

enum class LifecycleStatus {
    DRAFT, ACTIVE, REVIEW_REQUIRED, DEPRECATED, ARCHIVED
}

data class KnowledgeAsset(
    val id: String,
    val concept: OntologyConcept,
    val title: String,
    val content: String,
    val author: String,
    val owner: String,
    val version: Int,
    val status: LifecycleStatus,
    val confidence: Double,
    val evidenceIds: List<String>,
    val appliesTo: List<String>,
    val createdAt: Long,
    val updatedAt: Long,
    val expiresAt: Long?,
    val nextReviewAt: Long?
)
