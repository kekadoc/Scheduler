package app.domain.model

interface Model {
    val id: Long
}

class SubModel<T>(override val id: Long, val item: T) : Model

interface ModelProvider<M : Model> {
    val Empty: M

    companion object {
        const val EMPTY_ID = -1L
    }
}