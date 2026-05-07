package pt.atec.biblioweb.model

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val isbn: String,
    val genre: String,
    val available: Int,
    val total: Int,
    val coverColor: Long = 0xFF1A237E
)

data class Member(
    val id: Int,
    val name: String,
    val email: String,
    val role: String = "membro"
)

data class Loan(
    val id: Int,
    val bookTitle: String,
    val memberName: String,
    val requestDate: String,
    val status: String  // "pendente", "ativo", "devolvido"
)