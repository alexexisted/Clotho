package polako.cloud.clotho.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MainMenuBgCard(
    sessionsAmount: Int,
    sessionsTime: String,
) {
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
                style = MaterialTheme.typography.displayLarge,
                color = Color.White,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Start a focus session to track your productivity and build better habits.",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White.copy(alpha = 0.8f),
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                SecondaryStatCard(
                    value = sessionsAmount.toString(),
                    label = "Sessions",
                    modifier = Modifier,
                )

                SecondaryStatCard(
                    value = sessionsTime,
                    label = "Focus duration",
                    modifier = Modifier,
                )
            }
        }
    }
}

@Preview
@Composable
fun MainMenuBgPreview() {
    MainMenuBgCard(
        sessionsAmount = 3,
        sessionsTime = "45m 55s",
    )
}
