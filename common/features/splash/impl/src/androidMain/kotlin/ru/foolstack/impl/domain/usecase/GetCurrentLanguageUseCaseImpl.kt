package ru.foolstack.impl.domain.usecase

import ru.foolstack.impl.mapper.Mapper
import ru.foolstack.impl.model.LangResult
import ru.foolstack.splash.api.domain.usecase.GetCurrentLanguageUseCase
import ru.foolstack.splash.api.model.LangResultDomain
import java.util.Locale

class GetCurrentLanguageUseCaseImpl(private val mapper: Mapper):GetCurrentLanguageUseCase {
    override fun getCurrentLang(): LangResultDomain {
        return when(Locale.getDefault().toString().contains("RU")){
            true -> mapper.map(LangResult.Ru())
            false ->  mapper.map(LangResult.Eng())
        }
    }
}