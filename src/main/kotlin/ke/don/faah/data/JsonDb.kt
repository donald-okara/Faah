package ke.don.faah.data

import com.intellij.openapi.project.Project
import ke.don.faah.model.FaahEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlinx.serialization.*
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.*
import java.io.File

class JsonDb(private val project: Project) {

    private val file: File by lazy {
        val basePath = project.basePath
            ?: throw IllegalStateException("Project base path is null")


        val dir = File(basePath, ".faah")
        dir.mkdirs()
        File(dir, "tech_debt.json")
    }

    private val writeMutex = Mutex()

    private val json = Json { prettyPrint = true; encodeDefaults = true }

    // Single source of truth — backed by a StateFlow
    private val _entries = MutableStateFlow<List<FaahEntry>>(emptyList())

    init {
        // Seed the flow with whatever is on disk at startup
        _entries.value = readFromDisk()
    }

    // --- Flows ---

    /** Emits the full list whenever any entry is added, updated, or removed. */
    val allEntries: Flow<List<FaahEntry>> = _entries.asStateFlow()

    /** Emits the matching entry (or null) whenever the list changes. */
    fun getById(id: String): Flow<FaahEntry?> =
        _entries.map { list -> list.firstOrNull { it.id == id } }


    suspend fun save(entry: FaahEntry) {
        writeMutex.withLock {
            val current = _entries.value.toMutableList()
            val index = current.indexOfFirst { it.id == entry.id }
            if (index != -1) current[index] = entry else current.add(entry)
            persist(current)
        }
    }

    suspend fun saveAll(entries: List<FaahEntry>) {
        persist(entries)
    }

    suspend fun delete(entry: FaahEntry) {
        val updated = _entries.value.toMutableList().also { it.remove(entry) }
        persist(updated)
    }

    suspend fun deleteAll() {
        persist(emptyList())
    }

    fun count(): Int = _entries.value.size

    fun clear() {
        file.delete()
        _entries.value = emptyList()
    }

    // --- Private helpers ---

    private fun readFromDisk(): List<FaahEntry> {
        if (!file.exists()) return emptyList()
        return try {
            json.decodeFromString(ListSerializer(FaahEntry.serializer()), file.readText())
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    private suspend fun persist(entries: List<FaahEntry>) {
        withContext(Dispatchers.IO) {
            file.writeText(json.encodeToString(ListSerializer(FaahEntry.serializer()), entries))
        }
        _entries.value = entries
    }
}