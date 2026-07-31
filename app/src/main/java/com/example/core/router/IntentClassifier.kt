package com.example.core.router

import com.example.core.model.NormalizedInput
import com.example.domain.model.InjectedDnaContext

class IntentClassifier {
    fun classify(input: NormalizedInput, dnaContext: List<InjectedDnaContext>): List<String> {
        // Mock classification
        return listOf("Decision", "Strategy")
    }
}
