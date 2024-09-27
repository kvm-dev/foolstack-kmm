package ru.foolstack.profile.impl.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponse(
    @SerialName("userId") val userId: Int = 0,
    @SerialName("userName") val userName: String = "",
    @SerialName("userEmail") val userEmail: String = "",
    @SerialName("userPhotoUrl") val userPhotoUrl: String = "",
    @SerialName("userStatus") val userStatus: Int = 1,
    @SerialName("userType") val userType: String = "",
    @SerialName("userAchievements") val userAchievements: List<AchievementResponse> = listOf(),
    @SerialName("userPurchasedProfessions") val userPurchasedProfessions: List<Int> = listOf(),
    @SerialName("errorMsg") val errorMsg: String
)

@Serializable
data class AchievementResponse(
    @SerialName("achievementId") val achievementId: Int,
    @SerialName("achievementName") val achievementName: String,
    @SerialName("achievementDescription") val achievementDescription: String,
    @SerialName("achievementLevel") val achievementLevel: Int
)