package ru.foolstack.study.api.model

data class StudiesDomain(
    val studies: List<StudyDomain>,
    val prText: String,
    val errorMsg: String
)

data class StudyDomain(
    val studyId: Int,
    val studyName: String,
    val studyCost: Int,
    val studyImageBase64: String,
    val studyRefLink: String,
    val studySalePercent: Int,
    val studyLength: Int,
    val studyLengthType: Int,
    val professions: List<StudyProfessionDomain>,
    val studyOwner: String,
    val studyAdditionalText: String
)

data class StudyProfessionDomain(
    val professionId: Int,
    val professionName: String
)