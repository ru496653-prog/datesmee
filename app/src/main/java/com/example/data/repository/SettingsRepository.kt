package com.example.data.repository

import com.example.data.local.SettingsDao
import com.example.data.model.UserSettings
import kotlinx.coroutines.flow.Flow

class SettingsRepository(private val settingsDao: SettingsDao) {
    val settingsFlow: Flow<UserSettings?> = settingsDao.getUserSettings()

    suspend fun getSettingsOnce(): UserSettings {
        return settingsDao.getUserSettingsOnce() ?: UserSettings().also {
            settingsDao.insertOrUpdateSettings(it)
        }
    }

    suspend fun updateSettings(settings: UserSettings) {
        settingsDao.insertOrUpdateSettings(settings)
    }
}
