package ru.foolstack.network

import io.ktor.http.HttpStatusCode

 fun exceptionHandler(statusCode: HttpStatusCode):String{
        return when (statusCode) {
            HttpStatusCode.BadRequest -> NetworkError.BadRequest().message
            HttpStatusCode.Unauthorized -> NetworkError.Unauthorized().message
            HttpStatusCode.Forbidden -> NetworkError.Forbidden().message
            HttpStatusCode.NotFound -> NetworkError.NotFound().message
            HttpStatusCode.RequestTimeout -> NetworkError.RequestTimeOut().message
            HttpStatusCode.InternalServerError -> NetworkError.InternalServerError().message
            HttpStatusCode.ExpectationFailed -> NetworkError.ExpectationFailed().message
            else->NetworkError.Unauthorized().message
        }
    }
