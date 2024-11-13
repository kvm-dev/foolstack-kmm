package ru.foolstack.ui.model

data class StudyItem(
    val studyId: Int,
    val studyName: String,
    val studySalePercent: Int,
    val studyLength: Int,
    val studyLengthType: Int,
    val studyAdditionalText: String,
    val studyCost: Int,
    val studyImageBase64: String,
    val studyTags: List<String>,
    val studyRefLink: String,
    val studyOwner: String,
)