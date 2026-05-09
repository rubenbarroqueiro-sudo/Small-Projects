package pt.atec.casino.ui.games

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pt.atec.casino.viewmodel.CasinoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SlotMachineScreen(navController: NavController, viewModel: CasinoViewModel) {
    val balance by viewModel.balance.collectAsState()
    val reels by viewModel.slotReels.collectAsState()
    val isSpinning by viewModel.isSpinning.collectAsState()
    val lastResult by viewModel.lastSlotResult.collectAsState()
    var selectedBet by remember { mutableStateOf(10.0) }
    val bets = listOf(5.0, 10.0, 25.0, 50.0, 100.0)

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val reelScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.06f,
        animationSpec = infiniteRepeatable(
            animation = tween(300, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "reelPulse"
    )

    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )

    val btnScale by animateFloatAsState(
        targetValue = if (isSpinning) 0.93f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "btnScale"
    )

    val isWin = lastResult?.contains("+") == true
    val resultColor = when {
        lastResult?.contains("JACKPOT") == true -> Color(0xFFFFD700)
        isWin -> Color(0xFF4CAF50)
        else -> Color(0xFF9E9E9E)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🎰 Slot Machine", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF0A0A15))
            )
        },
        containerColor = Color(0xFF0A0A15)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .background(
                        Brush.horizontalGradient(listOf(Color(0xFF1A1A4E), Color(0xFF2D1B69), Color(0xFF1A1A4E))),
                        RoundedCornerShape(14.dp)
                    )
                    .padding(horizontal = 28.dp, vertical = 12.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("SALDO", fontSize = 11.sp, color = Color.Gray, letterSpacing = 2.sp)
                    Text(
                        "€${"%.2f".format(balance)}",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFD700)
                    )
                }
            }

            Spacer(Modifier.height(28.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(16.dp, RoundedCornerShape(28.dp))
                    .background(
                        Brush.verticalGradient(
                            listOf(Color(0xFF1F1F4E), Color(0xFF0D0D2E), Color(0xFF1F1F4E))
                        ),
                        RoundedCornerShape(28.dp)
                    )
                    .border(
                        2.dp,
                        Brush.horizontalGradient(listOf(Color(0xFFFFD700), Color(0xFFFF8C00), Color(0xFFFFD700))),
                        RoundedCornerShape(28.dp)
                    )
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    Text("✦ ✦ ✦", color = Color(0xFFFFD700).copy(alpha = 0.5f), fontSize = 12.sp)
                    Spacer(Modifier.height(16.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        reels.forEachIndexed { index, symbol ->
                            // Delay visual por tambor
                            val drumScale = if (isSpinning) reelScale else 1f

                            Box(
                                modifier = Modifier
                                    .size(92.dp)
                                    .scale(drumScale)
                                    .background(
                                        Brush.verticalGradient(
                                            listOf(Color(0xFF0A0A20), Color(0xFF12123A), Color(0xFF0A0A20))
                                        ),
                                        RoundedCornerShape(14.dp)
                                    )
                                    .border(
                                        1.5.dp,
                                        if (isSpinning)
                                            Color(0xFFFFD700).copy(alpha = glowAlpha)
                                        else
                                            Color(0xFFFFD700).copy(alpha = 0.2f),
                                        RoundedCornerShape(14.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(symbol, fontSize = 38.sp)
                            }
                        }
                    }

                    Spacer(Modifier.height(20.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isSpinning) {
                            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                repeat(3) { i ->
                                    val dotAlpha by infiniteTransition.animateFloat(
                                        initialValue = 0.2f,
                                        targetValue = 1f,
                                        animationSpec = infiniteRepeatable(
                                            animation = tween(400, delayMillis = i * 150),
                                            repeatMode = RepeatMode.Reverse
                                        ),
                                        label = "dot$i"
                                    )
                                    Text("●", color = Color(0xFFFFD700).copy(alpha = dotAlpha), fontSize = 14.sp)
                                }
                            }
                        } else {
                            lastResult?.let {
                                Text(
                                    it,
                                    color = resultColor.copy(alpha = if (isWin) glowAlpha else 1f),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                            } ?: Text("Faz a tua aposta!", color = Color.Gray, fontSize = 14.sp)
                        }
                    }

                    Text("✦ ✦ ✦", color = Color(0xFFFFD700).copy(alpha = 0.5f), fontSize = 12.sp)
                }
            }

            Spacer(Modifier.height(24.dp))

            Text("APOSTA", fontSize = 11.sp, color = Color.Gray, letterSpacing = 2.sp)
            Spacer(Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                bets.forEach { bet ->
                    val isSelected = selectedBet == bet
                    val chipScale by animateFloatAsState(
                        targetValue = if (isSelected) 1.1f else 1f,
                        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                        label = "chip$bet"
                    )
                    Box(
                        modifier = Modifier
                            .scale(chipScale)
                            .background(
                                if (isSelected)
                                    Brush.verticalGradient(listOf(Color(0xFFFFD700), Color(0xFFFF8C00)))
                                else
                                    Brush.verticalGradient(listOf(Color(0xFF1A1A2E), Color(0xFF12122A))),
                                RoundedCornerShape(10.dp)
                            )
                            .border(
                                1.dp,
                                if (isSelected) Color.Transparent else Color(0xFFFFD700).copy(alpha = 0.2f),
                                RoundedCornerShape(10.dp)
                            )
                            .padding(horizontal = 10.dp, vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        TextButton(
                            onClick = { if (!isSpinning) selectedBet = bet },
                            contentPadding = PaddingValues(0.dp),
                            modifier = Modifier.height(20.dp)
                        ) {
                            Text(
                                "€${bet.toInt()}",
                                color = if (isSelected) Color.Black else Color.White,
                                fontSize = 13.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = { viewModel.spin(selectedBet) },
                enabled = !isSpinning && balance >= selectedBet,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .scale(btnScale),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    disabledContainerColor = Color(0xFF2A2A2A)
                ),
                contentPadding = PaddingValues(0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            if (!isSpinning && balance >= selectedBet)
                                Brush.horizontalGradient(listOf(Color(0xFFFFD700), Color(0xFFFF8C00), Color(0xFFFFD700)))
                            else
                                Brush.horizontalGradient(listOf(Color(0xFF2A2A2A), Color(0xFF2A2A2A))),
                            RoundedCornerShape(18.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        if (isSpinning) "🎰  A rodar..." else "🎰  GIRAR  —  €${selectedBet.toInt()}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = if (!isSpinning && balance >= selectedBet) Color(0xFF0A0A15) else Color.Gray,
                        letterSpacing = 1.sp
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            Text("PRÉMIOS", fontSize = 11.sp, color = Color.Gray, letterSpacing = 2.sp)
            Spacer(Modifier.height(10.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1A1A2E), RoundedCornerShape(16.dp))
                    .border(1.dp, Color(0xFFFFD700).copy(alpha = 0.15f), RoundedCornerShape(16.dp))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(
                    Triple("💎💎💎", "JACKPOT", "x50"),
                    Triple("7️⃣7️⃣7️⃣", "Lucky 7s", "x20"),
                    Triple("⭐⭐⭐", "Estrelas", "x10"),
                    Triple("🍇🍇🍇", "Uvas", "x5"),
                    Triple("🍊🍊🍊", "Laranjas", "x4"),
                    Triple("🍋🍋🍋", "Limões", "x3"),
                    Triple("🍒🍒🍒", "Cerejas", "x2"),
                    Triple("Qualquer par", "", "x1.5"),
                ).forEach { (combo, name, mult) ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(combo, fontSize = 14.sp, color = Color.White)
                            if (name.isNotEmpty()) Text(name, fontSize = 12.sp, color = Color.Gray)
                        }
                        Text(
                            mult,
                            color = if (mult == "x50") Color(0xFFFFD700) else Color(0xFFFF8C00),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    if (combo != "Qualquer par") Divider(color = Color.White.copy(alpha = 0.05f))
                }
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}