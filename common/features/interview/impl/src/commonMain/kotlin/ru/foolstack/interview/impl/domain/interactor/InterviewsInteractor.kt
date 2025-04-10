package ru.foolstack.interview.impl.domain.interactor

import ru.foolstack.asmode.api.domain.usecase.GetAsModeUseCase
import ru.foolstack.comments.api.domain.usecase.SendMaterialCommentUseCase
import ru.foolstack.comments.api.model.MaterialCommentRequestDomain
import ru.foolstack.interview.api.domain.usecase.GetMaterialsUseCase
import ru.foolstack.interview.api.model.MaterialDomain
import ru.foolstack.interview.api.model.MaterialsDomain
import ru.foolstack.interview.impl.data.resources.StringResources
import ru.foolstack.interview.impl.presentation.ui.InterviewsViewState
import ru.foolstack.language.api.domain.GetCurrentLanguageUseCase
import ru.foolstack.networkconnection.api.domain.GetNetworkStateUseCase
import ru.foolstack.professions.api.domain.usecase.GetProfessionsUseCase
import ru.foolstack.professions.api.model.ProfessionsDomain
import ru.foolstack.profile.api.domain.usecase.GetProfileUseCase
import ru.foolstack.profile.api.model.ProfileDomain
import ru.foolstack.utils.BrowserUtils
import ru.foolstack.utils.model.ResultState

class InterviewsInteractor(
    private val getCurrentLanguageUseCase: GetCurrentLanguageUseCase,
    private val getNetworkStateUseCase: GetNetworkStateUseCase,
    private val getMaterialsUseCase: GetMaterialsUseCase,
    private val getProfessionsUseCase: GetProfessionsUseCase,
    private val sendMaterialCommentUseCase: SendMaterialCommentUseCase,
    private val getAsModeUseCase: GetAsModeUseCase,
    getProfileUseCase: GetProfileUseCase,
    private val browserUtils: BrowserUtils
){
    val materialsState = getMaterialsUseCase.materialsState
    val profileState = getProfileUseCase.profileState
    val professionsState = getProfessionsUseCase.professionsState

    fun getCurrentLang() = getCurrentLanguageUseCase.getCurrentLang()

    fun isConnectionAvailable() = getNetworkStateUseCase.isNetworkAvailable()

    suspend fun getMaterials():MaterialsDomain{
        val professionId = getProfessionId()
        return getMaterialsUseCase.getMateialsByProfession(professionId)
    }

    suspend fun getProfessionsFromServer() = getProfessionsUseCase.getProfessions()

    suspend fun getProfessionsFromLocal() = getProfessionsUseCase.getProfessions(fromLocal = true)

//    suspend fun getMaterialsFromServer():MaterialsDomain{
//        return getMaterialsUseCase.getMaterialsByProfession(professionId = getProfessionId())
//    }
//
//    suspend fun getMaterialsFromLocal() = getMaterialsUseCase.getMaterials(true)

    suspend fun checkState(state: ResultState<MaterialsDomain>,
                           profileState: ResultState<ProfileDomain>,
                           professionsState:ResultState<ProfessionsDomain>,
                           professionId: Int):InterviewsViewState{
        val lang = getCurrentLang()
        var isShowBanner = true
        val fullPurchasedList = HashSet<Int>()
        if(profileState is ResultState.Success && professionsState is ResultState.Success){
            profileState.data?.userPurchasedProfessions?.forEach { purchasedProfessionId->
                professionsState.data?.professions?.forEach { profession->
                    if(purchasedProfessionId == profession.professionId){
                        fullPurchasedList.add(purchasedProfessionId)
                        profession.subProfessions.forEach { splvl1->
                            fullPurchasedList.add(splvl1.professionId)
                            splvl1.subProfessions.forEach { splvl2->
                                fullPurchasedList.add(splvl2.professionId)
                                splvl2.subProfessions.forEach { splvl3->
                                    fullPurchasedList.add(splvl3.professionId)
                                    splvl3.subProfessions.forEach { splvl4->
                                        fullPurchasedList.add(splvl4.professionId)
                                        splvl4.subProfessions.forEach { splvl5->
                                            fullPurchasedList.add(splvl5.professionId)
                                        }
                                    }
                                }
                            }
                        }
                    }
                    else{
                        profession.subProfessions.forEach { splvl1->
                            if(purchasedProfessionId == splvl1.professionId){
                                fullPurchasedList.add(splvl1.professionId)
                                splvl1.subProfessions.forEach { splvl2->
                                    fullPurchasedList.add(splvl2.professionId)
                                    splvl2.subProfessions.forEach { splvl3->
                                        fullPurchasedList.add(splvl3.professionId)
                                        splvl3.subProfessions.forEach { splvl4->
                                            fullPurchasedList.add(splvl4.professionId)
                                        }
                                    }
                                }
                            }
                            else{
                                splvl1.subProfessions.forEach { splvl2->
                                    if(purchasedProfessionId == splvl2.professionId){
                                        fullPurchasedList.add(splvl2.professionId)
                                        splvl2.subProfessions.forEach { splvl3->
                                            fullPurchasedList.add(splvl3.professionId)
                                            splvl3.subProfessions.forEach { splvl4->
                                                fullPurchasedList.add(splvl4.professionId)
                                                splvl4.subProfessions.forEach { splvl5->
                                                    fullPurchasedList.add(splvl5.professionId)
                                                }
                                            }
                                        }
                                    }
                                    else{
                                        splvl2.subProfessions.forEach { splvl3->
                                            if(purchasedProfessionId == splvl3.professionId){
                                                fullPurchasedList.add(splvl3.professionId)
                                                splvl3.subProfessions.forEach { splvl4->
                                                    fullPurchasedList.add(splvl4.professionId)
                                                    splvl4.subProfessions.forEach { splvl5->
                                                        fullPurchasedList.add(splvl5.professionId)
                                                    }
                                                }
                                            }
                                            else{
                                                splvl3.subProfessions.forEach { splvl4->
                                                    if(purchasedProfessionId == splvl4.professionId){
                                                        fullPurchasedList.add(splvl4.professionId)
                                                        splvl4.subProfessions.forEach { splvl5->
                                                            fullPurchasedList.add(splvl5.professionId)
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if(fullPurchasedList.contains(professionId)){
                isShowBanner = false
            }
        }
        else{
            isShowBanner = true
        }
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
                        material.subProfessions.forEach { subProfession->
                            if(subProfession.professionId==professionId){
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
                    filteredMaterials.forEach { materialDomain ->
                        materialDomain.materialName

                    }
                    if(filteredMaterials.isNotEmpty()){
                        InterviewsViewState.SuccessState(isHaveConnection = isConnectionAvailable(), materials = filteredMaterials.toList(),
                            selectedFilters = filtersList.toList(), currentProfessionId = professionId, lang = lang, isShowBanner = isShowBanner)
                    }
                    else{
                        if(!isConnectionAvailable()){
                            InterviewsViewState.EmptyState(isHaveConnection = isConnectionAvailable(),
                                currentProfessionId = professionId, lang = lang)
                        }
                        else{
                            InterviewsViewState.SuccessState(isHaveConnection = isConnectionAvailable(), materials = filteredMaterials.toList(),
                                selectedFilters = filtersList.toList(), currentProfessionId = professionId, lang = lang, isShowBanner = isShowBanner)
                        }
                    }

                }
            }
        }
    }

    suspend fun sendComment(materialId: Int, comment: String){
        sendMaterialCommentUseCase.sendComment(MaterialCommentRequestDomain(materialId = materialId, comment = comment))
    }

    suspend fun getProfessionId(): Int{
        return getProfessionsUseCase.getProfessionId()
    }


    fun goToTelegram(){
        browserUtils.openInBrowser("https://t.me/+-fxZnU-zAJJkYzIy")
    }

    suspend fun isAsModeActive():Boolean{
        return getAsModeUseCase.isAsModeEnabled(isConnectionAvailable()).isAsModeActive
    }

    fun getNotFoundDataTitle() = StringResources.getScreenTitleText(getCurrentLang().lang)

    fun getNotFoundDataDescription() = StringResources.getDescriptionText(getCurrentLang().lang)
}