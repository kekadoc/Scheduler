package common.resources.api

import androidx.compose.runtime.Composable

interface Resources {

    val string: StringResourceGroup
        @Composable get

    val image: ImageResourceGroup
        @Composable get

    val color: ColorResourceGroup
        @Composable get
}

abstract class AbstractResources
<Strings : StringResourceGroup, Images : ImageResourceGroup, Colors : ColorResourceGroup> : Resources {

    final override val string: Strings
        @Composable get() = getStrings(LocalResourceQualifier.current)

    override val image: Images
        @Composable get() = getImages(LocalResourceQualifier.current)

    override val color: Colors
        @Composable get() = getColors(LocalResourceQualifier.current)


    abstract fun getStrings(qualifier: ResourceQualifier): Strings

    abstract fun getImages(qualifier: ResourceQualifier): Images

    abstract fun getColors(qualifier: ResourceQualifier): Colors
}