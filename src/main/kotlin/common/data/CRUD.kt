package common.data

/**
 * @param Key Ключ данных
 * @param Data Класс данных
 */
interface CRUD<Key, Data> {

    suspend fun all(): Result<List<Data>>

    suspend fun create(creator: Data.() -> Unit): Result<Data>

    suspend fun read(key: Key): Result<Data>

    suspend fun update(key: Key, updater: Data.() -> Unit): Result<Data>

    suspend fun delete(key: Key): Result<Data>
}