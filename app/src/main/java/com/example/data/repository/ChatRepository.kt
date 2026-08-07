package com.example.data.repository

import com.example.data.local.ChatDao
import com.example.data.model.ChatMessage
import kotlinx.coroutines.flow.Flow

class ChatRepository(private val chatDao: ChatDao) {
    fun getMessagesForMatch(matchId: String): Flow<List<ChatMessage>> = chatDao.getMessagesForMatch(matchId)

    suspend fun sendMessage(message: ChatMessage) {
        chatDao.insertMessage(message)
    }

    suspend fun deleteMessage(messageId: String) {
        chatDao.deleteMessage(messageId)
    }

    suspend fun setPinned(messageId: String, isPinned: Boolean) {
        chatDao.setPinned(messageId, isPinned)
    }

    suspend fun setReaction(messageId: String, reaction: String?) {
        chatDao.setReaction(messageId, reaction)
    }

    fun searchMessages(matchId: String, query: String): Flow<List<ChatMessage>> =
        chatDao.searchMessages(matchId, query)
}
