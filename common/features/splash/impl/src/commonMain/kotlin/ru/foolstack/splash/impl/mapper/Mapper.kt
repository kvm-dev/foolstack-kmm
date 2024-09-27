package ru.foolstack.splash.impl.mapper

import ru.foolstack.splash.impl.model.TokenValidateResult
import ru.foolstack.splash.api.model.TokenValidateResultDomain

class Mapper {
    fun map(result: TokenValidateResult): TokenValidateResultDomain {
        return when(result){
            TokenValidateResult.TokenIsNotFound -> TokenValidateResultDomain.TokenIsNotFound
            TokenValidateResult.TokenIsExpired -> TokenValidateResultDomain.TokenIsExpired
            TokenValidateResult.TokenIsNotValid -> TokenValidateResultDomain.TokenIsNotValid
            is TokenValidateResult.TokenIsValid -> TokenValidateResultDomain.TokenIsValid(result.tokenId)
        }
    }
}