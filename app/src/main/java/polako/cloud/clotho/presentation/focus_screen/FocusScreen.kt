package polako.cloud.clotho.presentation.focus_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import polako.cloud.clotho.navigation.Routes
import polako.cloud.clotho.presentation.reflection_screen.ReflectionBS
import polako.cloud.clotho.ui.composables.Stopwatch

@Composable
fun FocusScreen(
    navController: NavController,
    viewModel: FocusViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsState()
    val gradientColors = listOf(Color(0xFF006187), Color(0xFF313131))

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
                    .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            state.activity?.let { activity ->
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Icon(
                        painter = painterResource(id = activity.icon),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(32.dp),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = activity.name,
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                    )
                }
            }
            Stopwatch(
                elapsedTime = state.elapsedTimeMillis,
                isRunning = state.isRunning,
                onPause = { viewModel.onAction(FocusUIAction.Pause) },
                onStop = {
                    viewModel.onAction(FocusUIAction.Stop)
                },
                onStart = { viewModel.onAction(FocusUIAction.Start) },
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        if (state.showReflectionBottomSheet) {
            ReflectionBS(
                onDismissRequest = {
                    viewModel.onAction(FocusUIAction.DismissReflection)
                },
                onSaveClicked = { score, tags ->
                    viewModel.onAction(FocusUIAction.SaveReflection(score, tags))
                    navController.navigate(Routes.MAIN_SCREEN) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                reflectionScore = state.initialReflectionScore,
                reflectionTags = state.reflectionChips,
                selectedTags = state.selectedTags,
            )
        }
    }
}
