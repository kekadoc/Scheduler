package app.model.mvi

data class AppState(
    val spaceName: String? = "Пнипу"
)

val AppState.isAuthorized: Boolean
    get() = !spaceName.isNullOrEmpty()