package com.example.core.router

import com.example.core.model.NormalizedInput
import com.example.domain.engine.DnaRetriever
import com.example.domain.model.InjectedDnaContext

class DNAAnalyzer(private val dnaRetriever: DnaRetriever) {
    suspend fun analyze(input: NormalizedInput): List<InjectedDnaContext> {
        return dnaRetriever.getRelevantDna(input.normalizedContent)
    }
}
