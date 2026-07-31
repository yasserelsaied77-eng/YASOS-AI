package com.example.core.router

import com.example.core.model.ExecutiveContext
import com.example.domain.model.LifeInboxRecord

class ExecutiveRouter(
    private val inputNormalizer: InputNormalizer,
    private val dnaAnalyzer: DNAAnalyzer,
    private val intentClassifier: IntentClassifier,
    private val reasoningEngine: ReasoningEngine,
    private val routingEngine: RoutingEngine,
    private val contextBuilder: ContextBuilder
) {
    suspend fun route(inboxRecord: LifeInboxRecord): ExecutiveContext {
        // Layer 1
        val normalizedInput = inputNormalizer.normalize(inboxRecord)
        
        // Layer 2
        val dnaContext = dnaAnalyzer.analyze(normalizedInput)
        
        // Layer 3
        val intents = intentClassifier.classify(normalizedInput, dnaContext)
        
        // Layer 4
        val reasoning = reasoningEngine.buildReasoning(normalizedInput, dnaContext, intents)
        
        // Layer 5
        val routing = routingEngine.decideRoutes(reasoning)
        
        // Final Output
        return contextBuilder.build(
            input = normalizedInput,
            dnaContext = dnaContext,
            intents = intents,
            reasoning = reasoning,
            routing = routing
        )
    }
}
