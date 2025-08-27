package polako.cloud.clotho.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ReflectionSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(16.dp),
    ) {
        Text(
            text = "Rate your focus: ${value.toInt()}/10",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        brush =
                            Brush.horizontalGradient(
                                listOf(
                                    Color(0xFFFF3B30), // Red
                                    Color(0xFFFF9500), // Orange
                                    Color(0xFFFFD60A), // Yellow
                                    Color(0xFF34C759), // Green
                                ),
                            ),
                    ),
            contentAlignment = Alignment.Center,
        ) {
            Slider(
                value = value,
                onValueChange = onValueChange,
                valueRange = 1f..10f,
                steps = 8,
                modifier = Modifier.fillMaxWidth(),
                colors =
                    SliderDefaults.colors(
                        thumbColor = Color.White,
                        activeTrackColor = Color.Transparent,
                        inactiveTrackColor = Color.Transparent,
                    ),
            )
        }
    }
}
