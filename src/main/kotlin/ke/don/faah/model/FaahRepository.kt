package ke.don.faah.model

import kotlinx.coroutines.flow.Flow

interface FaahRepository {
    fun getAll(): Flow<List<FaahEntry>>
    fun get(id: String): Flow<FaahEntry?>
    suspend fun save(entry: FaahEntry)
    suspend fun delete(entry: FaahEntry)
    suspend fun deleteAll()
    fun count(): Int
}