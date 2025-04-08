package ru.foolstack.professions.impl.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName


@Serializable
data class ProfessionsResponse(
    @SerialName("errorMsg")
    val errorMsg: String = "",
    @SerialName("professions")
    val professions: List<ProfessionResponse> = listOf(),
    @SerialName("success")
    val success: Boolean = false
)

@Serializable
data class ProfessionResponse(
    @SerialName("professionId")
    val professionId: Int = 0,
    @SerialName("professionImage")
    val professionImage: String = "",
    @SerialName("professionName")
    val professionName: String = "",
    @SerialName("professionParent")
    val professionParent: Int = 0,
    @SerialName("professionPriority")
    val professionPriority: Int = 0,
    @SerialName("professionType")
    val professionType: Int = 0,
    @SerialName("subProfessions")
    val subProfessions: List<ProfessionResponse> = listOf()
)