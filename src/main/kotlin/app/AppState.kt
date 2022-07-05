package app

import app.domain.model.Space
import app.domain.model.Space.Companion.isEmpty

data class AppState(
    val space: Space = Space.Empty,
    val isAuthorizationProcess: Boolean = false,
    val isLoading: Boolean = false
)

val AppState.isAuthorized: Boolean
    get() = !space.isEmpty