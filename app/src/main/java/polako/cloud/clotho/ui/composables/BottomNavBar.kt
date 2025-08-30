package polako.cloud.clotho.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import polako.cloud.clotho.R
import polako.cloud.clotho.navigation.Routes

@Composable
fun BottomNavBar(navController: NavController) {
    val items =
        listOf(
            Screen.Home,
            Screen.History,
        )
    NavigationBar(
        containerColor = Color.White,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = screen.label) },
                label = { Text(screen.label) },
                selected = currentDestination?.route == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }
}

@Composable
fun BottomNavWithCustomButton(navController: NavController) {
    Box {
        BottomNavBar(navController)

        Button(
            onClick = { navController.navigate(Routes.SESSION_SETUP) },
            modifier =
                Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = (-30).dp)
                    .size(86.dp),
            shape = CircleShape,
            colors =
                ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.surface,
                ),
            elevation =
                ButtonDefaults.buttonElevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 12.dp,
                ),
            contentPadding = PaddingValues(0.dp),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.stress_management_icon),
                contentDescription = "Add",
                modifier = Modifier.size(44.dp),
            )
        }
    }
}

sealed class Screen(
    val route: String,
    val label: String,
    val icon: ImageVector,
) {
    object Home : Screen(Routes.MAIN_SCREEN, "Home", Icons.Default.Home)

    object History : Screen(Routes.HISTORY_SCREEN, "History", Icons.Default.List)
}
