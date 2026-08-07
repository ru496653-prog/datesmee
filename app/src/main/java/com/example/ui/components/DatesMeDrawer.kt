package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Diversity1
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Swipe
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class DrawerItemData(
    val title: String,
    val icon: ImageVector,
    val route: String,
    val badgeCount: Int = 0
)

@Composable
fun DatesMeDrawerSheet(
    currentRoute: String,
    userName: String,
    userPhotoUrl: String?,
    isVerified: Boolean,
    onNavigate: (String) -> Unit,
    onCloseDrawer: () -> Unit
) {
    val primaryGroup = listOf(
        DrawerItemData("Home", Icons.Default.Home, "home"),
        DrawerItemData("Discover", Icons.Default.Swipe, "discover"),
        DrawerItemData("Matches", Icons.Default.Favorite, "matches"),
        DrawerItemData("Messages", Icons.AutoMirrored.Filled.Chat, "messages"),
        DrawerItemData("Activity", Icons.Default.Notifications, "activity"),
        DrawerItemData("Profile", Icons.Default.Person, "profile")
    )

    val secondaryGroup = listOf(
        DrawerItemData("Settings", Icons.Default.Settings, "settings"),
        DrawerItemData("Safety Center", Icons.Default.Security, "safety"),
        DrawerItemData("Help Center", Icons.Default.HelpOutline, "help"),
        DrawerItemData("Privacy Policy", Icons.Default.PrivacyTip, "privacy"),
        DrawerItemData("Terms of Service", Icons.Default.Policy, "terms"),
        DrawerItemData("About", Icons.Default.Info, "about")
    )

    ModalDrawerSheet(
        modifier = Modifier
            .fillMaxHeight()
            .width(310.dp)
            .testTag("datesme_drawer"),
        drawerShape = RoundedCornerShape(topEnd = 32.dp, bottomEnd = 32.dp),
        drawerContainerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
                .padding(vertical = 24.dp)
        ) {
            // Header Profile Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primaryContainer,
                                MaterialTheme.colorScheme.secondaryContainer
                            )
                        )
                    )
                    .clickable {
                        onNavigate("profile")
                        onCloseDrawer()
                    }
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = userName.ifBlank { "DatesMe User" },
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            if (isVerified) {
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("✓", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                            }
                        }
                        Text(
                            text = "View Profile & Edit",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "MAIN MENU",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
            )

            primaryGroup.forEach { item ->
                NavigationDrawerItem(
                    label = {
                        Text(
                            text = item.title,
                            fontWeight = if (currentRoute == item.route) FontWeight.Bold else FontWeight.Medium
                        )
                    },
                    selected = currentRoute == item.route,
                    onClick = {
                        onNavigate(item.route)
                        onCloseDrawer()
                    },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title
                        )
                    },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Divider(modifier = Modifier.padding(horizontal = 24.dp), color = MaterialTheme.colorScheme.outlineVariant)
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "SYSTEM & SUPPORT",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
            )

            secondaryGroup.forEach { item ->
                NavigationDrawerItem(
                    label = { Text(text = item.title) },
                    selected = currentRoute == item.route,
                    onClick = {
                        onNavigate(item.route)
                        onCloseDrawer()
                    },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title
                        )
                    },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp),
                    shape = RoundedCornerShape(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "DatesMe v1.0.0 • Rahul Shah",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                modifier = Modifier.padding(horizontal = 24.dp)
            )
        }
    }
}
