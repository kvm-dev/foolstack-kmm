package ru.foolstack.di

import org.koin.core.module.Module
import org.koin.dsl.module

actual val injectionModule: Module
    get() = module {
//        includes(languageModule)
    }