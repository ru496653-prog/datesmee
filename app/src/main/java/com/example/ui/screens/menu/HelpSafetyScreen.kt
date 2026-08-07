package com.example.ui.screens.menu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ui.components.DatesMeTopBar

@Composable
fun HelpSafetyScreen(
    title: String,
    onOpenDrawer: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("help_safety_screen")
    ) {
        DatesMeTopBar(title = title, onOpenDrawer = onOpenDrawer)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Safety First at DatesMe 🛡️",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("1. Never share financial or sensitive account credentials.")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("2. Meet in public places for initial in-person dates.")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("3. Use the Report & Block tools immediately if anyone exhibits inappropriate behavior.")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("4. Keep initial communications inside the DatesMe chat platform for photo & voice moderation protection.")
                }
            }
        }
    }
}
