package ru.foolstack.authorization.impl.data.repository.network

object AuthorizationEndpoints {
    val isUserExist: String
        get() = "auth/is-user-exist/"

    val authByToken: String
        get() = "auth/token/"

    val refreshToken: String
        get() = "auth/refresh-token/"

    val confirmAuthAndReg: String
        get() = "confirm-auth-reg/confirm-email/"

    val authorizationByEmail: String
        get() = "auth/email/"
}