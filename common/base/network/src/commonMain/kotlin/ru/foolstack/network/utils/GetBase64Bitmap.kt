package ru.foolstack.network.utils

import io.ktor.client.request.get
import io.ktor.client.statement.readBytes
import io.ktor.util.encodeBase64
import ru.foolstack.network.client
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

suspend fun getBase64Bitmap(url: String) = client.get(url).readBytes().toBase64()
@OptIn(ExperimentalEncodingApi::class)
private fun ByteArray.toBase64(): String = Base64.encode(this)