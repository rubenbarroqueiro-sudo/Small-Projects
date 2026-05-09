package pt.atec.casino.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import pt.atec.casino.data.PreferencesManager
import pt.atec.casino.model.Transaction

class CasinoViewModel(app: Application) : AndroidViewModel(app) {

    private val prefs = PreferencesManager(app)

    private val _balance = MutableStateFlow(1000.0)
    val balance: StateFlow<Double> = _balance

    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions: StateFlow<List<Transaction>> = _transactions

    private val _slotReels = MutableStateFlow(listOf("🍒", "🍒", "🍒"))
    val slotReels: StateFlow<List<String>> = _slotReels

    private val _isSpinning = MutableStateFlow(false)
    val isSpinning: StateFlow<Boolean> = _isSpinning

    private val _lastSlotResult = MutableStateFlow<String?>(null)
    val lastSlotResult: StateFlow<String?> = _lastSlotResult

    private val symbols = listOf("🍒", "🍋", "🍊", "🍇", "⭐", "💎", "7️⃣")

    private val multipliers = mapOf(
        "💎" to 50.0,
        "7️⃣" to 20.0,
        "⭐" to 10.0,
        "🍇" to 5.0,
        "🍊" to 4.0,
        "🍋" to 3.0,
        "🍒" to 2.0,
    )

    init {
        viewModelScope.launch {
            _balance.value = prefs.balanceFlow.first()
        }
    }

    fun spin(bet: Double) {
        if (_isSpinning.value || _balance.value < bet) return

        viewModelScope.launch {
            _isSpinning.value = true
            _lastSlotResult.value = null

            updateBalance(_balance.value - bet, "Aposta Slots", -bet)

            repeat(15) {
                _slotReels.value = List(3) { symbols.random() }
                delay(80)
            }

            val result = List(3) { symbols.random() }
            _slotReels.value = result
            _isSpinning.value = false

            calculateWin(result, bet)
        }
    }

    private suspend fun calculateWin(reels: List<String>, bet: Double) {
        val (r1, r2, r3) = reels

        val (winAmount, message) = when {
            r1 == r2 && r2 == r3 -> {
                val mult = multipliers[r1] ?: 2.0
                val win = bet * mult
                win to "🎉 JACKPOT! +€${"%.2f".format(win)} (x${mult.toInt()})"
            }
            r1 == r2 || r2 == r3 || r1 == r3 -> {
                val win = bet * 1.5
                win to "✨ Par! +€${"%.2f".format(win)}"
            }
            else -> 0.0 to "😞 Sem sorte desta vez..."
        }

        if (winAmount > 0) {
            updateBalance(_balance.value + winAmount, "Ganho Slots $r1$r2$r3", winAmount)
        }

        _lastSlotResult.value = message
    }

    fun deposit(amount: Double) {
        viewModelScope.launch {
            updateBalance(_balance.value + amount, "💵 Depósito", amount)
        }
    }

    private suspend fun updateBalance(newBalance: Double, desc: String, amount: Double) {
        _balance.value = newBalance
        prefs.updateBalance(newBalance)
        val tx = Transaction(
            description = desc,
            amount = amount,
            balanceAfter = newBalance
        )
        _transactions.value = listOf(tx) + _transactions.value
    }
}