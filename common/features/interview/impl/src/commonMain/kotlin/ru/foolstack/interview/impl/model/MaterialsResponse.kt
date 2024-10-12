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
    @SerialName("knowledgeAreas") val knowledgeAreas: List<KnowledgeAreaResponse> = listOf(),
    @SerialName("subProfessions") val subProfessions: List<SubProfessionResponse> = listOf()
)
@Serializable
data class KnowledgeAreaResponse(
    @SerialName("areaId") val areaId: Int = 0,
    @SerialName("areaName") val areaName: String = ""
)
@Serializable
data class SubProfessionResponse(
    @SerialName("professionId") val professionId: Int,
    @SerialName("professionName") val professionName: String
)



