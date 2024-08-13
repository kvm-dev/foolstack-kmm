package ru.foolstack.splash.api.model

sealed class TokenValidateResultDomain{
    data object TokenIsNotFound:TokenValidateResultDomain()
    data object TokenIsExpired:TokenValidateResultDomain()
    data object TokenIsNotValid:TokenValidateResultDomain()
    data class TokenIsValid(val tokenId: Int):TokenValidateResultDomain()
}