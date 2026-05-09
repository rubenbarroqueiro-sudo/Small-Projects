package pt.atec.casino.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pt.atec.casino.viewmodel.CasinoViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletScreen(navController: NavController, viewModel: CasinoViewModel) {
    val balance by viewModel.balance.collectAsState()
    val transactions by viewModel.transactions.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("💰 Carteira", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF0D0D1A))
            )
        },
        containerColor = Color(0xFF0D0D1A)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1A1A2E), RoundedCornerShape(16.dp))
                    .padding(20.dp)
            ) {
                Column {
                    Text("Saldo atual", color = Color.Gray, fontSize = 13.sp)
                    Text("€${"%.2f".format(balance)}", color = Color(0xFFFFD700), fontSize = 30.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(Modifier.height(16.dp))

            Text("Depósito rápido", color = Color.White, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(100.0, 250.0, 500.0, 1000.0).forEach { amount ->
                    OutlinedButton(
                        onClick = { viewModel.deposit(amount) },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFFFD700)),
                        border = ButtonDefaults.outlinedButtonBorder.copy()
                    ) {
                        Text("+€${amount.toInt()}", fontSize = 11.sp)
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
            Text("Histórico", color = Color.White, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(8.dp))

            if (transactions.isEmpty()) {
                Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                    Text("Sem transações ainda", color = Color.Gray)
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(transactions) { tx ->
                        val isPositive = tx.amount >= 0
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFF1A1A2E), RoundedCornerShape(10.dp))
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(tx.description, color = Color.White, fontSize = 13.sp)
                                Text(
                                    SimpleDateFormat("dd/MM HH:mm", Locale.getDefault()).format(Date(tx.timestamp)),
                                    color = Color.Gray,
                                    fontSize = 11.sp
                                )
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    "${if (isPositive) "+" else ""}€${"%.2f".format(tx.amount)}",
                                    color = if (isPositive) Color(0xFF4CAF50) else Color(0xFFEF5350),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                                Text("€${"%.2f".format(tx.balanceAfter)}", color = Color.Gray, fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}