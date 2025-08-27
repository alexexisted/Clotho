package polako.cloud.clotho.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import polako.cloud.clotho.navigation.Routes

@Composable
fun StartSessionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonText: String = "Focus",
) {
    Button(
        onClick = onClick,
        modifier =
            modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(8.dp),
        colors =
            ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.scrim,
            ),
    ) {
        Text(
            text = buttonText,
            style = MaterialTheme.typography.titleMedium,
        )
    }
}

@Composable
fun StartSessionButton(
    navController: NavController,
    modifier: Modifier = Modifier,
    buttonText: String = "Focus",
) {
    StartSessionButton(
        onClick = { navController.navigate(Routes.SESSION_SETUP) },
        modifier = modifier,
        buttonText = buttonText,
    )
}
