package ru.foolstack.tests.impl.domain.interactor

import ru.foolstack.language.api.domain.GetCurrentLanguageUseCase
import ru.foolstack.networkconnection.api.domain.GetNetworkStateUseCase
import ru.foolstack.professions.api.domain.usecase.GetProfessionsUseCase
import ru.foolstack.professions.api.model.ProfessionsDomain
import ru.foolstack.profile.api.domain.usecase.GetProfileUseCase
import ru.foolstack.profile.api.model.ProfileDomain
import ru.foolstack.tests.api.domain.usecase.GetPassedTestsUseCase
import ru.foolstack.tests.api.domain.usecase.GetTestsUseCase
import ru.foolstack.tests.api.domain.usecase.SendTestResultUseCase
import ru.foolstack.tests.api.model.PassedTestDomain
import ru.foolstack.tests.api.model.PassedTestsDomain
import ru.foolstack.tests.api.model.SendRequestDomain
import ru.foolstack.tests.api.model.TestsDomain
import ru.foolstack.tests.impl.data.resources.StringResources
import ru.foolstack.tests.impl.presentation.ui.TestsViewState
import ru.foolstack.utils.model.ResultState

class TestsInteractor(
    private val getCurrentLanguageUseCase: GetCurrentLanguageUseCase,
    private val getNetworkStateUseCase: GetNetworkStateUseCase,
    private val getTestsUseCase: GetTestsUseCase,
    private val getPassedTestsUseCase: GetPassedTestsUseCase,
    private val sendTestResultUseCase: SendTestResultUseCase,
    private val getProfessionsUseCase: GetProfessionsUseCase,
    getProfileUseCase: GetProfileUseCase
){
    val testsState = getTestsUseCase.testsState
    val profileState = getProfileUseCase.profileState
    val professionsState = getProfessionsUseCase.professionsState
    val passedTestsState = getPassedTestsUseCase.passedTestsState
    fun getCurrentLang() = getCurrentLanguageUseCase.getCurrentLang()

    fun isConnectionAvailable() = getNetworkStateUseCase.isNetworkAvailable()

    suspend fun getTestsFromServer() = getTestsUseCase.getTestsByProfession(professionId = getProfessionId())

    suspend fun getTestsFromLocal() = getTestsUseCase.getTests(fromLocal = true)

    suspend fun checkState(testsState: ResultState<TestsDomain>,
                   passedTestsState: ResultState<PassedTestsDomain>,
                   profileState: ResultState<ProfileDomain>,
                   professionsState: ResultState<ProfessionsDomain>,
                   professionId: Int):TestsViewState{
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

        when(testsState){
            is ResultState.Loading->{
                return TestsViewState.LoadingState(lang = lang)
            }
            is ResultState.Idle->{
                return TestsViewState.LoadingState(lang = lang)
            }
            is ResultState.Success->{
                return if(testsState.data?.errorMsg?.isNotEmpty() == true){
                    TestsViewState.ErrorState(lang = lang)
                } else{
                    val passedTests = ArrayList<PassedTestDomain>()
                    if(passedTestsState is ResultState.Success){
                        passedTestsState.data?.passedTests?.forEach { passedTest->
                            passedTests.add(passedTest)
                        }
                    }
                    if(testsState.data?.tests?.isNotEmpty() == true){
                        TestsViewState.SuccessState(
                            isHaveConnection = isConnectionAvailable(),
                            tests = testsState.data?.tests?: listOf(),
                            lang = lang,
                            currentProfessionId = professionId,
                            passedTests = passedTests)
                    }
                    else{
                        if(!isConnectionAvailable()){
                            TestsViewState.EmptyState(
                                isHaveConnection = isConnectionAvailable(),
                                lang = lang,
                                currentProfessionId = professionId)
                        }
                        else{
                            TestsViewState.SuccessState(
                                isHaveConnection = isConnectionAvailable(),
                                tests = testsState.data?.tests?: listOf(),
                                lang = lang,
                                currentProfessionId = professionId,
                                passedTests = passedTests)
                        }
                    }

                }
            }
        }
    }

    suspend fun sendResult(testId: Int, testResult: Int){
        sendTestResultUseCase.sendTestResult(SendRequestDomain(
            testId = testId, testResult = testResult
        ))
    }

    suspend fun getPassedTestsFromServer(){
        getPassedTestsUseCase.getPassedTests()
    }

    suspend fun getPassedTestsFromLocal(){
        getPassedTestsUseCase.getPassedTests(fromLocal = true)
    }

    suspend fun getProfessionId(): Int{
        return getProfessionsUseCase.getProfessionId()
    }

    fun getNotFoundDataTitle() = StringResources.getScreenTitleText(getCurrentLang().lang)

    fun getNotFoundDataDescription() = StringResources.getDescriptionText(getCurrentLang().lang)

}