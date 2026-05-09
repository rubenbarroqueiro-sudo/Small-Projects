package pt.atec.casino.model

data class GameInfo(
    val id: String,
    val name: String,
    val description: String,
    val emoji: String,
    val available: Boolean = true
)

data class Transaction(
    val id: Long = System.currentTimeMillis(),
    val description: String,
    val amount: Double,
    val balanceAfter: Double,
    val timestamp: Long = System.currentTimeMillis()
)

val ALL_GAMES = listOf(
    GameInfo("slots",      "Slot Machine",  "Tenta a tua sorte nas slots!",         "🎰", available = true),
    GameInfo("blackjack",  "Blackjack",     "Chega ao 21 sem passar!",              "🃏", available = false),
    GameInfo("roulette",   "Roleta",        "Faz a tua aposta e roda!",             "🎡", available = false),
    GameInfo("dice",       "Dados",         "Aposta no resultado dos dados!",       "🎲", available = false),
)