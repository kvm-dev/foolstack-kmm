package ru.foolstack.utils

import android.content.Intent

actual class ShareUtils actual constructor(platformContext: PlatformContext) {
    private val context = platformContext.androidContext
    actual fun shareLink(url: String) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, url)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, "foolStack")
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.applicationContext.startActivity(shareIntent)
    }
}