package pt.atec.final_ruben.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pt.atec.final_ruben.model.Game
import pt.atec.final_ruben.network.RawgApi

class GameViewModel : ViewModel() {

    private val _games = MutableStateFlow<List<Game>>(emptyList())
    val games: StateFlow<List<Game>> = _games

    private val _selectedGame = MutableStateFlow<Game?>(null)
    val selectedGame: StateFlow<Game?> = _selectedGame

    private val _favorites = MutableStateFlow<List<Game>>(emptyList())
    val favorites: StateFlow<List<Game>> = _favorites

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    init {
        loadGames()
    }

    fun loadGames(search: String = "") {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RawgApi.service.getGames(search = search)
                _games.value = response.results
            } catch (e: Exception) {
                _games.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadGameDetail(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _selectedGame.value = RawgApi.service.getGameDetail(id)
            } catch (e: Exception) {
                _selectedGame.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun onSearchChange(query: String) {
        _searchQuery.value = query
        loadGames(query)
    }

    fun toggleFavorite(game: Game) {
        val current = _favorites.value.toMutableList()
        if (current.any { it.id == game.id }) {
            current.removeAll { it.id == game.id }
        } else {
            current.add(game)
        }
        _favorites.value = current
    }

    fun isFavorite(game: Game): Boolean =
        _favorites.value.any { it.id == game.id }
}