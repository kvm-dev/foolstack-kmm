package ru.foolstack.study.impl.domain.interactor

import ru.foolstack.language.api.domain.GetCurrentLanguageUseCase
import ru.foolstack.networkconnection.api.domain.GetNetworkStateUseCase
import ru.foolstack.study.api.domain.usecase.GetStudiesUseCase
import ru.foolstack.study.api.model.StudiesDomain
import ru.foolstack.study.impl.presentation.ui.StudiesViewState
import ru.foolstack.utils.BrowserUtils
import ru.foolstack.utils.model.ResultState

class StudiesInteractor(
    private val getCurrentLanguageUseCase: GetCurrentLanguageUseCase,
    private val getNetworkStateUseCase: GetNetworkStateUseCase,
    private val getStudiesUseCase: GetStudiesUseCase,
    private val browserUtils: BrowserUtils
){
    val studiesState = getStudiesUseCase.studiesState

    fun getCurrentLang() = getCurrentLanguageUseCase.getCurrentLang()

    fun isConnectionAvailable() = getNetworkStateUseCase.isNetworkAvailable()

    suspend fun getStudiesFromServer() = getStudiesUseCase.getStudies()

    suspend fun getStudiesFromLocal() = getStudiesUseCase.getStudies(fromLocal = true)

    fun checkState(state: ResultState<StudiesDomain>):StudiesViewState{
        val lang = getCurrentLang()
        when(state){
            is ResultState.Loading->{
                return StudiesViewState.LoadingState(lang = lang)
            }
            is ResultState.Idle->{
                return StudiesViewState.LoadingState(lang = lang)
            }
            is ResultState.Success->{
                return if(state.data?.errorMsg?.isNotEmpty() == true){
                    StudiesViewState.ErrorState(lang = lang)
                } else{
                    val filtersList = HashSet<String>()
                    state.data?.studies?.forEach { study->
                        study.professions.forEach { sub->
                            filtersList.add(sub.professionName)
                        }
                    }
                    StudiesViewState.SuccessState(isHaveConnection = isConnectionAvailable(), studies = state.data, selectedFilters = filtersList.toList(), lang = lang)
                }
            }
        }
    }

    fun openInBrowser(url: String){
        browserUtils.openInBrowser(url)
    }
}