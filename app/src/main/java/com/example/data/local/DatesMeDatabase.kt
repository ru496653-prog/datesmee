package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.model.ActivityItem
import com.example.data.model.ChatMessage
import com.example.data.model.Match
import com.example.data.model.UserProfile
import com.example.data.model.UserSettings

@Database(
    entities = [
        UserProfile::class,
        Match::class,
        ChatMessage::class,
        ActivityItem::class,
        UserSettings::class
    ],
    version = 1,
    exportSchema = false
)
abstract class DatesMeDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
    abstract fun matchDao(): MatchDao
    abstract fun chatDao(): ChatDao
    abstract fun activityDao(): ActivityDao
    abstract fun settingsDao(): SettingsDao

    companion object {
        @Volatile
        private var INSTANCE: DatesMeDatabase? = null

        fun getDatabase(context: Context): DatesMeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatesMeDatabase::class.java,
                    "datesme_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
