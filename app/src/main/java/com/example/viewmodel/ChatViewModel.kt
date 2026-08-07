package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.ChatMessage
import com.example.data.model.MessageType
import com.example.data.repository.ChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ActiveCallState(
    val isCallActive: Boolean = false,
    val isVideo: Boolean = false,
    val matchName: String = "",
    val isMuted: Boolean = false,
    val isSpeakerOn: Boolean = false,
    val durationSeconds: Int = 0
)

class ChatViewModel(private val chatRepository: ChatRepository) : ViewModel() {
    val activeMatchId = MutableStateFlow<String?>(null)
    val activeMatchName = MutableStateFlow<String>("")

    val messages: StateFlow<List<ChatMessage>> = activeMatchId.flatMapLatest { matchId ->
        if (matchId == null) flowOf(emptyList())
        else chatRepository.getMessagesForMatch(matchId)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val activeCallState = MutableStateFlow(ActiveCallState())

    fun setActiveMatch(matchId: String, matchName: String) {
        activeMatchId.value = matchId
        activeMatchName.value = matchName
    }

    fun sendTextMessage(text: String, replyToId: String? = null) {
        val matchId = activeMatchId.value ?: return
        if (text.isBlank()) return
        viewModelScope.launch {
            val msg = ChatMessage(
                messageId = "msg_${System.currentTimeMillis()}",
                matchId = matchId,
                senderId = "me",
                recipientId = matchId,
                text = text.trim(),
                messageType = MessageType.TEXT,
                replyToId = replyToId
            )
            chatRepository.sendMessage(msg)
        }
    }

    fun sendPhotoMessage(photoUrl: String) {
        val matchId = activeMatchId.value ?: return
        viewModelScope.launch {
            val msg = ChatMessage(
                messageId = "msg_${System.currentTimeMillis()}",
                matchId = matchId,
                senderId = "me",
                recipientId = matchId,
                text = "📷 Shared a photo",
                mediaUrl = photoUrl,
                messageType = MessageType.PHOTO
            )
            chatRepository.sendMessage(msg)
        }
    }

    fun sendVoiceMessage(voiceUrl: String) {
        val matchId = activeMatchId.value ?: return
        viewModelScope.launch {
            val msg = ChatMessage(
                messageId = "msg_${System.currentTimeMillis()}",
                matchId = matchId,
                senderId = "me",
                recipientId = matchId,
                text = "🎙️ Voice message (0:14)",
                mediaUrl = voiceUrl,
                messageType = MessageType.VOICE
            )
            chatRepository.sendMessage(msg)
        }
    }

    fun setReaction(messageId: String, reaction: String?) {
        viewModelScope.launch {
            chatRepository.setReaction(messageId, reaction)
        }
    }

    fun pinMessage(messageId: String, isPinned: Boolean) {
        viewModelScope.launch {
            chatRepository.setPinned(messageId, isPinned)
        }
    }

    fun deleteMessage(messageId: String) {
        viewModelScope.launch {
            chatRepository.deleteMessage(messageId)
        }
    }

    fun startCall(isVideo: Boolean) {
        activeCallState.value = ActiveCallState(
            isCallActive = true,
            isVideo = isVideo,
            matchName = activeMatchName.value.ifBlank { "Match" }
        )
    }

    fun endCall() {
        activeCallState.value = ActiveCallState(isCallActive = false)
    }

    fun toggleMute() {
        activeCallState.value = activeCallState.value.copy(isMuted = !activeCallState.value.isMuted)
    }

    fun toggleSpeaker() {
        activeCallState.value = activeCallState.value.copy(isSpeakerOn = !activeCallState.value.isSpeakerOn)
    }
}
