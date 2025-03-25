package ru.foolstack.ui.components

import android.annotation.SuppressLint
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView


@SuppressLint("SetJavaScriptEnabled")
@Composable
fun InterviewWebView(materialContent: String){
    val textColor: String
    val background: String

    val metaViewPort = "<head><meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=2, user-scalable=yes\"></head>"


    val maxImagesStyle = "<style>img{max-width: 320px}</style>\n\n";
    if(MaterialTheme.colorScheme.background.value.toString()=="18446744069414584320"){
        background = "rgba(255, 255, 255, 100);"
        textColor = "color: rgba(0, 0, 0, 100);"
    }
    else{
        background = "rgba(36, 37, 36, 100);"
        textColor = "color: rgba(255, 255, 255, 100);"
    }

    val materialContentWithTheme = "$metaViewPort <body style=\"background-color:$background $textColor\">$maxImagesStyle $materialContent</body>"
    AndroidView(factory = {
        WebView(it).apply {
            this.webChromeClient = CustomWebChromeClient()
            this.settings.setSupportZoom(true)
            this.settings.builtInZoomControls = true
            this.settings.displayZoomControls = false
            this.settings.javaScriptEnabled = true
        }
    }, update = {
        it.loadData(materialContentWithTheme, "text/html", "UTF-8");
    })
}

class CustomWebChromeClient : WebChromeClient() {


    override fun onCloseWindow(window: WebView?) {}

    override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean = false
}