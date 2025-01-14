package ru.foolstack.tests.api.domain.usecase

import kotlinx.coroutines.flow.StateFlow
import ru.foolstack.tests.api.model.TestsDomain
import ru.foolstack.utils.model.ResultState

interface GetTestsUseCase {

    val testsState: StateFlow<ResultState<TestsDomain>>

    suspend fun getTests(fromLocal: Boolean = false):TestsDomain

    suspend fun getTestsByProfession(professionId: Int, fromLocal: Boolean = false): TestsDomain

}