package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class ActivityType {
    LIKE_RECEIVED, PROFILE_VISIT, MATCH_EVENT, SYSTEM_ALERT, CALL_INVITE
}

@Entity(tableName = "activity_items")
data class ActivityItem(
    @PrimaryKey val id: String,
    val type: ActivityType,
    val title: String,
    val description: String,
    val actorId: String? = null,
    val actorName: String? = null,
    val actorPhotoUrl: String? = null,
    val timestamp: Long = System.currentTimeMillis(),
    val isRead: Boolean = false
)
