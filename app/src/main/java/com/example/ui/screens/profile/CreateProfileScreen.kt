package com.example.ui.screens.profile

import android.Manifest
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.UserProfile
import com.example.viewmodel.ProfileViewModel

@Composable
fun CreateProfileScreen(
    profileViewModel: ProfileViewModel,
    onProfileCreated: () -> Unit
) {
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var ageText by remember { mutableStateOf("20") }
    var gender by remember { mutableStateOf("Male") } // "Male" or "Female"
    var city by remember { mutableStateOf("") }
    var occupation by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var profilePhotoUri by remember { mutableStateOf<Uri?>(null) }
    var citizenshipDocUri by remember { mutableStateOf<Uri?>(null) }

    var permissionsGranted by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Launcher for profile photo
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { profilePhotoUri = it }
    }

    // Launcher for citizenship document
    val citizenshipPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { citizenshipDocUri = it }
    }

    // Launcher for permissions (Notifications + Gallery + Camera)
    val permissionsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { map ->
        permissionsGranted = map.values.any { it }
    }

    val permissionsToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arrayOf(
            Manifest.permission.POST_NOTIFICATIONS,
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.CAMERA
        )
    } else {
        arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFCF8F9))
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
            .testTag("create_profile_screen"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Complete Your Profile",
            fontSize = 26.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF201A1B)
        )

        Text(
            text = "16+ Straight Dating Community",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF9C4275),
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Permissions Request Banner
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            color = Color(0xFFFFF0F5),
            border = BorderStroke(1.dp, Color(0xFFFFC0CB))
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = if (permissionsGranted) Icons.Default.CheckCircle else Icons.Default.NotificationsActive,
                    contentDescription = null,
                    tint = Color(0xFF9C4275),
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = if (permissionsGranted) "Permissions Active" else "Enable Notifications & Gallery",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color(0xFF201A1B)
                    )
                    Text(
                        text = "Receive instant match updates and select high quality photos from gallery.",
                        fontSize = 12.sp,
                        color = Color(0xFF524345)
                    )
                }
                if (!permissionsGranted) {
                    Button(
                        onClick = { permissionsLauncher.launch(permissionsToRequest) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C4275)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Allow", fontSize = 12.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Profile Photo Selector
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Profile Photo",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF201A1B)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFCECEF))
                        .border(BorderStroke(2.dp, Color(0xFF9C4275)), CircleShape)
                        .clickable { photoPickerLauncher.launch("image/*") },
                    contentAlignment = Alignment.Center
                ) {
                    if (profilePhotoUri != null) {
                        AsyncImage(
                            model = profilePhotoUri,
                            contentDescription = "Profile Photo",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.AddPhotoAlternate,
                                contentDescription = null,
                                tint = Color(0xFF9C4275),
                                modifier = Modifier.size(36.dp)
                            )
                            Text("Upload", fontSize = 11.sp, color = Color(0xFF9C4275), fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Detailed Form Fields Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Full Name") },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Age input with 16+ validation
                OutlinedTextField(
                    value = ageText,
                    onValueChange = { ageText = it },
                    label = { Text("Age (Must be 16 or older)") },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Gender Selection (Male / Female)
                Text("Your Gender:", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF201A1B))
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    val isMale = gender == "Male"
                    Button(
                        onClick = { gender = "Male" },
                        modifier = Modifier.weight(1f).height(48.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isMale) Color(0xFF9C4275) else Color(0xFFF0F0F0),
                            contentColor = if (isMale) Color.White else Color.Black
                        )
                    ) {
                        Text("Male 🙋‍♂️", fontWeight = FontWeight.Bold)
                    }

                    val isFemale = gender == "Female"
                    Button(
                        onClick = { gender = "Female" },
                        modifier = Modifier.weight(1f).height(48.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isFemale) Color(0xFF9C4275) else Color(0xFFF0F0F0),
                            contentColor = if (isFemale) Color.White else Color.Black
                        )
                    ) {
                        Text("Female 🙋‍♀️", fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Dating Mode: Straight (Seeking ${if (gender == "Male") "Female" else "Male"})",
                    fontSize = 12.sp,
                    color = Color(0xFF9C4275),
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = city,
                    onValueChange = { city = it },
                    label = { Text("City / Location") },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = occupation,
                    onValueChange = { occupation = it },
                    label = { Text("Occupation / Profession") },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = bio,
                    onValueChange = { bio = it },
                    label = { Text("About Me / Bio") },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Citizenship Verification Upload Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Badge,
                        contentDescription = null,
                        tint = Color(0xFF9C4275)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Citizenship ID Upload",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF201A1B)
                    )
                }

                Text(
                    text = "Upload a photo of your Citizenship or National ID card to verify 16+ age & authentic profile.",
                    fontSize = 12.sp,
                    color = Color(0xFF524345),
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                OutlinedButton(
                    onClick = { citizenshipPickerLauncher.launch("image/*") },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Color(0xFF9C4275))
                ) {
                    Icon(Icons.Default.PhotoLibrary, contentDescription = null, tint = Color(0xFF9C4275))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (citizenshipDocUri != null) "Document Selected ✓" else "Select Citizenship ID Photo",
                        color = Color(0xFF9C4275),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        errorMessage?.let { err ->
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = err,
                color = MaterialTheme.colorScheme.error,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Save & Continue Button
        Button(
            onClick = {
                val ageVal = ageText.toIntOrNull()
                if (name.isBlank()) {
                    errorMessage = "Please enter your full name"
                } else if (ageVal == null || ageVal < 16) {
                    errorMessage = "DatesMe requires users to be 16 years of age or older."
                } else {
                    errorMessage = null
                    val seeking = if (gender == "Male") "Female" else "Male"
                    val createdProfile = UserProfile(
                        id = "my_user_id",
                        displayName = name,
                        age = ageVal,
                        gender = gender,
                        lookingFor = seeking,
                        city = city.ifBlank { "San Francisco, CA" },
                        occupation = occupation,
                        bio = bio,
                        photosJson = profilePhotoUri?.toString() ?: "[]",
                        citizenshipDocUri = citizenshipDocUri?.toString(),
                        isCitizenshipUploaded = citizenshipDocUri != null,
                        isVerified = citizenshipDocUri != null,
                        isProfileComplete = true
                    )
                    profileViewModel.updateProfile(createdProfile)
                    onProfileCreated()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .testTag("save_profile_button"),
            shape = RoundedCornerShape(27.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C4275))
        ) {
            Text("Save Profile & Start Dating 💕", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}
