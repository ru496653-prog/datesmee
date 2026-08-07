package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.ChatMessage
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {
    @Query("SELECT * FROM chat_messages WHERE matchId = :matchId ORDER BY timestamp ASC")
    fun getMessagesForMatch(matchId: String): Flow<List<ChatMessage>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: ChatMessage)

    @Update
    suspend fun updateMessage(message: ChatMessage)

    @Query("DELETE FROM chat_messages WHERE messageId = :messageId")
    suspend fun deleteMessage(messageId: String)

    @Query("UPDATE chat_messages SET isPinned = :isPinned WHERE messageId = :messageId")
    suspend fun setPinned(messageId: String, isPinned: Boolean)

    @Query("UPDATE chat_messages SET reaction = :reaction WHERE messageId = :messageId")
    suspend fun setReaction(messageId: String, reaction: String?)

    @Query("SELECT * FROM chat_messages WHERE matchId = :matchId AND text LIKE '%' || :query || '%' ORDER BY timestamp DESC")
    fun searchMessages(matchId: String, query: String): Flow<List<ChatMessage>>
}
