package ru.foolstack.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = MainGreenLight,
    secondary = MainOrangeDark,
    background = MainBackgroundDark
)

private val LightColorScheme = lightColorScheme(
    primary = MainGreenLight,
    secondary = MainOrangeLight,
    background = MainBackgroundLight,
)
//extraColor
var MainGreenColorSchemeColor by mutableStateOf(MainGreenLight)
var MainWhiteColorSchemeColor by mutableStateOf(White)
var MainBlackColorSchemeColor by mutableStateOf(Black)
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
var MainDarkGreenSchemeColor by mutableStateOf(MainDarkGreenLightColor)
var MainBackgroundSchemeColor by mutableStateOf(MainBackgroundLight)
var DividerSchemeColor by mutableStateOf(DividerLightColor)
var ServiceBackgroundSchemeColor by mutableStateOf(ServiceBackgroundLightColor)
var ServiceTextSchemeColor by mutableStateOf(ServiceTextLightColor)
var LoadingIndicatorBackgroundSchemeColor by mutableStateOf(LoadingIndicatorLightBackground)
var UnselectedChipBackgroundSchemeColor by mutableStateOf(MainBackgroundLight)
var UnselectedChipStrokeSchemeColor by mutableStateOf(ServiceBorderLightColor)
var SelectedChipBackgroundSchemeColor by mutableStateOf(MainYellowColorLight)
var CardTextSchemeColor by mutableStateOf(CardTextLightColor)
var GreenButtonContentSchemeColor by mutableStateOf(GreenButtonContentLightColor)
var GreenButtonContainerSchemeColor by mutableStateOf(GreenButtonContainerLightColor)
var SalePriceSchemeColor by mutableStateOf(SalePriceLightColor)
var InputTextSchemeColor by mutableStateOf(InputTextLightColor)
var HintTextSchemeColor by mutableStateOf(HintTextLightColor)
var BannerBackgroundSchemeColor by mutableStateOf(BannerBackgroundLightColor)
var ProfessionsBanner1SchemeColor by mutableStateOf(ProfessionsSaleBannerColor1)
var ProfessionsBanner2SchemeColor by mutableStateOf(ProfessionsSaleBannerColor2)




@Suppress("unused")
var ColorScheme.MainGreen: Color
    get() = MainGreenColorSchemeColor
    set(value) {
        MainGreenColorSchemeColor = value
    }
var ColorScheme.MainWhite: Color
    get() = MainWhiteColorSchemeColor
    set(value) {
        MainWhiteColorSchemeColor = value
    }

var ColorScheme.MainBlack: Color
    get() = MainBlackColorSchemeColor
    set(value) {
        MainBlackColorSchemeColor = value
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

var ColorScheme.MainDarkGreen: Color
    get() = MainDarkGreenSchemeColor
    set(value) {
        MainDarkGreenSchemeColor = value
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

var ColorScheme.ServiceBackground: Color
    get() = ServiceBackgroundSchemeColor
    set(value) {
        ServiceBackgroundSchemeColor = value
    }

var ColorScheme.ServiceText: Color
    get() = ServiceTextSchemeColor
    set(value) {
        ServiceTextSchemeColor = value
    }

var ColorScheme.LoadingIndicatorBackground: Color
    get() = LoadingIndicatorBackgroundSchemeColor
    set(value) {
        LoadingIndicatorBackgroundSchemeColor = value
    }

var ColorScheme.UnselectedChipBackground: Color
    get() = UnselectedChipBackgroundSchemeColor
    set(value) {
        UnselectedChipBackgroundSchemeColor = value
    }

var ColorScheme.UnselectedChipStroke: Color
    get() = UnselectedChipStrokeSchemeColor
    set(value) {
        UnselectedChipStrokeSchemeColor = value
    }

var ColorScheme.SelectedChipBackground: Color
    get() = SelectedChipBackgroundSchemeColor
    set(value) {
        SelectedChipBackgroundSchemeColor = value
    }

var ColorScheme.CardText: Color
    get() = CardTextSchemeColor
    set(value) {
        CardTextSchemeColor = value
    }

var ColorScheme.GreenButtonContent: Color
    get() = GreenButtonContentSchemeColor
    set(value) {
        GreenButtonContentSchemeColor = value
    }

var ColorScheme.GreenButtonContainer: Color
    get() = GreenButtonContainerSchemeColor
    set(value) {
        GreenButtonContainerSchemeColor = value
    }

var ColorScheme.SalePrice: Color
    get() = SalePriceSchemeColor
    set(value) {
        SalePriceSchemeColor = value
    }

var ColorScheme.InputText: Color
    get() = InputTextSchemeColor
    set(value) {
        InputTextSchemeColor = value
    }

var ColorScheme.HintText: Color
    get() = HintTextSchemeColor
    set(value) {
        HintTextSchemeColor = value
    }

var ColorScheme.BannerBackground: Color
    get() = BannerBackgroundSchemeColor
    set(value) {
        BannerBackgroundSchemeColor = value
    }

var ColorScheme.ProfessionsBannerColor1: Color
    get() = ProfessionsBanner1SchemeColor
    set(value) {
        ProfessionsBanner1SchemeColor = value
    }

var ColorScheme.ProfessionsBannerColor2: Color
    get() = ProfessionsBanner2SchemeColor
    set(value) {
        ProfessionsBanner2SchemeColor = value
    }








@Composable
fun FoolStackTheme(
    theme: String,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val darkTheme = when(theme){
        "dark" -> true
        "light"-> false
        "system"-> isSystemInDarkTheme()
        else-> isSystemInDarkTheme()
    }
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
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

    colorScheme.MainWhite = if (darkTheme) {
        White
    } else {
        White
    }

    colorScheme.MainBlack = if (darkTheme) {
        White
    } else {
        Black
    }

    colorScheme.MainOrange = if (darkTheme) {
        MainOrangeDark
    } else {
        MainOrangeLight
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
        MainGreenDark
    } else {
        MainGreenLight
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

    colorScheme.MainDarkGreen = if (darkTheme) {
        MainDarkGreenDarkColor
    } else {
        MainDarkGreenLightColor
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

    colorScheme.ServiceBackground = if (darkTheme) {
        ServiceBackgroundDarkColor
    } else {
        ServiceBackgroundLightColor
    }

    colorScheme.ServiceText = if (darkTheme) {
        ServiceTextDarkColor
    } else {
        ServiceTextLightColor
    }

    colorScheme.LoadingIndicatorBackground = if (darkTheme) {
        LoadingIndicatorDarkBackground
    } else {
        LoadingIndicatorLightBackground
    }

    colorScheme.UnselectedChipBackground = if (darkTheme) {
        MainBackgroundDark
    } else {
        MainBackgroundLight
    }

    colorScheme.UnselectedChipStroke = if (darkTheme) {
        ServiceBorderDarkColor
    } else {
        ServiceBorderLightColor
    }

    colorScheme.SelectedChipBackground = if (darkTheme) {
        MainYellowColorDark
    } else {
        MainYellowColorLight
    }

    colorScheme.CardText = if (darkTheme) {
        CardTextDarkColor
    } else {
        CardTextLightColor
    }

    colorScheme.GreenButtonContent = if (darkTheme) {
        GreenButtonContentDarkColor
    } else {
        GreenButtonContentLightColor
    }

    colorScheme.GreenButtonContainer = if (darkTheme) {
        GreenButtonContainerDarkColor
    } else {
        GreenButtonContainerLightColor
    }

    colorScheme.SalePrice = if (darkTheme) {
        SalePriceDarkColor
    } else {
        SalePriceLightColor
    }

    colorScheme.InputText = if (darkTheme) {
        InputTextDarkColor
    } else {
        InputTextLightColor
    }

    colorScheme.HintText = if (darkTheme) {
        HintTextDarkColor
    } else {
        HintTextLightColor
    }

    colorScheme.BannerBackground = if (darkTheme) {
        BannerBackgroundDarkColor
    } else {
        BannerBackgroundLightColor
    }

    colorScheme.ProfessionsBannerColor1 = if (darkTheme) {
        ProfessionsSaleBannerColor1
    } else {
        ProfessionsSaleBannerColor1
    }

    colorScheme.ProfessionsBannerColor2 = if (darkTheme) {
        ProfessionsSaleBannerColor2
    } else {
        ProfessionsSaleBannerColor2
    }
}