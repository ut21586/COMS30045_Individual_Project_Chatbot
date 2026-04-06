//package com.withapp.with.viewmodels
//
//import androidx.compose.runtime.mutableStateListOf
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.launch
//
//// Data class for chat messages
//data class ChatMessage(
//    val content: String,
//    val isUser: Boolean
//)
//
//class ChatViewModel : ViewModel() {
//
//    // UI state for messages
//    val messages = mutableStateListOf<ChatMessage>()
//
//    init {
//        // Initial bot greeting
//        messages.add(ChatMessage(content = "你好！我是 With，今天感觉怎么样？", isUser = false))
//    }
//
//    // Process outgoing messages
//    fun sendMessage(text: String) {
//        messages.add(ChatMessage(content = text, isUser = true))
//
//        viewModelScope.launch {
//            /* * BACKEND INTEGRATION POINT:
//             * Replace this mock logic with actual DeepSeek API calls.
//             */
//            delay(1500)
//            val mockApiResponse = "我已经收到你的消息：“${text}”。前端的 UI 已经完美搞定了！"
//            messages.add(ChatMessage(content = mockApiResponse, isUser = false))
//        }
//    }
//}

package com.withapp.with.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

data class ChatMessage(val content: String, val isUser: Boolean)

class ChatViewModel : ViewModel() {
    val messages = mutableStateListOf<ChatMessage>()
    init {
        messages.add(ChatMessage("你好！我是 With，今天感觉怎么样？", false))
    }
    fun sendMessage(text: String) {
        messages.add(ChatMessage(text, true))
        // Mock response
        messages.add(ChatMessage("收到你的消息了，正在处理...", false))
    }
}