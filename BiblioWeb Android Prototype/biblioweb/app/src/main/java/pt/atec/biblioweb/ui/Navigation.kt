package pt.atec.biblioweb.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import pt.atec.biblioweb.viewmodel.BiblioViewModel

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Home    : Screen("home",    "Início",      Icons.Default.Home)
    object Books   : Screen("books",   "Livros",      Icons.Default.Book)
    object Loans   : Screen("loans",   "Empréstimos", Icons.Default.SwapHoriz)
    object Members : Screen("members", "Membros",     Icons.Default.People)
    object Detail  : Screen("detail/{bookId}", "Detalhe", Icons.Default.Book) {
        fun createRoute(id: Int) = "detail/$id"
    }
}

val bottomNavItems = listOf(Screen.Home, Screen.Books, Screen.Loans, Screen.Members)

@Composable
fun AppNavHost(
    navController: NavHostController,
    viewModel: BiblioViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route)    { HomeScreen(viewModel) }
        composable(Screen.Books.route)   { BooksScreen(navController, viewModel) }
        composable(Screen.Loans.route)   { LoansScreen(viewModel) }
        composable(Screen.Members.route) { MembersScreen(viewModel) }
        composable(Screen.Detail.route)  { back ->
            val id = back.arguments?.getString("bookId")?.toIntOrNull()
            id?.let { BookDetailScreen(navController, viewModel, it) }
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val backStack by navController.currentBackStackEntryAsState()
    val current = backStack?.destination?.route

    NavigationBar {
        bottomNavItems.forEach { screen ->
            NavigationBarItem(
                selected = current == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(Screen.Home.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(screen.icon, contentDescription = screen.label) },
                label = { Text(screen.label) }
            )
        }
    }
}