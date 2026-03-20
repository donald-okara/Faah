package ke.don.faah.ui.entry_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ke.don.faah.model.FaahEntry

@Composable
fun EntryList(
    entries: List<FaahEntry>,
    selectedEntry: FaahEntry? = null,
    onItemSelect: (FaahEntry) -> Unit
){
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(entries) { entry ->
            EntryListItem(
                entry = entry,
                selected = selectedEntry?.id == entry.id,
                modifier = Modifier.clickable { onItemSelect(entry) }
            )
            Divider(color = MaterialTheme.colors.onSurface.copy(0.7f), thickness = 1.dp)
        }
    }
}