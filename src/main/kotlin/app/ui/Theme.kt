package app

import androidx.compose.material.Colors
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import common.resources.AppLocalResources
import common.resources.api.ResourceQualifier

@Composable
fun appColors(isDark: Boolean): Colors {
    val appColors = AppLocalResources.current.getColors(ResourceQualifier.Default.copy(isDarkTheme = isDark))
    /*Colors(
        primary = appColors.primaryColor,
        primaryVariant = appColors.primaryLightColor,
        secondary = appColors.secondaryColor,
        secondaryVariant = appColors.secondaryLightColor,
        background = appColors.background,
        surface = appColors.surface,
        error = appColors.error,
        onPrimary = appColors.onPrimary,
        onSecondary = appColors.onSecondary,
        onBackground = appColors.onBackground,
        onSurface = appColors.onSurface,
        onError = appColors.onError,
        isLight = !isDark
    )*/
    return if (isDark) {
        darkColors(
            primary = appColors.primaryColor,
            onPrimary = Color.White,
            primaryVariant = appColors.primaryLightColor,
            secondary = appColors.secondaryColor,
            secondaryVariant = appColors.secondaryLightColor,
        )
    } else {
        lightColors(
            primary = appColors.primaryColor,
            primaryVariant = appColors.primaryLightColor,
            secondary = appColors.secondaryColor,
            secondaryVariant = appColors.secondaryLightColor,
        )
    }
}