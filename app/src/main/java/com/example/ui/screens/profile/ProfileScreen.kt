package com.example.ui.screens.profile

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.DatesMeTopBar
import com.example.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    onOpenDrawer: () -> Unit
) {
    val myProfile by profileViewModel.myProfile.collectAsState()
    var isEditing by remember { mutableStateOf(false) }

    var name by remember { mutableStateOf(myProfile?.displayName ?: "") }
    var age by remember { mutableStateOf((myProfile?.age ?: 24).toString()) }
    var occupation by remember { mutableStateOf(myProfile?.occupation ?: "") }
    var education by remember { mutableStateOf(myProfile?.education ?: "") }
    var city by remember { mutableStateOf(myProfile?.city ?: "") }
    var bio by remember { mutableStateOf(myProfile?.bio ?: "") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("profile_screen")
    ) {
        DatesMeTopBar(
            title = "My Profile",
            onOpenDrawer = onOpenDrawer
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Photo Header
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.tertiary
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = (myProfile?.displayName?.take(1) ?: "U"),
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "${myProfile?.displayName ?: "User"}, ${myProfile?.age ?: 18}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                if (myProfile?.isVerified == true || myProfile?.isCitizenshipUploaded == true) {
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Verified",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Text(
                text = "${myProfile?.gender ?: "Male"} • Seeking ${myProfile?.lookingFor ?: "Female"} (16+ Straight Dating)",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Citizenship Verification Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (myProfile?.isCitizenshipUploaded == true) Color(0xFFE8F5E9) else Color(0xFFFFF0F5)
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.VerifiedUser,
                        contentDescription = "Verify",
                        tint = if (myProfile?.isCitizenshipUploaded == true) Color(0xFF2E7D32) else MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (myProfile?.isCitizenshipUploaded == true) "Citizenship ID Verified ✓" else "Citizenship Document Status",
                            fontWeight = FontWeight.Bold,
                            color = if (myProfile?.isCitizenshipUploaded == true) Color(0xFF1B5E20) else Color(0xFF201A1B)
                        )
                        Text(
                            text = if (myProfile?.isCitizenshipUploaded == true) "Your citizenship identity is verified" else "Upload ID photo in profile setup for instant verification",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            // Edit Profile Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Profile Details",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f)
                        )
                        TextButton(onClick = {
                            if (isEditing) {
                                // Save
                                myProfile?.let { curr ->
                                    val updated = curr.copy(
                                        displayName = name,
                                        age = age.toIntOrNull() ?: curr.age,
                                        occupation = occupation,
                                        education = education,
                                        city = city,
                                        bio = bio
                                    )
                                    profileViewModel.updateProfile(updated)
                                }
                            }
                            isEditing = !isEditing
                        }) {
                            Text(if (isEditing) "Save" else "Edit")
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    if (isEditing) {
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("Display Name") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedTextField(
                            value = age,
                            onValueChange = { age = it },
                            label = { Text("Age") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedTextField(
                            value = occupation,
                            onValueChange = { occupation = it },
                            label = { Text("Occupation") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedTextField(
                            value = education,
                            onValueChange = { education = it },
                            label = { Text("Education") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedTextField(
                            value = city,
                            onValueChange = { city = it },
                            label = { Text("City") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedTextField(
                            value = bio,
                            onValueChange = { bio = it },
                            label = { Text("Bio") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        val occ = myProfile?.occupation.orEmpty().ifBlank { "Not specified" }
                        val edu = myProfile?.education.orEmpty().ifBlank { "Not specified" }
                        Text("📍 City: ${myProfile?.city ?: "San Francisco, CA"}")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("💼 Occupation: $occ")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("🎓 Education: $edu")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("💬 Bio: ${myProfile?.bio ?: ""}")
                    }
                }
            }
        }
    }
}
