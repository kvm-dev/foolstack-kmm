package ru.foolstack.tests.impl.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module
import ru.foolstack.tests.api.domain.usecase.GetPassedTestsUseCase
import ru.foolstack.tests.api.domain.usecase.GetTestsUseCase
import ru.foolstack.tests.api.domain.usecase.SendTestResultUseCase
import ru.foolstack.tests.impl.data.repository.TestsRepository
import ru.foolstack.tests.impl.data.repository.local.LocalDataSource
import ru.foolstack.tests.impl.data.repository.network.NetworkDataSource
import ru.foolstack.tests.impl.data.repository.network.TestsApi
import ru.foolstack.tests.impl.domain.interactor.TestsInteractor
import ru.foolstack.tests.impl.domain.usecase.GetPassedTestsUseCaseImpl
import ru.foolstack.tests.impl.domain.usecase.GetTestsUseCaseImpl
import ru.foolstack.tests.impl.domain.usecase.SendTestResultUseCaseImpl
import ru.foolstack.tests.impl.mapper.Mapper
import ru.foolstack.tests.impl.presentation.viewmodel.TestsViewModel

actual val testsModule: Module
    get()  = module {
    single<Mapper> { Mapper() }
    single<LocalDataSource> { LocalDataSource(databaseSdk = get(), mapper = get()) }
    single<TestsApi> { TestsApi(get()) }
    single<NetworkDataSource> { NetworkDataSource(api = get(), mapper = get()) }
    single<TestsRepository> { TestsRepository(
        localDataSource = get(),
        networkDataSource = get(),
        authorizationNetworkDataSource = get(),
        authorizationLocalDataSource = get(),
        mapper = get()) }
    single<GetTestsUseCase> { GetTestsUseCaseImpl(get()) }
    single<GetPassedTestsUseCase> { GetPassedTestsUseCaseImpl(repository = get()) }
    single<SendTestResultUseCase> { SendTestResultUseCaseImpl(repository = get())}
    single<TestsInteractor> { TestsInteractor(
        getCurrentLanguageUseCase = get(),
        getNetworkStateUseCase = get(),
        getTestsUseCase = get(),
        getPassedTestsUseCase = get(),
        sendTestResultUseCase = get(),
        getProfessionsUseCase = get(),
        getProfileUseCase = get()
    )}
        viewModelOf(::TestsViewModel)
}