package ru.foolstack.tests.api.domain.usecase

import ru.foolstack.tests.api.model.SendRequestDomain
import ru.foolstack.tests.api.model.SendResultDomain

interface SendTestResultUseCase {

    suspend fun sendTestResult(request: SendRequestDomain): SendResultDomain
}