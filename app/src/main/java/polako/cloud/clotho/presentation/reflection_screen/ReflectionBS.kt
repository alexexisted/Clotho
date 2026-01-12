package polako.cloud.clotho.presentation.reflection_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    selectedTags: List<String>,
) {
    var score by remember { mutableStateOf(reflectionScore) }
    var tagSelections by remember { mutableStateOf(selectedTags) }

    ModalBottomSheet(
        onDismissRequest = { onDismissRequest() },
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(Color(0xFF5D10FD))
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
                style = MaterialTheme.typography.headlineSmall,
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
                colors =
                    ButtonColors(
                        containerColor = Color(0xFF1E1A3D),
                        contentColor = MaterialTheme.colorScheme.background,
                        disabledContainerColor = MaterialTheme.colorScheme.error,
                        disabledContentColor = MaterialTheme.colorScheme.secondary,
                    ),
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
