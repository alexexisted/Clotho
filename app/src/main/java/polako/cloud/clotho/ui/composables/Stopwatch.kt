package polako.cloud.clotho.ui.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@SuppressLint("DefaultLocale")
@Composable
fun Stopwatch(
    elapsedTime: Long,
    isRunning: Boolean,
    onStart: () -> Unit,
    onPause: () -> Unit,
    onStop: () -> Unit,
) {
    val minutes = (elapsedTime / 1000) / 60
    val seconds = (elapsedTime / 1000) % 60

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = String.format("%02d:%02d", minutes, seconds),
            style = MaterialTheme.typography.displayLarge,
        )

        Spacer(modifier = Modifier.padding(vertical = 50.dp))

        Row {
            if (isRunning) {
                ElevatedButton(
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
                ) { Text("Pause") }
            } else {
                ElevatedButton(
                    onClick = onStart,
                    colors =
                        ButtonColors(
                            containerColor = MaterialTheme.colorScheme.inversePrimary,
                            contentColor = MaterialTheme.colorScheme.surface,
                            disabledContainerColor = MaterialTheme.colorScheme.error,
                            disabledContentColor = MaterialTheme.colorScheme.secondary,
                        ),
                    elevation =
                        ButtonDefaults.buttonElevation(
                            defaultElevation = 8.dp,
                            pressedElevation = 12.dp,
                        ),
                ) { Text("Start") }
            }
            Spacer(modifier = Modifier.width(16.dp))

            ElevatedButton(
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
            ) { Text("Stop") }
        }
    }
}
