package ru.foolstack.network

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.KotlinxSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import ru.foolstack.network.model.UserType
import java.util.Locale

@OptIn(ExperimentalSerializationApi::class)
actual val client: HttpClient
    get() = HttpClient(OkHttp) {
    //Timeout plugin to set up timeout milliseconds for client
    install(HttpTimeout) {
        socketTimeoutMillis = 180_000
        requestTimeoutMillis = 180_000
    }
    //Logging plugin combined with kermit(KMP Logger library)
    install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.ALL
        logger = object: Logger {
            override fun log(message: String) {
                Log.i("KtorClient", message)
            }
        }
    }

        install(ResponseObserver) {
            onResponse { response ->
                Log.d("HTTP status:", "${response.status.value}")
            }
        }

        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            header("Local", if(Locale.getDefault().toString().contains("RU")){"RU"} else {"ENG"})
            header("Platform", getPlatform().name.lowercase(Locale.ROOT).split(" ")[0])
            header("Version", BuildConfig.VERSION_NAME)
            header(UserType.Client().key, UserType.Client().type)
        }
        install(ContentNegotiation) {
            register(
                ContentType.Application.Json, KotlinxSerializationConverter(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                        explicitNulls = false
                    }
                )
            )
        }
}