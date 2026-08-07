package com.example.ui.screens.chat

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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ChatMessage
import com.example.ui.components.EmptyStateView
import com.example.viewmodel.ChatViewModel

@Composable
fun ChatScreen(
    chatViewModel: ChatViewModel,
    matchId: String,
    matchName: String,
    onBack: () -> Unit
) {
    chatViewModel.setActiveMatch(matchId, matchName)

    val messages by chatViewModel.messages.collectAsState()
    val callState by chatViewModel.activeCallState.collectAsState()

    var textInput by remember { mutableStateOf("") }
    var showMenu by remember { mutableStateOf(false) }

    if (callState.isCallActive) {
        CallOverlayScreen(
            callState = callState,
            onEndCall = { chatViewModel.endCall() },
            onToggleMute = { chatViewModel.toggleMute() },
            onToggleSpeaker = { chatViewModel.toggleSpeaker() }
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .testTag("chat_screen")
        ) {
            // Chat Header
            Surface(
                tonalElevation = 4.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("chat_back_button")) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }

                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = matchName.take(1).ifBlank { "M" },
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = matchName.ifBlank { "Match Conversation" },
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Online • Active now",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    IconButton(onClick = { chatViewModel.startCall(isVideo = false) }) {
                        Icon(Icons.Default.Call, contentDescription = "Voice Call", tint = MaterialTheme.colorScheme.primary)
                    }

                    IconButton(onClick = { chatViewModel.startCall(isVideo = true) }) {
                        Icon(Icons.Default.Videocam, contentDescription = "Video Call", tint = MaterialTheme.colorScheme.primary)
                    }

                    Box {
                        IconButton(onClick = { showMenu = true }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "More Options")
                        }
                        DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                            DropdownMenuItem(
                                text = { Text("Mute Notifications") },
                                onClick = { showMenu = false }
                            )
                            DropdownMenuItem(
                                text = { Text("Archive Chat") },
                                onClick = { showMenu = false }
                            )
                            DropdownMenuItem(
                                text = { Text("Block & Report") },
                                onClick = { showMenu = false }
                            )
                        }
                    }
                }
            }

            // Messages Feed
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                if (messages.isEmpty()) {
                    EmptyStateView(
                        title = "Say Hello to $matchName!",
                        description = "You matched! Send a friendly message or photo to start your conversation.",
                        actionTitle = "Send 👋 Wave",
                        onActionClick = {
                            chatViewModel.sendTextMessage("👋 Hi $matchName! Great to connect with you!")
                        }
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        reverseLayout = false
                    ) {
                        items(messages, key = { it.messageId }) { msg ->
                            MessageBubbleItem(
                                message = msg,
                                onReact = { chatViewModel.setReaction(msg.messageId, it) },
                                onDelete = { chatViewModel.deleteMessage(msg.messageId) }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }

            // Message Input Bar
            Surface(
                tonalElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        chatViewModel.sendPhotoMessage("https://images.unsplash.com/photo-1518791841217-8f162f1e1131")
                    }) {
                        Icon(Icons.Default.Photo, contentDescription = "Send Photo", tint = MaterialTheme.colorScheme.primary)
                    }

                    IconButton(onClick = {
                        chatViewModel.sendVoiceMessage("sample_voice_url")
                    }) {
                        Icon(Icons.Default.Mic, contentDescription = "Voice Note", tint = MaterialTheme.colorScheme.primary)
                    }

                    OutlinedTextField(
                        value = textInput,
                        onValueChange = { textInput = it },
                        placeholder = { Text("Type a message...") },
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("chat_input_field")
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(
                        onClick = {
                            if (textInput.isNotBlank()) {
                                chatViewModel.sendTextMessage(textInput)
                                textInput = ""
                            }
                        },
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                            .testTag("chat_send_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Send",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MessageBubbleItem(
    message: ChatMessage,
    onReact: (String) -> Unit,
    onDelete: () -> Unit
) {
    val isMe = message.senderId == "me"

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = if (isMe) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Card(
            shape = RoundedCornerShape(
                topStart = 20.dp,
                topEnd = 20.dp,
                bottomStart = if (isMe) 20.dp else 4.dp,
                bottomEnd = if (isMe) 4.dp else 20.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = if (isMe) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
            ),
            modifier = Modifier.testTag("message_bubble_${message.messageId}")
        ) {
            Column(modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)) {
                Text(
                    text = message.text,
                    color = if (isMe) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium
                )
                if (message.reaction != null) {
                    Text(text = message.reaction!!, fontSize = 14.sp)
                }
            }
        }
    }
}
