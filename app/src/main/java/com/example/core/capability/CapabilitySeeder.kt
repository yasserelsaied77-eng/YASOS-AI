package com.example.core.capability

import com.example.core.ontology.OntologyConcept
import com.example.core.process.ExecutiveProcess
import com.example.core.process.ProcessStep
import com.example.core.process.ExecutiveProcessLibrary
import com.example.core.decision.DecisionModel
import com.example.core.decision.DecisionCriteria

object CapabilitySeeder {
    fun seed(
        registry: CapabilityRegistry,
        processLibrary: ExecutiveProcessLibrary
    ) {
        // 1. Strategic Planning
        registry.register(
            ExecutiveCapability(
                id = "cap_strategic_planning",
                name = "Strategic Planning",
                description = "Define long term goals and align resources to achieve them",
                metadata = CapabilityMetadata(
                    owner = "Executive Board",
                    inputs = listOf(OntologyConcept.VISION, OntologyConcept.ASSET, OntologyConcept.OPPORTUNITY),
                    outputs = listOf(OntologyConcept.GOAL, OntologyConcept.INITIATIVE),
                    metrics = listOf("Goal Alignment", "Execution Speed"),
                    dependencies = listOf("cap_financial_planning")
                ),
                policies = listOf("pol_resource_allocation", "pol_risk_tolerance"),
                playbooks = listOf("pb_annual_planning", "pb_quarterly_review"),
                decisionModels = listOf("dm_strategic_investment"),
                knowledgeAssets = listOf("ka_market_trends", "ka_competitor_analysis"),
                aiSkills = listOf("Trend Analysis", "Scenario Planning"),
                automations = listOf("Quarterly Report Generation")
            )
        )

        // 2. Real Estate Management
        registry.register(
            ExecutiveCapability(
                id = "cap_real_estate_management",
                name = "Real Estate Management",
                description = "Acquire, maintain, and optimize real estate assets",
                metadata = CapabilityMetadata(
                    owner = "Real Estate Director",
                    inputs = listOf(OntologyConcept.REAL_ESTATE_ASSET, OntologyConcept.OPPORTUNITY),
                    outputs = listOf(OntologyConcept.PROJECT, OntologyConcept.TASK),
                    metrics = listOf("ROI", "Occupancy Rate", "Maintenance Cost"),
                    dependencies = listOf("cap_contract_management")
                ),
                policies = listOf("pol_property_acquisition", "pol_tenant_screening"),
                playbooks = listOf("pb_property_purchase", "pb_maintenance_request"),
                decisionModels = listOf("dm_property_purchase"),
                knowledgeAssets = listOf("ka_local_zoning_laws", "ka_contractor_directory"),
                aiSkills = listOf("Valuation Estimation", "Contract Analysis"),
                automations = listOf("Rent Collection Reminders")
            )
        )
        
        processLibrary.registerProcess(
            ExecutiveProcess(
                id = "proc_property_purchase",
                name = "Property Purchase Process",
                capabilityId = "cap_real_estate_management",
                steps = listOf(
                    ProcessStep("s1", "Evaluation", "Assess the property value and condition", "Analyst"),
                    ProcessStep("s2", "Approval", "Obtain executive approval", "Executive"),
                    ProcessStep("s3", "Negotiation", "Negotiate terms with seller", "Negotiator"),
                    ProcessStep("s4", "Signing", "Sign final contracts", "Legal"),
                    ProcessStep("s5", "Execution", "Transfer funds and ownership", "Finance")
                ),
                dependencies = emptyList()
            )
        )

        // 3. Pharmacy Operations
        registry.register(
            ExecutiveCapability(
                id = "cap_pharmacy_operations",
                name = "Pharmacy Operations",
                description = "Manage pharmacy inventory, staff, and customer service",
                metadata = CapabilityMetadata(
                    owner = "Pharmacy Manager",
                    inputs = listOf(OntologyConcept.BUSINESS_UNIT, OntologyConcept.RESOURCE),
                    outputs = listOf(OntologyConcept.PROJECT, OntologyConcept.TASK),
                    metrics = listOf("Inventory Turnover", "Customer Satisfaction", "Profit Margin"),
                    dependencies = listOf("cap_supply_chain")
                ),
                policies = listOf("pol_inventory_control", "pol_customer_service"),
                playbooks = listOf("pb_inventory_audit", "pb_staff_scheduling"),
                decisionModels = listOf("dm_supplier_selection"),
                knowledgeAssets = listOf("ka_drug_interactions", "ka_supplier_catalog"),
                aiSkills = listOf("Demand Forecasting", "Stock Optimization"),
                automations = listOf("Low Stock Alerts")
            )
        )
    }
}
