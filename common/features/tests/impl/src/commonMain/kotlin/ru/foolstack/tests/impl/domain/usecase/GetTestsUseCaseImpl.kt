package ru.foolstack.tests.impl.domain.usecase

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.foolstack.tests.api.domain.usecase.GetTestsUseCase
import ru.foolstack.tests.api.model.TestsDomain
import ru.foolstack.tests.impl.data.repository.TestsRepository
import ru.foolstack.utils.model.ResultState

class GetTestsUseCaseImpl(private val repository: TestsRepository):GetTestsUseCase {

    private val _tests = MutableStateFlow<ResultState<TestsDomain>>(
        ResultState.Idle)
    override val testsState = _tests.asStateFlow()
    override suspend fun getTests(fromLocal: Boolean): TestsDomain {
        _tests.tryEmit(ResultState.Loading)
        return if(fromLocal){
            val cachedTests = repository.getTestsFromLocal()
            _tests.tryEmit(ResultState.Success(cachedTests))
            cachedTests
        }
        else{
           val responseTests = repository.getTestsFromServer()
            _tests.tryEmit(ResultState.Success(responseTests))
            responseTests
        }
    }
}