package common.data

import common.dispose.Disposable

interface DataSource<Key, Data> {

    fun addListener(listener: DataListener<Key, Data>): Disposable

    suspend fun getAll(): Result<List<Data>>
}