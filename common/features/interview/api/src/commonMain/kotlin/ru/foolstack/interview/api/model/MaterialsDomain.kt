package ru.foolstack.interview.api.model

data class MaterialsDomain(
    val materials: List<MaterialDomain>,
    val errorMsg: String
)

data class MaterialDomain(
    val materialId: Int,
    val materialName: String,
    val materialText: String,
    val materialPriority: Int,
    val knowledgeAreas: List<KnowledgeAreaDomain>,
    val subProfessions: List<SubProfessionDomain>
)

data class KnowledgeAreaDomain(
    val areaId: Int,
    val areaName: String
)

data class SubProfessionDomain(
    val professionId: Int,
    val professionName: String
)