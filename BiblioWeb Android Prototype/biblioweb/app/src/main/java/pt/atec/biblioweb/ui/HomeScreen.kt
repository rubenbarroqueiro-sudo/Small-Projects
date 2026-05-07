package pt.atec.biblioweb.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.atec.biblioweb.viewmodel.BiblioViewModel

@Composable
fun HomeScreen(viewModel: BiblioViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Spacer(Modifier.height(16.dp))
        Text("📚 BiblioWeb", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text("Painel de controlo", fontSize = 14.sp, color = Color.Gray)
        Spacer(Modifier.height(24.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatCard("Livros", viewModel.totalBooks.toString(), Icons.Default.Book, Color(0xFF1565C0), Modifier.weight(1f))
            StatCard("Membros", viewModel.totalMembers.toString(), Icons.Default.People, Color(0xFF2E7D32), Modifier.weight(1f))
        }
        Spacer(Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatCard("Ativos", viewModel.activeLoans.toString(), Icons.Default.SwapHoriz, Color(0xFFE65100), Modifier.weight(1f))
            StatCard("Pendentes", viewModel.pendingLoans.toString(), Icons.Default.HourglassEmpty, Color(0xFF6A1B9A), Modifier.weight(1f))
        }

        Spacer(Modifier.height(28.dp))
        Text("Empréstimos recentes", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        Spacer(Modifier.height(12.dp))

        viewModel.loans.value.take(3).forEach { loan ->
            val (color, label) = when (loan.status) {
                "ativo"     -> Color(0xFF2E7D32) to "Ativo"
                "pendente"  -> Color(0xFFE65100) to "Pendente"
                else        -> Color.Gray to "Devolvido"
            }
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(loan.bookTitle, fontWeight = FontWeight.Medium, fontSize = 14.sp)
                        Text(loan.memberName, fontSize = 12.sp, color = Color.Gray)
                    }
                    Badge(containerColor = color) {
                        Text(label, color = Color.White, modifier = Modifier.padding(horizontal = 6.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun StatCard(title: String, value: String, icon: ImageVector, color: Color, modifier: Modifier = Modifier) {
    Card(modifier = modifier, shape = RoundedCornerShape(16.dp)) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(color.copy(alpha = 0.15f), RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(22.dp))
            }
            Spacer(Modifier.height(10.dp))
            Text(value, fontSize = 26.sp, fontWeight = FontWeight.Bold, color = color)
            Text(title, fontSize = 13.sp, color = Color.Gray)
        }
    }
}