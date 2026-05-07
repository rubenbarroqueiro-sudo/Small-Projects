package pt.atec.biblioweb.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import pt.atec.biblioweb.data.MockData
import pt.atec.biblioweb.model.Book
import pt.atec.biblioweb.model.Loan
import pt.atec.biblioweb.model.Member

class BiblioViewModel : ViewModel() {

    private val _books = MutableStateFlow(MockData.books.toList())
    val books: StateFlow<List<Book>> = _books

    private val _members = MutableStateFlow(MockData.members.toList())
    val members: StateFlow<List<Member>> = _members

    private val _loans = MutableStateFlow(MockData.loans.toList())
    val loans: StateFlow<List<Loan>> = _loans

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    val totalBooks get() = MockData.books.size
    val totalMembers get() = MockData.members.size
    val activeLoans get() = MockData.loans.count { it.status == "ativo" }
    val pendingLoans get() = MockData.loans.count { it.status == "pendente" }

    fun onSearchChange(query: String) {
        _searchQuery.value = query
        _books.value = if (query.isEmpty()) {
            MockData.books.toList()
        } else {
            MockData.books.filter {
                it.title.contains(query, ignoreCase = true) ||
                        it.author.contains(query, ignoreCase = true) ||
                        it.genre.contains(query, ignoreCase = true)
            }
        }
    }

    fun acceptLoan(loan: Loan) {
        val updated = MockData.loans.map {
            if (it.id == loan.id) it.copy(status = "ativo") else it
        }
        MockData.loans.apply { clear(); addAll(updated) }
        _loans.value = MockData.loans.toList()
    }

    fun returnLoan(loan: Loan) {
        val updated = MockData.loans.map {
            if (it.id == loan.id) it.copy(status = "devolvido") else it
        }
        MockData.loans.apply { clear(); addAll(updated) }
        _loans.value = MockData.loans.toList()
    }

    fun deleteMember(member: Member) {
        MockData.members.remove(member)
        _members.value = MockData.members.toList()
    }
}