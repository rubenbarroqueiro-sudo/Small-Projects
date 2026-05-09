package pt.atec.casino

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import pt.atec.casino.ui.AppNavHost
import pt.atec.casino.ui.BottomBar
import pt.atec.casino.viewmodel.CasinoViewModel

private val CasinoDarkTheme = darkColorScheme(
    primary         = Color(0xFFFFD700),
    onPrimary       = Color(0xFF0A0A15),
    background      = Color(0xFF0A0A15),
    onBackground    = Color.White,
    surface         = Color(0xFF1A1A2E),
    onSurface       = Color.White,
    surfaceVariant  = Color(0xFF1A1A2E),
    onSurfaceVariant= Color.Gray,
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color(0xFF0A0A15).toArgb()),
            navigationBarStyle = SystemBarStyle.dark(Color(0xFF0A0A15).toArgb())
        )

        setContent {
            MaterialTheme(colorScheme = CasinoDarkTheme) {
                val navController = rememberNavController()
                val viewModel: CasinoViewModel = viewModel()

                Scaffold(
                    containerColor = Color(0xFF0A0A15), // ← fundo sempre escuro
                    bottomBar = { BottomBar(navController) }
                ) { innerPadding ->
                    AppNavHost(
                        navController = navController,
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}