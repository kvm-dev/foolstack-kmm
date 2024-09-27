package ru.foolstack.registration.impl.di

import org.koin.dsl.module
import ru.foolstack.registration.api.domain.usecase.RegistrationByEmailUseCase
import ru.foolstack.registration.impl.data.repository.RegistrationRepository
import ru.foolstack.registration.impl.data.repository.network.NetworkDataSource
import ru.foolstack.registration.impl.data.repository.network.RegistrationApi
import ru.foolstack.registration.impl.domain.usecase.RegistrationByEmailUseCaseImpl
import ru.foolstack.registration.impl.mapper.Mapper

val registrationModule = module {
    single<Mapper> { Mapper() }
    single<RegistrationApi> { RegistrationApi(get()) }
    single<NetworkDataSource> { NetworkDataSource(api = get(), mapper = get()) }
    single<RegistrationRepository> { RegistrationRepository(networkDataSource = get()) }
    single<RegistrationByEmailUseCase> { RegistrationByEmailUseCaseImpl(get()) }
}