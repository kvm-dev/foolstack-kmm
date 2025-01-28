package ru.foolstack.tests.api.domain.usecase

import kotlinx.coroutines.flow.StateFlow
import ru.foolstack.tests.api.model.PassedTestsDomain
import ru.foolstack.utils.model.ResultState

interface GetPassedTestsUseCase {

    val passedTestsState: StateFlow<ResultState<PassedTestsDomain>>

    suspend fun getPassedTests(fromLocal: Boolean = false): PassedTestsDomain

}