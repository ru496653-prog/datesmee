package com.example.data.repository

import com.example.data.local.MatchDao
import com.example.data.model.Match
import kotlinx.coroutines.flow.Flow

class MatchRepository(private val matchDao: MatchDao) {
    val allActiveMatches: Flow<List<Match>> = matchDao.getAllActiveMatches()
    val favoriteMatches: Flow<List<Match>> = matchDao.getFavoriteMatches()
    val archivedMatches: Flow<List<Match>> = matchDao.getArchivedMatches()

    fun getMatchById(matchId: String): Flow<Match?> = matchDao.getMatchById(matchId)

    suspend fun insertMatch(match: Match) {
        matchDao.insertMatch(match)
    }

    suspend fun toggleFavorite(matchId: String, isFavorite: Boolean) {
        matchDao.toggleFavorite(matchId, isFavorite)
    }

    suspend fun toggleArchive(matchId: String, isArchived: Boolean) {
        matchDao.toggleArchive(matchId, isArchived)
    }

    suspend fun deleteMatch(matchId: String) {
        matchDao.deleteMatch(matchId)
    }
}
