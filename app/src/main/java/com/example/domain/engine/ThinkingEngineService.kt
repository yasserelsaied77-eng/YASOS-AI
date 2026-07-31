package com.example.domain.engine

import com.example.domain.model.DomainCode
import com.example.domain.model.InjectedDnaContext
import com.example.domain.model.LifeInboxRecord
import com.example.domain.model.RouterAnalysisResult

interface DnaRetriever {
    suspend fun getRelevantDna(content: String): List<InjectedDnaContext>
}

interface LlmProvider {
    suspend fun <T> generateStructuredOutput(systemPrompt: String, userPrompt: String, clazz: Class<T>): T
}

class ThinkingEngineService(
    private val dnaRetriever: DnaRetriever,
    private val llmProvider: LlmProvider
) {
    suspend fun processInboxItem(inboxRecord: LifeInboxRecord): RouterAnalysisResult {
        // 1. استرجاع قواعد الـ DNA ذات الصلة دلالياً
        val injectedDna = dnaRetriever.getRelevantDna(inboxRecord.rawContent)

        // 2. تركيب الـ System Prompt و User Message
        val systemPrompt = ExecutivePromptComposer.composeSystemPrompt(
            PromptComposerInput(
                inboxRecord = inboxRecord,
                dnaContexts = injectedDna,
                availableDomains = DomainCode.values().toList()
            )
        )

        val userPrompt = ExecutivePromptComposer.composeUserMessage(inboxRecord)

        // 3. استدعاء النموذج اللغوي لإصدار التفكير المفهوم والنتيجة الهيكلية
        val analysisResult = llmProvider.generateStructuredOutput(
            systemPrompt,
            userPrompt,
            RouterAnalysisResult::class.java
        )

        // 4. إرفاق قواعد الـ DNA التي استُند إليها في القرار النهائي لأغراض التدقيق
        return analysisResult.copy(appliedDnaRules = injectedDna)
    }
}
