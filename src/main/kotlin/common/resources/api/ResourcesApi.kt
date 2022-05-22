package common.resources.api

import androidx.compose.runtime.staticCompositionLocalOf

//val LocalResources = staticCompositionLocalOf<Resources> { Resources }

val LocalResourceQualifier = staticCompositionLocalOf<ResourceQualifier> { ResourceQualifier.Default }