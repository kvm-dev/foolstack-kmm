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
    val knowledgeAreas: List<Int>,
    val subProfessions: List<Int>
)