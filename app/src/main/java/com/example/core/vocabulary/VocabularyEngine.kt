package com.example.core.vocabulary

import com.example.core.ontology.OntologyConcept

data class SemanticNode(
    val concept: OntologyConcept,
    val value: String
)

data class SemanticPath(
    val root: SemanticNode,
    val path: List<SemanticNode>
)

class VocabularyEngine {
    // A more advanced map would resolve terms to full semantic paths
    fun resolveSemanticPath(term: String): SemanticPath? {
        return when (term) {
            "صيدلية" -> SemanticPath(
                root = SemanticNode(OntologyConcept.BUSINESS_UNIT, "Pharmacy"),
                path = listOf(
                    SemanticNode(OntologyConcept.ASSET, "Healthcare"),
                    SemanticNode(OntologyConcept.ASSET, "Retail"),
                    SemanticNode(OntologyConcept.ASSET, "Branch"),
                    SemanticNode(OntologyConcept.PERSON, "Owner"),
                    SemanticNode(OntologyConcept.PERSON, "Employees"),
                    SemanticNode(OntologyConcept.RESOURCE, "Assets")
                )
            )
            "عمارة" -> SemanticPath(
                root = SemanticNode(OntologyConcept.REAL_ESTATE_ASSET, "Building"),
                path = emptyList()
            )
            "عمر" -> SemanticPath(
                root = SemanticNode(OntologyConcept.FAMILY_MEMBER, "Omar"),
                path = emptyList()
            )
            else -> null
        }
    }
}
