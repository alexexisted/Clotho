package polako.cloud.clotho.ui.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import polako.cloud.clotho.domain.model.ActivityType
import java.util.concurrent.TimeUnit

@Preview
@Composable
fun MainScreenRunningSessionCard(
    onClick: () -> Unit,
    session: ActivityType,
    elapsedTime: Long,
) {
    Card(
        modifier =
            Modifier
                .padding(horizontal = 20.dp, vertical = 10.dp)
                .clickable(enabled = true, onClick = onClick),
        colors =
            CardDefaults.cardColors(
                containerColor = Color.Transparent.copy(alpha = 0.3f),
            ),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(0.3.dp, Color.LightGray),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = session.icon),
                    contentDescription = null,
                    tint = Color(session.color),
                    modifier = Modifier.size(28.dp),
                )

                Column(
                    modifier = Modifier.padding(start = 12.dp),
                ) {
                    Text(
                        text = session.name,
                        style = MaterialTheme.typography.bodyLarge,
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = formatElapsedTime(elapsedTime),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }
}

@SuppressLint("DefaultLocale")
private fun formatElapsedTime(timeMillis: Long): String {
    val hours = TimeUnit.MILLISECONDS.toHours(timeMillis)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(timeMillis) % 60
    val seconds = TimeUnit.MILLISECONDS.toSeconds(timeMillis) % 60

    return if (hours > 0) {
        String.format("%02d:%02d:%02d", hours, minutes, seconds)
    } else {
        String.format("%02d:%02d", minutes, seconds)
    }
}
