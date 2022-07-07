package app.data.injector

import app.data.repository.space.SpacesRepository
import app.domain.model.Space

interface DataInjector {

    val type: Type

    val id: Long

    suspend fun getSpace(spaces: SpacesRepository): Space

    suspend fun checkIsNeedInject(data: DataRepository): Boolean

    suspend fun inject(data: DataRepository)

    enum class Type {
        EVERY_LAUNCH,
        ONLY_ONCE
    }
}