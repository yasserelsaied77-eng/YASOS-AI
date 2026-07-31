package com.example.core.router

import com.example.core.model.ExecutiveContext
import com.example.core.model.NormalizedInput
import com.example.core.model.RoutingDecision
import com.example.domain.model.InjectedDnaContext
import java.util.Date
import java.util.UUID

class ContextBuilder {
    fun build(
        input: NormalizedInput,
        dnaContext: List<InjectedDnaContext>,
        intents: List<String>,
        reasoning: ReasoningResult,
        routing: RoutingDecision
    ): ExecutiveContext {
        return ExecutiveContext(
            id = UUID.randomUUID().toString(),
            rawInput = input.rawContent,
            normalizedInput = input.normalizedContent,
            inputSource = input.source,
            inputType = input.type,
            intent = intents,
            domain = reasoning.domain,
            priority = routing.priority,
            urgency = reasoning.urgency,
            importance = reasoning.importance,
            risk = reasoning.risk,
            confidence = reasoning.confidence,
            requiresDecision = reasoning.requiresDecision,
            requiresExecution = reasoning.requiresExecution,
            requiresKnowledge = reasoning.requiresKnowledge,
            requiresMemory = reasoning.requiresMemory,
            recommendedEngines = routing.targetEngines,
            strategicAlignment = reasoning.strategicAlignment,
            summary = reasoning.summary,
            createdAt = Date()
        )
    }
}
