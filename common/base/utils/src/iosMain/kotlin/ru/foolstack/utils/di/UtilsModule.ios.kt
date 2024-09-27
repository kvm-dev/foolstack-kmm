package ru.foolstack.utils.di

import org.koin.core.module.Module
import org.koin.dsl.module
import ru.foolstack.utils.PlatformContext

actual val utilsModule: Module
    get() =  module {
        single<PlatformContext>{ PlatformContext() }
    }