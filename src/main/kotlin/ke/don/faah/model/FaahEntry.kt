package ke.don.faah.model
    
import kotlinx.serialization.Serializable

@Serializable
data class FaahEntry(
    val id: String,
    val title: String,
    val description: String,
    val file: String,
    val line: Int,
    val author: String,
    val createdAt: Long,
    val status: Status
)

@Serializable
enum class Status {
    OPEN,
    RESOLVED
}