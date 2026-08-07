package com.example.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.DatesMeTopBar
import com.example.ui.theme.AppThemePreset
import com.example.viewmodel.SettingsViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SettingsScreen(
    settingsViewModel: SettingsViewModel,
    onOpenDrawer: () -> Unit
) {
    val settings by settingsViewModel.settingsState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("settings_screen")
    ) {
        DatesMeTopBar(
            title = "Settings",
            onOpenDrawer = onOpenDrawer
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Theme Customizer Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Palette, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("App Theme & Styling", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Dark Mode", fontWeight = FontWeight.Medium)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Enable Dark Aesthetic", style = MaterialTheme.typography.bodySmall, modifier = Modifier.weight(1f))
                        Switch(
                            checked = settings.isDarkMode,
                            onCheckedChange = { settingsViewModel.toggleDarkMode(it) },
                            modifier = Modifier.testTag("dark_mode_switch")
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Pastel Theme Presets (14 Themes)", fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(8.dp))

                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp),
                        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
                    ) {
                        AppThemePreset.values().forEach { preset ->
                            val isSelected = settings.currentThemeName == preset.name
                            Surface(
                                shape = RoundedCornerShape(16.dp),
                                color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant,
                                modifier = Modifier
                                    .clickable { settingsViewModel.updateTheme(preset) }
                                    .testTag("theme_preset_${preset.name}")
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(16.dp)
                                            .clip(CircleShape)
                                            .background(Color(android.graphics.Color.parseColor(preset.primaryColorHex)))
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = preset.displayName,
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text("Corner Radius: ${settings.cornerRadiusDp} dp", fontWeight = FontWeight.Medium)
                    Slider(
                        value = settings.cornerRadiusDp.toFloat(),
                        onValueChange = { settingsViewModel.updateCornerRadius(it.toInt()) },
                        valueRange = 16f..36f
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Privacy Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.PrivacyTip, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("Privacy Controls", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Incognito Mode", fontWeight = FontWeight.Medium)
                            Text("Only profiles you like will see you", style = MaterialTheme.typography.bodySmall)
                        }
                        Switch(
                            checked = settings.incognitoMode,
                            onCheckedChange = {
                                settingsViewModel.updatePrivacySettings(it, settings.hideOnlineStatus, settings.hideDistance, settings.hideAge)
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Hide Online Status", fontWeight = FontWeight.Medium)
                            Text("Don't display your online indicator", style = MaterialTheme.typography.bodySmall)
                        }
                        Switch(
                            checked = settings.hideOnlineStatus,
                            onCheckedChange = {
                                settingsViewModel.updatePrivacySettings(settings.incognitoMode, it, settings.hideDistance, settings.hideAge)
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Hide Distance", fontWeight = FontWeight.Medium)
                            Text("Don't show distance on your card", style = MaterialTheme.typography.bodySmall)
                        }
                        Switch(
                            checked = settings.hideDistance,
                            onCheckedChange = {
                                settingsViewModel.updatePrivacySettings(settings.incognitoMode, settings.hideOnlineStatus, it, settings.hideAge)
                            }
                        )
                    }
                }
            }
        }
    }
}
