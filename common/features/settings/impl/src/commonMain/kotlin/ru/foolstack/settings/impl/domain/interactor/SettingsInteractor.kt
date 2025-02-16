package ru.foolstack.settings.impl.domain.interactor

import ru.foolstack.language.api.domain.GetCurrentLanguageUseCase
import ru.foolstack.language.api.model.LangResultDomain
import ru.foolstack.networkconnection.api.domain.GetNetworkStateUseCase
import ru.foolstack.profile.api.domain.usecase.DeleteProfileUseCase
import ru.foolstack.profile.api.domain.usecase.GetProfileUseCase
import ru.foolstack.profile.api.domain.usecase.UpdateEmailUseCase
import ru.foolstack.profile.api.domain.usecase.UpdateNameUseCase
import ru.foolstack.profile.api.domain.usecase.UpdatePhotoUseCase
import ru.foolstack.profile.api.model.DeleteProfileResponseDomain
import ru.foolstack.profile.api.model.ProfileDomain
import ru.foolstack.profile.api.model.UpdateEmailResponseDomain
import ru.foolstack.profile.api.model.UpdateNameResponseDomain
import ru.foolstack.profile.api.model.UpdatePhotoResponseDomain
import ru.foolstack.settings.api.domain.usecase.GetAppThemeUseCase
import ru.foolstack.settings.api.domain.usecase.SetAppThemeUseCase
import ru.foolstack.settings.api.model.AppThemeDomain
import ru.foolstack.settings.impl.data.resources.StringResources
import ru.foolstack.settings.impl.presentation.ui.SettingsViewState
import ru.foolstack.utils.model.ResultState

class SettingsInteractor(
    private val getCurrentLanguageUseCase: GetCurrentLanguageUseCase,
    private val getNetworkStateUseCase: GetNetworkStateUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val updateNameUseCase: UpdateNameUseCase,
    private val updateEmailUseCase: UpdateEmailUseCase,
    private val updatePhotoUseCase: UpdatePhotoUseCase,
    private val deleteProfileUseCase: DeleteProfileUseCase,
    private val getAppThemeUseCase: GetAppThemeUseCase,
    private val setAppThemeUseCase: SetAppThemeUseCase
){
    val profileState = getProfileUseCase.profileState
    fun getCurrentLang() = getCurrentLanguageUseCase.getCurrentLang()

    fun isConnectionAvailable() = getNetworkStateUseCase.isNetworkAvailable()

    suspend fun getProfileFromServer() = getProfileUseCase.getProfile()

    suspend fun getProfileFromLocal() = getProfileUseCase.getProfile(fromLocal = true)


    fun checkState(state: ResultState<ProfileDomain>):SettingsViewState{
        val lang = getCurrentLang()
        when(state){
            is ResultState.Loading->{
                return SettingsViewState.LoadingState(lang = lang)
            }
            is ResultState.Idle->{
                return SettingsViewState.LoadingState(lang = lang)
            }
            is ResultState.Success->{
                return if(state.data?.errorMsg?.isNotEmpty() == true){
                    SettingsViewState.ErrorState(lang = lang)
                } else{
                    SettingsViewState.SuccessState(isHaveConnection = isConnectionAvailable(),
                        lang = lang,
                        userName = state.data?.userName?: "",
                        userEmail = state.data?.userEmail?: "",
                        userPhoto = state.data?.userPhotoBase64?: "",
                        appTheme = getAppThemeUseCase.getCurrentAppTheme()
                        )
                }
            }
        }
    }

    fun getErrorEmailIncorrect(lang: String? = null) = StringResources.getErrorEmailIncorrect(lang)
    fun getErrorEmailEmpty(lang: String? = null) = StringResources.getErrorEmailEmpty(lang)

    fun getErrorUserNameEmpty(lang: String? = null) =
        StringResources.getErrorUserNameEmpty(lang)

    fun setTheme(theme: String){
            setAppThemeUseCase.setCurrentAppTheme(checkTheme(theme))
    }

    fun checkTheme(theme: String): AppThemeDomain{
        if(getCurrentLang() is LangResultDomain.Ru){
            val appTheme = when(theme.lowercase()){
                "системная" -> AppThemeDomain.SYSTEM_THEME
                "светлая" -> AppThemeDomain.LIGHT_THEME
                "темная" -> AppThemeDomain.DARK_THEME
                else -> AppThemeDomain.SYSTEM_THEME
            }
          return appTheme
        }
        else{
            val appTheme = when(theme.lowercase()){
                "system" -> AppThemeDomain.SYSTEM_THEME
                "light" -> AppThemeDomain.LIGHT_THEME
                "dark" -> AppThemeDomain.DARK_THEME
                else -> AppThemeDomain.SYSTEM_THEME
            }
            return appTheme
        }
    }

    suspend fun updateUserName(userName: String):UpdateNameResponseDomain{
        return updateNameUseCase.updateName(userName = userName)
    }

    suspend fun updateEmail(email: String):UpdateEmailResponseDomain{
       return updateEmailUseCase.updateEmail(email = email)
    }

    suspend fun updateNameAndEmail(userName: String, email: String):Boolean{
        val userNameResponse = updateUserName(userName = userName)
        val emailResponse = updateEmail(email = email)
        return !(!userNameResponse.success || !emailResponse.success)
    }

    fun getConfirmChangeEmailDialogTitle() = StringResources.getConfirmChangeEmailDialogTitle(getCurrentLanguageUseCase.getCurrentLang().lang)

    fun getConfirmChangeEmailDialogText() = StringResources.getConfirmChangeEmailDialogText(getCurrentLanguageUseCase.getCurrentLang().lang)

    fun getConfirmChangeEmailDialogActionBtn() = StringResources.getConfirmChangeEmailDialogMainButton(getCurrentLanguageUseCase.getCurrentLang().lang)

    fun getConfirmChangeEmailDialogCancelBtn() = StringResources.getConfirmChangeEmailDialogSecondButton(getCurrentLanguageUseCase.getCurrentLang().lang)

    fun getConfirmDeleteAccountDialogTitle() = StringResources.getConfirmDeleteAccountDialogTitle(getCurrentLanguageUseCase.getCurrentLang().lang)

    fun getConfirmDeleteAccountDialogText() = StringResources.getConfirmDeleteAccountDialogText(getCurrentLanguageUseCase.getCurrentLang().lang)

    fun getConfirmDeleteAccountDialogActionBtn() = StringResources.getConfirmDeleteAccountDialogMainButton(getCurrentLanguageUseCase.getCurrentLang().lang)

    fun getConfirmDeleteAccountDialogCancelBtn() = StringResources.getConfirmDeleteAccountDialogSecondButton(getCurrentLanguageUseCase.getCurrentLang().lang)

    fun getSuccessDialogTitle() = StringResources.getSuccessDialogTitle(getCurrentLanguageUseCase.getCurrentLang().lang)

    fun getSuccessDialogText() = StringResources.getSuccessDialogText(getCurrentLanguageUseCase.getCurrentLang().lang)

    fun getSuccessDialogBtn() = StringResources.getSuccessDialogBtn(getCurrentLanguageUseCase.getCurrentLang().lang)

    fun getFailDialogTitle() = StringResources.getFailDialogTitle(getCurrentLanguageUseCase.getCurrentLang().lang)

    fun getFailDialogText() = StringResources.getFailDialogText(getCurrentLanguageUseCase.getCurrentLang().lang)

    fun getFailDialogBtn() = StringResources.getFailDialogBtn(getCurrentLanguageUseCase.getCurrentLang().lang)

    fun getNoConnectionDialogTitle() = StringResources.getNoConnectionDialogTitle(getCurrentLanguageUseCase.getCurrentLang().lang)

    fun getNoConnectionDialogText() = StringResources.getNoConnectionDialogText(getCurrentLanguageUseCase.getCurrentLang().lang)

    fun getNoConnectionDialogBtn() = StringResources.getNoConnectionDialogBtn(getCurrentLanguageUseCase.getCurrentLang().lang)

    suspend fun updatePhoto(photo: ByteArray): UpdatePhotoResponseDomain{
       return updatePhotoUseCase.updatePhoto(photo)
    }

    suspend fun deleteProfile(): DeleteProfileResponseDomain{
        return deleteProfileUseCase.deleteProfile()
    }
}