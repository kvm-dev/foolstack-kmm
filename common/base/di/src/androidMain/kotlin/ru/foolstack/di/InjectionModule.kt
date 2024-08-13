package ru.foolstack.di

import org.koin.dsl.module
import ru.foolstack.authorization.impl.di.authorizationModule
import ru.foolstack.impl.di.splashModule

val injectionModule = module {
//    includes(splashModule, localModule, networkModule, localStorageModule)
    includes(authorizationModule, splashModule)
}