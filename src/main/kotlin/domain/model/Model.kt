package domain.model

interface Model {
    val id: Long
}

class SubModel<T>(override val id: Long, val item: T) : Model