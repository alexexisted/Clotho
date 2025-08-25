package polako.cloud.clotho

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import polako.cloud.clotho.navigation.ClothoNavHost
import polako.cloud.clotho.navigation.Routes
import polako.cloud.clotho.ui.composables.BottomNavBar
import polako.cloud.clotho.ui.theme.ClothoTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableImmersiveMode()
        enableEdgeToEdge()
        setContent {
            ClothoTheme {
                val navController = rememberNavController()

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                Scaffold(
                    bottomBar = {
                        if (currentRoute == Routes.MAIN_SCREEN || currentRoute == Routes.HISTORY_SCREEN) {
                            BottomNavBar(navController)
                        }
                    },
                ) { innerPadding ->
                    Surface(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                        color = MaterialTheme.colorScheme.background,
                    ) {
                        ClothoNavHost(navController)
                    }
                }
            }
        }
    }

    fun enableImmersiveMode() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
}
