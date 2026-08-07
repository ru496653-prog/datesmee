package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_settings")
data class UserSettings(
    @PrimaryKey val id: Int = 1,
    val currentThemeName: String = "FROSTED_GLASS", // FROSTED_GLASS, SAKURA_PINK, etc.
    val isDarkMode: Boolean = false,
    val isDynamicColorEnabled: Boolean = false,
    val accentColorHex: String = "#9C4275",
    val cornerRadiusDp: Int = 28,
    val animationSpeedFactor: Float = 1.0f,
    val fontSizeScale: Float = 1.0f,
    val chatBubbleStyle: String = "MODERN_ROUNDED",
    val profileCardStyle: String = "GLASS_ELEVATED",
    val incognitoMode: Boolean = false,
    val hideOnlineStatus: Boolean = false,
    val hideDistance: Boolean = false,
    val hideAge: Boolean = false,
    val screenshotWarning: Boolean = true,
    val newMatchNotification: Boolean = true,
    val newMessageNotification: Boolean = true,
    val profileLikeNotification: Boolean = true,
    val callInviteNotification: Boolean = true,
    val maxDistanceKm: Int = 50,
    val minAgeFilter: Int = 18,
    val maxAgeFilter: Int = 45,
    val verifiedOnlyFilter: Boolean = false,
    val blockedUsersJson: String = "[]"
)
