package ru.foolstack.ui.components

import android.content.res.Resources.Theme
import android.util.Log
import android.view.ViewGroup
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun InterviewWebView(materialContent: String){
    val background = if(MaterialTheme.colorScheme.background.value.toString()=="18446744069414584320"){
        "rgba(255, 255, 255, 100);"
    }
    else{
        "rgba(165, 183, 163, 100);"
    }

    val materialContentWithTheme = "<body style=\"background-color:$background\">$materialContent</body>"
    AndroidView(factory = {
        WebView(it).apply {
            this.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            this.webChromeClient = CustomWebChromeClient()
            this.settings.setSupportZoom(true)
            this.settings.builtInZoomControls = true
            this.settings.displayZoomControls = false
        }
    }, update = {
        it.loadData(materialContentWithTheme, "text/html", "UTF-8");
    })
}

class CustomWebChromeClient : WebChromeClient() {
    override fun onCloseWindow(window: WebView?) {}

    override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean = false
}