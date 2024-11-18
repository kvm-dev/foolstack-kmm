package ru.foolstack.interview.impl.domain.interactor

import ru.foolstack.interview.api.domain.usecase.GetMaterialsUseCase
import ru.foolstack.interview.api.model.MaterialDomain
import ru.foolstack.interview.api.model.MaterialsDomain
import ru.foolstack.interview.impl.data.repository.local.LocalDataSource
import ru.foolstack.interview.impl.presentation.ui.InterviewsViewState
import ru.foolstack.language.api.domain.GetCurrentLanguageUseCase
import ru.foolstack.networkconnection.api.domain.GetNetworkStateUseCase
import ru.foolstack.professions.api.domain.usecase.GetProfessionsUseCase
import ru.foolstack.utils.model.ResultState

class InterviewsInteractor(
    private val getCurrentLanguageUseCase: GetCurrentLanguageUseCase,
    private val getNetworkStateUseCase: GetNetworkStateUseCase,
    private val getMaterialsUseCase: GetMaterialsUseCase,
    private val getProfessionsUseCase: GetProfessionsUseCase
){
    val materialsState = getMaterialsUseCase.materialsState

    fun getCurrentLang() = getCurrentLanguageUseCase.getCurrentLang()

    private fun isConnectionAvailable() = getNetworkStateUseCase.isNetworkAvailable()

    suspend fun getMaterialsFromServer() = getMaterialsUseCase.getMaterials()

    fun checkState(state: ResultState<MaterialsDomain>, professionId: Int):InterviewsViewState{
        val lang = getCurrentLang()
        when(state){
            is ResultState.Loading->{
                return InterviewsViewState.LoadingState(lang = lang)
            }
            is ResultState.Idle->{
                return InterviewsViewState.LoadingState(lang = lang)
            }
            is ResultState.Success->{
                return if(state.data?.errorMsg?.isNotEmpty() == true){
                    InterviewsViewState.ErrorState(lang = lang)
                } else{
                    val filteredMaterials = HashSet<MaterialDomain>()
                    state.data?.materials?.forEach { material->
                        material.subProfessions.forEach { subProfessin->
                            if(subProfessin.professionId==professionId){
                                filteredMaterials.add(material)
                            }
                        }
                    }
                    val filtersList = HashSet<String>()
                    filteredMaterials.forEach { material->
                        material.knowledgeAreas.forEach { sub->
                            filtersList.add(sub.areaName)
                        }
                    }
                    InterviewsViewState.SuccessState(isHaveConnection = isConnectionAvailable(), materials = filteredMaterials.toList(),
                     selectedFilters = filtersList.toList(), currentProfessionId = professionId, lang = lang)
                }
            }
        }
    }

    suspend fun getProfessionId(): Int{
        return getProfessionsUseCase.getProfessionId()
    }
}