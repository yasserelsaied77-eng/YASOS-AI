package com.example.core.graph.model

data class ExecutiveGraphContext(
    val root: ExecutiveNode,
    val parents: List<ExecutiveNode>,
    val children: List<ExecutiveNode>,
    val related: List<ExecutiveNode>,
    val relations: List<ExecutiveRelation>
)
