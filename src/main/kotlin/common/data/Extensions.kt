package common.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

val <Key, Data> DataSource<Key, Data>.all: Flow<List<Data>>
    get() {
        val flow = MutableStateFlow<List<Data>>(emptyList())
        addListener(object : DataListener<Key, Data> {
            override suspend fun onUpdate(key: Key, oldData: Data, newData: Data) {
                getAll().getOrNull()?.apply { flow.value = this }
            }

            override suspend fun onCreate(key: Key, data: Data) {
                getAll().getOrNull()?.apply { flow.value = this }
            }

            override suspend fun onDelete(key: Key, data: Data) {
                getAll().getOrNull()?.apply { flow.value = this }
            }
        })
        return flow
    }