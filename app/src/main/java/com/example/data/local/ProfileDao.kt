package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.UserProfile
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {
    @Query("SELECT * FROM user_profiles WHERE isPassedByMe = 0 ORDER BY profileScore DESC")
    fun getDiscoveryProfiles(): Flow<List<UserProfile>>

    @Query("SELECT * FROM user_profiles WHERE id = :id")
    fun getProfileById(id: String): Flow<UserProfile?>

    @Query("SELECT * FROM user_profiles WHERE id = :id")
    suspend fun getProfileByIdOnce(id: String): UserProfile?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateProfile(profile: UserProfile)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfiles(profiles: List<UserProfile>)

    @Query("UPDATE user_profiles SET isLikedByMe = 1 WHERE id = :id")
    suspend fun markLiked(id: String)

    @Query("UPDATE user_profiles SET isPassedByMe = 1 WHERE id = :id")
    suspend fun markPassed(id: String)

    @Query("UPDATE user_profiles SET isSuperLikedByMe = 1, isLikedByMe = 1 WHERE id = :id")
    suspend fun markSuperLiked(id: String)

    @Query("UPDATE user_profiles SET isPassedByMe = 0, isLikedByMe = 0, isSuperLikedByMe = 0 WHERE id = :id")
    suspend fun rewindDecision(id: String)

    @Query("DELETE FROM user_profiles WHERE id = :id")
    suspend fun deleteProfile(id: String)
}
