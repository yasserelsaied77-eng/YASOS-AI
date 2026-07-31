package com.example.domain.module

import com.example.domain.model.DomainCode

object DomainRegistryManager {
    private val modules = mutableMapOf<DomainCode, IDomainModule>()

    init {
        registerDefaultModules()
    }

    private fun registerDefaultModules() {
        register(PharmacyDomainModule())
        register(RealEstateDomainModule())
    }

    fun register(module: IDomainModule) {
        modules[module.code] = module
    }

    fun getModule(code: DomainCode): IDomainModule? {
        return modules[code]
    }

    fun getAllCodes(): List<DomainCode> {
        return modules.keys.toList()
    }

    fun getAllModules(): List<IDomainModule> {
        return modules.values.toList()
    }
}
