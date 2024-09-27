package ru.foolstack.interview.impl.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MaterialsResponse(
    @SerialName("materials") val materials: List<MaterialResponse> = listOf(),
    @SerialName("errorMsg") val errorMsg: String = ""
)

@Serializable
data class MaterialResponse(
    @SerialName("materialId") val materialId: Int = 0,
    @SerialName("materialName") val materialName: String = "",
    @SerialName("materialText") val materialText: String = "",
    @SerialName("materialPriority") val materialPriority: Int = 0,
    @SerialName("knowledgeAreas") val knowledgeAreas: List<Int> = listOf(),
    @SerialName("subProfessions") val subProfessions: List<Int> = listOf()
)