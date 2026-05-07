package pt.atec.biblioweb.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
fun MembersScreen(viewModel: BiblioViewModel) {
    val members by viewModel.members.collectAsState()
    var memberToDelete by remember { mutableStateOf<pt.atec.biblioweb.model.Member?>(null) }

    memberToDelete?.let { member ->
        AlertDialog(
            onDismissRequest = { memberToDelete = null },
            title = { Text("Remover membro") },
            text = { Text("Tens a certeza que queres remover ${member.name}?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteMember(member)
                    memberToDelete = null
                }) { Text("Remover", color = Color.Red) }
            },
            dismissButton = {
                TextButton(onClick = { memberToDelete = null }) { Text("Cancelar") }
            }
        )
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Spacer(Modifier.height(16.dp))
        Text("Membros", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text("${members.size} membros registados", fontSize = 13.sp, color = Color.Gray)
        Spacer(Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(members) { member ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier
                                .size(46.dp)
                                .background(
                                    if (member.role == "admin") Color(0xFF1A237E) else Color(0xFF1565C0),
                                    CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(member.name.first().toString(), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        }
                        Spacer(Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(member.name, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                            Text(member.email, fontSize = 12.sp, color = Color.Gray)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Badge(
                                containerColor = if (member.role == "admin") Color(0xFF1A237E) else Color(0xFF1565C0)
                            ) {
                                Text(
                                    if (member.role == "admin") "Admin" else "Membro",
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 6.dp)
                                )
                            }
                            Spacer(Modifier.height(6.dp))
                            if (member.role != "admin") {
                                IconButton(
                                    onClick = { memberToDelete = member },
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(Icons.Default.Delete, contentDescription = "Remover", tint = Color.Red, modifier = Modifier.size(18.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}