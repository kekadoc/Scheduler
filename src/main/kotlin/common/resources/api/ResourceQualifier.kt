package common.resources.api

import java.util.*

data class ResourceQualifier(
    val locale: Locale,
    val isDarkTheme: Boolean
) {

    companion object {
        val Default = ResourceQualifier(
            locale = Locale.ENGLISH,
            isDarkTheme = false
        )
    }
}