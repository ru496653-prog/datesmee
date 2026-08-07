package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.ActivityItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityDao {
    @Query("SELECT * FROM activity_items ORDER BY timestamp DESC")
    fun getAllActivities(): Flow<List<ActivityItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivity(item: ActivityItem)

    @Query("UPDATE activity_items SET isRead = 1 WHERE id = :id")
    suspend fun markAsRead(id: String)

    @Query("DELETE FROM activity_items WHERE id = :id")
    suspend fun deleteActivity(id: String)

    @Query("DELETE FROM activity_items")
    suspend fun clearAll()
}
