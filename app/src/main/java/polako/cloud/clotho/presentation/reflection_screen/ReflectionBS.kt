package polako.cloud.clotho.presentation.reflection_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import polako.cloud.clotho.ui.composables.ReflectionSlider
import polako.cloud.clotho.ui.composables.ReflectionTagChips

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReflectionBS(
    onDismissRequest: () -> Unit,
    onSaveClicked: (score: Int, tags: List<String>) -> Unit,
    reflectionScore: Float,
    reflectionTags: List<String>,
    selectedTags: List<String>
) {
    var score by remember { mutableStateOf(reflectionScore) }
    var tagSelections by remember { mutableStateOf(selectedTags) }

    ModalBottomSheet(
        onDismissRequest = {  onDismissRequest() },
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(Color(0xFF1E2A38))
                    .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            Text(
                text = "Reflect on your session",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
            )

            ReflectionSlider(
                value = score,
                onValueChange = { score = it },
            )

            Text(
                text = "How did you feel?",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
            )
            ReflectionTagChips(
                tags = reflectionTags,
                selectedTags = tagSelections,
                onTagToggle = { tag ->
                    tagSelections =
                        if (tagSelections.contains(tag)) {
                            tagSelections - tag
                        } else {
                            tagSelections + tag
                        }
                },
            )

            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    onSaveClicked(score.toInt(), tagSelections)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
            ) {
                Text("Save Reflection")
            }
        }
    }
}
