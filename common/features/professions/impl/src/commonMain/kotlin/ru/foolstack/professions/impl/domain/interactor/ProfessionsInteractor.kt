package ru.foolstack.professions.impl.domain.interactor

import ru.foolstack.language.api.domain.GetCurrentLanguageUseCase
import ru.foolstack.networkconnection.api.domain.GetNetworkStateUseCase
import ru.foolstack.professions.api.domain.usecase.GetProfessionsUseCase
import ru.foolstack.professions.api.model.ProfessionsDomain
import ru.foolstack.professions.impl.presentation.ui.ProfessionsViewState
import ru.foolstack.utils.model.ResultState

class ProfessionsInteractor(
    private val getCurrentLanguageUseCase: GetCurrentLanguageUseCase,
    private val getNetworkStateUseCase: GetNetworkStateUseCase,
    private val getProfessionsUseCase: GetProfessionsUseCase,
){
    val professionsState = getProfessionsUseCase.professionsState

    fun getCurrentLang() = getCurrentLanguageUseCase.getCurrentLang()

    private fun isConnectionAvailable() = getNetworkStateUseCase.isNetworkAvailable()

    suspend fun getProfessionsFromServer() = getProfessionsUseCase.getProfessions()

    fun checkState(state: ResultState<ProfessionsDomain>):ProfessionsViewState{
        val lang = getCurrentLang()
        return when(state){
            is ResultState.Loading->{
                ProfessionsViewState.LoadingState(lang = lang)
            }

            is ResultState.Idle->{
                ProfessionsViewState.LoadingState(lang = lang)
            }

            is ResultState.Success->{
                if(state.data?.errorMsg?.isNotEmpty() == true){
                    ProfessionsViewState.ErrorState(lang = lang)
                } else{
                    ProfessionsViewState.SuccessState(isHaveConnection = isConnectionAvailable(), professions = state.data, lang = lang)
                }
            }
        }
    }

    suspend fun saveProfession(professionId: Int){
        getProfessionsUseCase.saveProfessionId(professionId)
    }
}