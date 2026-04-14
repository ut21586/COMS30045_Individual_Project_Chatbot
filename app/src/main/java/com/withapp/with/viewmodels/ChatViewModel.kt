
//package com.withapp.with.viewmodels
//
//import androidx.compose.runtime.mutableStateListOf
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.withapp.with.models.ContextMode
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.launch
//import java.util.UUID
//import kotlin.random.Random
//
//data class ChatMessage(
//    val id: UUID = UUID.randomUUID(),
//    val content: String,
//    val isUser: Boolean,
//    val timestamp: Long = System.currentTimeMillis(),
//    val emoji: String? = null,
//    val glucoseContext: Double? = null,
//    val activityContext: String? = null,
//    val imageData: ByteArray? = null
//)
//
//enum class EmotionType { POSITIVE, NEGATIVE, NEUTRAL, UNCERTAIN }
//
//class ChatViewModel : ViewModel() {
//    val messages = mutableStateListOf<ChatMessage>()
//    var isTyping by mutableStateOf(false)
//    var conversationContext by mutableStateOf(ContextMode.PRIVATE)
//
//    // 对应 Swift 里的 quickSuggestions
//    var quickSuggestions = mutableStateListOf(
//        "我不知道该怎么形容",
//        "能聊点别的吗？",
//        "我需要一些指导",
//        "你有什么建议？",
//        "让我想想"
//    )
//
//    init { loadInitialMessage() }
//
//    private fun loadInitialMessage() {
//        messages.add(ChatMessage(content = "你好！我是 With，今天感觉怎么样？", isUser = false))
//    }
//
//    fun sendMessage(text: String, emoji: String? = null) {
//        val trimmed = text.trim()
//        if (trimmed.isEmpty()) return
//
//        messages.add(ChatMessage(content = trimmed, isUser = true, emoji = emoji))
//
//        // 这里就是刚刚报错找不到的方法，现在完美闭环了！
//        updateSuggestions(trimmed)
//        generateResponse(trimmed)
//    }
//
//    fun sendImageMessage(imageData: ByteArray) {
//        messages.add(ChatMessage(content = "", isUser = true, imageData = imageData))
//    }
//
//    // 1:1 翻译 Swift 的 updateSuggestions 方法
//    private fun updateSuggestions(input: String) {
//        val emotion = analyzeEmotion(input)
//        quickSuggestions.clear()
//
//        when (emotion) {
//            EmotionType.NEGATIVE -> quickSuggestions.addAll(listOf(
//                "我现在感觉好点了",
//                "你能帮我理解这种感觉吗？",
//                "我需要休息一下",
//                "告诉我一些积极的事情",
//                "我想尝试呼吸练习"
//            ))
//            EmotionType.POSITIVE -> quickSuggestions.addAll(listOf(
//                "我想分享更多",
//                "我今天应该关注什么？",
//                "我怎样才能保持这种感觉？",
//                "我们定个小目标吧",
//                "谢谢有你在"
//            ))
//            else -> quickSuggestions.addAll(listOf(
//                "我不知道该怎么形容",
//                "能聊点别的吗？",
//                "我需要一些指导",
//                "你有什么建议？",
//                "让我想想"
//            ))
//        }
//    }
//
//    private fun generateResponse(userInput: String) {
//        isTyping = true
//        val emotion = analyzeEmotion(userInput)
//
//        viewModelScope.launch {
//            // 模拟 LLM 网络请求延迟
//            delay(Random.nextLong(1000, 2500))
//
//            // 1:1 模拟 LLMService 的返回
//            val responseText = if (emotion == EmotionType.NEGATIVE) "我感到了你的情绪，深呼吸，我们一起慢慢来。" else "听起来不错！继续保持这种节奏。"
//            addResponseMessages(listOf(responseText))
//        }
//    }
//
//    private fun addResponseMessages(responseTexts: List<String>) {
//        viewModelScope.launch {
//            for (text in responseTexts) {
//                delay(Random.nextLong(800, 1500))
//                messages.add(ChatMessage(content = text, isUser = false))
//            }
//            isTyping = false
//        }
//    }
//
//    private fun analyzeEmotion(text: String): EmotionType {
//        val lower = text.lowercase()
//        // 匹配 Swift 里的关键字检测
//        val negativeKeywords = listOf("upset", "sad", "angry", "frustrated", "tired", "累", "烦", "痛", "难受")
//        val positiveKeywords = listOf("happy", "good", "great", "calm", "好", "开心", "棒", "不错")
//
//        return when {
//            negativeKeywords.any { lower.contains(it) } -> EmotionType.NEGATIVE
//            positiveKeywords.any { lower.contains(it) } -> EmotionType.POSITIVE
//            else -> EmotionType.NEUTRAL
//        }
//    }
//}
package com.withapp.with.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
// 🚀 注意看这两行导入，它们是消灭红线的关键：
import com.withapp.with.ui.components.ChatMessage
import com.withapp.with.network.MockBackend
import com.withapp.with.network.ChatRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun sendMessage(text: String) {
        if (text.isBlank()) return

        // 创建用户消息
        val userMsg = ChatMessage(text = text, isUser = true)
        _messages.value = _messages.value + userMsg

        _isLoading.value = true

        viewModelScope.launch {
            try {
                // 调用咱们刚才写的假后端
                val response = MockBackend.sendMessage(ChatRequest(message = text))

                // 创建 AI 回复消息
                val aiMsg = ChatMessage(text = response.reply, isUser = false)
                _messages.value = _messages.value + aiMsg
            } catch (e: Exception) {
                // 报错时显示
                _messages.value = _messages.value + ChatMessage("系统提示：连接失败", false)
            } finally {
                _isLoading.value = false
            }
        }
    }
}