package polako.cloud.clotho.ui.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@SuppressLint("DefaultLocale")
@Composable
fun Stopwatch(
    elapsedTime: Long,
    isRunning: Boolean,
    isPaused: Boolean,
    onStart: () -> Unit,
    onPause: () -> Unit,
    onStop: () -> Unit,
) {
    val hours = (elapsedTime / 1000) / 3600
    val minutes = (elapsedTime / 1000 / 60) % 60
    val seconds = (elapsedTime / 1000) % 60

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = String.format("%02d:%02d:%02d", hours, minutes, seconds),
            style = MaterialTheme.typography.displayLarge.copy(color = MaterialTheme.colorScheme.surface),
            fontSize = 60.sp,
        )

        Spacer(modifier = Modifier.padding(vertical = 70.dp))

        Column(
            modifier = Modifier,
        ) {
            if (isPaused) {
                ElevatedButton(
                    modifier =
                        Modifier
                            .height(80.dp)
                            .fillMaxWidth()
                            .padding(horizontal = 30.dp, vertical = 5.dp),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(vertical = 0.dp),
                    onClick = onStart,
                    colors =
                        ButtonColors(
                            containerColor = Color(0xFF1B143F),
                            contentColor = MaterialTheme.colorScheme.surface,
                            disabledContainerColor = MaterialTheme.colorScheme.error,
                            disabledContentColor = MaterialTheme.colorScheme.secondary,
                        ),
                    elevation =
                        ButtonDefaults.buttonElevation(
                            defaultElevation = 8.dp,
                            pressedElevation = 12.dp,
                        ),
                ) {
                    Text(
                        text = "Start",
                        style = MaterialTheme.typography.headlineLarge,
                    )
                }
            } else if (isRunning) {
                ElevatedButton(
                    modifier =
                        Modifier
                            .height(80.dp)
                            .fillMaxWidth()
                            .padding(horizontal = 30.dp, vertical = 5.dp),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(vertical = 0.dp),
                    onClick = onPause,
                    colors =
                        ButtonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            contentColor = MaterialTheme.colorScheme.surface,
                            disabledContainerColor = MaterialTheme.colorScheme.error,
                            disabledContentColor = MaterialTheme.colorScheme.secondary,
                        ),
                    elevation =
                        ButtonDefaults.buttonElevation(
                            defaultElevation = 8.dp,
                            pressedElevation = 12.dp,
                        ),
                ) { Text(text = "Pause", style = MaterialTheme.typography.headlineLarge) }
            } else {
                ElevatedButton(
                    modifier =
                        Modifier
                            .height(80.dp)
                            .fillMaxWidth()
                            .padding(horizontal = 30.dp, vertical = 5.dp),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(vertical = 0.dp),
                    onClick = onStart,
                    colors =
                        ButtonColors(
                            containerColor = Color(0xFF1B143F),
                            contentColor = MaterialTheme.colorScheme.surface,
                            disabledContainerColor = MaterialTheme.colorScheme.error,
                            disabledContentColor = MaterialTheme.colorScheme.secondary,
                        ),
                    elevation =
                        ButtonDefaults.buttonElevation(
                            defaultElevation = 8.dp,
                            pressedElevation = 12.dp,
                        ),
                ) { Text(text = "Start", style = MaterialTheme.typography.headlineLarge) }
            }
            ElevatedButton(
                modifier =
                    Modifier
                        .height(80.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp, vertical = 5.dp),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(vertical = 0.dp),
                onClick = onStop,
                elevation =
                    ButtonDefaults.buttonElevation(
                        defaultElevation = 8.dp,
                        pressedElevation = 12.dp,
                    ),
                colors =
                    ButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.primary,
                        disabledContainerColor = MaterialTheme.colorScheme.error,
                        disabledContentColor = MaterialTheme.colorScheme.secondary,
                    ),
            ) { Text(text = "Finish", style = MaterialTheme.typography.headlineLarge) }
        }
    }
}

@Preview
@Composable
fun StopwatchPreview() {
    Stopwatch(
        elapsedTime = 839987L,
        isRunning = false,
        isPaused = false,
        onStart = {},
        onPause = {},
    ) { }
}
