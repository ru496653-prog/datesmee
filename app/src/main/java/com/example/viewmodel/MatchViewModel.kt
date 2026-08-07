package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.Match
import com.example.data.repository.MatchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class MatchTab { ACTIVE, FAVORITES, ARCHIVED }

class MatchViewModel(private val matchRepository: MatchRepository) : ViewModel() {
    val activeTab = MutableStateFlow(MatchTab.ACTIVE)
    val searchQuery = MutableStateFlow("")

    val activeMatches: StateFlow<List<Match>> = matchRepository.allActiveMatches
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val favoriteMatches: StateFlow<List<Match>> = matchRepository.favoriteMatches
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val archivedMatches: StateFlow<List<Match>> = matchRepository.archivedMatches
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val displayedMatches: StateFlow<List<Match>> = combine(
        activeMatches, favoriteMatches, archivedMatches, activeTab, searchQuery
    ) { active, favs, archived, tab, query ->
        val targetList = when (tab) {
            MatchTab.ACTIVE -> active
            MatchTab.FAVORITES -> favs
            MatchTab.ARCHIVED -> archived
        }
        if (query.isBlank()) targetList
        else targetList.filter { it.userName.contains(query, ignoreCase = true) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun toggleFavorite(matchId: String, currentFav: Boolean) {
        viewModelScope.launch {
            matchRepository.toggleFavorite(matchId, !currentFav)
        }
    }

    fun toggleArchive(matchId: String, currentArchived: Boolean) {
        viewModelScope.launch {
            matchRepository.toggleArchive(matchId, !currentArchived)
        }
    }

    fun deleteMatch(matchId: String) {
        viewModelScope.launch {
            matchRepository.deleteMatch(matchId)
        }
    }
}
