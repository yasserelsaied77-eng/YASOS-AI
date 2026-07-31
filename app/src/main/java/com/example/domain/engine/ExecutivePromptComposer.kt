package com.example.domain.engine

import com.example.domain.model.DomainCode
import com.example.domain.model.InjectedDnaContext
import com.example.domain.model.LifeInboxRecord

data class PromptComposerInput(
    val inboxRecord: LifeInboxRecord,
    val dnaContexts: List<InjectedDnaContext>,
    val availableDomains: List<DomainCode>
)

object ExecutivePromptComposer {
    fun composeSystemPrompt(input: PromptComposerInput): String {
        val formattedDnaRules = if (input.dnaContexts.isNotEmpty()) {
            input.dnaContexts.mapIndexed { index, dna ->
                "${index + 1}. [Rule ID: ${dna.ruleId}] [Category: ${dna.category.name}] [Domain: ${dna.domainCode.name}] (Priority: ${dna.priorityLevel}/10)\n   NEXUS STATEMENT: \"${dna.statement}\""
            }.joinToString("\n")
        } else {
            "لا توجد قواعد DNA خاصة مفعّلة لهذا المُدخل التحديدي (التزم بالمبادئ العامة للـ CORE)."
        }

        return """
            You are the Executive Thinking & Routing Engine for Dr. Yasser ElSaeedy's Personal Executive Operating System (YASOS).
            Your sole purpose is to process unstructured input, filter it through Dr. Yasser's Executive DNA, and issue structured routing instructions.

            --- EXECUTIVE DNA INJECTED RULES ---
            The following core rules were dynamically retrieved based on semantic relevance. You MUST enforce them:
            $formattedDnaRules

            --- AVAILABLE DOMAINS ---
            Available target domains: [${input.availableDomains.joinToString(", ") { it.name }}]

            --- OPERATIONAL MANDATES ---
            1. STRICT ALIGNMENT: Prioritize injected "constraint" and "decision_rule" categories over general recommendations.
            2. EXECUTIVE CONDENSATION: Write concise, highly professional summaries in Arabic.
            3. REASONING TRANSPARENCY: Explain explicitly WHY this input belongs to the chosen domain and destination table.
            4. CONFIDENCE SCORING: Evaluate your output precision (0.0 to 1.0). If input is ambiguous, assign score < 0.70.
        """.trimIndent()
    }

    fun composeUserMessage(inboxRecord: LifeInboxRecord): String {
        return """
            --- INCOMING RAW INPUT ---
            Source Type: ${inboxRecord.sourceType.name}
            Received At: ${inboxRecord.receivedAt}
            Raw Payload Metadata: ${inboxRecord.rawPayload}

            Content:
            ""\"
            ${inboxRecord.rawContent}
            ""\"

            Execute analysis and output the final JSON matching the RouterAnalysisResult Schema.
        """.trimIndent()
    }
}
