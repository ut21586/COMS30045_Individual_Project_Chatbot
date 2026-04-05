package com.withapp.with.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

// Data model for messages
data class ChatMessage(
    val content: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)

class ChatViewModel : ViewModel() {
    // Observable list of messages
    val messages = mutableStateListOf<ChatMessage>()

    // Replicating Swift's sendMessage logic
    fun sendMessage(text: String) {
        if (text.isNotBlank()) {
            messages.add(ChatMessage(content = text, isUser = true))
            // Later: Add AI logic or HealthManager data here
        }
    }
}