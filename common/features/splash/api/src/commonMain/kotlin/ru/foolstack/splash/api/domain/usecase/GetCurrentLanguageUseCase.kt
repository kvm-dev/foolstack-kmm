package ru.foolstack.splash.api.domain.usecase

import ru.foolstack.splash.api.model.LangResultDomain

interface GetCurrentLanguageUseCase {
    fun getCurrentLang(): LangResultDomain
}