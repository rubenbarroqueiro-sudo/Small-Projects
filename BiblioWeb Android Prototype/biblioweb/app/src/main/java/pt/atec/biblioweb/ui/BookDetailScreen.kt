package pt.atec.biblioweb.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pt.atec.biblioweb.viewmodel.BiblioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailScreen(navController: NavController, viewModel: BiblioViewModel, bookId: Int) {
    val book = viewModel.books.value.find { it.id == bookId } ?: return

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhes do Livro") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1A237E), titleContentColor = Color.White, navigationIconContentColor = Color.White)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .size(130.dp)
                    .background(Color(book.coverColor), RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(book.title.first().toString(), color = Color.White, fontSize = 52.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(20.dp))
            Text(book.title, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text(book.author, fontSize = 15.sp, color = Color.Gray)
            Spacer(Modifier.height(8.dp))

            AssistChip(onClick = {}, label = { Text(book.genre) })

            Spacer(Modifier.height(24.dp))

            // Info cards
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                InfoTile("ISBN", book.isbn, Modifier.weight(1f))
                InfoTile("Disponíveis", "${book.available} / ${book.total}", Modifier.weight(1f))
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {},
                enabled = book.available > 0,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A237E))
            ) {
                Text(if (book.available > 0) "Solicitar Empréstimo" else "Indisponível")
            }
        }
    }
}

@Composable
fun InfoTile(label: String, value: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier, shape = RoundedCornerShape(12.dp)) {
        Column(modifier = Modifier.padding(14.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(value, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(label, fontSize = 12.sp, color = Color.Gray)
        }
    }
}