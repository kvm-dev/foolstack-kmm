package ru.foolstack.profile.impl.mapper

import ru.foolstack.profile.api.model.AchievementDomain
import ru.foolstack.profile.api.model.ProfileDomain
import ru.foolstack.profile.impl.model.AchievementResponse
import ru.foolstack.storage.model.Profile
import ru.foolstack.storage.model.UserAchievement

class Mapper {

    private fun map(userAchievements: List<UserAchievement>):List<AchievementDomain>{
        val list = ArrayList<AchievementDomain>()
        userAchievements.forEach {userAchievement ->
            list.add(
                AchievementDomain(
                achievementId = userAchievement.achievementId,
                achievementName = userAchievement.achievementName,
                achievementDescription = userAchievement.achievementDescription,
                achievementLevel = userAchievement.achievementLevel
            )
            )
        }
        return list
    }

    fun mapToProfileDomain(profile: Profile, base64Photo: String):ProfileDomain{
        return ProfileDomain(
            userId = profile.userId,
            userName = profile.userName,
            userEmail = profile.userEmail,
            userPhotoUrl = profile.userPhotoUrl,
            userPhotoBase64 = base64Photo,
            userStatus = profile.userStatus,
            userType = profile.userType,
            userAchievements = map(profile.userAchievements),
            userPurchasedProfessions = profile.userPurchasedProfessions,
            errorMsg = ""
        )
    }

    private fun map(userAchievments: List<AchievementDomain>, userId: Int):List<UserAchievement>{
        val list = ArrayList<UserAchievement>()
        userAchievments.forEach {userAchievement ->
            list.add(
                UserAchievement(
                    achievementId = userAchievement.achievementId,
                    userId = userId,
                    achievementName = userAchievement.achievementName,
                    achievementDescription = userAchievement.achievementDescription,
                    achievementLevel = userAchievement.achievementLevel
                )
            )
        }
        return list
    }
    fun mapFromProfileDomain(profile: ProfileDomain, base64Photo: String): Profile{
        return Profile(
            userId = profile.userId,
            userName = profile.userName,
            userEmail = profile.userEmail,
            userType = profile.userType,
            userStatus = profile.userStatus,
            userAchievements = map(profile.userAchievements, userId = profile.userId),
            userPhotoUrl = profile.userPhotoUrl,
            userPhotoBase64 = base64Photo,
            userPurchasedProfessions = profile.userPurchasedProfessions
        )
    }

    fun map(response: AchievementResponse): AchievementDomain{
        return AchievementDomain(
            achievementId = response.achievementId,
            achievementName = response.achievementName,
            achievementDescription = response.achievementDescription,
            achievementLevel = response.achievementLevel
        )
    }
}