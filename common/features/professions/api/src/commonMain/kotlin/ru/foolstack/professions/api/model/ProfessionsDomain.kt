package ru.foolstack.professions.api.model



data class ProfessionsDomain(
    val errorMsg: String,
    val professions: List<ProfessionDomain>,
    val success: Boolean
)

data class ProfessionDomain(
    val professionId: Int,
    val professionImageBase64: String,
    val professionName: String,
    val professionParent: Int,
    val professionPriority: Int,
    val professionType: Int,
    var subProfessions: List<ProfessionDomain> = listOf()
)