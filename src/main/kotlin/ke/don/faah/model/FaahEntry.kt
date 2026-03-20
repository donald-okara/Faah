package ke.don.faah.model
    
import com.jetbrains.rd.util.UUID
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class FaahEntry(
    val title: String,
    val description: String,

    val id: String = UUID.randomUUID().toString(),
    val file: String = "",
    val line: Int = 0,
    val author: String = "",
    val createdAt: Long = Clock.System.now().toEpochMilliseconds(),
    val status: Status = Status.OPEN
)

@Serializable
enum class Status {
    OPEN,
    RESOLVED
}