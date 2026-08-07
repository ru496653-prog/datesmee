package com.example.ui.screens.matches

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Match
import com.example.ui.components.DatesMeTopBar
import com.example.ui.components.EmptyStateView
import com.example.viewmodel.MatchTab
import com.example.viewmodel.MatchViewModel

@Composable
fun MatchesScreen(
    matchViewModel: MatchViewModel,
    onOpenDrawer: () -> Unit,
    onNavigateToChat: (String, String) -> Unit,
    onNavigateToDiscover: () -> Unit
) {
    val matches by matchViewModel.displayedMatches.collectAsState()
    val activeTab by matchViewModel.activeTab.collectAsState()
    val searchQuery by matchViewModel.searchQuery.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("matches_screen")
    ) {
        DatesMeTopBar(
            title = "Matches",
            onOpenDrawer = onOpenDrawer
        )

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { matchViewModel.searchQuery.value = it },
            placeholder = { Text("Search matches by name...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            singleLine = true,
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .testTag("matches_search_input")
        )

        // Tabs
        TabRow(
            selectedTabIndex = activeTab.ordinal,
            containerColor = MaterialTheme.colorScheme.surface,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Tab(
                selected = activeTab == MatchTab.ACTIVE,
                onClick = { matchViewModel.activeTab.value = MatchTab.ACTIVE },
                text = { Text("Active") }
            )
            Tab(
                selected = activeTab == MatchTab.FAVORITES,
                onClick = { matchViewModel.activeTab.value = MatchTab.FAVORITES },
                text = { Text("Favorites") }
            )
            Tab(
                selected = activeTab == MatchTab.ARCHIVED,
                onClick = { matchViewModel.activeTab.value = MatchTab.ARCHIVED },
                text = { Text("Archived") }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (matches.isEmpty()) {
            EmptyStateView(
                title = when (activeTab) {
                    MatchTab.ACTIVE -> "No Matches Yet"
                    MatchTab.FAVORITES -> "No Favorite Matches"
                    MatchTab.ARCHIVED -> "No Archived Conversations"
                },
                description = when (activeTab) {
                    MatchTab.ACTIVE -> "When you and another member like each other, your matches will appear here."
                    MatchTab.FAVORITES -> "Tap the heart icon on any match to add them to your favorites."
                    MatchTab.ARCHIVED -> "Archived matches will be saved here quietly."
                },
                actionTitle = if (activeTab == MatchTab.ACTIVE) "Start Swiping in Discover" else null,
                onActionClick = if (activeTab == MatchTab.ACTIVE) onNavigateToDiscover else null
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                items(matches, key = { it.matchId }) { match ->
                    MatchCardItem(
                        match = match,
                        onClick = { onNavigateToChat(match.matchId, match.userName) },
                        onToggleFavorite = { matchViewModel.toggleFavorite(match.matchId, match.isFavorite) },
                        onToggleArchive = { matchViewModel.toggleArchive(match.matchId, match.isArchived) },
                        onDelete = { matchViewModel.deleteMatch(match.matchId) }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun MatchCardItem(
    match: Match,
    onClick: () -> Unit,
    onToggleFavorite: () -> Unit,
    onToggleArchive: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("match_item_${match.matchId}"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.secondary
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = match.userName.take(1),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${match.userName}, ${match.userAge}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                if (match.userOccupation.isNotBlank()) {
                    Text(
                        text = match.userOccupation,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = match.matchInsight,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Row {
                IconButton(onClick = onToggleFavorite) {
                    Icon(
                        imageVector = if (match.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (match.isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(onClick = onToggleArchive) {
                    Icon(
                        imageVector = Icons.Default.Archive,
                        contentDescription = "Archive",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
