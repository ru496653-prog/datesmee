package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profiles")
data class UserProfile(
    @PrimaryKey val id: String,
    val displayName: String,
    val age: Int,
    val gender: String, // Female, Male, Non-binary, Other
    val lookingFor: String, // Women, Men, Everyone
    val relationshipGoals: String = "Long-term relationship", // Marriage, Long-term, Casual, New friends
    val city: String = "San Francisco, CA",
    val distanceKm: Double = 3.5,
    val heightCm: Int = 172,
    val occupation: String = "",
    val education: String = "",
    val bio: String = "",
    val photosJson: String = "[]", // List of photo URLs/drawables
    val languagesJson: String = "[\"English\"]",
    val hobbiesJson: String = "[]",
    val interestsJson: String = "[]",
    val lifestyleJson: String = "{}", // e.g. smoking, drinking, exercise, pets
    val promptsJson: String = "[]", // Q&A prompts
    val voiceIntroUrl: String? = null,
    val isVerified: Boolean = false,
    val isOnline: Boolean = true,
    val lastActiveTimestamp: Long = System.currentTimeMillis(),
    val profileScore: Int = 85,
    val isLikedByMe: Boolean = false,
    val isPassedByMe: Boolean = false,
    val isSuperLikedByMe: Boolean = false
)
