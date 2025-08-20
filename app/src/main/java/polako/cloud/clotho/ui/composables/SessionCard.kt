package polako.cloud.clotho.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import polako.cloud.clotho.R
import polako.cloud.clotho.domain.model.ActivityType
import polako.cloud.clotho.domain.model.FocusSession
import java.time.Duration
import java.time.format.DateTimeFormatter

@Composable
fun SessionCard(
    session: FocusSession,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(160.dp)
            .height(180.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E2A38)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            val iconRes = getActivityIcon(session.activityType)
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = "Activity Icon",
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = Color(0xFF2A3A4A),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(8.dp),
                tint = Color.White
            )
            
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = formatDuration(session.duration),
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = formatDate(session),
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Score: ",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 14.sp
                )
                Text(
//                    text = "${session.reflectionScore}/10",
                    text = "${5}/10",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

private fun getActivityIcon(activityType: ActivityType?): Int {
    return activityType?.icon ?: R.drawable.icon_other
}

private fun formatDuration(duration: Duration): String {
    val hours = duration.toHours()
    val minutes = duration.toMinutesPart()
    
    return if (hours > 0) {
        "$hours h $minutes min"
    } else {
        "$minutes min"
    }
}

private fun formatDate(session: FocusSession): String {
    val formatter = DateTimeFormatter.ofPattern("MMM d, yyyy")
    return session.startTime.format(formatter)
}