package polako.cloud.clotho.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import polako.cloud.clotho.presentation.focus_screen.FocusScreen
import polako.cloud.clotho.presentation.history_screen.HistoryScreen
import polako.cloud.clotho.presentation.main_screen.MainScreen
import polako.cloud.clotho.presentation.session_setup.SessionSetupScreen
import polako.cloud.clotho.presentation.shared.SharedFocusViewModel

object Routes {
    const val MAIN_GRAPH = "main_graph"
    const val MAIN_SCREEN = "main_screen"
    const val SESSION_SETUP = "session_setup"
    const val FOCUS_SCREEN = "focus_screen"
    const val HISTORY_SCREEN = "history_screen"
}

@Composable
fun ClothoNavHost(
    navController: NavHostController,
    startDestination: String = Routes.MAIN_SCREEN,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        route = Routes.MAIN_GRAPH,
    ) {
        composable(Routes.MAIN_SCREEN) { backStackEntry ->
            val parentEntry =
                remember(backStackEntry) {
                    navController.getBackStackEntry(Routes.MAIN_GRAPH)
                }
            val sharedVM: SharedFocusViewModel = hiltViewModel(parentEntry)
            MainScreen(navController, sharedFocusViewModel = sharedVM)
        }

        composable(Routes.SESSION_SETUP) {
            SessionSetupScreen(navController)
        }

        composable(Routes.FOCUS_SCREEN) { backStackEntry ->
            val parentEntry =
                remember(backStackEntry) {
                    navController.getBackStackEntry(Routes.MAIN_GRAPH)
                }
            val sharedVM: SharedFocusViewModel = hiltViewModel(parentEntry)
            FocusScreen(navController = navController, sharedFocusViewModel = sharedVM)
        }

        composable(Routes.HISTORY_SCREEN) { backStackEntry ->
            val parentEntry =
                remember(backStackEntry) {
                    navController.getBackStackEntry(Routes.MAIN_GRAPH)
                }
            val sharedVM: SharedFocusViewModel = hiltViewModel(parentEntry)
            HistoryScreen(navController, sharedFocusViewModel = sharedVM)
        }
    }
}
