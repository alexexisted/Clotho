package polako.cloud.clotho.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import polako.cloud.clotho.R
import polako.cloud.clotho.domain.model.ActivityType

@Composable
fun ActivityItem(
    activity: ActivityType,
    onStartClick: () -> Unit,
) {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        color = Color.White.copy(alpha = 0.5f),
        border = BorderStroke(1.dp, Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color(activity.color)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(activity.icon),
                    contentDescription = null,
                    modifier = Modifier,
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = activity.name,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row {
//                    Tag(text = "Personal", containerColor = Color(0xFF2D2C35))
//                    Spacer(modifier = Modifier.width(8.dp))
                    Tag(text = "Tags will come in v2", containerColor = Color(0xFF3B3028), textColor = Color(0xFFF4B375))
                }
            }

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                IconButton(
                    onClick = onStartClick,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Start",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun Tag(text: String, containerColor: Color, textColor: Color = Color.LightGray) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = containerColor.copy(alpha = 0.7f)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = textColor
        )
    }
}

@Preview
@Composable
fun ActivityItemPreview() {
    val icon = R.drawable.icon_barbell
    ActivityItem(
        activity = ActivityType(
            id = 1L,
            name = "Gym",
            icon = icon,
            color = 0xFFFF7043.toInt(),
        )
    ) { }
}
