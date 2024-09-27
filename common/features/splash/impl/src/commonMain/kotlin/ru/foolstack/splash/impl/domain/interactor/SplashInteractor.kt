package ru.foolstack.splash.impl.domain.interactor

import ru.foolstack.authorization.api.domain.usecase.AuthByEmailUseCase
import ru.foolstack.authorization.api.domain.usecase.AuthByTokenUseCase
import ru.foolstack.authorization.api.domain.usecase.ConfirmAuthAndRegUseCase
import ru.foolstack.authorization.api.domain.usecase.GetTokenFromLocalUseCase
import ru.foolstack.authorization.api.domain.usecase.IsUserExistUseCase
import ru.foolstack.books.api.domain.usecase.GetBooksUseCase
import ru.foolstack.books.api.model.BooksDomain
import ru.foolstack.events.api.domain.usecase.GetEventsUseCase
import ru.foolstack.events.api.model.EventsDomain
import ru.foolstack.splash.impl.data.StringResources
import ru.foolstack.splash.impl.presentation.ui.SplashBottomText
import ru.foolstack.splash.impl.presentation.ui.SplashViewState
import ru.foolstack.language.api.domain.GetCurrentLanguageUseCase
import ru.foolstack.language.api.model.LangResultDomain
import ru.foolstack.interview.api.domain.usecase.GetMaterialsUseCase
import ru.foolstack.interview.api.model.MaterialsDomain
import ru.foolstack.networkconnection.api.domain.GetNetworkStateUseCase
import ru.foolstack.news.api.domain.usecase.GetNewsUseCase
import ru.foolstack.news.api.model.NewsDomain
import ru.foolstack.profile.api.domain.usecase.GetProfileUseCase
import ru.foolstack.profile.api.model.ProfileDomain
import ru.foolstack.registration.api.domain.usecase.RegistrationByEmailUseCase
import ru.foolstack.study.api.domain.usecase.GetStudiesUseCase
import ru.foolstack.study.api.model.StudiesDomain
import ru.foolstack.tests.api.domain.usecase.GetTestsUseCase
import ru.foolstack.tests.api.model.TestsDomain

class SplashInteractor(
    private val getCurrentLanguageUseCase: GetCurrentLanguageUseCase,
    private val getNetworkStateUseCase: GetNetworkStateUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val getTokenFromLocalUseCase: GetTokenFromLocalUseCase,
    private val getBooksUseCase: GetBooksUseCase,
    private val getEventsUseCase: GetEventsUseCase,
    private val getMaterialsUseCase: GetMaterialsUseCase,
    private val getNewsUseCase: GetNewsUseCase,
    private val getStudiesUseCase: GetStudiesUseCase,
    private val getTestsUseCase: GetTestsUseCase,
    private val isUserExistUseCase: IsUserExistUseCase,
    private val authByEmailUseCase: AuthByEmailUseCase,
    private val authByTokenUseCase: AuthByTokenUseCase,
    private val registrationByEmailUseCase: RegistrationByEmailUseCase,
    private val confirmAuthAndRegUseCase: ConfirmAuthAndRegUseCase
) {

    //methods
    fun getCurrentLang() = getCurrentLanguageUseCase.getCurrentLang()

    fun isConnectionAvailable() = getNetworkStateUseCase.isNetworkAvailable()

    suspend fun getBooksFromServer() = getBooksUseCase.getBooks()
    suspend fun getBooksFromLocal() = getBooksUseCase.getBooks(fromLocal = true)

    suspend fun getEventsFromServer() = getEventsUseCase.getEvents()
    suspend fun getEventsFromLocal() = getEventsUseCase.getEvents(fromLocal = true)

    suspend fun getMaterialsFromServer() = getMaterialsUseCase.getMaterials()
    suspend fun getMaterialsFromLocal() = getMaterialsUseCase.getMaterials(fromLocal = true)

    suspend fun getNewsFromServer() = getNewsUseCase.getNews()
    suspend fun getNewsFromLocal() = getNewsUseCase.getNews(fromLocal = true)

    suspend fun getStudiesFromServer() = getStudiesUseCase.getStudies()
    suspend fun getStudiesFromLocal() = getStudiesUseCase.getStudies(fromLocal = true)

    suspend fun getTestsFromServer() = getTestsUseCase.getTests()
    suspend fun getTestsFromLocal() = getTestsUseCase.getTests(fromLocal = true)

    suspend fun getProfileFromServer() = getProfileUseCase.getProfile()
    suspend fun getProfileFromLocal() = getProfileUseCase.getProfile(fromLocal = true)

    suspend fun isTokenExist() = getTokenFromLocalUseCase.getToken().isNotEmpty()

    suspend fun isUserExist(email: String) = isUserExistUseCase.isUserExist(email)

    suspend fun authByEmail(email: String, code: String) =
        authByEmailUseCase.auth(email = email, code = code)

    suspend fun registrationByEmail(email: String) =
        registrationByEmailUseCase.registration(email = email)

    suspend fun confirmAuthAndReg(email: String, code: String) =
        confirmAuthAndRegUseCase.confirm(email = email, code = code)

    private fun responseErrorHandler(errMsg: String): SplashViewState {
        val lang = getCurrentLang().lang
        val isInternetAvailable = isConnectionAvailable()
        val defLang = "RU"
        return when (errMsg) {
            StringResources.getErrorNetworkBadRequestText(defLang) -> {
                SplashViewState.AnyError(
                    splashTitleText = StringResources.getErrorDefaultTitle(lang),
                    splashDescriptionText = "${StringResources.getErrorDefaultText(lang)}${
                        StringResources.getErrorNetworkBadRequestText(
                            lang
                        )
                    }",
                    tryAgainButtonText = StringResources.getSplashTryAgainButton(lang)
                )
            }

            StringResources.getErrorNetworkExpectationFailedText(defLang) -> {
                SplashViewState.AnyError(
                    splashTitleText = StringResources.getErrorDefaultTitle(lang),
                    splashDescriptionText = "${StringResources.getErrorDefaultText(lang)}${
                        StringResources.getErrorNetworkExpectationFailedText(
                            lang
                        )
                    }",
                    tryAgainButtonText = StringResources.getSplashTryAgainButton(lang)
                )
            }

            StringResources.getErrorNetworkTokenExpiredText(defLang) -> {
                SplashViewState.AnyError(
                    splashTitleText = StringResources.getErrorDefaultTitle(lang),
                    splashDescriptionText = "${StringResources.getErrorDefaultText(lang)}${
                        StringResources.getErrorNetworkTokenExpiredText(
                            lang
                        )
                    }",
                    tryAgainButtonText = StringResources.getSplashTryAgainButton(lang)
                )
            }

            StringResources.getErrorNetworkUnauthorizedText(defLang) -> {
                SplashViewState.UnAuthorized(
                    lang = lang,
                    isInternetConnected = isInternetAvailable,
                    splashBottomText = SplashBottomText(
                        splashTitleText = StringResources.getSplashTitle(lang),
                        splashDescriptionText = StringResources.getSplashPreAuthDescription(lang),
                        splashMainButtonText = StringResources.getJoinButtonText(lang),
                        splashResendButtonText = StringResources.getGuestButtonText(lang),
                        splashSecondButtonText = StringResources.getGuestButtonText(lang)
                    )
                )
            }

            StringResources.getErrorNetworkForbiddenText(defLang) -> {
                SplashViewState.AnyError(
                    splashTitleText = StringResources.getErrorDefaultTitle(lang),
                    splashDescriptionText = "${StringResources.getErrorDefaultText(lang)}${
                        StringResources.getErrorNetworkForbiddenText(
                            lang
                        )
                    }",
                    tryAgainButtonText = StringResources.getSplashTryAgainButton(lang)
                )
            }

            StringResources.getErrorNetworkNotFoundText(defLang) -> {
                SplashViewState.NoConnectionError(
                    splashTitleText = StringResources.getErrorNotFoundConnectionTitle(lang),
                    splashDescriptionText = StringResources.getErrorNotFoundConnectionText(lang)
                )
            }

            StringResources.getErrorNetworkRequestTimeOutText(defLang) -> {
                SplashViewState.AnyError(
                    splashTitleText = StringResources.getErrorDefaultTitle(lang),
                    splashDescriptionText = "${StringResources.getErrorDefaultText(lang)}${
                        StringResources.getErrorNetworkRequestTimeOutText(
                            lang
                        )
                    }",
                    tryAgainButtonText = StringResources.getSplashTryAgainButton(lang)
                )
            }

            StringResources.getErrorNetworkInternalServerText(defLang) -> {
                SplashViewState.AnyError(
                    splashTitleText = StringResources.getErrorDefaultTitle(lang),
                    splashDescriptionText = "${StringResources.getErrorDefaultText(lang)}${
                        StringResources.getErrorNetworkInternalServerText(
                            lang
                        )
                    }",
                    tryAgainButtonText = StringResources.getSplashTryAgainButton(lang)
                )
            }

            StringResources.getErrorNetworkUnknownText(defLang) -> {
                SplashViewState.AnyError(
                    splashTitleText = StringResources.getErrorDefaultTitle(lang),
                    splashDescriptionText = "${StringResources.getErrorDefaultText(lang)}${
                        StringResources.getErrorNetworkUnknownText(
                            lang
                        )
                    }",
                    tryAgainButtonText = StringResources.getSplashTryAgainButton(lang)
                )
            }

            else -> {
                SplashViewState.AnyError(
                    splashTitleText = StringResources.getErrorDefaultTitle(lang),
                    splashDescriptionText = "${StringResources.getErrorDefaultText(lang)}${
                        StringResources.getErrorNetworkUnknownText(
                            lang
                        )
                    }",
                    tryAgainButtonText = StringResources.getSplashTryAgainButton(lang)
                )
            }
        }
    }

     fun authorizedErrorsResponseHandler(
        profile: ProfileDomain,
        materials: MaterialsDomain,
        tests: TestsDomain,
        books: BooksDomain,
        news: NewsDomain,
        events: EventsDomain,
        studies: StudiesDomain): SplashViewState {
        if(profile.errorMsg.isNotEmpty()){
            return responseErrorHandler(profile.errorMsg)
        }
        else if(materials.errorMsg.isNotEmpty()){
            return responseErrorHandler(materials.errorMsg)

        }
        else if(tests.errorMsg.isNotEmpty()){
            return responseErrorHandler(tests.errorMsg)
        }
        else if(news.errorMsg.isNotEmpty()){
            return responseErrorHandler(news.errorMsg)
        }
        else if(events.errorMsg.isNotEmpty()){
            return responseErrorHandler(events.errorMsg)
        }
        else if(studies.errorMsg.isNotEmpty()){
            return responseErrorHandler(studies.errorMsg)
        }
        else if(books.errorMsg.isNotEmpty()){
            return responseErrorHandler(books.errorMsg)
        }
        else{
            return responseErrorHandler("")
        }
    }

    suspend fun validateAuthorizedData(profile: ProfileDomain,
                                       books: BooksDomain,
                                       materials: MaterialsDomain,
                                       news: NewsDomain,
                                       events: EventsDomain,
                                       studies: StudiesDomain,
                                       tests: TestsDomain
    ):Boolean{
        var newProfile = profile
        if(newProfile.errorMsg.isNotEmpty()){
            //this is necessary in order to immediately authorize only the registered user
            newProfile = getProfileFromServer()
        }
        return (newProfile.errorMsg.isEmpty()
                && books.errorMsg.isEmpty()
                && materials.errorMsg.isEmpty()
                && news.errorMsg.isEmpty()
                && events.errorMsg.isEmpty()
                && studies.errorMsg.isEmpty()
                && tests.errorMsg.isEmpty())
    }

    fun getUnauthorizedState(): SplashViewState.UnAuthorized{
        val lang = getCurrentLang().lang
        return SplashViewState.UnAuthorized(
            lang = getCurrentLang().lang, isInternetConnected = isConnectionAvailable(),
            splashBottomText = SplashBottomText(
                splashTitleText = getSplashUnauthorizedTitle(lang = lang),
                splashDescriptionText = getSplashUnauthorizedDescription(lang = lang),
                splashMainButtonText = getSplashUnauthorizedMainButtonText(
                    lang = lang
                ),
                splashSecondButtonText = getSplashUnauthorizedSecondButtonText(
                    lang = lang
                )
            )
        )
    }

    fun getNoConnectionState() = SplashViewState.NoConnectionError(
        splashTitleText = getErrorNotFoundConnectionTitle(getCurrentLang().lang),
        splashDescriptionText = getErrorNotFoundConnectionText(getCurrentLang().lang)
    )

    fun getConfirmationState(isUserExist: Boolean): SplashViewState.Confirmation{
        val lang = getCurrentLang().lang
        return SplashViewState.Confirmation(
            lang = lang,
            splashBottomText = SplashBottomText(
                splashTitleText = getSplashConfirmAuthorizationOrRegistrationTitle(
                    lang
                ),
                splashDescriptionText = getSplashConfirmAuthorizationOrRegistrationText(
                    lang
                ),
                splashMainButtonText = getSplashConfirmAuthorizationOrRegistrationMainButtonText(
                    lang
                ),
                splashSecondButtonText = getSplashConfirmAuthorizationOrRegistrationSecondButtonText(
                    lang
                ),
                splashResendButtonText = getSplashConfirmAuthorizationOrRegistrationResendButtonText(
                    lang
                )
            ),
            isUserExist = isUserExist
        )
    }

    fun getAuthorizationOrRegistrationState(): SplashViewState.AuthorizationOrRegistration{
        val lang = getCurrentLang().lang
        return SplashViewState.AuthorizationOrRegistration(
            lang = lang,
            splashBottomText = SplashBottomText(
                splashTitleText = getSplashAuthorizationOrRegistrationTitle(lang = lang),
                splashDescriptionText = getSplashAuthorizationOrRegistrationText(lang = lang),
                splashMainButtonText = getSplashAuthorizationOrRegistrationMainButtonText(
                    lang = lang
                ),
                splashSecondButtonText = getSplashAuthorizationOrRegistrationSecondButtonText(
                    lang = lang
                )
            )
        )
    }

    fun getAuthorizedState(
        local: LangResultDomain,
        isInternetConnected: Boolean,
        profile: ProfileDomain,
        books: BooksDomain? = null,
        materials: MaterialsDomain,
        news: NewsDomain? = null,
        events: EventsDomain? = null,
        studies: StudiesDomain? = null,
        tests: TestsDomain): SplashViewState.Authorized{
        return SplashViewState.Authorized(
            lang = local.lang,
            isHaveToken = true,
            profileData = profile,
            isInternetConnected = isInternetConnected,
            books = books,
            events = events,
            materials = materials,
            news = news,
            studies = studies,
            tests = tests
        )
    }

    //text values
    private fun getSplashUnauthorizedTitle(lang: String? = null) = StringResources.getSplashTitle(lang)
    private fun getSplashUnauthorizedDescription(lang: String? = null) =
        StringResources.getSplashPreAuthDescription(lang)

    private fun getSplashUnauthorizedMainButtonText(lang: String? = null) =
        StringResources.getJoinButtonText(lang)

    private fun getSplashUnauthorizedSecondButtonText(lang: String? = null) =
        StringResources.getGuestButtonText(lang)

    private fun getSplashAuthorizationOrRegistrationTitle(lang: String? = null) =
        StringResources.getAuthorizationOrRegistrationTitle(lang)

    private fun getSplashAuthorizationOrRegistrationText(lang: String? = null) =
        StringResources.getAuthorizationOrRegistrationText(lang)

    private fun getSplashAuthorizationOrRegistrationMainButtonText(lang: String? = null) =
        StringResources.getAuthorizationRegistrationButtonText(lang)

    private fun getSplashAuthorizationOrRegistrationSecondButtonText(lang: String? = null) =
        StringResources.getBackButtonText(lang)

    private fun getSplashConfirmAuthorizationOrRegistrationTitle(lang: String? = null) =
        StringResources.getConfirmTitleText(lang)

    private fun getSplashConfirmAuthorizationOrRegistrationText(lang: String? = null) =
        StringResources.getConfirmText(lang)

    private fun getSplashConfirmAuthorizationOrRegistrationMainButtonText(lang: String? = null) =
        StringResources.getConfirmButtonText(lang)

    private fun getSplashConfirmAuthorizationOrRegistrationSecondButtonText(lang: String? = null) =
        StringResources.getBackButtonText(lang)

    private fun getSplashConfirmAuthorizationOrRegistrationResendButtonText(lang: String? = null) =
        StringResources.getResendButtonText(lang)

    //errors
    fun getErrorEmailIncorrect(lang: String? = null) = StringResources.getErrorEmailIncorrect(lang)
    fun getErrorEmailEmpty(lang: String? = null) = StringResources.getErrorEmailEmpty(lang)
    fun getErrorEmailEmptyOrIncorrect(lang: String? = null) =
        StringResources.getErrorEmailEmptyOrIncorrect(lang)

    fun getErrorCodeAlreadySent(lang: String? = null) =
        StringResources.getErrorCodeAlreadySent(lang)

    fun getErrorCodeEmptyOrIncorrect(lang: String? = null) =
        StringResources.getErrorCodeEmptyOrIncorrect(lang)

    fun getErrorUserNotFound(lang: String? = null) = StringResources.getErrorUserNotFound(lang)
    fun getErrorUserUnconfirmed(lang: String? = null) =
        StringResources.getErrorUserUnconfirmed(lang)

    fun getErrorCodeWrongOrExpired(lang: String? = null) =
        StringResources.getErrorCodeWrongOrExpired(lang)

    fun getErrorEmailNotFoundOrInvalidCodeOrUserConfirmed(lang: String? = null) =
        StringResources.getErrorEmailNotFoundOrInvalidCodeOrUserAlreadyConfirmed(lang)

    fun getErrorUserExist(lang: String? = null) = StringResources.getErrorUserAlreadyExist(lang)
    fun getErrorMethodNotFound(lang: String? = null) = StringResources.getErrorMethodNotFound(lang)

    private fun getErrorNotFoundConnectionTitle(lang: String? = null) =
        StringResources.getErrorNotFoundConnectionTitle(lang)

    private fun getErrorNotFoundConnectionText(lang: String? = null) =
        StringResources.getErrorNotFoundConnectionText(lang)
}