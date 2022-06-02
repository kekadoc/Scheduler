package common.data.crud

/**
 * @param Key Ключ данных
 * @param InnerModel Model которая сохраняется
 * @param OutsideModel Model которая выдается
 */
interface CRUD<Key, InnerModel, OutsideModel> {

    suspend fun create(creator: InnerModel.() -> Unit): Result<OutsideModel>

    suspend fun read(key: Key): Result<OutsideModel>

    suspend fun update(key: Key, updater: InnerModel.() -> Unit): Result<OutsideModel>

    suspend fun delete(key: Key): Result<OutsideModel>
}