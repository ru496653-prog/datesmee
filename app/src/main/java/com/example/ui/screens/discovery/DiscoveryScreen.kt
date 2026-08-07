package com.example.ui.screens.discovery

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.model.Match
import com.example.data.model.UserProfile
import com.example.ui.components.DatesMeTopBar
import com.example.ui.components.EmptyStateView
import com.example.ui.components.MatchOverlayDialog
import com.example.ui.components.SwipeCard
import com.example.viewmodel.DiscoveryViewModel

@Composable
fun DiscoveryScreen(
    discoveryViewModel: DiscoveryViewModel,
    onOpenDrawer: () -> Unit,
    onOpenThemePicker: () -> Unit,
    onNavigateToChat: (String, String) -> Unit
) {
    val profiles by discoveryViewModel.filteredProfiles.collectAsState()
    val filters by discoveryViewModel.filterState.collectAsState()
    val matchOverlay by discoveryViewModel.matchOverlay.collectAsState()

    var showFilterSheet by remember { mutableStateOf(false) }
    var selectedProfileForDetail by remember { mutableStateOf<UserProfile?>(null) }
    var showAddProfileDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .testTag("discovery_screen")
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            DatesMeTopBar(
                title = "Discover",
                onOpenDrawer = onOpenDrawer,
                onOpenFilter = { showFilterSheet = true },
                onOpenThemePicker = onOpenThemePicker
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                if (profiles.isEmpty()) {
                    EmptyStateView(
                        title = "No Nearby Profiles Yet",
                        description = "There are no new profiles matching your current filters in your area. Adjust your discovery settings or add a profile!",
                        actionTitle = "Add Profile to Community",
                        onActionClick = { showAddProfileDialog = true }
                    )
                } else {
                    val currentCardProfile = profiles.last()
                    SwipeCard(
                        profile = currentCardProfile,
                        onLike = { discoveryViewModel.swipeRight(currentCardProfile) },
                        onPass = { discoveryViewModel.swipeLeft(currentCardProfile) },
                        onSuperLike = { discoveryViewModel.superLike(currentCardProfile) },
                        onRewind = { discoveryViewModel.rewind() },
                        onShowDetail = { selectedProfileForDetail = currentCardProfile }
                    )
                }
            }
        }

        if (showFilterSheet) {
            FilterBottomSheet(
                currentFilter = filters,
                onApplyFilters = { discoveryViewModel.updateFilters(it) },
                onDismiss = { showFilterSheet = false }
            )
        }

        matchOverlay?.let { match ->
            MatchOverlayDialog(
                match = match,
                onSendMessage = {
                    discoveryViewModel.dismissMatchOverlay()
                    onNavigateToChat(it.matchId, it.userName)
                },
                onKeepSwiping = {
                    discoveryViewModel.dismissMatchOverlay()
                }
            )
        }

        selectedProfileForDetail?.let { profile ->
            AlertDialog(
                onDismissRequest = { selectedProfileForDetail = null },
                title = { Text("${profile.displayName}, ${profile.age}") },
                text = {
                    Column {
                        Text("📍 ${profile.city} (${profile.distanceKm} km away)")
                        if (profile.occupation.isNotBlank()) Text("💼 ${profile.occupation}")
                        if (profile.education.isNotBlank()) Text("🎓 ${profile.education}")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(profile.bio)
                    }
                },
                confirmButton = {
                    Button(onClick = { selectedProfileForDetail = null }) {
                        Text("Close")
                    }
                }
            )
        }

        if (showAddProfileDialog) {
            var newName by remember { mutableStateOf("") }
            var newAge by remember { mutableStateOf("25") }
            var newBio by remember { mutableStateOf("") }
            var newOccupation by remember { mutableStateOf("") }

            AlertDialog(
                onDismissRequest = { showAddProfileDialog = false },
                title = { Text("Add Profile") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = newName,
                            onValueChange = { newName = it },
                            label = { Text("Name") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = newAge,
                            onValueChange = { newAge = it },
                            label = { Text("Age") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = newOccupation,
                            onValueChange = { newOccupation = it },
                            label = { Text("Occupation") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = newBio,
                            onValueChange = { newBio = it },
                            label = { Text("Bio") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (newName.isNotBlank()) {
                                val p = UserProfile(
                                    id = "user_${System.currentTimeMillis()}",
                                    displayName = newName,
                                    age = newAge.toIntOrNull() ?: 25,
                                    gender = "Female",
                                    lookingFor = "Everyone",
                                    bio = newBio,
                                    occupation = newOccupation,
                                    isVerified = true
                                )
                                discoveryViewModel.addSampleUserProfile(p)
                                showAddProfileDialog = false
                            }
                        }
                    ) {
                        Text("Add")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showAddProfileDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}
