package ru.foolstack.network.utils

import io.ktor.client.request.get
import io.ktor.client.statement.readBytes
import ru.foolstack.network.client
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

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