package ru.foolstack.utils

import android.content.Intent
import android.net.Uri

actual class BrowserUtils actual constructor(platformContext: PlatformContext){
    private val context = platformContext.androidContext
    actual fun openInBrowser(url: String){
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.applicationContext.startActivity(browserIntent)
    }
}

