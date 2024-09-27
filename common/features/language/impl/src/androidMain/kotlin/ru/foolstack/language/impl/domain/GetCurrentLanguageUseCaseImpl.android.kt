package ru.foolstack.language.impl.domain

import ru.foolstack.language.api.domain.GetCurrentLanguageUseCase
import ru.foolstack.language.api.model.LangResultDomain
import ru.foolstack.language.impl.mapper.Mapper
import ru.foolstack.language.impl.model.LangResult
import java.util.Locale

actual class GetCurrentLanguageUseCaseImpl actual constructor(private val mapper: Mapper) : GetCurrentLanguageUseCase{
    override fun getCurrentLang(): LangResultDomain {
        return when(Locale.getDefault().toString().contains("RU")){
            true -> mapper.map(LangResult.Ru())
            false ->  mapper.map(LangResult.Eng())
        }
    }
}