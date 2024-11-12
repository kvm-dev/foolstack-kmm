package ru.foolstack.utils

actual class BrowserUtils actual constructor(platformContext: PlatformContext){
    val context = platformContext
    actual fun openInBrowser(url: String) {
    }
}