package pt.atec.casino.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import pt.atec.casino.ui.games.SlotMachineScreen
import pt.atec.casino.viewmodel.CasinoViewModel

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Home        : Screen("home",   "Lobby",    Icons.Default.Home)
    object Wallet      : Screen("wallet", "Carteira", Icons.Default.AccountBalanceWallet)
    object SlotMachine : Screen("slots",  "Slots",    Icons.Default.Home)
}

val bottomNavItems = listOf(Screen.Home, Screen.Wallet)

@Composable
fun AppNavHost(
    navController: NavHostController,
    viewModel: CasinoViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier,
        enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 350))
        },
        exitTransition = {
            fadeOut(animationSpec = tween(durationMillis = 350))
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 350))
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(durationMillis = 350))
        }
    ) {
        composable(Screen.Home.route)        { HomeScreen(navController, viewModel) }
        composable(Screen.Wallet.route)      { WalletScreen(navController, viewModel) }
        composable(Screen.SlotMachine.route) { SlotMachineScreen(navController, viewModel) }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val backStack by navController.currentBackStackEntryAsState()
    val current = backStack?.destination?.route

    if (current == Screen.SlotMachine.route) return

    NavigationBar(containerColor = Color(0xFF1A1A2E)) {
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
                icon = {
                    Icon(
                        screen.icon,
                        contentDescription = screen.label,
                        tint = if (current == screen.route) Color(0xFFFFD700) else Color.Gray
                    )
                },
                label = {
                    Text(
                        screen.label,
                        color = if (current == screen.route) Color(0xFFFFD700) else Color.Gray
                    )
                },
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFF2A2A4A))
            )
        }
    }
}