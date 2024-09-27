package ru.foolstack.language.api.domain

import ru.foolstack.language.api.model.LangResultDomain

interface GetCurrentLanguageUseCase {
    fun getCurrentLang(): LangResultDomain
}