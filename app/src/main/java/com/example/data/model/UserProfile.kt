package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profiles")
data class UserProfile(
    @PrimaryKey val id: String,
    val displayName: String,
    val age: Int = 18, // 16+ requirement
    val gender: String = "Male", // Male or Female
    val lookingFor: String = "Female", // Female for Male, Male for Female (Straight dating)
    val relationshipGoals: String = "Long-term relationship",
    val city: String = "San Francisco, CA",
    val distanceKm: Double = 3.5,
    val heightCm: Int = 172,
    val occupation: String = "",
    val education: String = "",
    val bio: String = "",
    val photosJson: String = "[]", // List of photo URIs
    val citizenshipDocUri: String? = null, // Citizenship document / ID photo upload
    val isCitizenshipUploaded: Boolean = false,
    val languagesJson: String = "[\"English\"]",
    val hobbiesJson: String = "[]",
    val interestsJson: String = "[]",
    val isVerified: Boolean = false,
    val isOnline: Boolean = true,
    val lastActiveTimestamp: Long = System.currentTimeMillis(),
    val profileScore: Int = 85,
    val isLikedByMe: Boolean = false,
    val isPassedByMe: Boolean = false,
    val isSuperLikedByMe: Boolean = false,
    val isProfileComplete: Boolean = true
)

