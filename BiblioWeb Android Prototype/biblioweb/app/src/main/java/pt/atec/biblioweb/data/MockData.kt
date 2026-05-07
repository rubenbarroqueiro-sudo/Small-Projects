package pt.atec.biblioweb.data

import pt.atec.biblioweb.model.Book
import pt.atec.biblioweb.model.Loan
import pt.atec.biblioweb.model.Member

object MockData {

    val books = mutableListOf(
        Book(1, "O Senhor dos Anéis", "J.R.R. Tolkien", "978-0-261-10235-4", "Fantasia", 3, 5, 0xFF1565C0),
        Book(2, "Harry Potter e a Pedra Filosofal", "J.K. Rowling", "978-0-7475-3269-9", "Fantasia", 1, 4, 0xFF6A1B9A),
        Book(3, "1984", "George Orwell", "978-0-452-28423-4", "Distopia", 2, 3, 0xFFB71C1C),
        Book(4, "O Código Da Vinci", "Dan Brown", "978-0-307-47427-2", "Thriller", 0, 2, 0xFF1B5E20),
        Book(5, "Duna", "Frank Herbert", "978-0-441-17271-9", "Ficção Científica", 4, 4, 0xFFE65100),
        Book(6, "Dom Quixote", "Cervantes", "978-0-06-093434-8", "Clássico", 2, 3, 0xFF880E4F),
        Book(7, "O Pequeno Príncipe", "Antoine de Saint-Exupéry", "978-2-07-040850-4", "Infantil", 5, 5, 0xFF004D40),
        Book(8, "Crime e Castigo", "Dostoiévski", "978-0-14-044913-6", "Clássico", 1, 2, 0xFF37474F),
    )

    val members = mutableListOf(
        Member(1, "Ana Silva", "ana.silva@email.com", "admin"),
        Member(2, "Bruno Costa", "bruno.costa@email.com"),
        Member(3, "Carla Mendes", "carla.mendes@email.com"),
        Member(4, "Diogo Ferreira", "diogo.ferreira@email.com"),
        Member(5, "Eva Rodrigues", "eva.rodrigues@email.com"),
    )

    val loans = mutableListOf(
        Loan(1, "Harry Potter e a Pedra Filosofal", "Bruno Costa", "2026-05-01", "ativo"),
        Loan(2, "1984", "Carla Mendes", "2026-05-03", "ativo"),
        Loan(3, "O Código Da Vinci", "Diogo Ferreira", "2026-05-04", "pendente"),
        Loan(4, "Dom Quixote", "Eva Rodrigues", "2026-04-20", "devolvido"),
        Loan(5, "O Senhor dos Anéis", "Bruno Costa", "2026-04-15", "devolvido"),
        Loan(6, "Duna", "Carla Mendes", "2026-05-06", "pendente"),
    )
}