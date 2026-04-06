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
//    chatViewModel: ChatViewModel, // Signature must match MainContainer call
//    onBack: () -> Unit
//) {
//    var inputText by remember { mutableStateOf("") }
//    val listState = rememberLazyListState()
//
//    // Auto-scroll to bottom whenever a new message arrives
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
//            .imePadding() // Essential to shift UI when keyboard opens
//    ) {
//        // Header (Aligned with iOS design)
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 16.dp, vertical = 12.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            IconButton(onClick = onBack) {
//                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color(0xFF008080))
//            }
//            Spacer(modifier = Modifier.width(8.dp))
//            Box(
//                modifier = Modifier.size(40.dp).background(Color(0xFFE6F2F2), CircleShape),
//                contentAlignment = Alignment.Center
//            ) {
//                Icon(Icons.Default.Face, contentDescription = "Bot", tint = Color(0xFF008080))
//            }
//            Spacer(modifier = Modifier.width(12.dp))
//            Column {
//                Text("With", fontSize = 18.sp, fontWeight = FontWeight.Bold)
//                Text("在线 - 随时倾听", fontSize = 12.sp, color = Color.Gray)
//            }
//        }
//
//        // Fixed Divider based on Material 3 compatibility
//        Divider(color = Color.LightGray.copy(alpha = 0.3f))
//
//        // Message List
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
//                    Box(
//                        modifier = Modifier
//                            .background(
//                                color = if (isUser) Color(0xFF008080) else Color.White,
//                                shape = RoundedCornerShape(
//                                    topStart = 20.dp,
//                                    topEnd = 20.dp,
//                                    bottomStart = if (isUser) 20.dp else 4.dp,
//                                    bottomEnd = if (isUser) 4.dp else 20.dp
//                                )
//                            )
//                            .padding(horizontal = 16.dp, vertical = 12.dp)
//                    ) {
//                        Text(
//                            text = message.content,
//                            color = if (isUser) Color.White else Color.Black,
//                            fontSize = 15.sp
//                        )
//                    }
//                }
//            }
//        }
//
//        // Chat Input Bar
//        Surface(color = Color.White, shadowElevation = 8.dp) {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp, vertical = 12.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                IconButton(onClick = { /* File/Image selection */ }) {
//                    Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.Gray)
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
//                        Icon(Icons.Default.Send, contentDescription = "Send", tint = Color.White, modifier = Modifier.size(20.dp))
//                    }
//                } else {
//                    IconButton(onClick = { /* Voice recognition trigger */ }) {
//                        Icon(Icons.Default.Call, contentDescription = "Call", tint = Color.Gray)
//                    }
//                }
//            }
//        }
//    }
//}
package com.withapp.with.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.withapp.with.viewmodels.ChatViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatView(
    chatViewModel: ChatViewModel, // Param name matches call in MainContainer
    onBack: () -> Unit
) {
    val listState = rememberLazyListState()

    // Auto-scroll to the bottom when messages list updates
    LaunchedEffect(chatViewModel.messages.size) {
        if (chatViewModel.messages.isNotEmpty()) {
            listState.animateScrollToItem(chatViewModel.messages.size - 1)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("对话详情", fontSize = 18.sp) },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "返回") }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).fillMaxSize().background(Color(0xFFF8FAFC)).imePadding()
        ) {
            // Message display list
            LazyColumn(
                state = listState,
                modifier = Modifier.weight(1f).padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                items(chatViewModel.messages) { message ->
                    Text(
                        text = message.content,
                        modifier = Modifier
                            .background(
                                if (message.isUser) Color(0xFF008080) else Color.White,
                                RoundedCornerShape(12.dp)
                            )
                            .padding(12.dp),
                        color = if (message.isUser) Color.White else Color.Black
                    )
                }
            }
            // Input area placeholder
            Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                Text("输入框正在同步 iOS 端逻辑...", color = Color.Gray)
            }
        }
    }
}