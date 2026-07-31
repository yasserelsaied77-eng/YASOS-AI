package com.example.core.ontology

enum class OntologyConcept {
    GOAL, MISSION, VISION, PRINCIPLE, CONSTRAINT, RESOURCE, ASSET, OPPORTUNITY,
    THREAT, RISK, DECISION, PROJECT, INITIATIVE, TASK, COMMITMENT, RELATIONSHIP,
    KNOWLEDGE, EVIDENCE, LESSON, POLICY, CAPABILITY,
    BUSINESS_UNIT, REAL_ESTATE_ASSET, FAMILY_MEMBER, PERSON
}

enum class OntologyRelation {
    CONTAINS, CREATES, AFFECTS, BLOCKS, SUPPORTS, MITIGATES, DEPENDS_ON, ALIGNS_WITH, EXECUTES
}

object OntologyRegistry {
    val validRelations = mapOf(
        OntologyConcept.GOAL to listOf(OntologyRelation.CONTAINS to OntologyConcept.INITIATIVE),
        OntologyConcept.CAPABILITY to listOf(OntologyRelation.EXECUTES to OntologyConcept.GOAL, OntologyRelation.CONTAINS to OntologyConcept.PROJECT),
        OntologyConcept.INITIATIVE to listOf(OntologyRelation.CONTAINS to OntologyConcept.PROJECT),
        OntologyConcept.PROJECT to listOf(OntologyRelation.CREATES to OntologyConcept.TASK),
        OntologyConcept.DECISION to listOf(OntologyRelation.AFFECTS to OntologyConcept.PROJECT),
        OntologyConcept.RISK to listOf(OntologyRelation.BLOCKS to OntologyConcept.GOAL),
        OntologyConcept.EVIDENCE to listOf(OntologyRelation.SUPPORTS to OntologyConcept.DECISION)
    )
}
