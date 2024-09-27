package ru.foolstack.language.impl.di

import org.koin.dsl.module
import ru.foolstack.language.api.domain.GetCurrentLanguageUseCase
import ru.foolstack.language.impl.domain.GetCurrentLanguageUseCaseImpl
import ru.foolstack.language.impl.mapper.Mapper

val languageModule = module {
    single <Mapper> { Mapper() }
    single<GetCurrentLanguageUseCase> { GetCurrentLanguageUseCaseImpl( mapper = get()) }
}