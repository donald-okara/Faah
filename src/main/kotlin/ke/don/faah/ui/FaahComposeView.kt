package ke.don.faah.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ke.don.faah.model.FaahEntry
import ke.don.faah.model.FaahRepository
import ke.don.faah.model.Status
import ke.don.faah.ui.entry_details.EntryDetailView
import ke.don.faah.ui.entry_form.AddEntryDialog
import ke.don.faah.ui.entry_list.EntryList
import ke.don.faah.ui.entry_list.EntryListItem
import ke.don.faah.ui.theme.FaahTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.skia.Surface

@Composable
fun FaahComposeView(repository: FaahRepository) {
    val entries by repository.getAll().collectAsState(initial = emptyList())
    var selectedEntry by remember { mutableStateOf<FaahEntry?>(null) }
    var showAddDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    // Update selection when entries change if current selection is gone
    LaunchedEffect(entries) {
        if (selectedEntry == null && entries.isNotEmpty()) {
            selectedEntry = entries.first()
        } else if (selectedEntry != null && entries.none { it.id == selectedEntry?.id }) {
            selectedEntry = entries.firstOrNull()
        }
    }

    FaahTheme {

        Surface(
            color = MaterialTheme.colors.background
        ){// Main Content Area
            Column(modifier = Modifier.fillMaxSize()) {
                // Header
                Header(onAddClick = { showAddDialog = true })

                // List and Detail
                Column(modifier = Modifier.fillMaxSize()) {
                    // List
                    Box(modifier = Modifier.weight(1f)) {
                        EntryList(
                            entries = entries,
                            selectedEntry = selectedEntry,
                            onItemSelect = { entry -> selectedEntry = entry }
                        )
                    }

                    // Detail View (at the bottom, matching screenshot)
                    selectedEntry?.let { entry ->
                        EntryDetailView(entry)
                    }
                }
            }

            if (showAddDialog) {
                AddEntryDialog(
                    onDismiss = { showAddDialog = false },
                    onConfirm = { title, description ->
                        scope.launch {
                            repository.save(
                                FaahEntry(
                                    title = title,
                                    description = description,
                                    author = "Current User", // In a real app, get this from system
                                    file = "Unknown",
                                    line = 0
                                )
                            )
                            showAddDialog = false
                        }
                    }
                )
            }
        }
    }
}


@Composable
fun Header(onAddClick: () -> Unit) {
    val onSurfaceColor = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface.copy(alpha = 0.6f))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "FAAH - TECH DEBT",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onAddClick, modifier = Modifier.size(24.dp)) {
                Icon(Icons.Default.Add, contentDescription = "Add", tint = onSurfaceColor, modifier = Modifier.size(16.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Icon(Icons.Default.List, contentDescription = null, tint = onSurfaceColor, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Icon(Icons.Rounded.MoreVert, contentDescription = null, tint = onSurfaceColor, modifier = Modifier.size(16.dp))
        }
    }
}