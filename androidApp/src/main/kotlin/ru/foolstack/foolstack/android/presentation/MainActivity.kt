package ru.foolstack.foolstack.android.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import ru.foolstack.foolstack.android.FoolStackTheme
import ru.foolstack.navigation.StartApplication
import ru.foolstack.storage.prefs.EncryptedPreferences
import ru.foolstack.utils.PlatformContext


class MainActivity : ComponentActivity() {
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //edge2Edge mode, fullscreen
        enableEdgeToEdge()
//        val insetsController = WindowCompat.getInsetsController(window, window.decorView)
//        insetsController.apply {
//            hide(WindowInsetsCompat.Type.statusBars())
//            hide(WindowInsetsCompat.Type.navigationBars())
//            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//        }
        setContent {
            FoolStackTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .safeDrawingPadding(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    StartApplication(theme = getCurrentTheme(), recreate = { recreate()}, restart = {restart()})
                }
            }
        }
        hideSystemUI()
    }

    private fun hideSystemUI() {

        //Hides the ugly action bar at the top
        actionBar?.hide()

        //Hide the status bars

        WindowCompat.setDecorFitsSystemWindows(window, false)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        } else {
            window.insetsController?.apply {
                hide(WindowInsets.Type.statusBars())
                systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
    }

    private fun getCurrentTheme(): String{
        val currentTheme  = EncryptedPreferences(PlatformContext(applicationContext)).getCurrentAppTheme()
        if(currentTheme.isEmpty()){
            EncryptedPreferences(PlatformContext(applicationContext)).setCurrentAppTheme("light")
        }
        return EncryptedPreferences(PlatformContext(applicationContext)).getCurrentAppTheme()
    }

    private fun restart() {
        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)
        this.onDestroy()
        finishAffinity()
    }
}

@Composable
fun GreetingView(text: String) {
    Text(text = text)
}

@Preview
@Composable
fun DefaultPreview() {
    FoolStackTheme {
        GreetingView("Hello, Android!")
    }
}
