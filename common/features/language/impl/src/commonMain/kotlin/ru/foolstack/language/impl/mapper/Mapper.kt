package ru.foolstack.language.impl.mapper

import ru.foolstack.language.api.model.LangResultDomain
import ru.foolstack.language.impl.model.LangResult

class Mapper {
    fun map (result: LangResult): LangResultDomain {
        return when(result){
            is LangResult.Eng -> LangResultDomain.Eng()
            is LangResult.Ru -> LangResultDomain.Ru()
        }
    }
}