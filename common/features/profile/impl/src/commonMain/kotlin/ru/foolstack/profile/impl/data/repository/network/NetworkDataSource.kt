package ru.foolstack.profile.impl.data.repository.network

import ru.foolstack.network.utils.getBase64Bitmap
import ru.foolstack.profile.api.model.AchievementDomain
import ru.foolstack.profile.api.model.DeleteProfileResponseDomain
import ru.foolstack.profile.api.model.ProfileDomain
import ru.foolstack.profile.api.model.UpdateEmailResponseDomain
import ru.foolstack.profile.api.model.UpdateNameResponseDomain
import ru.foolstack.profile.api.model.UpdatePhotoResponseDomain
import ru.foolstack.profile.impl.mapper.Mapper

class NetworkDataSource(private val api: ProfileApi, private val mapper: Mapper){

    suspend fun getProfile(userToken: String): ProfileDomain {
        val result = api.getProfile(userToken)
        var profileBase64Photo = ""
        if(result.userPhotoUrl.isNotEmpty()){
         profileBase64Photo = getBase64Bitmap(result.userPhotoUrl)
        }
        val userAchievements = ArrayList<AchievementDomain>()
        result.userAchievements.forEach { achievement->
            userAchievements.add(mapper.map(achievement))
        }
        return ProfileDomain(
            userId = result.userId,
            userName = result.userName,
            userType = result.userType,
            userStatus = result.userStatus,
            userEmail = result.userEmail,
            userPhotoUrl = result.userPhotoUrl,
            userPhotoBase64 = profileBase64Photo,
            userPurchasedProfessions = result.userPurchasedProfessions,
            userAchievements = userAchievements,
            errorMsg = result.errorMsg
        )
   }

    suspend fun updateName(userToken: String, name: String):UpdateNameResponseDomain{
        return mapper.mapToNameResponseDomain(api.updateName(userToken = userToken, name = name))
    }

    suspend fun updateEmail(userToken: String, email: String):UpdateEmailResponseDomain{
        return mapper.mapToEmailResponseDomain(api.updateEmail(userToken = userToken, email = email))
    }

    suspend fun updatePhoto(userToken: String, photo: ByteArray):UpdatePhotoResponseDomain{
        return mapper.mapToPhotoResponseDomain(api.updatePhoto(userToken = userToken, photo = photo))
    }

    suspend fun deleteProfile(userToken: String):DeleteProfileResponseDomain{
        return mapper.mapToDeleteProfileResponseDomain(api.deleteProfile(userToken = userToken))
    }
}