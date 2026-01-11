package polako.cloud.clotho.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
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
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
        )

        Spacer(modifier = Modifier.height(15.dp))

        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        brush =
                            Brush.horizontalGradient(
                                listOf(
                                    Color(0xFF19001F),
                                    Color(0xFF2D0439),
                                    Color(0xFF6B0282),
                                    Color(0xFF9501B4),
                                ),
                            ),
                    ),
            contentAlignment = Alignment.Center,
        ) {
            Slider(
                value = value,
                onValueChange = onValueChange,
                valueRange = 1f..10f,
//                steps = 2,
                modifier = Modifier
                    .fillMaxWidth(),
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
@Preview
@Composable
fun PreviewSlider() {
    ReflectionSlider(
        value = 5f,
        onValueChange = {},
        modifier = Modifier
    )
}
