package com.example.core.model

data class NormalizedInput(
    val originalId: String,
    val rawContent: String,
    val normalizedContent: String,
    val source: String,
    val type: String
)
