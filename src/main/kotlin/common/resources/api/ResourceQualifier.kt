package common.resources.api

import java.util.*

data class ResourceQualifier(
    val locale: Locale
) {

    companion object {
        val Default = ResourceQualifier(
            locale = Locale.ENGLISH
        )
    }
}