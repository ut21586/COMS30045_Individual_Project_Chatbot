
//
//package com.withapp.with.ui.screens
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.lazy.rememberLazyListState
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.ArrowBack
//import androidx.compose.material.icons.automirrored.filled.Send
//import androidx.compose.material.icons.filled.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.withapp.with.viewmodels.ChatViewModel
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ChatView(
//    chatViewModel: ChatViewModel,
//    onBack: () -> Unit
//) {
//    var inputText by remember { mutableStateOf("") }
//    val listState = rememberLazyListState()
//
//    // Auto-scroll logic: ensures the latest message is always visible
//    LaunchedEffect(chatViewModel.messages.size) {
//        if (chatViewModel.messages.isNotEmpty()) {
//            listState.animateScrollToItem(chatViewModel.messages.size - 1)
//        }
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color(0xFFF8FAFC))
//            .statusBarsPadding()
//            .navigationBarsPadding()
//            .imePadding()
//    ) {
//        // Header Section
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 16.dp, vertical = 12.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            IconButton(onClick = onBack) {
//                // FIXED: Using AutoMirrored version to resolve warning
//                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回", tint = Color(0xFF008080))
//            }
//            Spacer(modifier = Modifier.width(8.dp))
//            Box(
//                modifier = Modifier.size(40.dp).background(Color(0xFFE6F2F2), CircleShape),
//                contentAlignment = Alignment.Center
//            ) {
//                Icon(Icons.Default.Face, contentDescription = "Robot", tint = Color(0xFF008080))
//            }
//            Spacer(modifier = Modifier.width(12.dp))
//            Column {
//                Text("With", fontSize = 18.sp, fontWeight = FontWeight.Bold)
//                Text("在线 - 陪你聊天", fontSize = 12.sp, color = Color.Gray)
//            }
//        }
//
//        // FIXED: Using HorizontalDivider instead of deprecated Divider
//        HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))
//
//        // Messages List Section
//        LazyColumn(
//            state = listState,
//            modifier = Modifier.weight(1f).padding(horizontal = 16.dp),
//            contentPadding = PaddingValues(vertical = 16.dp),
//            verticalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            items(chatViewModel.messages) { message ->
//                val isUser = message.isUser
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
//                ) {
//                    Surface(
//                        color = if (isUser) Color(0xFF008080) else Color.White,
//                        shape = RoundedCornerShape(
//                            topStart = 20.dp,
//                            topEnd = 20.dp,
//                            bottomStart = if (isUser) 20.dp else 4.dp,
//                            bottomEnd = if (isUser) 4.dp else 20.dp
//                        ),
//                        shadowElevation = 1.dp
//                    ) {
//                        Text(
//                            text = message.content,
//                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
//                            color = if (isUser) Color.White else Color.Black,
//                            fontSize = 15.sp
//                        )
//                    }
//                }
//            }
//        }
//
//        // Input Area Section
//        Surface(
//            color = Color.White,
//            shadowElevation = 8.dp
//        ) {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp, vertical = 12.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                IconButton(onClick = { /* TODO: Image picker */ }) {
//                    Icon(Icons.Default.Add, contentDescription = "添加", tint = Color.Gray)
//                }
//
//                TextField(
//                    value = inputText,
//                    onValueChange = { inputText = it },
//                    placeholder = { Text("说点什么吧...") },
//                    colors = TextFieldDefaults.colors(
//                        focusedContainerColor = Color(0xFFF1F5F9),
//                        unfocusedContainerColor = Color(0xFFF1F5F9),
//                        focusedIndicatorColor = Color.Transparent,
//                        unfocusedIndicatorColor = Color.Transparent
//                    ),
//                    shape = RoundedCornerShape(20.dp),
//                    modifier = Modifier.weight(1f).padding(horizontal = 8.dp)
//                )
//
//                if (inputText.isNotBlank()) {
//                    IconButton(
//                        onClick = {
//                            chatViewModel.sendMessage(inputText)
//                            inputText = ""
//                        },
//                        modifier = Modifier.background(Color(0xFF008080), CircleShape).size(40.dp)
//                    ) {
//                        // FIXED: Using AutoMirrored version to resolve warning
//                        Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "发送", tint = Color.White, modifier = Modifier.size(20.dp))
//                    }
//                } else {
//                    IconButton(onClick = { /* TODO: Voice recorder */ }) {
//                        Icon(Icons.Default.Mic, contentDescription = "语音", tint = Color.Gray)
//                    }
//                }
//            }
//        }
//    }
//}

package com.withapp.with.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.withapp.with.viewmodels.ChatMessage
import com.withapp.with.viewmodels.ChatViewModel

@Composable
fun ChatView(
    chatViewModel: ChatViewModel,
    onBack: () -> Unit
) {
    var inputText by remember { mutableStateOf("") }
    var isRecording by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    val focusRequester = remember { FocusRequester() }

    // 自动滚动到最新消息 (对齐 Swift 的 ScrollViewReader onChange)
    LaunchedEffect(chatViewModel.messages.size, chatViewModel.isTyping) {
        if (chatViewModel.messages.isNotEmpty()) {
            listState.animateScrollToItem(chatViewModel.messages.size) // +1 for typing indicator
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F8FA)) // iOS Background Color: Color(red: 0.95, green: 0.97, blue: 0.98)
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
    ) {
        // 1. Header (1:1 还原)
        ChatHeader(onBack = onBack)

        // 2. Messages List
        LazyColumn(
            state = listState,
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "今天",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }

            items(chatViewModel.messages) { message ->
                ChatBubble(message = message)
            }

            if (chatViewModel.isTyping) {
                item { TypingIndicator() }
            }
        }

        // 3. Input Area
        ChatInputArea(
            inputText = inputText,
            onTextChange = { inputText = it },
            isRecording = isRecording,
            onToggleRecording = { isRecording = !isRecording },
            onSend = {
                if (inputText.isNotBlank()) {
                    chatViewModel.sendMessage(inputText)
                    inputText = ""
                }
            },
            focusRequester = focusRequester
        )
    }
}

@Composable
fun ChatHeader(onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack, modifier = Modifier.size(24.dp)) {
            Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Back", tint = Color.Gray)
        }

        Spacer(modifier = Modifier.weight(1f))

        HStack(spacing = 12.dp) {
            // Placeholder for CharacterAvatar
            Box(modifier = Modifier.size(40.dp).background(Color(0xFF008080), RoundedCornerShape(12.dp)))

            Column {
                Text("With", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(6.dp).background(Color(0xFF008080), CircleShape))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("在线", fontSize = 12.sp, color = Color(0xFF008080))
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.size(24.dp)) // Balance the back button
    }
}

@Composable
fun ChatBubble(message: ChatMessage) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start) {
        Column(horizontalAlignment = if (message.isUser) Alignment.End else Alignment.Start) {
            Box(
                modifier = Modifier
                    .shadow(if (message.isUser) 2.dp else 5.dp, RoundedCornerShape(20.dp))
                    .background(
                        if (message.isUser) Color(0xFF008080) else Color.White,
                        RoundedCornerShape(20.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = message.content,
                    fontSize = 15.sp,
                    color = if (message.isUser) Color.White else Color.Black
                )
            }
        }
    }
}

@Composable
fun ChatInputArea(
    inputText: String,
    onTextChange: (String) -> Unit,
    isRecording: Boolean,
    onToggleRecording: () -> Unit,
    onSend: () -> Unit,
    focusRequester: FocusRequester
) {
    Column(modifier = Modifier.background(Color.White)) {
        HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.5f))
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Photo", tint = Color.Gray, modifier = Modifier.size(24.dp))
            Icon(Icons.Default.Face, contentDescription = "Emoji", tint = Color.Gray, modifier = Modifier.size(24.dp))

            Icon(
                imageVector = Icons.Default.Mic,
                contentDescription = "Mic",
                tint = if (isRecording) Color(0xFF008080) else Color.Gray,
                modifier = Modifier.size(24.dp)
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(Color.Gray.copy(alpha = 0.1f), RoundedCornerShape(20.dp))
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                if (inputText.isEmpty()) {
                    Text("输入信息...", color = Color.Gray, fontSize = 15.sp)
                }
                BasicTextField(
                    value = inputText,
                    onValueChange = onTextChange,
                    modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
                    textStyle = TextStyle(fontSize = 15.sp, color = Color.Black)
                )
            }

            IconButton(onClick = onSend, enabled = inputText.isNotBlank(), modifier = Modifier.size(24.dp)) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send",
                    tint = if (inputText.isEmpty()) Color.Gray else Color(0xFF008080)
                )
            }
        }
    }
}

@Composable
fun TypingIndicator() {
    val infiniteTransition = rememberInfiniteTransition(label = "typing")
    val dot1 by infiniteTransition.animateFloat(initialValue = 0f, targetValue = -10f, animationSpec = infiniteRepeatable(tween(500), RepeatMode.Reverse), label = "dot1")
    val dot2 by infiniteTransition.animateFloat(initialValue = 0f, targetValue = -10f, animationSpec = infiniteRepeatable(tween(500, delayMillis = 150), RepeatMode.Reverse), label = "dot2")
    val dot3 by infiniteTransition.animateFloat(initialValue = 0f, targetValue = -10f, animationSpec = infiniteRepeatable(tween(500, delayMillis = 300), RepeatMode.Reverse), label = "dot3")

    Row(
        modifier = Modifier
            .background(Color.White, RoundedCornerShape(20.dp))
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(modifier = Modifier.size(8.dp).offset(y = dot1.dp).background(Color.Gray.copy(alpha = 0.5f), CircleShape))
        Box(modifier = Modifier.size(8.dp).offset(y = dot2.dp).background(Color.Gray.copy(alpha = 0.5f), CircleShape))
        Box(modifier = Modifier.size(8.dp).offset(y = dot3.dp).background(Color.Gray.copy(alpha = 0.5f), CircleShape))
    }
}

// Simple Helper for Row layout
@Composable
fun HStack(spacing: androidx.compose.ui.unit.Dp = 0.dp, content: @Composable RowScope.() -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(spacing), content = content)
}