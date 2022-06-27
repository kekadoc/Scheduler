package common.extensions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

fun <T> flowOf(block: suspend () -> T): Flow<T> {
    return flow { emit(block()) }
}

suspend fun <T> Flow<Result<T>>.await(): T {
    return first().getOrThrow()
}

fun <T> Flow<Result<T>>.catchResult(): Flow<Result<T>> {
    return catch { emit(Result.failure(it)) }
}