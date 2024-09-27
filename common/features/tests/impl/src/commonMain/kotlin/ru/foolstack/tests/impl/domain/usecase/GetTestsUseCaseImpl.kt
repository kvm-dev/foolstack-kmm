package ru.foolstack.tests.impl.domain.usecase

import ru.foolstack.tests.api.domain.usecase.GetTestsUseCase
import ru.foolstack.tests.api.model.TestsDomain
import ru.foolstack.tests.impl.data.repository.TestsRepository

class GetTestsUseCaseImpl(private val repository: TestsRepository):GetTestsUseCase {
    override suspend fun getTests(fromLocal: Boolean): TestsDomain {
        return if(fromLocal){
            repository.getTestsFromLocal()
        }
        else{
            repository.getTestsFromServer()
        }
    }
}