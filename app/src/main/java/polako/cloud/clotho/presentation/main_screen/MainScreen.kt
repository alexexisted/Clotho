package polako.cloud.clotho.presentation.main_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import polako.cloud.clotho.navigation.Routes
import polako.cloud.clotho.presentation.shared.SharedFocusUIAction
import polako.cloud.clotho.presentation.shared.SharedFocusViewModel
import polako.cloud.clotho.ui.composables.MainMenuBgCard
import polako.cloud.clotho.ui.composables.MainScreenRunningSessionCard

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainMenuViewModel = hiltViewModel(),
    sharedFocusViewModel: SharedFocusViewModel = hiltViewModel(),
) {
    val gradientColors = listOf(Color(0xFF1E1A3D), Color(0xFF5D10FD))
    val globalUIState by sharedFocusViewModel.globalUiState.collectAsStateWithLifecycle()
    val localState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        sharedFocusViewModel.uiAction.collect { action ->
            when (action) {
                SharedFocusUIAction.NavigateToFocusScreen -> {
                    navController.navigate(Routes.FOCUS_SCREEN)
                }
            }
        }
    }

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(
                    brush =
                        Brush.linearGradient(
                            gradientColors,
                        ),
                ),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(vertical = 10.dp, horizontal = 5.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            MainMenuBgCard(localState.sessionsAmount, localState.sessionsDurationUIModel)

            Spacer(modifier = Modifier.height(10.dp))

            if (globalUIState.isRunning) {
                globalUIState.activityType?.let { activity ->
                    MainScreenRunningSessionCard(
                        onClick = {
                            sharedFocusViewModel.navigateToFocusScreen()
                        },
                        session = activity,
                        elapsedTime = globalUIState.elapsedTimeMillis,
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
