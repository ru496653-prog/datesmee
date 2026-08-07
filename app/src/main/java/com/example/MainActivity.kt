package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.local.DatesMeDatabase
import com.example.data.repository.AuthRepository
import com.example.data.repository.ChatRepository
import com.example.data.repository.MatchRepository
import com.example.data.repository.ProfileRepository
import com.example.data.repository.SettingsRepository
import com.example.ui.navigation.DatesMeNavGraph
import com.example.ui.theme.AppThemePreset
import com.example.ui.theme.DatesMeTheme
import com.example.viewmodel.ActivityViewModel
import com.example.viewmodel.AuthViewModel
import com.example.viewmodel.ChatViewModel
import com.example.viewmodel.DiscoveryViewModel
import com.example.viewmodel.MatchViewModel
import com.example.viewmodel.ProfileViewModel
import com.example.viewmodel.SettingsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = DatesMeDatabase.getDatabase(applicationContext)

        val authRepository = AuthRepository()
        val profileRepository = ProfileRepository(db.profileDao())
        val matchRepository = MatchRepository(db.matchDao())
        val chatRepository = ChatRepository(db.chatDao())
        val settingsRepository = SettingsRepository(db.settingsDao())

        setContent {
            val authViewModel = viewModel { AuthViewModel(authRepository) }
            val discoveryViewModel = viewModel { DiscoveryViewModel(profileRepository, matchRepository) }
            val matchViewModel = viewModel { MatchViewModel(matchRepository) }
            val chatViewModel = viewModel { ChatViewModel(chatRepository) }
            val profileViewModel = viewModel { ProfileViewModel(profileRepository) }
            val settingsViewModel = viewModel { SettingsViewModel(db.settingsDao()) }
            val activityViewModel = viewModel { ActivityViewModel(db.activityDao()) }

            val userSettings by settingsViewModel.settingsState.collectAsState()

            val preset = try {
                AppThemePreset.valueOf(userSettings.currentThemeName)
            } catch (_: Exception) {
                AppThemePreset.FROSTED_GLASS
            }

            DatesMeTheme(
                preset = preset,
                isDarkMode = userSettings.isDarkMode,
                isDynamicColor = userSettings.isDynamicColorEnabled,
                cornerRadiusDp = userSettings.cornerRadiusDp
            ) {
                DatesMeNavGraph(
                    authViewModel = authViewModel,
                    discoveryViewModel = discoveryViewModel,
                    matchViewModel = matchViewModel,
                    chatViewModel = chatViewModel,
                    profileViewModel = profileViewModel,
                    settingsViewModel = settingsViewModel,
                    activityViewModel = activityViewModel
                )
            }
        }
    }
}
