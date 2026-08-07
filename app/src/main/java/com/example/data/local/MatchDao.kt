package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.Match
import kotlinx.coroutines.flow.Flow

@Dao
interface MatchDao {
    @Query("SELECT * FROM matches WHERE isArchived = 0 ORDER BY matchedAtTimestamp DESC")
    fun getAllActiveMatches(): Flow<List<Match>>

    @Query("SELECT * FROM matches WHERE isFavorite = 1 AND isArchived = 0 ORDER BY matchedAtTimestamp DESC")
    fun getFavoriteMatches(): Flow<List<Match>>

    @Query("SELECT * FROM matches WHERE isArchived = 1 ORDER BY matchedAtTimestamp DESC")
    fun getArchivedMatches(): Flow<List<Match>>

    @Query("SELECT * FROM matches WHERE matchId = :matchId")
    fun getMatchById(matchId: String): Flow<Match?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMatch(match: Match)

    @Update
    suspend fun updateMatch(match: Match)

    @Query("UPDATE matches SET isFavorite = :isFavorite WHERE matchId = :matchId")
    suspend fun toggleFavorite(matchId: String, isFavorite: Boolean)

    @Query("UPDATE matches SET isArchived = :isArchived WHERE matchId = :matchId")
    suspend fun toggleArchive(matchId: String, isArchived: Boolean)

    @Query("DELETE FROM matches WHERE matchId = :matchId")
    suspend fun deleteMatch(matchId: String)
}
