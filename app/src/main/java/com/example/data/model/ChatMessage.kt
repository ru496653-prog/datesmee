package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class MessageType {
    TEXT, PHOTO, VOICE, GIF
}

@Entity(tableName = "chat_messages")
data class ChatMessage(
    @PrimaryKey val messageId: String,
    val matchId: String,
    val senderId: String,
    val recipientId: String,
    val text: String,
    val mediaUrl: String? = null,
    val messageType: MessageType = MessageType.TEXT,
    val timestamp: Long = System.currentTimeMillis(),
    val isRead: Boolean = false,
    val isDelivered: Boolean = true,
    val isPinned: Boolean = false,
    val replyToId: String? = null,
    val reaction: String? = null
)
