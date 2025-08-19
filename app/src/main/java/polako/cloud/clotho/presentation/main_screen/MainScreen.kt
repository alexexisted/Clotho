package polako.cloud.clotho.presentation.main_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import polako.cloud.clotho.ui.composables.MainMenuBgCard
import polako.cloud.clotho.ui.composables.StartSessionButton
import polako.cloud.clotho.ui.theme.ClothoTheme

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainMenuViewModel = hiltViewModel()
) {
    val gradientColors = listOf(Color(0xFF006187), Color(0xFF313131))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    gradientColors
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 10.dp, horizontal = 5.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            MainMenuBgCard()

            Spacer(modifier = Modifier.weight(1f))

            StartSessionButton(
                navController = navController,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}