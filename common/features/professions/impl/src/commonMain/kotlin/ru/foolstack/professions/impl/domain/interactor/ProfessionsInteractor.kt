package ru.foolstack.professions.impl.domain.interactor

import ru.foolstack.interview.api.domain.usecase.GetMaterialsUseCase
import ru.foolstack.language.api.domain.GetCurrentLanguageUseCase
import ru.foolstack.networkconnection.api.domain.GetNetworkStateUseCase
import ru.foolstack.professions.api.domain.usecase.GetProfessionsUseCase
import ru.foolstack.professions.api.model.ProfessionsDomain
import ru.foolstack.professions.impl.presentation.ui.ProfessionsViewState
import ru.foolstack.tests.api.domain.usecase.GetPassedTestsUseCase
import ru.foolstack.tests.api.domain.usecase.GetTestsUseCase
import ru.foolstack.utils.model.ResultState

class ProfessionsInteractor(
    private val getCurrentLanguageUseCase: GetCurrentLanguageUseCase,
    private val getNetworkStateUseCase: GetNetworkStateUseCase,
    private val getProfessionsUseCase: GetProfessionsUseCase,
    private val getMaterialsUseCase: GetMaterialsUseCase,
    private val getTestsUseCase: GetTestsUseCase,
    private val getPassedTestsUseCase: GetPassedTestsUseCase
){
    val professionsState = getProfessionsUseCase.professionsState

    fun getCurrentLang() = getCurrentLanguageUseCase.getCurrentLang()

    fun isConnectionAvailable() = getNetworkStateUseCase.isNetworkAvailable()

    suspend fun getProfessionsFromServer() = getProfessionsUseCase.getProfessions()

    suspend fun getMaterials(professionId: Int) = getMaterialsUseCase.getMateialsByProfession(professionId = professionId)

    suspend fun getTestsFromServer(professionId: Int) = getTestsUseCase.getTestsByProfession(professionId = professionId)

    suspend fun getTestsFromLocal() = getTestsUseCase.getTests(fromLocal = true)

    suspend fun getPassedTestsFromServer() = getPassedTestsUseCase.getPassedTests()

    suspend fun getPassedTestsFromLocal() = getPassedTestsUseCase.getPassedTests(fromLocal = true)

    //    suspend fun getMaterialsFromServer(professionId: Int) = getMaterialsUseCase.getMaterialsByProfession(professionId = professionId)
    //    suspend fun getMaterialsFromLocal() = getMaterialsUseCase.getMaterials(fromLocal = true)

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