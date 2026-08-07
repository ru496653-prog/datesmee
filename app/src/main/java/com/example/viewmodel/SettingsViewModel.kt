package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.SettingsDao
import com.example.data.model.UserSettings
import com.example.ui.theme.AppThemePreset
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(private val settingsDao: SettingsDao) : ViewModel() {
    val settingsState: StateFlow<UserSettings> = settingsDao.getUserSettings()
        .map { it ?: UserSettings() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserSettings())

    fun updateTheme(preset: AppThemePreset) {
        viewModelScope.launch {
            val current = settingsDao.getUserSettingsOnce() ?: UserSettings()
            settingsDao.insertOrUpdateSettings(current.copy(currentThemeName = preset.name, accentColorHex = preset.primaryColorHex))
        }
    }

    fun toggleDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            val current = settingsDao.getUserSettingsOnce() ?: UserSettings()
            settingsDao.insertOrUpdateSettings(current.copy(isDarkMode = enabled))
        }
    }

    fun toggleDynamicColor(enabled: Boolean) {
        viewModelScope.launch {
            val current = settingsDao.getUserSettingsOnce() ?: UserSettings()
            settingsDao.insertOrUpdateSettings(current.copy(isDynamicColorEnabled = enabled))
        }
    }

    fun updateCornerRadius(radiusDp: Int) {
        viewModelScope.launch {
            val current = settingsDao.getUserSettingsOnce() ?: UserSettings()
            settingsDao.insertOrUpdateSettings(current.copy(cornerRadiusDp = radiusDp))
        }
    }

    fun updatePrivacySettings(incognito: Boolean, hideOnline: Boolean, hideDistance: Boolean, hideAge: Boolean) {
        viewModelScope.launch {
            val current = settingsDao.getUserSettingsOnce() ?: UserSettings()
            settingsDao.insertOrUpdateSettings(
                current.copy(
                    incognitoMode = incognito,
                    hideOnlineStatus = hideOnline,
                    hideDistance = hideDistance,
                    hideAge = hideAge
                )
            )
        }
    }
}
