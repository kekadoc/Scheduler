package common.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onEach

val <Key, Data> DataSource<Key, Data>.all: Flow<List<Data>>
    get() {
        val flow = MutableStateFlow<List<Data>>(emptyList())
        var initialized = false
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
        return flow.onEach { data ->
             if (data.isEmpty() && !initialized) {
                 getAll().onSuccess { allData ->
                     flow.value = allData
                     initialized = true
                 }
             }
        }
    }