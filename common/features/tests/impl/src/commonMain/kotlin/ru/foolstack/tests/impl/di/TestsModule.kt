package ru.foolstack.tests.impl.di

import org.koin.core.module.Module
import org.koin.dsl.module
import ru.foolstack.tests.api.domain.usecase.GetTestsUseCase
import ru.foolstack.tests.impl.data.repository.TestsRepository
import ru.foolstack.tests.impl.data.repository.local.LocalDataSource
import ru.foolstack.tests.impl.data.repository.network.NetworkDataSource
import ru.foolstack.tests.impl.data.repository.network.TestsApi
import ru.foolstack.tests.impl.domain.usecase.GetTestsUseCaseImpl
import ru.foolstack.tests.impl.mapper.Mapper

expect val testsModule: Module