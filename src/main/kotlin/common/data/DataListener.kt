package common.data

interface DataListener<Key, Data> {

    suspend fun onUpdate(key: Key, data: Data)

    suspend fun onCreate(key: Key, data: Data)

    suspend fun onDelete(key: Key, data: Data)

    suspend fun onClear()
}