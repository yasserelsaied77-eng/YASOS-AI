package com.example.core.governance

class ExecutiveGovernanceEngine {
    fun auditAsset(asset: KnowledgeAsset): KnowledgeAsset {
        val currentTime = System.currentTimeMillis()
        
        var newStatus = asset.status
        if (asset.expiresAt != null && currentTime > asset.expiresAt) {
            newStatus = LifecycleStatus.DEPRECATED
        } else if (asset.nextReviewAt != null && currentTime > asset.nextReviewAt) {
            newStatus = LifecycleStatus.REVIEW_REQUIRED
        }

        return asset.copy(status = newStatus)
    }
    
    fun createNewVersion(asset: KnowledgeAsset, newContent: String, newEvidence: List<String>): KnowledgeAsset {
        return asset.copy(
            version = asset.version + 1,
            content = newContent,
            evidenceIds = (asset.evidenceIds + newEvidence).distinct(),
            updatedAt = System.currentTimeMillis(),
            status = LifecycleStatus.ACTIVE
        )
    }
}
