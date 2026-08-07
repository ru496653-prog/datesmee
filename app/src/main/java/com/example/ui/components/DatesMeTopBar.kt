package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DatesMeTopBar(
    title: String,
    onOpenDrawer: () -> Unit,
    onOpenFilter: (() -> Unit)? = null,
    onOpenThemePicker: (() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .testTag("datesme_top_bar")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp)
                .shadow(
                    elevation = 6.dp,
                    shape = RoundedCornerShape(29.dp),
                    spotColor = Color(0x1A9C4275)
                )
                .clip(RoundedCornerShape(29.dp))
                .background(Color.White)
                .border(
                    BorderStroke(1.dp, Color(0xFFF0E4E8)),
                    shape = RoundedCornerShape(29.dp)
                )
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left Action Pill Container
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFFD9E2).copy(alpha = 0.85f))
                    .border(BorderStroke(1.dp, Color(0xFFFFB0C7)), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = onOpenDrawer,
                    modifier = Modifier
                        .size(42.dp)
                        .testTag("menu_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Open Drawer",
                        tint = Color(0xFF9C4275)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // App Title & Heart Icon Logo
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF9C4275),
                                    Color(0xFFF27D26)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "💕",
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF201A1B)
                )
            }

            if (onOpenThemePicker != null) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.6f))
                        .border(BorderStroke(1.dp, Color.White.copy(alpha = 0.8f)), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(
                        onClick = onOpenThemePicker,
                        modifier = Modifier
                            .size(42.dp)
                            .testTag("theme_picker_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Palette,
                            contentDescription = "Change Theme",
                            tint = Color(0xFF9C4275)
                        )
                    }
                }
            }

            if (onOpenFilter != null) {
                Spacer(modifier = Modifier.width(6.dp))
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.6f))
                        .border(BorderStroke(1.dp, Color.White.copy(alpha = 0.8f)), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(
                        onClick = onOpenFilter,
                        modifier = Modifier
                            .size(42.dp)
                            .testTag("filter_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Tune,
                            contentDescription = "Filters",
                            tint = Color(0xFF201A1B)
                        )
                    }
                }
            }
        }
    }
}
