package com.example.domain.model

enum class DomainCode {
    CORE,
    PHARMACY,
    REAL_ESTATE,
    POLITICAL,
    BUSINESS,
    INVESTMENT,
    FAMILY,
    LEGAL,
    CUSTOM // للسماح بنطاقات مخصصة مستقبلاً
}

data class DomainConfig(
    val preferredLLM: String? = null,
    val autoRouteConfidenceThreshold: Double? = null,
    val requireHumanValidation: Boolean? = null,
    val customSettings: Map<String, Any>? = null
)

data class DomainRegistryRecord(
    val code: DomainCode,
    val customCode: String? = null,
    val nameAr: String,
    val nameEn: String,
    val description: String?,
    val isActive: Boolean,
    val config: DomainConfig,
    val createdAt: String,
    val updatedAt: String
)
