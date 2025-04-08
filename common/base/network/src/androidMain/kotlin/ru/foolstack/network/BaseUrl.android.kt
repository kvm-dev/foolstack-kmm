package ru.foolstack.network

actual val baseUrl: String
    get() =
    when(BuildConfig.BUILD_TYPE){
        "debug"->  "https://test.foolstack.ru/api/v1/"
//        "debug"->  "https://foolstack.ru/api/v1/"
//        "release"-> "https://foolstack.ru/api/v1/"
                "release"-> "https://test.foolstack.ru/api/v1/"
//        else -> "https://foolstack.ru/api/v1/"
        else -> "https://test.foolstack.ru/api/v1/"
    }