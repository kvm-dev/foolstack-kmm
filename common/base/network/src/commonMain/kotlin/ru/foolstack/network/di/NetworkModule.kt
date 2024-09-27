package ru.foolstack.network.di

import io.ktor.client.HttpClient
import org.koin.dsl.module
import ru.foolstack.network.client

val networkModule = module {
    single<HttpClient> { client }
}