package pt.atec.biblioweb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import pt.atec.biblioweb.ui.AppNavHost
import pt.atec.biblioweb.ui.BottomBar
import pt.atec.biblioweb.viewmodel.BiblioViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val viewModel: BiblioViewModel = viewModel()

            Scaffold(
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