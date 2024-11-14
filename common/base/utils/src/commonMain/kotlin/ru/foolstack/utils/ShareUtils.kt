package ru.foolstack.utils

expect class ShareUtils(platformContext: PlatformContext){
    fun shareLink(url: String)
}