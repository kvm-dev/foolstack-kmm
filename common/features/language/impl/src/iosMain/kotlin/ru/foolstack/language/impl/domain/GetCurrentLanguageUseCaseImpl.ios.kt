package ru.foolstack.language.impl.domain

import ru.foolstack.language.api.domain.GetCurrentLanguageUseCase
import ru.foolstack.language.api.model.LangResultDomain
import ru.foolstack.language.impl.mapper.Mapper
import ru.foolstack.language.impl.model.LangResult

actual class GetCurrentLanguageUseCaseImpl actual constructor(private val mapper: Mapper) :
    GetCurrentLanguageUseCase {
    override fun getCurrentLang(): LangResultDomain {
       return mapper.map(LangResult.Ru())
    }
}