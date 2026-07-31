package com.example.core.model

data class RoutingDecision(
    val targetEngines: List<String>,
    val priority: String,
    val requiresHumanReview: Boolean,
    val reasoning: String
)
