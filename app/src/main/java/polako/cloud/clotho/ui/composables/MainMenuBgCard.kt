package polako.cloud.clotho.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainMenuBgCard() {
    Card(
        modifier =
            Modifier
                .padding(24.dp)
                .fillMaxWidth(),
        colors =
            CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.3f),
            ),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(0.3.dp, Color.LightGray),
    ) {
        Column(
            modifier =
                Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Clotho App",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Start a focus session to track your productivity and build better habits.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.8f),
                textAlign = TextAlign.Center,
                lineHeight = 20.sp,
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                SecondaryStatCard(
                    value = "3",
                    label = "Today",
                    modifier = Modifier.weight(1f),
                )

                SecondaryStatCard(
                    value = "16h",
                    label = "This week",
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}
