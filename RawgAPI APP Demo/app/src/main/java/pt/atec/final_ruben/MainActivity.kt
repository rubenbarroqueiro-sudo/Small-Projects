package pt.atec.final_ruben

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import pt.atec.final_ruben.ui.AppNavHost
import pt.atec.final_ruben.viewmodel.GameViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val gameViewModel: GameViewModel = viewModel()
            AppNavHost(navController = navController, viewModel = gameViewModel)
        }
    }
}