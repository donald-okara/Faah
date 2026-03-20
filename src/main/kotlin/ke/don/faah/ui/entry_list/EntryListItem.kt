package ke.don.faah.ui.entry_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ke.don.faah.model.FaahEntry
import ke.don.faah.model.Status
import ke.don.faah.model.formattedTime
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun EntryListItem(
    entry: FaahEntry,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    val dateString = entry.createdAt.formattedTime()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                if (selected) MaterialTheme.colors.surface
                else Color.Transparent
            )
            .padding(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        // Left Icon (Yellow Emoji/Icon)
        Box(
            modifier = Modifier
                .size(32.dp)
                .padding(top = 2.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("🧐", fontSize = 20.sp) // Matching the screenshot emoji
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = entry.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.White
                )

                Icon(
                    imageVector = if (entry.status == Status.OPEN) Icons.Rounded.Warning else Icons.Rounded.CheckCircle,
                    contentDescription = entry.status.name,
                    tint = if (entry.status == Status.OPEN) Color(0xFFE26D5C) else Color(0xFF81B29A),
                    modifier = Modifier.size(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Rounded.Info,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${entry.file} : ${entry.line}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colors.primary // Blueish color for file path
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(Color.Gray),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = entry.author.take(1).uppercase(),
                            fontSize = 8.sp,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = entry.author.uppercase(),
                        fontSize = 10.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Medium
                    )
                }

                Text(
                    text = dateString,
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            }
        }
    }
}