package com.example.data.repository

import com.example.data.local.ProfileDao
import com.example.data.model.UserProfile
import kotlinx.coroutines.flow.Flow

class ProfileRepository(private val profileDao: ProfileDao) {
    fun getDiscoveryProfiles(): Flow<List<UserProfile>> = profileDao.getDiscoveryProfiles()

    fun getProfileById(id: String): Flow<UserProfile?> = profileDao.getProfileById(id)

    suspend fun getProfileByIdOnce(id: String): UserProfile? = profileDao.getProfileByIdOnce(id)

    suspend fun saveProfile(profile: UserProfile) {
        profileDao.insertOrUpdateProfile(profile)
    }

    suspend fun likeProfile(id: String) {
        profileDao.markLiked(id)
    }

    suspend fun passProfile(id: String) {
        profileDao.markPassed(id)
    }

    suspend fun superLikeProfile(id: String) {
        profileDao.markSuperLiked(id)
    }

    suspend fun rewindDecision(id: String) {
        profileDao.rewindDecision(id)
    }

    suspend fun deleteProfile(id: String) {
        profileDao.deleteProfile(id)
    }
}
