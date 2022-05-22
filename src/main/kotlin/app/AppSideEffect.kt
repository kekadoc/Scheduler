package app

import domain.model.Space

sealed class AppSideEffect {
    data class Authorized(val space: Space) : AppSideEffect()
    object Unauthorized : AppSideEffect()
}