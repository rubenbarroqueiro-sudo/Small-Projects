package pt.atec.gtn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GTNApp()
        }
    }
}

// Modelo de cada palpite
data class Palpite(
    val numero: Int,
    val resultado: String   // "baixo", "alto", "certo"
)

@Composable
fun GTNApp() {
    var numeroSecreto by remember { mutableIntStateOf((1..100).random()) }
    var tentativa by remember { mutableStateOf("") }
    var mensagem by remember { mutableStateOf("Adivinhe o número entre 1 e 100!") }
    var tentativas by remember { mutableIntStateOf(0) }
    var jogoTerminado by remember { mutableStateOf(false) }
    val listaPalpites = remember { mutableStateListOf<Palpite>() }

    fun reiniciar() {
        numeroSecreto = (1..100).random()
        tentativa = ""
        mensagem = "Adivinhe o número entre 1 e 100!"
        tentativas = 0
        jogoTerminado = false
        listaPalpites.clear()
    }

    fun verificar() {
        val num = tentativa.toIntOrNull()
        if (num == null || num < 1 || num > 100) {
            mensagem = "⚠️ Insere um número entre 1 e 100!"
            return
        }
        tentativas++
        tentativa = ""

        when {
            num < numeroSecreto -> {
                listaPalpites.add(0, Palpite(num, "baixo"))
                mensagem = "Continua a tentar..."
            }
            num > numeroSecreto -> {
                listaPalpites.add(0, Palpite(num, "alto"))
                mensagem = "Continua a tentar..."
            }
            else -> {
                listaPalpites.add(0, Palpite(num, "certo"))
                mensagem = "🎉 Acertaste em $tentativas tentativa(s)! Era o $numeroSecreto!"
                jogoTerminado = true
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text("🎯 Guess The Number", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        Text(mensagem, fontSize = 15.sp, modifier = Modifier.padding(horizontal = 8.dp))
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = tentativa,
                onValueChange = { tentativa = it },
                label = { Text("O teu número") },
                enabled = !jogoTerminado,
                singleLine = true,
                modifier = Modifier.width(150.dp)
            )
            Button(
                onClick = { verificar() },
                enabled = !jogoTerminado
            ) {
                Text("Adivinhar")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text("Tentativas: $tentativas", fontSize = 14.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(8.dp))

        if (jogoTerminado) {
            Button(
                onClick = { reiniciar() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF43A047))
            ) {
                Text("Jogar de Novo", color = Color.White)
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Lista de palpites
        if (listaPalpites.isNotEmpty()) {
            Text(
                "Histórico de palpites:",
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(listaPalpites) { palpite ->
                    PalpiteItem(palpite)
                }
            }
        }
    }
}

@Composable
fun PalpiteItem(palpite: Palpite) {
    val (cor, texto, icone) = when (palpite.resultado) {
        "baixo" -> Triple(Color(0xFFFFEBEE), "📈 Muito baixo", R.drawable.ic_arrow_up)
        "alto"  -> Triple(Color(0xFFE3F2FD), "📉 Muito alto",  R.drawable.ic_arrow_down)
        else    -> Triple(Color(0xFFE8F5E9), "✅ Correto!",    R.drawable.ic_check)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(cor, RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = icone),
                contentDescription = palpite.resultado,
                modifier = Modifier.size(36.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = "Palpite: ${palpite.numero}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = texto,
                    fontSize = 13.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
}