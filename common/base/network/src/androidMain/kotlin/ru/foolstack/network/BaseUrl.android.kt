package ru.foolstack.network

actual val baseUrl: String
    get() =
    when(BuildConfig.BUILD_TYPE){
        "debugDev"->  "https://test.foolstack.ru/api/v1/"
        "releaseDev"-> "https://test.foolstack.ru/api/v1/"
        "debug"->  "https://test.foolstack.ru/api/v1/"
//        "debug"->  "https://foolstack.ru/api/v1/"
        "release"-> "https://foolstack.ru/api/v1/"
        else -> "https://foolstack.ru/api/v1/"
    }