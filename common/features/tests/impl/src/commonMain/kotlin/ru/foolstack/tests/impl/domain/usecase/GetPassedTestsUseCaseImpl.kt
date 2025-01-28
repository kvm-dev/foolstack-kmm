package ru.foolstack.tests.impl.domain.usecase

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.foolstack.tests.api.domain.usecase.GetPassedTestsUseCase
import ru.foolstack.tests.api.model.PassedTestsDomain
import ru.foolstack.tests.impl.data.repository.TestsRepository
import ru.foolstack.utils.model.ResultState

class GetPassedTestsUseCaseImpl(private val repository: TestsRepository): GetPassedTestsUseCase {

    private val _passedTestsState = MutableStateFlow<ResultState<PassedTestsDomain>>(
        ResultState.Idle)
    override val passedTestsState = _passedTestsState.asStateFlow()
    override suspend fun getPassedTests(fromLocal: Boolean): PassedTestsDomain {
        _passedTestsState.tryEmit(ResultState.Loading)
        return if(fromLocal){
            val cachedPassedTests = repository.getPassedTestsFromLocal()
            _passedTestsState.tryEmit(ResultState.Success(cachedPassedTests))
            cachedPassedTests
        }
        else{
            val responsePassedTests = repository.getPassedTestsFromServer()
            _passedTestsState.tryEmit(ResultState.Success(responsePassedTests))
            responsePassedTests
        }
    }
}