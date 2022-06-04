package app.model.mvi

data class AppState(
    val spaceName: String? = null
)

val AppState.isAuthorized: Boolean
    get() = !spaceName.isNullOrEmpty()