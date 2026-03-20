package ke.don.faah.data

import ke.don.faah.model.FaahEntry
import ke.don.faah.model.FaahRepository
import kotlinx.coroutines.flow.Flow

class FaahService( private val db: JsonDb ): FaahRepository {
    override fun getAll(): Flow<List<FaahEntry>> = db.allEntries

    override fun get(id: String): Flow<FaahEntry?> = db.getById(id)

    override suspend fun save(entry: FaahEntry) = db.save(entry)

    override suspend fun delete(entry: FaahEntry) = db.delete(entry)

    override suspend fun deleteAll() = db.deleteAll()

    override fun count(): Int = db.count()

}