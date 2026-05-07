package pt.atec.final_ruben.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import pt.atec.final_ruben.viewmodel.GameViewModel

sealed class Screen(val route: String) {
    object Home      : Screen("home")
    object Detail    : Screen("detail/{gameId}") {
        fun createRoute(id: Int) = "detail/$id"
    }
    object Favorites : Screen("favorites")
}

@Composable
fun AppNavHost(navController: NavHostController, viewModel: GameViewModel) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(navController, viewModel)
        }
        composable(Screen.Detail.route) { backStackEntry ->
            val gameId = backStackEntry.arguments?.getString("gameId")?.toIntOrNull()
            gameId?.let {
                viewModel.loadGameDetail(it)
                DetailScreen(navController, viewModel)
            }
        }
        composable(Screen.Favorites.route) {
            FavoritesScreen(navController, viewModel)
        }
    }
}