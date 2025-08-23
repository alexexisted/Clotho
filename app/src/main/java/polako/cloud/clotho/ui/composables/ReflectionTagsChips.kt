package polako.cloud.clotho.ui.composables

import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ReflectionTagChips(
    tags: List<String>,
    selectedTags: List<String>,
    onTagToggle: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier.padding(8.dp),
//        mainAxisSpacing = 8.dp,
//        crossAxisSpacing = 8.dp
    ) {
        tags.forEach { tag ->
            FilterChip(
                selected = selectedTags.contains(tag),
                onClick = { onTagToggle(tag) },
                label = { Text(tag) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    selectedLabelColor = Color.White
                )
            )
        }
    }
}
