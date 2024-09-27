package ru.foolstack.tests.api.domain.usecase

import ru.foolstack.tests.api.model.TestsDomain

interface GetTestsUseCase {

    suspend fun getTests(fromLocal: Boolean = false):TestsDomain

}