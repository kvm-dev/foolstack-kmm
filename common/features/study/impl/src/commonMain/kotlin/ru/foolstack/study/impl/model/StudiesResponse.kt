package ru.foolstack.study.impl.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StudiesResponse(
    @SerialName("studies") val studies: List<StudyResponse> = listOf(),
    @SerialName("prText") val prText: String = "",
    @SerialName("errorMsg") val errorMsg: String
)

@Serializable
data class StudyResponse(
    @SerialName("studyId") val studyId: Int = 0,
    @SerialName("studyName") val studyName: String = "",
    @SerialName("studyCost") val studyCost: Int = 0,
    @SerialName("studyImage") val studyImage: String = "",
    @SerialName("studyRefLink") val studyRefLink: String = "",
    @SerialName("studySalePercent") val studySalePercent: Int = 0,
    @SerialName("studyLength") val studyLength: Int = 0,
    @SerialName("studyLengthType") val studyLengthType: Int = 0,
    @SerialName("professions") val professions: List<StudyProfessionResponse> = listOf(),
    @SerialName("studyOwner") val studyOwner: String = "",
    @SerialName("studyAdditionalText") val studyAdditionalText: String = ""
)
@Serializable

data class StudyProfessionResponse(
    @SerialName("professionId") val professionId: Int = 0,
    @SerialName("professionName") val professionName: String = ""
)