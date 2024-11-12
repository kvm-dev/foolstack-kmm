package ru.foolstack.utils

expect class BrowserUtils(platformContext: PlatformContext){
    fun openInBrowser(url: String)
}