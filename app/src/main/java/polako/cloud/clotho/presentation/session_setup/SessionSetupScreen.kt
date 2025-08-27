package polako.cloud.clotho.presentation.session_setup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import polako.cloud.clotho.ui.composables.ActivityItem

@Composable
fun SessionSetupScreen(
    navController: NavController,
    viewModel: SessionSetupViewModel = hiltViewModel(),
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
        IconButton(
            onClick = { navController.popBackStack() },
            modifier =
                Modifier
                    .padding(20.dp)
                    .align(Alignment.TopStart),
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
            )
        }

        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Choose the Activity",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                modifier = Modifier.padding(vertical = 20.dp),
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                contentPadding = PaddingValues(horizontal = 5.dp, vertical = 2.dp),
                modifier = Modifier,
            ) {
                items(state.activityTypesLists) { activityType ->
                    ActivityItem(
                        activity = activityType,
//                        onSelected = { viewModel.onActivityTypeSelected(activityType) },
                    ) {
                        viewModel.onActivityTypeSelected(activityType)
                        navController.navigate(polako.cloud.clotho.navigation.Routes.FOCUS_SCREEN)
                    }
                }
            }
        }
    }
}
