package ru.foolstack.impl.mapper

import ru.foolstack.impl.model.LangResult
import ru.foolstack.impl.model.TokenValidateResult
import ru.foolstack.splash.api.model.LangResultDomain
import ru.foolstack.splash.api.model.TokenValidateResultDomain

class Mapper {
    fun map (result: LangResult):LangResultDomain{
        return when(result){
            is LangResult.Eng -> LangResultDomain.Eng()
            is LangResult.Ru -> LangResultDomain.Ru()
        }
    }

    fun map(result: TokenValidateResult): TokenValidateResultDomain {
        return when(result){
            TokenValidateResult.TokenIsNotFound -> TokenValidateResultDomain.TokenIsNotFound
            TokenValidateResult.TokenIsExpired -> TokenValidateResultDomain.TokenIsExpired
            TokenValidateResult.TokenIsNotValid -> TokenValidateResultDomain.TokenIsNotValid
            is TokenValidateResult.TokenIsValid -> TokenValidateResultDomain.TokenIsValid(result.tokenId)
        }
    }
}