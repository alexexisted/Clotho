package polako.cloud.clotho.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ReflectionTagChips(
    tags: List<String>,
    selectedTags: List<String>,
    onTagToggle: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    FlowRow(
        modifier = modifier.padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        tags.forEach { tag ->
            FilterChip(
                selected = selectedTags.contains(tag),
                onClick = { onTagToggle(tag) },
                label = { Text(tag) },
                border = BorderStroke(0.5.dp, Color.White),
                colors =
                    FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                        selectedLabelColor = Color.Black,
                        disabledLabelColor = Color.White,
                        labelColor = Color.White
                    ),
            )
        }
    }
}

@Preview
@Composable
fun PreviewChips() {
    ReflectionTagChips(
        tags = listOf("one", "two", "three"),
        selectedTags = listOf("one", "two", "three"),
        onTagToggle = {},
        modifier = Modifier
    )
}
