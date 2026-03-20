package ke.don.faah.ui.entry_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ke.don.faah.model.FaahEntry


@Composable
fun EntryDetailView(entry: FaahEntry) {
    Column(
        modifier = Modifier.fillMaxWidth().background(MaterialTheme.colors.surface).padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("😩", fontSize = 20.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = entry.title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White
                )
            }

            Surface(
                color = Color.Transparent,
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE26D5C)),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = "OPEN",
                    color = Color(0xFFE26D5C),
                    fontSize = 10.sp,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colors.background).padding(12.dp)
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colors.onSurface.copy(0.7f),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = entry.file, fontSize = 12.sp, color = MaterialTheme.colors.primary
                    )
                }
                Text(
                    text = "Line: ${entry.line}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colors.onSurface.copy(0.7f),
                    modifier = Modifier.padding(start = 18.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = entry.description, fontSize = 13.sp, color = Color.LightGray, lineHeight = 18.sp
        )
    }
}