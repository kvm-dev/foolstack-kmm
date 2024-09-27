package ru.foolstack.network.ext

import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
fun ByteArray.toBase64(): String {
    return Base64.encode(this, startIndex = 0, endIndex = this.size)
}