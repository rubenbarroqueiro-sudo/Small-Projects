package pt.atec.casino.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pt.atec.casino.model.ALL_GAMES
import pt.atec.casino.viewmodel.CasinoViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: CasinoViewModel) {
    val balance by viewModel.balance.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D0D1A))
            .padding(20.dp)
    ) {
        Spacer(Modifier.height(16.dp))

        // Header
        Text("🎰 Royal Casino", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFFD700))
        Text("Boa sorte!", fontSize = 14.sp, color = Color.Gray)

        Spacer(Modifier.height(20.dp))

        // Saldo card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(listOf(Color(0xFF1A1A4E), Color(0xFF2D1B69))),
                    RoundedCornerShape(16.dp)
                )
                .padding(20.dp)
        ) {
            Column {
                Text("Saldo disponível", fontSize = 13.sp, color = Color.Gray)
                Text(
                    "€${"%.2f".format(balance)}",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFD700)
                )
            }
        }

        Spacer(Modifier.height(28.dp))
        Text("Jogos", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
        Spacer(Modifier.height(12.dp))

        // Grid de jogos
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(ALL_GAMES) { game ->
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .background(Color(0xFF1A1A2E), RoundedCornerShape(16.dp))
                        .border(
                            1.dp,
                            if (game.available) Color(0xFFFFD700).copy(alpha = 0.4f) else Color.DarkGray,
                            RoundedCornerShape(16.dp)
                        )
                        .alpha(if (game.available) 1f else 0.5f)
                        .clickable(enabled = game.available) {
                            navController.navigate(game.id)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(game.emoji, fontSize = 40.sp)
                        Spacer(Modifier.height(8.dp))
                        Text(game.name, color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                        Text(game.description, color = Color.Gray, fontSize = 11.sp)
                        if (!game.available) {
                            Spacer(Modifier.height(6.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Lock, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(12.dp))
                                Spacer(Modifier.width(4.dp))
                                Text("Em breve", color = Color.Gray, fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}