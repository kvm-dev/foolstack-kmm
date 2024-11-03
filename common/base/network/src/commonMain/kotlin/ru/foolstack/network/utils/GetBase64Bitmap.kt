package ru.foolstack.network.utils

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.readBytes
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.encodeBase64
import ru.foolstack.network.baseUrl
import ru.foolstack.network.client
import ru.foolstack.network.exceptionHandler
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

//suspend fun getBase64Bitmap(url: String) = client.get(url).readBytes().toBase64()
suspend fun getBase64Bitmap(url: String):String{
    return try {
        client.get(url).readBytes().toBase64()
    }
    catch (e: Throwable){
        return ""
    }
}

@OptIn(ExperimentalEncodingApi::class)
private fun ByteArray.toBase64(): String{
    return if(this.isNotEmpty()){
        Base64.encode(this)
    }
    else{
        ""
    }
}