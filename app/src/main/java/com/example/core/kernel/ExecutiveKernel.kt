package com.example.core.kernel

import com.example.core.router.ExecutiveRouter
import com.example.core.intelligence.analyzers.ContextBuilder
import com.example.core.intelligence.pipeline.InferencePipeline
import com.example.core.intelligence.pipeline.InferenceContext
import com.example.core.policy.ExecutivePolicyEngine
import com.example.core.engine.MemoryEngine
import com.example.core.graph.builder.GraphBuilder
import com.example.data.database.entities.LifeInboxEntity
import com.example.data.repository.ExecutiveContextRepository
import com.example.data.repository.InsightRepository
import com.example.domain.model.LifeInboxRecord
import com.example.core.llm.LlmProvider
import com.example.core.model.ExecutiveContext
import com.example.data.database.entities.ExecutiveContextEntity
import com.example.data.database.entities.IntelligenceInsightEntity

class ExecutiveKernel(
    private val router: ExecutiveRouter,
    private val llmProvider: LlmProvider,
    private val contextBuilder: ContextBuilder,
    private val inferencePipeline: InferencePipeline,
    private val policyEngine: ExecutivePolicyEngine,
    private val memoryEngine: MemoryEngine,
    private val graphBuilder: GraphBuilder,
    private val contextRepo: ExecutiveContextRepository,
    private val insightRepo: InsightRepository
) {
    suspend fun processInput(inboxRecord: LifeInboxRecord, rawText: String): ExecutiveContext {
        // 1. Router -> Base Context
        val baseContext = router.route(inboxRecord)
        
        // 2. LlmProvider -> AI Context
        val aiContext = llmProvider.analyze(
            inboxRecord, 
            baseContext
        )
        
        // Save Context
        contextRepo.insert(
            ExecutiveContextEntity(
                id = aiContext.id,
                rawInput = aiContext.rawInput,
                normalizedInput = aiContext.normalizedInput,
                inputSource = aiContext.inputSource,
                inputType = aiContext.inputType,
                intentJson = aiContext.intent.joinToString(","),
                domainJson = aiContext.domain.joinToString(","),
                priority = aiContext.priority,
                urgency = aiContext.urgency,
                importance = aiContext.importance,
                risk = aiContext.risk,
                confidence = aiContext.confidence,
                requiresDecision = aiContext.requiresDecision,
                requiresExecution = aiContext.requiresExecution,
                requiresKnowledge = aiContext.requiresKnowledge,
                requiresMemory = aiContext.requiresMemory,
                recommendedEnginesJson = aiContext.recommendedEngines.joinToString(","),
                strategicAlignment = aiContext.strategicAlignment,
                summary = aiContext.summary,
                createdAt = aiContext.createdAt.time
            )
        )

        // Graph Builder: Base Nodes
        graphBuilder.buildInboxNode(inboxRecord, rawText)
        graphBuilder.buildContextNode(aiContext, inboxRecord.id)

        // 3. Context Builder
        val graphContext = contextBuilder.buildSyntheticContext(aiContext.summary)

        // 4. Inference Pipeline
        val initialInferenceContext = InferenceContext(graphContext = graphContext)
        val inferenceResult = inferencePipeline.run(initialInferenceContext)

        // 5. Policy Engine
        val policyResult = policyEngine.applyPolicy(inferenceResult)

        // 6. Memory Engine
        memoryEngine.index(aiContext)

        // 7. Graph Builder: Commit Inference
        graphBuilder.commitInference(policyResult)
        
        // Save Insights
        policyResult.insights.forEach { insight ->
            val insightEntity = IntelligenceInsightEntity(
                id = insight.id,
                type = insight.type.name,
                title = insight.title,
                description = insight.description,
                confidence = insight.confidence,
                timestamp = insight.timestamp
            )
            insightRepo.insertInsight(insightEntity)
        }
        
        return aiContext
    }
}
