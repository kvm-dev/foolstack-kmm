package ru.foolstack.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = MainGreenLight,
    secondary = MainGreenDark,
    background = MainBackgroundDark
)

private val LightColorScheme = lightColorScheme(
    primary = MainGreenLight,
    secondary = MainOrangeLight,
    background = MainBackgroundLight,
)
//extraColor
var MainGreenLightColorSchemeColor by mutableStateOf(MainGreenLight)
var MainOrangeColorSchemeColor by mutableStateOf(MainOrangeLight)
var DisabledColorSchemeColor by mutableStateOf(DisabledColorLight)
var SplashGradient0ColorSchemeColor by mutableStateOf(GreenColor1)
var SplashGradient1ColorSchemeColor by mutableStateOf(GreenColor2)
var SplashGradient2ColorSchemeColor by mutableStateOf(GreenColor3)
var BottomScreenBackgroundColorSchemeColor by mutableStateOf(BottomScreenLight)
var BaseTextColorSchemeColor by mutableStateOf(BaseTextColorLight)
var EnabledButtonSecondContentSchemeColor by mutableStateOf(EnabledButtonSecondContentColorLight)
var EnabledButtonSecondBackgroundColorSchemeColor by mutableStateOf(EnabledButtonSecondBackgroundLight)
var EnabledButtonContentColorSchemeColor by mutableStateOf(ContentButtonEnabledLight)
var DisabledButtonContentColorSchemeColor by mutableStateOf(ContentButtonDisabledLight)
var FieldsDefaultBorderColorSchemeColor by mutableStateOf(FieldsDefaultBorderColorLight)
var ErrorSchemeColor by mutableStateOf(ErrorColorLight)
var FieldsPlaceholderSchemeColor by mutableStateOf(FieldsPlaceholderColorLight)
var PrimaryTitleSchemeColor by mutableStateOf(PrimaryTitleColorLight)
var MainYellowSchemeColor by mutableStateOf(MainYellowColorLight)
var DisabledButtonTextSchemeColor by mutableStateOf(DisabledButtonTextColorLight)
var LoadingIndicatorSchemeColor by mutableStateOf(LoadingIndicatorColorLight)
var NavigationDisabledSchemeColor by mutableStateOf(NavigationDisabledLight)
var NavigationSelectedSchemeColor by mutableStateOf(NavigationSelectedLight)
var MainBackgroundSchemeColor by mutableStateOf(MainBackgroundLight)
var DividerSchemeColor by mutableStateOf(DividerLightColor)



@Suppress("unused")
var ColorScheme.MainGreen: Color
    get() = MainGreenLightColorSchemeColor
    set(value) {
        MainGreenLightColorSchemeColor = value
    }
var ColorScheme.MainOrange: Color
    get() = MainOrangeColorSchemeColor
    set(value) {
        MainOrangeColorSchemeColor = value
    }
var ColorScheme.DisabledColor: Color
    get() = DisabledColorSchemeColor
    set(value) {
        DisabledColorSchemeColor = value
    }

var ColorScheme.GradientColorSplash0: Color
    get() = SplashGradient0ColorSchemeColor
    set(value) {
        SplashGradient0ColorSchemeColor = value
    }
var ColorScheme.GradientColorSplash1: Color
    get() = SplashGradient1ColorSchemeColor
    set(value) {
        SplashGradient1ColorSchemeColor = value
    }

var ColorScheme.GradientColorSplash2: Color
    get() = SplashGradient2ColorSchemeColor
    set(value) {
        SplashGradient2ColorSchemeColor = value
    }

var ColorScheme.BottomScreenBackground: Color
    get() = BottomScreenBackgroundColorSchemeColor
    set(value) {
        BottomScreenBackgroundColorSchemeColor = value
    }

var ColorScheme.BaseText: Color
    get() = BaseTextColorSchemeColor
    set(value) {
        BaseTextColorSchemeColor = value
    }

var ColorScheme.EnabledButtonSecondContentColor
    get() = EnabledButtonSecondContentSchemeColor
    set(value) {
    EnabledButtonSecondContentSchemeColor = value
    }

var ColorScheme.EnabledButtonSecondBackground: Color
    get() = EnabledButtonSecondBackgroundColorSchemeColor
    set(value) {
        EnabledButtonSecondBackgroundColorSchemeColor = value
    }

var ColorScheme.EnabledButtonContent: Color
    get() = EnabledButtonContentColorSchemeColor
    set(value) {
        EnabledButtonContentColorSchemeColor = value
    }

var ColorScheme.FieldsDefaultBorderColor: Color
    get() = FieldsDefaultBorderColorSchemeColor
    set(value) {
        FieldsDefaultBorderColorSchemeColor = value
    }

var ColorScheme.DisabledButtonContent: Color
    get() = DisabledButtonContentColorSchemeColor
    set(value) {
        DisabledButtonContentColorSchemeColor = value
    }

var ColorScheme.ErrorColor: Color
    get() = ErrorSchemeColor
    set(value) {
        ErrorSchemeColor = value
    }

var ColorScheme.FieldsPlaceholderColor: Color
    get() = FieldsPlaceholderSchemeColor
    set(value) {
        FieldsPlaceholderSchemeColor = value
    }

var ColorScheme.PrimaryTitleColor: Color
    get() = PrimaryTitleSchemeColor
    set(value) {
        PrimaryTitleSchemeColor = value
    }
var ColorScheme.MainYellow: Color
    get() = MainYellowSchemeColor
    set(value) {
        MainYellowSchemeColor = value
    }

var ColorScheme.DisabledButtonTextColor: Color
    get() = DisabledButtonTextSchemeColor
    set(value) {
        DisabledButtonTextSchemeColor = value
    }

var ColorScheme.LoadingIndicatorColor: Color
    get() = LoadingIndicatorSchemeColor
    set(value) {
        LoadingIndicatorSchemeColor = value
    }
var ColorScheme.NavigationDisabled: Color
    get() = NavigationDisabledSchemeColor
    set(value) {
        NavigationDisabledSchemeColor = value
    }

var ColorScheme.NavigationSelected: Color
    get() = NavigationSelectedSchemeColor
    set(value) {
        NavigationSelectedSchemeColor = value
    }

var ColorScheme.MainBackground: Color
    get() = MainBackgroundSchemeColor
    set(value) {
        MainBackgroundSchemeColor = value
    }

var ColorScheme.Divider: Color
    get() = DividerSchemeColor
    set(value) {
        DividerSchemeColor = value
    }





@Composable
fun FoolStackTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
//    dynamicColor: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )

    //extra colors
    colorScheme.MainGreen = if (darkTheme) {
        MainGreenDark
    } else {
        MainGreenLight
    }

    colorScheme.MainOrange = if (darkTheme) {
        MainOrangeDark
    } else {
        MainOrangeDark
    }

    colorScheme.DisabledColor = if (darkTheme) {
        DisabledColorDark
    } else {
        DisabledColorLight
    }

    colorScheme.GradientColorSplash0 = if (darkTheme) {
        GreenColor1
    } else {
        GreenColor1
    }

    colorScheme.GradientColorSplash1 = if (darkTheme) {
        GreenColor2
    } else {
        GreenColor2
    }

    colorScheme.GradientColorSplash2 = if (darkTheme) {
        GreenColor3
    } else {
        GreenColor3
    }

    colorScheme.BottomScreenBackground = if (darkTheme) {
        BottomScreenDark
    } else {
        BottomScreenLight
    }

    colorScheme.BaseText = if (darkTheme) {
        BaseTextColorDark
    } else {
        BaseTextColorLight
    }

    colorScheme.EnabledButtonSecondContentColor = if (darkTheme) {
        EnabledButtonSecondContentColorDark
    } else {
        EnabledButtonSecondContentColorLight
    }

    colorScheme.EnabledButtonSecondBackground = if (darkTheme) {
        EnabledButtonSecondBackgroundDark
    } else {
        EnabledButtonSecondBackgroundLight
    }

    colorScheme.EnabledButtonContent = if (darkTheme) {
        ContentButtonEnabledDark
    } else {
        ContentButtonEnabledLight
    }

    colorScheme.DisabledButtonContent = if (darkTheme) {
        ContentButtonDisabledDark
    } else {
        ContentButtonDisabledLight
    }
    colorScheme.MainGreen = if (darkTheme) {
        FieldsDefaultBorderColorDark
    } else {
        FieldsDefaultBorderColorLight
    }

    colorScheme.ErrorColor = if (darkTheme) {
        ErrorColorDark
    } else {
        ErrorColorLight
    }

    colorScheme.FieldsPlaceholderColor = if (darkTheme) {
        FieldsPlaceholderColorDark
    } else {
        FieldsPlaceholderColorLight
    }

    colorScheme.PrimaryTitleColor = if (darkTheme) {
        PrimaryTitleColorDark
    } else {
        PrimaryTitleColorLight
    }

    colorScheme.MainYellow = if (darkTheme) {
        MainYellowColorDark
    } else {
        MainYellowColorLight
    }

    colorScheme.DisabledButtonTextColor = if (darkTheme) {
        DisabledButtonTextColorDark
    } else {
        DisabledButtonTextColorLight
    }

    colorScheme.LoadingIndicatorColor = if (darkTheme) {
        LoadingIndicatorColorDark
    } else {
        LoadingIndicatorColorLight
    }

    colorScheme.NavigationDisabled = if (darkTheme) {
        NavigationDisabledDark
    } else {
        NavigationDisabledLight
    }

    colorScheme.NavigationSelected = if (darkTheme) {
        NavigationSelectedDark
    } else {
        NavigationSelectedLight
    }

    colorScheme.MainBackground = if (darkTheme) {
        MainBackgroundDark
    } else {
        MainBackgroundLight
    }

    colorScheme.Divider = if (darkTheme) {
        DividerDarkColor
    } else {
        DividerLightColor
    }

}