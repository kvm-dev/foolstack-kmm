package ru.foolstack.authorization.impl.di

import org.koin.dsl.module
import ru.foolstack.authorization.api.domain.usecase.AuthByEmailUseCase
import ru.foolstack.authorization.api.domain.usecase.AuthByTokenOfflineLogUseCase
import ru.foolstack.authorization.api.domain.usecase.AuthByTokenUseCase
import ru.foolstack.authorization.api.domain.usecase.ConfirmAuthAndRegUseCase
import ru.foolstack.authorization.api.domain.usecase.GetTokenFromLocalUseCase
import ru.foolstack.authorization.api.domain.usecase.GuestAuthUseCase
import ru.foolstack.authorization.api.domain.usecase.IsUserExistUseCase
import ru.foolstack.authorization.impl.data.repository.AuthorizationRepository
import ru.foolstack.authorization.impl.data.repository.local.LocalDataSource
import ru.foolstack.authorization.impl.data.repository.network.AuthorizationApi
import ru.foolstack.authorization.impl.data.repository.network.DelayedAuthByTokenLogger
import ru.foolstack.authorization.impl.data.repository.network.NetworkDataSource
import ru.foolstack.authorization.impl.domain.usecase.AuthByEmailUseCaseImpl
import ru.foolstack.authorization.impl.domain.usecase.AuthByTokenOfflineLogUseCaseImpl
import ru.foolstack.authorization.impl.domain.usecase.AuthByTokenUseCaseImpl
import ru.foolstack.authorization.impl.domain.usecase.ConfirmAuthAndRegUseCaseImpl
import ru.foolstack.authorization.impl.domain.usecase.GetTokenFromLocalUseCaseImpl
import ru.foolstack.authorization.impl.domain.usecase.GuestAuthUseCaseImpl
import ru.foolstack.authorization.impl.domain.usecase.IsUserExistUseCaseImpl
import ru.foolstack.authorization.impl.mapper.Mapper

val authorizationModule = module {
    single<Mapper> { Mapper() }
    single<DelayedAuthByTokenLogger> { DelayedAuthByTokenLogger(context = get()) }
    single<LocalDataSource> { LocalDataSource(get()) }
    single<AuthorizationApi> { AuthorizationApi(get()) }
    single<NetworkDataSource> { NetworkDataSource(api = get(), mapper = get()) }
    single<AuthorizationRepository> { AuthorizationRepository(localDataSource = get(), networkDataSource = get(), delayedAuthByTokenLogger = get()) }
    single<AuthByEmailUseCase> { AuthByEmailUseCaseImpl(get())}
    single<AuthByTokenUseCase> { AuthByTokenUseCaseImpl(get())}
    single<ConfirmAuthAndRegUseCase> { ConfirmAuthAndRegUseCaseImpl(get())}
    single<GetTokenFromLocalUseCase> { GetTokenFromLocalUseCaseImpl(get())}
    single<IsUserExistUseCase> { IsUserExistUseCaseImpl(get())}
    single<AuthByTokenOfflineLogUseCase> {AuthByTokenOfflineLogUseCaseImpl(repository = get())}
    single<GuestAuthUseCase> {GuestAuthUseCaseImpl(repository = get())}
}