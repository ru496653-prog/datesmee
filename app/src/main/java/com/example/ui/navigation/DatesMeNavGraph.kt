package com.example.ui.navigation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Swipe
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ui.components.DatesMeDrawerSheet
import com.example.ui.screens.activity.ActivityScreen
import com.example.ui.screens.auth.AuthScreen
import com.example.ui.screens.chat.ChatScreen
import com.example.ui.screens.discovery.DiscoveryScreen
import com.example.ui.screens.matches.MatchesScreen
import com.example.ui.screens.menu.AboutScreen
import com.example.ui.screens.menu.HelpSafetyScreen
import com.example.ui.screens.profile.ProfileScreen
import com.example.ui.screens.settings.SettingsScreen
import com.example.viewmodel.ActivityViewModel
import com.example.viewmodel.AuthViewModel
import com.example.viewmodel.ChatViewModel
import com.example.viewmodel.DiscoveryViewModel
import com.example.viewmodel.MatchViewModel
import com.example.viewmodel.ProfileViewModel
import com.example.viewmodel.SettingsViewModel
import kotlinx.coroutines.launch

@Composable
fun DatesMeNavGraph(
    authViewModel: AuthViewModel,
    discoveryViewModel: DiscoveryViewModel,
    matchViewModel: MatchViewModel,
    chatViewModel: ChatViewModel,
    profileViewModel: ProfileViewModel,
    settingsViewModel: SettingsViewModel,
    activityViewModel: ActivityViewModel
) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    val currentUser by authViewModel.currentUserState.collectAsState()
    val myProfile by profileViewModel.myProfile.collectAsState()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "discover"

    val showBars = currentRoute != "auth" && !currentRoute.startsWith("chat/")

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = showBars,
        drawerContent = {
            DatesMeDrawerSheet(
                currentRoute = currentRoute,
                userName = myProfile?.displayName ?: currentUser?.displayName ?: "DatesMe User",
                userPhotoUrl = myProfile?.photosJson,
                isVerified = myProfile?.isVerified ?: false,
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                onCloseDrawer = {
                    coroutineScope.launch { drawerState.close() }
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFCF8F9))
        ) {
            // Ambient Decorative Blurs for Frosted Glass Depth
            Box(
                modifier = Modifier
                    .size(240.dp)
                    .offset(x = (-40).dp, y = 120.dp)
                    .blur(60.dp)
                    .background(Color(0x20F27D26), CircleShape)
            )
            Box(
                modifier = Modifier
                    .size(260.dp)
                    .offset(x = 220.dp, y = 480.dp)
                    .blur(70.dp)
                    .background(Color(0x209C4275), CircleShape)
            )

            Scaffold(
                modifier = Modifier.testTag("datesme_nav_scaffold"),
                containerColor = Color.Transparent,
                bottomBar = {
                    if (showBars) {
                        NavigationBar(
                            modifier = Modifier
                                .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                                .border(
                                    BorderStroke(1.dp, Color.White.copy(alpha = 0.6f)),
                                    shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
                                )
                                .testTag("datesme_bottom_nav"),
                            containerColor = Color(0xDCFCECEF),
                            contentColor = Color(0xFF201A1B)
                        ) {
                            val items = listOf(
                                Triple("discover", "Discover", Icons.Default.Swipe),
                                Triple("matches", "Likes", Icons.Default.Favorite),
                                Triple("messages", "Chat", Icons.AutoMirrored.Filled.Chat),
                                Triple("activity", "Activity", Icons.Default.Notifications)
                            )

                            items.forEach { (route, label, icon) ->
                                val selected = currentRoute == route || (route == "discover" && currentRoute == "home")
                                NavigationBarItem(
                                    selected = selected,
                                    onClick = {
                                        navController.navigate(route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = icon,
                                            contentDescription = label
                                        )
                                    },
                                    label = {
                                        Text(
                                            text = label,
                                            fontSize = 10.sp,
                                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
                                        )
                                    },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = Color(0xFF31101D),
                                        selectedTextColor = Color(0xFF31101D),
                                        indicatorColor = Color(0xFFFFD9E2),
                                        unselectedIconColor = Color(0xFF201A1B).copy(alpha = 0.5f),
                                        unselectedTextColor = Color(0xFF201A1B).copy(alpha = 0.5f)
                                    )
                                )
                            }
                        }
                    }
                }
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = if (currentUser == null) "auth" else "discover",
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable("auth") {
                        AuthScreen(
                            authViewModel = authViewModel,
                            onAuthSuccess = {
                                navController.navigate("discover") {
                                    popUpTo("auth") { inclusive = true }
                                }
                            }
                        )
                    }

                    composable("home") {
                        DiscoveryScreen(
                            discoveryViewModel = discoveryViewModel,
                            onOpenDrawer = { coroutineScope.launch { drawerState.open() } },
                            onOpenThemePicker = { navController.navigate("settings") },
                            onNavigateToChat = { matchId, name ->
                                navController.navigate("chat/$matchId/$name")
                            }
                        )
                    }

                    composable("discover") {
                        DiscoveryScreen(
                            discoveryViewModel = discoveryViewModel,
                            onOpenDrawer = { coroutineScope.launch { drawerState.open() } },
                            onOpenThemePicker = { navController.navigate("settings") },
                            onNavigateToChat = { matchId, name ->
                                navController.navigate("chat/$matchId/$name")
                            }
                        )
                    }

                    composable("matches") {
                        MatchesScreen(
                            matchViewModel = matchViewModel,
                            onOpenDrawer = { coroutineScope.launch { drawerState.open() } },
                            onNavigateToChat = { matchId, name ->
                                navController.navigate("chat/$matchId/$name")
                            },
                            onNavigateToDiscover = {
                                navController.navigate("discover")
                            }
                        )
                    }

                    composable(
                        route = "chat/{matchId}/{matchName}",
                        arguments = listOf(
                            navArgument("matchId") { type = NavType.StringType },
                            navArgument("matchName") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val matchId = backStackEntry.arguments?.getString("matchId") ?: ""
                        val matchName = backStackEntry.arguments?.getString("matchName") ?: ""
                        ChatScreen(
                            chatViewModel = chatViewModel,
                            matchId = matchId,
                            matchName = matchName,
                            onBack = { navController.popBackStack() }
                        )
                    }

                    composable("messages") {
                        MatchesScreen(
                            matchViewModel = matchViewModel,
                            onOpenDrawer = { coroutineScope.launch { drawerState.open() } },
                            onNavigateToChat = { matchId, name ->
                                navController.navigate("chat/$matchId/$name")
                            },
                            onNavigateToDiscover = {
                                navController.navigate("discover")
                            }
                        )
                    }

                    composable("activity") {
                        ActivityScreen(
                            activityViewModel = activityViewModel,
                            onOpenDrawer = { coroutineScope.launch { drawerState.open() } }
                        )
                    }

                    composable("profile") {
                        ProfileScreen(
                            profileViewModel = profileViewModel,
                            onOpenDrawer = { coroutineScope.launch { drawerState.open() } }
                        )
                    }

                    composable("settings") {
                        SettingsScreen(
                            settingsViewModel = settingsViewModel,
                            onOpenDrawer = { coroutineScope.launch { drawerState.open() } }
                        )
                    }

                    composable("safety") {
                        HelpSafetyScreen(title = "Safety Center", onOpenDrawer = { coroutineScope.launch { drawerState.open() } })
                    }

                    composable("help") {
                        HelpSafetyScreen(title = "Help Center", onOpenDrawer = { coroutineScope.launch { drawerState.open() } })
                    }

                    composable("privacy") {
                        HelpSafetyScreen(title = "Privacy Policy", onOpenDrawer = { coroutineScope.launch { drawerState.open() } })
                    }

                    composable("terms") {
                        HelpSafetyScreen(title = "Terms of Service", onOpenDrawer = { coroutineScope.launch { drawerState.open() } })
                    }

                    composable("about") {
                        AboutScreen(onOpenDrawer = { coroutineScope.launch { drawerState.open() } })
                    }
                }
            }
        }
    }
}
