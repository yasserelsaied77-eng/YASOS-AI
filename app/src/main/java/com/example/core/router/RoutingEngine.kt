package com.example.core.router

import com.example.core.model.RoutingDecision

class RoutingEngine {
    fun decideRoutes(reasoning: ReasoningResult): RoutingDecision {
        val engines = mutableListOf<String>()
        if (reasoning.requiresDecision) engines.add("ThinkingEngine")
        if (reasoning.requiresMemory) engines.add("MemoryEngine")
        if (reasoning.requiresKnowledge) engines.add("KnowledgeEngine")
        if (reasoning.requiresExecution) engines.add("ExecutionEngine")
        
        return RoutingDecision(
            targetEngines = engines,
            priority = reasoning.priority,
            requiresHumanReview = reasoning.confidence < 0.8,
            reasoning = "Routed based on reasoning results."
        )
    }
}
