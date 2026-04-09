package com.withapp.with.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.withapp.with.viewmodels.ChatViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatView(
    chatViewModel: ChatViewModel,
    onBack: () -> Unit
) {
    var inputText by remember { mutableStateOf("") }

    // Mock local chat history for UI demonstration
    val chatHistory = remember { mutableStateListOf(
        ChatMessage("嗨，我在这里陪着你。你今天感觉怎么样？", isUser = false)
    )}

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("With", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text("● 始终在线", fontSize = 10.sp, color = Color(0xFF008080))
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
                    }
                },
                // CB-01: Removed the confusing "Story" action icon entirely
                actions = {},
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.White,
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    placeholder = { Text("分享你的感受或刚刚吃了什么...") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF008080),
                        unfocusedBorderColor = Color.LightGray
                    )
                )
                IconButton(
                    onClick = {
                        if (inputText.isNotBlank()) {
                            // 1. Add user message
                            val userMsg = inputText
                            chatHistory.add(ChatMessage(userMsg, isUser = true))
                            inputText = ""

                            // 2. Mock Real-time Feedback (RP-06)
                            val aiResponse = generateImmediateFeedback(userMsg)
                            chatHistory.add(ChatMessage(aiResponse, isUser = false))
                        }
                    },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Icon(Icons.Default.Send, contentDescription = "发送", tint = Color(0xFF008080))
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF7F9FA))
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(chatHistory.size) { index ->
                val message = chatHistory[index]
                ChatBubble(message = message)
            }
        }
    }
}

// Data class for local UI state
data class ChatMessage(val text: String, val isUser: Boolean)

@Composable
fun ChatBubble(message: ChatMessage) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start
    ) {
        Surface(
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = if (message.isUser) 16.dp else 4.dp,
                bottomEnd = if (message.isUser) 4.dp else 16.dp
            ),
            color = if (message.isUser) Color(0xFF008080) else Color.White,
            shadowElevation = 1.dp,
            modifier = Modifier.widthIn(max = 280.dp)
        ) {
            Text(
                text = message.text,
                modifier = Modifier.padding(12.dp),
                color = if (message.isUser) Color.White else Color.Black,
                fontSize = 14.sp
            )
        }
    }
}

// Helper function to simulate RP-06: Real-time feedback based on keywords
fun generateImmediateFeedback(userInput: String): String {
    return when {
        userInput.contains("午餐") || userInput.contains("吃") ->
            "收到你的饮食记录！你的身体正在把美味的食物转化为能量。注意饭后散步有助于平稳血糖哦～"
        userInput.contains("压力") || userInput.contains("累") ->
            "听起来你现在有些疲惫。深呼吸三次，或者听一首喜欢的歌放松一下吧，我一直在这里陪你。"
        userInput.contains("状态还可以") || userInput.contains("不错") ->
            "太棒了！很高兴看到你今天充满活力，继续保持这份好心情！"
        else ->
            "谢谢你的分享，我已经为你记录下来了。如果需要深入聊聊，我随时都在。"
    }
}