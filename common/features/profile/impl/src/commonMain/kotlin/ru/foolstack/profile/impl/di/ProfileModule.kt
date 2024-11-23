package ru.foolstack.profile.impl.di

import org.koin.dsl.module
import ru.foolstack.profile.api.domain.usecase.GetProfileUseCase
import ru.foolstack.profile.impl.data.repository.ProfileRepository
import ru.foolstack.profile.impl.data.repository.local.LocalDataSource
import ru.foolstack.profile.impl.data.repository.network.NetworkDataSource
import ru.foolstack.profile.impl.data.repository.network.ProfileApi
import ru.foolstack.profile.impl.domain.usecase.GetProfileUseCaseImpl
import ru.foolstack.profile.impl.mapper.Mapper

val profileModule = module {
    single<Mapper> { Mapper() }
    single<LocalDataSource> { LocalDataSource(databaseSdk = get(), mapper = get()) }
    single<ProfileApi> { ProfileApi(get()) }
    single<NetworkDataSource> { NetworkDataSource(api = get(), mapper = get()) }
    single<ProfileRepository> { ProfileRepository(
        localDataSource = get(),
        networkDataSource = get(),
        authorizationLocalDataSource = get(),
        authorizationNetworkDataSource = get()) }
    single<GetProfileUseCase> { GetProfileUseCaseImpl(get())}
}