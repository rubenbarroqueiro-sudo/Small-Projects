package pt.atec.biblioweb.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.atec.biblioweb.viewmodel.BiblioViewModel

@Composable
fun LoansScreen(viewModel: BiblioViewModel) {
    val loans by viewModel.loans.collectAsState()
    var filtro by remember { mutableStateOf("todos") }

    val filtrados = if (filtro == "todos") loans else loans.filter { it.status == filtro }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Spacer(Modifier.height(16.dp))
        Text("Empréstimos", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("todos", "pendente", "ativo", "devolvido").forEach { f ->
                FilterChip(
                    selected = filtro == f,
                    onClick = { filtro = f },
                    label = { Text(f.replaceFirstChar { it.uppercase() }) }
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(filtrados) { loan ->
                val (bgColor, statusLabel) = when (loan.status) {
                    "ativo"    -> Color(0xFFE8F5E9) to "Ativo"
                    "pendente" -> Color(0xFFFFF3E0) to "Pendente"
                    else       -> Color(0xFFF5F5F5) to "Devolvido"
                }
                val badgeColor = when (loan.status) {
                    "ativo"    -> Color(0xFF2E7D32)
                    "pendente" -> Color(0xFFE65100)
                    else       -> Color.Gray
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = bgColor)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(loan.bookTitle, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                                Text(loan.memberName, fontSize = 12.sp, color = Color.Gray)
                                Text(loan.requestDate, fontSize = 11.sp, color = Color.Gray)
                            }
                            Badge(containerColor = badgeColor) {
                                Text(statusLabel, color = Color.White, modifier = Modifier.padding(horizontal = 6.dp))
                            }
                        }

                        if (loan.status == "pendente" || loan.status == "ativo") {
                            Spacer(Modifier.height(10.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                if (loan.status == "pendente") {
                                    Button(
                                        onClick = { viewModel.acceptLoan(loan) },
                                        modifier = Modifier.height(34.dp),
                                        contentPadding = PaddingValues(horizontal = 14.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
                                    ) { Text("Aceitar", fontSize = 12.sp) }
                                }
                                if (loan.status == "ativo") {
                                    OutlinedButton(
                                        onClick = { viewModel.returnLoan(loan) },
                                        modifier = Modifier.height(34.dp),
                                        contentPadding = PaddingValues(horizontal = 14.dp)
                                    ) { Text("Devolver", fontSize = 12.sp) }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}