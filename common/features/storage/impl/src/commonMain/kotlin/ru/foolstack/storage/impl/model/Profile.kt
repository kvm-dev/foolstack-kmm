package ru.foolstack.storage.impl.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    @SerialName("userId") val userId: Int,
    @SerialName("userName") val userName: String,
    @SerialName("userType") val userType: String,
    @SerialName("userStatus") val userStatus: Int,
    @SerialName("userEmail") val userEmail: String,
    @SerialName("userAchievments") val userAchievments: List<UserAchievment>,
)
@Serializable
data class UserAchievment(
    @SerialName("achievementId") val achievementId: String,
    @SerialName("userId") val userId: Int,
    @SerialName("achievementName") val achievementName: String,
    @SerialName("achievementDescription") val achievementDescription: String,
    @SerialName("achievementLevel") val achievementLevel: Int
)