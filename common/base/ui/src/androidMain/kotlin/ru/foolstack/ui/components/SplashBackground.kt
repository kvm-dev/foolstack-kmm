package ru.foolstack.ui.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.FloatState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.lifecycle.Lifecycle
import ru.foolstack.ui.theme.FoolStackTheme
import ru.foolstack.ui.theme.GradientColorSplash0
import ru.foolstack.ui.theme.GradientColorSplash2

@Composable
fun SplashBackground(){
    FoolStackTheme {
        val infiniteTransition = rememberInfiniteTransition(label = "infiniteTransition")

        val offset by infiniteTransition.animateFloat(
            initialValue = 200f,
            targetValue = 4000f,
            animationSpec = infiniteRepeatable(tween(30*1500, easing = LinearOutSlowInEasing)), label = "splashOffset"
        )

        val gradients = listOf(MaterialTheme.colorScheme.GradientColorSplash0, MaterialTheme.colorScheme.GradientColorSplash2)
        val brush = Brush.linearGradient(
            gradients,
            start = Offset(offset, offset),
            end = Offset(offset + 1000f, offset + 1000f),
            tileMode = TileMode.Mirror
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = brush)
        )
    }
}