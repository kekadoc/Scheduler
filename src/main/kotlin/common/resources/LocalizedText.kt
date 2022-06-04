package common.resources

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import common.resources.api.*
import common.resources.colors.AppColorsDark
import common.resources.colors.AppColorsLight
import common.resources.images.AppImages
import common.resources.strings.AppStringsEn
import common.resources.strings.AppStringsRu
import java.util.*

val AppLocalResources = staticCompositionLocalOf<AppResources> { AppResources() }

interface AppStrings : StringResourceGroup {
    val name: StringResource
    val helpMessage: StringResource
    val foo: StringResource
}

interface AppImages : ImageResourceGroup

interface AppColors : ColorResourceGroup {
    val primaryColor: Color
    val primaryLightColor: Color
    val primaryDarkColor: Color
    val secondaryColor: Color
    val secondaryLightColor: Color
    val secondaryDarkColor: Color
    val primaryTextColor: Color
    val secondaryTextColor: Color
    val background: Color
    val surface: Color
    val error: Color
    val onPrimary: Color
    val onSecondary: Color
    val onBackground: Color
    val onSurface: Color
    val onError: Color
}

class AppResources : AbstractResources<AppStrings, AppImages, AppColors>() {

    override fun getStrings(qualifier: ResourceQualifier): AppStrings {
        return when {
            qualifier.locale == Locale.ENGLISH -> AppStringsEn
            qualifier.locale.country.contentEquals("ru", ignoreCase = true) -> AppStringsRu
            else -> AppStringsEn
        }
    }

    override fun getImages(qualifier: ResourceQualifier): AppImages {
        return AppImages
    }

    override fun getColors(qualifier: ResourceQualifier): AppColors {
        return if (qualifier.isDarkTheme) AppColorsDark
        else AppColorsLight
    }
}

object R : Resources {

    override val string: AppStrings
        @Composable
        //@ReadOnlyComposable
        get() = AppLocalResources.current.string

    override val image: AppImages
        @Composable
        //@ReadOnlyComposable
        get() = AppLocalResources.current.image

    override val color: AppColors
        @Composable
        //@ReadOnlyComposable
        get() = AppLocalResources.current.color

}