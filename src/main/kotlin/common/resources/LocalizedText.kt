package common.resources

import androidx.compose.material.*
import androidx.compose.runtime.*
import common.resources.api.*
import common.resources.colors.AppColors
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

interface AppColors : ColorResourceGroup

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
        return AppColors
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