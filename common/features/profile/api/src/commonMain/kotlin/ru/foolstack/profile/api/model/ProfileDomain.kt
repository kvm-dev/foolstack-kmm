package ru.foolstack.profile.api.model


data class ProfileDomain(
    val userId: Int,
    val userName: String,
    val userEmail: String,
    val userPhotoUrl: String,
    val userPhotoBase64: String,
    val userStatus: Int,
    val userType: String,
    val userAchievements: List<AchievementDomain>,
    val userPurchasedProfessions: List<Int>,
    val errorMsg: String
)

data class AchievementDomain(
    val achievementId: Int,
    val achievementName: String,
    val achievementDescription: String,
    val achievementLevel: Int
)