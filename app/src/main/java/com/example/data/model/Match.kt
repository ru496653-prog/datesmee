package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "matches")
data class Match(
    @PrimaryKey val matchId: String,
    val userId: String,
    val userName: String,
    val userAge: Int,
    val userPhotoUrl: String,
    val userOccupation: String = "",
    val matchedAtTimestamp: Long = System.currentTimeMillis(),
    val isFavorite: Boolean = false,
    val isArchived: Boolean = false,
    val lastMessageText: String = "",
    val lastMessageTimestamp: Long = System.currentTimeMillis(),
    val unreadCount: Int = 0,
    val matchInsight: String = "94% Compatibility • Both love Coffee & Travel"
)
