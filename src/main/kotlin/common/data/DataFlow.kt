package common.data

interface DataFlow<T> {

    val data: kotlinx.coroutines.flow.Flow<T>
}