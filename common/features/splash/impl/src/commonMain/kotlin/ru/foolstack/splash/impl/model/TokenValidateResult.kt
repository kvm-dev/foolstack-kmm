package ru.foolstack.splash.impl.model

sealed class TokenValidateResult{
    data object TokenIsNotFound: TokenValidateResult()
    data object TokenIsExpired: TokenValidateResult()
    data object TokenIsNotValid: TokenValidateResult()
    data class TokenIsValid(val tokenId: Int): TokenValidateResult()
}