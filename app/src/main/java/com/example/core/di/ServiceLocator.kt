package com.example.core.di

import android.content.Context
import com.example.core.engine.MemoryEngine
import com.example.core.graph.engine.KnowledgeGraphEngine
import com.example.core.router.*
import com.example.core.llm.*
import com.example.data.repository.*
import com.example.domain.engine.DnaRetriever
import com.example.domain.model.InjectedDnaContext

class MockDnaRetriever : DnaRetriever {
    override suspend fun getRelevantDna(content: String): List<InjectedDnaContext> {
        return emptyList()
    }
}

object ServiceLocator {
    fun provideExecutiveRouter(): ExecutiveRouter {
        return ExecutiveRouter(
            inputNormalizer = InputNormalizer(),
            dnaAnalyzer = DNAAnalyzer(MockDnaRetriever()),
            intentClassifier = IntentClassifier(),
            reasoningEngine = ReasoningEngine(),
            routingEngine = RoutingEngine(),
            contextBuilder = ContextBuilder()
        )
    }

    fun provideLlmProvider(): LlmProvider {
        return GeminiProvider()
    }

    fun provideGraphRepository(context: Context): GraphRepository {
        return GraphRepository(DatabaseProvider.getDatabase(context).graphDao())
    }

    fun provideKnowledgeGraphEngine(context: Context): KnowledgeGraphEngine {
        return KnowledgeGraphEngine(provideGraphRepository(context))
    }

    fun provideMemoryEngine(context: Context): MemoryEngine {
        val database = DatabaseProvider.getDatabase(context)
        return MemoryEngine(
            decisionRepository = DecisionRepository(database.decisionDao()),
            knowledgeRepository = KnowledgeRepository(database.knowledgeDao()),
            lessonRepository = LessonRepository(database.lessonDao()),
            strategyRepository = StrategyRepository(database.strategyDao()),
            projectRepository = ProjectRepository(database.projectDao()),
            taskRepository = TaskRepository(database.taskDao()),
            graphEngine = provideKnowledgeGraphEngine(context)
        )
    }

    fun provideInboxRepository(context: Context): InboxRepository {
        return InboxRepository(DatabaseProvider.getDatabase(context).lifeInboxDao())
    }
    
    fun provideDecisionRepository(context: Context): DecisionRepository {
        return DecisionRepository(DatabaseProvider.getDatabase(context).decisionDao())
    }

    fun provideKnowledgeRepository(context: Context): KnowledgeRepository {
        return KnowledgeRepository(DatabaseProvider.getDatabase(context).knowledgeDao())
    }

    fun provideLessonRepository(context: Context): LessonRepository {
        return LessonRepository(DatabaseProvider.getDatabase(context).lessonDao())
    }

    fun provideStrategyRepository(context: Context): StrategyRepository {
        return StrategyRepository(DatabaseProvider.getDatabase(context).strategyDao())
    }

    fun provideProjectRepository(context: Context): ProjectRepository {
        return ProjectRepository(DatabaseProvider.getDatabase(context).projectDao())
    }

    fun provideTaskRepository(context: Context): TaskRepository {
        return TaskRepository(DatabaseProvider.getDatabase(context).taskDao())
    }
    
    fun provideTimelineEngine(context: Context): com.example.core.engine.TimelineEngine {
        return com.example.core.engine.TimelineEngine(
            graphEngine = provideKnowledgeGraphEngine(context)
        )
    }

    fun provideInsightRepository(context: Context): InsightRepository {
        return InsightRepository(DatabaseProvider.getDatabase(context).insightDao())
    }

    fun provideInferencePipeline(): com.example.core.intelligence.pipeline.InferencePipeline {
        return com.example.core.intelligence.pipeline.InferencePipeline(
            stages = listOf(
                com.example.core.intelligence.pipeline.stages.RelationResolverStage(),
                com.example.core.intelligence.pipeline.stages.DuplicateFinderStage(),
                com.example.core.intelligence.pipeline.stages.ConflictDetectorStage(),
                com.example.core.intelligence.pipeline.stages.PatternAnalyzerStage(),
                com.example.core.intelligence.pipeline.stages.OpportunityFinderStage(),
                com.example.core.intelligence.pipeline.stages.DecisionAdvisorStage()
            )
        )
    }

    fun provideCapabilityRegistry(): com.example.core.capability.CapabilityRegistry {
        return com.example.core.capability.CapabilityRegistry()
    }

    fun provideExecutiveProcessLibrary(): com.example.core.process.ExecutiveProcessLibrary {
        return com.example.core.process.ExecutiveProcessLibrary()
    }

    fun provideExecutiveSimulationEngine(): com.example.core.decision.ExecutiveSimulationEngine {
        return com.example.core.decision.ExecutiveSimulationEngine()
    }

    fun provideOperatingModelEngine(): com.example.core.operatingmodel.OperatingModelEngine {
        return com.example.core.operatingmodel.OperatingModelEngine()
    }

    fun provideExecutiveEvidenceStore(): com.example.core.evidence.ExecutiveEvidenceStore {
        return com.example.core.evidence.ExecutiveEvidenceStore()
    }

    fun provideExecutiveStateManager(): com.example.core.state.ExecutiveStateManager {
        return com.example.core.state.ExecutiveStateManager()
    }

    fun provideCapabilityWorkspaceManager(): com.example.core.capability.runtime.CapabilityWorkspaceManager {
        return com.example.core.capability.runtime.CapabilityWorkspaceManager()
    }

    fun provideExecutiveGovernanceEngine(): com.example.core.governance.ExecutiveGovernanceEngine {
        return com.example.core.governance.ExecutiveGovernanceEngine()
    }

    fun provideVocabularyEngine(): com.example.core.vocabulary.VocabularyEngine {
        return com.example.core.vocabulary.VocabularyEngine()
    }

    fun provideExecutiveLearningLoop(): com.example.core.learning.ExecutiveLearningLoop {
        return com.example.core.learning.ExecutiveLearningLoop()
    }

    fun provideExecutiveMetricsEngine(): com.example.core.metrics.ExecutiveMetricsEngine {
        return com.example.core.metrics.ExecutiveMetricsEngine()
    }

    fun provideExecutiveKernel(context: Context): com.example.core.kernel.ExecutiveKernel {
        return com.example.core.kernel.ExecutiveKernel(
            router = provideExecutiveRouter(),
            llmProvider = provideLlmProvider(),
            contextBuilder = com.example.core.intelligence.analyzers.ContextBuilder(provideKnowledgeGraphEngine(context)),
            inferencePipeline = provideInferencePipeline(),
            policyEngine = com.example.core.policy.ExecutivePolicyEngine(),
            memoryEngine = provideMemoryEngine(context),
            graphBuilder = com.example.core.graph.builder.GraphBuilder(provideKnowledgeGraphEngine(context)),
            contextRepo = provideExecutiveContextRepository(context),
            insightRepo = provideInsightRepository(context)
        )
    }

    fun provideExecutiveContextRepository(context: Context): ExecutiveContextRepository {
        return ExecutiveContextRepository(DatabaseProvider.getDatabase(context).executiveContextDao())
    }
}

