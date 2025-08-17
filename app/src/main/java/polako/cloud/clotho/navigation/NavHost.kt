package polako.cloud.clotho.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import polako.cloud.clotho.presentation.main_screen.MainScreen
import polako.cloud.clotho.presentation.session_setup.SessionSetupScreen

object Routes {
    const val MAIN_SCREEN = "main_screen"
    const val SESSION_SETUP = "session_setup"
}

@Composable
fun ClothoNavHost(
    startDestination: String = Routes.MAIN_SCREEN,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Routes.MAIN_SCREEN) {
            MainScreen(navController)
        }

        composable(Routes.SESSION_SETUP) {
            SessionSetupScreen(navController)
        }
    }
}