package ru.foolstack.events.impl.di

import org.koin.dsl.module
import ru.foolstack.events.api.domain.usecase.GetEventsUseCase
import ru.foolstack.events.impl.data.repository.EventsRepository
import ru.foolstack.events.impl.data.repository.local.LocalDataSource
import ru.foolstack.events.impl.data.repository.network.EventsApi
import ru.foolstack.events.impl.data.repository.network.NetworkDataSource
import ru.foolstack.events.impl.domain.usecase.GetEventsUseCaseImpl
import ru.foolstack.events.impl.mapper.Mapper

val eventsModule = module {
    single<Mapper> { Mapper() }
    single<EventsApi> { EventsApi(get()) }
    single<NetworkDataSource> { NetworkDataSource(api = get(), mapper = get()) }
    single<LocalDataSource> { LocalDataSource(databaseSdk = get(), mapper = get()) }
    single<EventsRepository> { EventsRepository(
        networkDataSource = get(), localDataSource = get()) }
    single<GetEventsUseCase> { GetEventsUseCaseImpl(get())}
}