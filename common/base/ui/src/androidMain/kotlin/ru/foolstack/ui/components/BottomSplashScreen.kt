package ru.foolstack.ui.components

import android.os.CountDownTimer
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import ru.foolstack.ui.theme.BottomScreenBackground

@Composable
fun BottomSplashScreen(bitmap: ImageBitmap, onclickLogin: ()->Unit, onclickGuest: ()->Unit, titleText: String, baseText:String, generalButtonText: String, guestButtonText: String){
    var offset by remember { mutableFloatStateOf(0f) }
    var isAnimated by remember { mutableStateOf(false) }
    val transition = updateTransition(targetState = isAnimated, label = "transition")
    val bottomHeight by transition.animateDp(transitionSpec = {
        tween(3*1000)
    }, "") { animated ->
        if (!animated) 0.dp else 1000.dp
    }
    val shape = RoundedCornerShape(32.dp, 32.dp)
    Column(modifier = Modifier
        .fillMaxSize()
        .scrollable(
            orientation = Orientation.Vertical,
            state = rememberScrollableState { delta ->
                offset += delta
                delta
            }
        ),
        verticalArrangement = Arrangement.Bottom) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(bottomHeight)
                .clip(shape)
                .background(MaterialTheme.colorScheme.BottomScreenBackground),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(110.dp, 10.dp),
                bitmap = bitmap,
                contentDescription = "FoolStack"
            )
            Spacer(modifier = Modifier.height(10.dp))
            Title(text = titleText)
            Spacer(modifier = Modifier.height(10.dp))
            BaseText(text = baseText)
            Spacer(modifier = Modifier.weight(1f))
            GeneralButton(onClickLogin = onclickLogin, text = generalButtonText)
            SplashGuestButton(guestButtonText, onClickGuest = { /*TODO*/ })
        }
    }

    val timer = object : CountDownTimer(1000, 500) {
        override fun onTick(seconds: Long) {
            isAnimated = true
            cancel()
        }
        override fun onFinish() {
            cancel()
        }
    }
    timer.start()
}