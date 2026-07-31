package com.example.core.engine

import com.example.core.graph.engine.KnowledgeGraphEngine
import com.example.core.graph.model.NodeType
import com.example.core.graph.model.RelationType
import com.example.core.model.ExecutiveContext
import com.example.data.database.entities.*
import com.example.data.repository.*
import java.util.UUID

class MemoryEngine(
    private val decisionRepository: DecisionRepository,
    private val knowledgeRepository: KnowledgeRepository,
    private val lessonRepository: LessonRepository,
    private val strategyRepository: StrategyRepository,
    private val projectRepository: ProjectRepository,
    private val taskRepository: TaskRepository,
    private val graphEngine: KnowledgeGraphEngine
) {
    suspend fun index(context: ExecutiveContext) {
        val intents = context.intent.map { it.uppercase() }
        val domain = context.domain.firstOrNull() ?: "CORE"

        // Based on the intent of the Executive Context, route to the proper memory stores
        if (intents.contains("DECISION") || context.requiresDecision) {
            val id = UUID.randomUUID().toString()
            decisionRepository.insert(
                DecisionEntity(
                    id = id,
                    title = "Decision based on ${context.summary.take(20)}...",
                    contextId = context.id,
                    description = context.summary,
                    domain = domain,
                    createdAt = context.createdAt.time
                )
            )
            graphEngine.addNode(id, NodeType.DECISION, "Decision", context.summary, context.createdAt.time)
            graphEngine.addRelation(id, context.id, RelationType.CREATED_FROM)
        }

        if (intents.contains("KNOWLEDGE") || context.requiresKnowledge) {
            val id = UUID.randomUUID().toString()
            knowledgeRepository.insert(
                KnowledgeEntity(
                    id = id,
                    title = "Knowledge from ${context.inputSource}",
                    content = context.normalizedInput,
                    tagsJson = "[]", // TODO: serialize tags
                    domain = domain,
                    createdAt = context.createdAt.time
                )
            )
            graphEngine.addNode(id, NodeType.KNOWLEDGE, "Knowledge", context.normalizedInput, context.createdAt.time)
            graphEngine.addRelation(id, context.id, RelationType.CREATED_FROM)
        }

        if (intents.contains("LESSON")) {
            val id = UUID.randomUUID().toString()
            lessonRepository.insert(
                LessonEntity(
                    id = id,
                    title = "Lesson learned",
                    learning = context.summary,
                    sourceContextId = context.id,
                    domain = domain,
                    createdAt = context.createdAt.time
                )
            )
            graphEngine.addNode(id, NodeType.LESSON, "Lesson", context.summary, context.createdAt.time)
            graphEngine.addRelation(id, context.id, RelationType.CREATED_FROM)
        }

        if (intents.contains("STRATEGY")) {
            val id = UUID.randomUUID().toString()
            strategyRepository.insert(
                StrategyEntity(
                    id = id,
                    title = "Strategic update",
                    description = context.summary,
                    domain = domain,
                    targetDate = null,
                    createdAt = context.createdAt.time
                )
            )
            graphEngine.addNode(id, NodeType.STRATEGY, "Strategy", context.summary, context.createdAt.time)
            graphEngine.addRelation(id, context.id, RelationType.CREATED_FROM)
        }

        if (intents.contains("PROJECT")) {
            val id = UUID.randomUUID().toString()
            projectRepository.insert(
                ProjectEntity(
                    id = id,
                    name = "Project: ${context.summary.take(20)}",
                    description = context.normalizedInput,
                    domain = domain,
                    status = "PENDING",
                    createdAt = context.createdAt.time
                )
            )
            graphEngine.addNode(id, NodeType.PROJECT, "Project", context.summary, context.createdAt.time)
            graphEngine.addRelation(id, context.id, RelationType.CREATED_FROM)
        }

        if (intents.contains("TASK") || context.requiresExecution) {
            val id = UUID.randomUUID().toString()
            taskRepository.insert(
                TaskEntity(
                    id = id,
                    projectId = null,
                    title = "Task: ${context.summary.take(20)}",
                    description = context.summary,
                    status = "TODO",
                    domain = domain,
                    dueDate = null,
                    createdAt = context.createdAt.time
                )
            )
            graphEngine.addNode(id, NodeType.TASK, "Task", context.summary, context.createdAt.time)
            graphEngine.addRelation(id, context.id, RelationType.CREATED_FROM)
        }
    }
}
