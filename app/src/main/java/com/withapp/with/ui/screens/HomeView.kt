//
//package com.withapp.with.ui.screens
//
//import androidx.compose.foundation.*
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyRow
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
//import androidx.compose.material.icons.filled.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//
//// Accessing global state and shared components
//import com.withapp.with.models.AppState
//import com.withapp.with.ui.components.CharacterView
//import com.withapp.with.ui.components.SupportMessageBubble
//
//@Composable
//fun HomeView(
//    appState: AppState,
//    @Suppress("UNUSED_PARAMETER") safePadding: PaddingValues, // Suppressing as it's handled by Box/Column
//    onNavigateToChat: (String) -> Unit,
//    onRequestPermission: () -> Unit // Now used in HomeHeader
//) {
//    val scrollState = rememberScrollState()
//
//    // Personalized greeting matching iOS logic
//    val greeting = "你好, ${appState.userName}! 我是 With。作为${appState.diabetesType}伙伴，今天感觉如何？"
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .verticalScroll(scrollState)
//            .statusBarsPadding(),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        // 1. Header Section with Permission Trigger
//        HomeHeader(onSyncClick = onRequestPermission)
//
//        Spacer(modifier = Modifier.height(40.dp))
//
//        // 2. Character and Interactive Bubble
//        CharacterView(modifier = Modifier.size(180.dp))
//        Spacer(modifier = Modifier.height(24.dp))
//        SupportMessageBubble(message = greeting)
//
//        Spacer(modifier = Modifier.height(40.dp))
//
//        // 3. Daily Progress / Story Card
//        HomeStoryCard(onClick = { onNavigateToChat("查看今日故事") })
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // 4. Horizontal Quick Suggestions
//        Text(
//            text = "你可以这样问我：",
//            fontSize = 12.sp,
//            color = Color.Gray,
//            modifier = Modifier.align(Alignment.Start).padding(start = 40.dp)
//        )
//        QuickSuggestionsRow(onSuggestionClick = onNavigateToChat)
//
//        Spacer(modifier = Modifier.height(100.dp)) // Safe space for the floating tab bar
//    }
//}
//
//@Composable
//fun HomeHeader(onSyncClick: () -> Unit) {
//    Row(
//        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 12.dp),
//        horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        // User Avatar with soft theme color
//        Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(Color(0xFFFFC0CB).copy(alpha = 0.3f))) {
//            Icon(Icons.Default.Person, null, tint = Color(0xFFFF69B4), modifier = Modifier.align(Alignment.Center))
//        }
//
//        Text("With", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF333333))
//
//        // Sync / Permission button: Triggers onRequestPermission when clicked
//        Surface(
//            onClick = onSyncClick,
//            modifier = Modifier.size(40.dp),
//            shape = CircleShape,
//            color = Color.White.copy(alpha = 0.6f)
//        ) {
//            Box(contentAlignment = Alignment.Center) {
//                Text("☀️") // Representing 'Sync/Refresh' or 'Today'
//            }
//        }
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun HomeStoryCard(onClick: () -> Unit) {
//    Surface(
//        onClick = onClick,
//        modifier = Modifier.padding(horizontal = 32.dp).fillMaxWidth(),
//        shape = RoundedCornerShape(20.dp),
//        color = Color.White,
//        shadowElevation = 2.dp
//    ) {
//        Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
//            Icon(Icons.Default.Book, null, tint = Color(0xFF008080))
//            Spacer(modifier = Modifier.width(16.dp))
//            Column(modifier = Modifier.weight(1f)) {
//                Text("你的故事", fontSize = 10.sp, color = Color.Gray)
//                Text("今日章节：平衡的艺术", fontSize = 15.sp, fontWeight = FontWeight.Medium)
//            }
//            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, tint = Color.LightGray)
//        }
//    }
//}
//
//@Composable
//fun QuickSuggestionsRow(onSuggestionClick: (String) -> Unit) {
//    val suggestions = listOf("我刚才吃了一顿大餐", "感觉压力有点大", "想看看血糖趋势", "打个招呼")
//    LazyRow(
//        modifier = Modifier.padding(vertical = 12.dp),
//        contentPadding = PaddingValues(horizontal = 32.dp),
//        horizontalArrangement = Arrangement.spacedBy(10.dp)
//    ) {
//        items(suggestions) { text ->
//            Surface(
//                modifier = Modifier.clickable { onSuggestionClick(text) },
//                shape = RoundedCornerShape(18.dp),
//                color = Color.White.copy(alpha = 0.8f),
//                border = BorderStroke(1.dp, Color.White)
//            ) {
//                Text(text, modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), fontSize = 13.sp, color = Color(0xFF008080))
//            }
//        }
//    }
//}

package com.withapp.with.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.withapp.with.models.AppState
import com.withapp.with.models.ContextMode
import com.withapp.with.services.HealthManager
import com.withapp.with.viewmodels.ChatViewModel
import com.withapp.with.ui.components.SupportMessage
import com.withapp.with.ui.components.SupportMessageBubble

@Composable
fun HomeView(
    appState: AppState,
    @Suppress("UNUSED_PARAMETER") safePadding: PaddingValues,
    onNavigateToChat: (String) -> Unit,
    onRequestPermission: () -> Unit
) {
    // 注入 Swift 中对应的 ViewModel
    val healthManager: HealthManager = viewModel()
    val chatViewModel: ChatViewModel = viewModel()

    var currentMessage by remember { mutableStateOf(SupportMessage.defaultMessage) }
    val showStoryCard by remember { mutableStateOf(true) }

    // 监听血糖变化和场景模式，动态更新文案（完全对齐 Swift 逻辑）
    LaunchedEffect(healthManager.glucoseLevel, appState.contextMode) {
        currentMessage = SupportMessage.getMessage(healthManager.glucoseLevel, appState.contextMode)
    }

    val bgGradient = Brush.verticalGradient(
        colors = listOf(Color(0xFFF2F9F7), Color(0xFFE6F2F2))
    )

    Box(modifier = Modifier.fillMaxSize().background(bgGradient)) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).statusBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 1. Header 区域
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(onClick = { /* TODO: showProfile = true */ }, shape = CircleShape, color = Color(0xFFFFC0CB).copy(alpha = 0.3f), modifier = Modifier.size(44.dp)) {
                    Icon(Icons.Default.Person, contentDescription = null, tint = Color(0xFFFF69B4), modifier = Modifier.padding(10.dp))
                }

                Spacer(modifier = Modifier.weight(1f))

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("With", fontSize = 32.sp, fontWeight = FontWeight.Medium)
                    Text("与你同在", fontSize = 10.sp, color = Color.Gray, letterSpacing = 2.sp)
                }

                Spacer(modifier = Modifier.weight(1f))

                // 场景模式切换按钮
                Surface(
                    onClick = {
                        val modes = ContextMode.values()
                        val next = (appState.contextMode.ordinal + 1) % modes.size
                        appState.contextMode = modes[next]
                    },
                    shape = CircleShape, color = Color.White.copy(alpha = 0.8f), shadowElevation = 2.dp, modifier = Modifier.size(44.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) { Text("📍") }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // 2. Character 区域 (先用Emoji占位，后面我们翻译你 Swift 里复杂的画布动物)
            Box(modifier = Modifier.size(150.dp).background(Color(0xFF008080).copy(alpha=0.1f), CircleShape), contentAlignment = Alignment.Center) {
                Text(appState.selectedCharacter.emoji, fontSize = 80.sp)
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 动态气泡
            SupportMessageBubble(message = currentMessage)

            Spacer(modifier = Modifier.weight(1f))

            // 3. 故事卡片
            if (showStoryCard) {
                Surface(
                    shape = RoundedCornerShape(16.dp), color = Color.White, shadowElevation = 2.dp,
                    modifier = Modifier.padding(horizontal = 40.dp).fillMaxWidth()
                ) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Book, null, tint = Color(0xFF008080))
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("你的故事", fontSize = 10.sp, color = Color.Gray)
                            Text("今日章节：平衡的艺术", fontSize = 14.sp)
                        }
                    }
                }
            }

            // 4. 快捷回复栏
            val suggestions = listOf("我刚才吃了一顿大餐", "感觉压力有点大", "想看看血糖趋势", "打个招呼")
            LazyRow(
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(vertical = 10.dp)
            ) {
                items(suggestions) { text ->
                    Surface(
                        onClick = {
                            chatViewModel.sendMessage(text)
                            onNavigateToChat(text)
                        },
                        shape = RoundedCornerShape(20.dp), color = Color.White, shadowElevation = 2.dp
                    ) {
                        Text(text, fontSize = 13.sp, color = Color.Gray, modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp))
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}