package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

import com.example.data.model.UserProfile
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun SwipeCard(
    profile: UserProfile,
    onLike: () -> Unit,
    onPass: () -> Unit,
    onSuperLike: () -> Unit,
    onRewind: () -> Unit,
    onShowDetail: () -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }

    val rotation = (offsetX.value / 25f).coerceIn(-15f, 15f)

    Box(
        modifier = modifier
            .fillMaxSize()
            .offset { IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt()) }
            .rotate(rotation)
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, _, _ ->
                    coroutineScope.launch {
                        offsetX.snapTo(offsetX.value + pan.x)
                        offsetY.snapTo(offsetY.value + pan.y)
                    }
                }
            }
            .testTag("swipe_card_${profile.id}")
    ) {
        Card(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(36.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            border = BorderStroke(1.5.dp, Color.White.copy(alpha = 0.8f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(36.dp))
            ) {
                // Background Gradient
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFF9C4275).copy(alpha = 0.75f),
                                    Color(0xFF5E2045).copy(alpha = 0.85f),
                                    Color(0xFF201A1B).copy(alpha = 0.95f)
                                )
                            )
                        )
                )

                // Content Overlay
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    // Top Badges
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color.White.copy(alpha = 0.25f),
                            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.4f))
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = "Location",
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "${profile.city} • ${profile.distanceKm} km away",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = Color.White,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        if (profile.isVerified) {
                            Surface(
                                shape = CircleShape,
                                color = Color(0xFFFFD9E2),
                                border = BorderStroke(1.dp, Color.White)
                            ) {
                                Text(
                                    text = " Verified ✓ ",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = Color(0xFF31101D),
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // Frosted Glass Detail Card Container
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(28.dp),
                        color = Color.White.copy(alpha = 0.22f),
                        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.45f))
                    ) {
                        Column(modifier = Modifier.padding(18.dp)) {
                            Text(
                                text = "${profile.displayName}, ${profile.age}",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )

                            if (profile.occupation.isNotBlank()) {
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "💼 ${profile.occupation}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.White.copy(alpha = 0.95f)
                                )
                            }

                            if (profile.bio.isNotBlank()) {
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = profile.bio,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.White.copy(alpha = 0.85f),
                                    maxLines = 2
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Swipe Control Buttons Bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = onRewind,
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.25f))
                                .border(BorderStroke(1.dp, Color.White.copy(alpha = 0.5f)), CircleShape)
                                .testTag("rewind_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Rewind",
                                tint = Color(0xFFFFD54F)
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        IconButton(
                            onClick = onPass,
                            modifier = Modifier
                                .size(58.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE53935))
                                .testTag("pass_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Pass",
                                tint = Color.White,
                                modifier = Modifier.size(30.dp)
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        IconButton(
                            onClick = onSuperLike,
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF00B0FF))
                                .testTag("superlike_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Super Like",
                                tint = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        IconButton(
                            onClick = onLike,
                            modifier = Modifier
                                .size(58.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF9C4275))
                                .testTag("like_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Like",
                                tint = Color.White,
                                modifier = Modifier.size(30.dp)
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        IconButton(
                            onClick = onShowDetail,
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.25f))
                                .border(BorderStroke(1.dp, Color.White.copy(alpha = 0.5f)), CircleShape)
                                .testTag("info_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "Info",
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}
