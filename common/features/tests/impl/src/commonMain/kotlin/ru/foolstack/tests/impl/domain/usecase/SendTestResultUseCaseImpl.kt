package ru.foolstack.tests.impl.domain.usecase

import ru.foolstack.tests.api.domain.usecase.SendTestResultUseCase
import ru.foolstack.tests.api.model.SendRequestDomain
import ru.foolstack.tests.api.model.SendResultDomain
import ru.foolstack.tests.impl.data.repository.TestsRepository

class SendTestResultUseCaseImpl(private val repository: TestsRepository):
    SendTestResultUseCase {

    override suspend fun sendTestResult(
        request: SendRequestDomain,
        toLocal: Boolean
    ): SendResultDomain {
        if(toLocal){
            return repository.sendTestResultToLocal(requestDomain =  request)

        }
        else{
          return  repository.sendTestResultToServer(requestDomain = request)
        }
    }
}