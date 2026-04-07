
//
//package com.withapp.with.ui.screens
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyRow
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Book
//import androidx.compose.material.icons.filled.Person
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.withapp.with.models.AppState
//import com.withapp.with.services.HealthManager
//import com.withapp.with.viewmodels.ChatViewModel
//import com.withapp.with.ui.components.CharacterView
//import com.withapp.with.ui.components.SupportMessage
//import com.withapp.with.ui.components.SupportMessageBubble
//
//@Composable
//fun HomeView(
//    appState: AppState,
//    @Suppress("UNUSED_PARAMETER") safePadding: PaddingValues,
//    onNavigateToChat: (String) -> Unit,
//    onNavigateToSettings: () -> Unit,
//    onRequestPermission: () -> Unit
//) {
//    val healthManager: HealthManager = viewModel()
//    val chatViewModel: ChatViewModel = viewModel()
//
//    var currentMessage by remember { mutableStateOf(SupportMessage.defaultMessage) }
//    val showStoryCard by remember { mutableStateOf(true) }
//
//    LaunchedEffect(healthManager.glucoseLevel, appState.contextMode) {
//        currentMessage = SupportMessage.getMessage(healthManager.glucoseLevel, appState.contextMode)
//    }
//
//    val bgGradient = Brush.verticalGradient(
//        colors = listOf(Color(0xFFF2F9F7), Color(0xFFE6F2F2))
//    )
//
//    Box(modifier = Modifier.fillMaxSize().background(bgGradient)) {
//        Column(
//            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).statusBarsPadding(),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            // 1. Header 区域
//            Row(
//                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 10.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                // 左上角还原为粉色小人 (触发跳转设置)
//                Surface(
//                    onClick = onNavigateToSettings,
//                    shape = CircleShape,
//                    color = Color(0xFFFFC0CB).copy(alpha = 0.3f), // 粉色背景
//                    modifier = Modifier.size(44.dp)
//                ) {
//                    Icon(
//                        Icons.Default.Person,
//                        contentDescription = "Settings",
//                        tint = Color(0xFFFF69B4), // 粉色小人
//                        modifier = Modifier.padding(10.dp)
//                    )
//                }
//
//                Spacer(modifier = Modifier.weight(1f))
//
//                // 中间的 App Logo
//                Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                    Text("With", fontSize = 32.sp, fontWeight = FontWeight.Medium, fontFamily = androidx.compose.ui.text.font.FontFamily.Serif)
//                    Text("与你同在", fontSize = 10.sp, color = Color.Gray, letterSpacing = 2.sp)
//                }
//
//                Spacer(modifier = Modifier.weight(1f))
//
//                // 右上角的曲别针已彻底删除！
//                // 用一个同等大小的 Spacer 占位，保证中间的 Logo 绝对居中对称
//                Spacer(modifier = Modifier.size(44.dp))
//            }
//
//            Spacer(modifier = Modifier.weight(1f))
//
//            // 2. Character 区域
//            CharacterView(character = appState.selectedCharacter, isAnimating = true, modifier = Modifier.size(150.dp))
//
//            Spacer(modifier = Modifier.height(20.dp))
//
//            // 动态气泡
//            SupportMessageBubble(message = currentMessage)
//
//            Spacer(modifier = Modifier.weight(1f))
//
//            // 3. 故事卡片
//            if (showStoryCard) {
//                Surface(
//                    shape = RoundedCornerShape(16.dp), color = Color.White, shadowElevation = 2.dp,
//                    modifier = Modifier.padding(horizontal = 40.dp).fillMaxWidth()
//                ) {
//                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
//                        Icon(Icons.Default.Book, null, tint = Color(0xFF008080))
//                        Spacer(modifier = Modifier.width(12.dp))
//                        Column {
//                            Text("你的故事", fontSize = 10.sp, color = Color.Gray)
//                            Text("今日章节：平衡的艺术", fontSize = 14.sp)
//                        }
//                    }
//                }
//            }
//
//            // 4. 快捷回复栏
//            val suggestions = listOf("我刚才吃了一顿大餐", "感觉压力有点大", "想看看血糖趋势")
//            LazyRow(
//                contentPadding = PaddingValues(horizontal = 20.dp),
//                horizontalArrangement = Arrangement.spacedBy(10.dp),
//                modifier = Modifier.padding(vertical = 10.dp)
//            ) {
//                items(suggestions) { text ->
//                    Surface(
//                        onClick = {
//                            chatViewModel.sendMessage(text)
//                            onNavigateToChat(text)
//                        },
//                        shape = RoundedCornerShape(20.dp), color = Color.White, shadowElevation = 2.dp
//                    ) {
//                        Text(text, fontSize = 13.sp, color = Color.Gray, modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp))
//                    }
//                }
//            }
//            Spacer(modifier = Modifier.height(20.dp))
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
import com.withapp.with.services.HealthManager
import com.withapp.with.viewmodels.ChatViewModel
import com.withapp.with.ui.components.CharacterView
import com.withapp.with.ui.components.SupportMessage
import com.withapp.with.ui.components.SupportMessageBubble

@Composable
fun HomeView(
    appState: AppState,
    @Suppress("UNUSED_PARAMETER") safePadding: PaddingValues,
    onNavigateToChat: (String) -> Unit,
    onNavigateToSettings: () -> Unit,
    onRequestPermission: () -> Unit
) {
    val healthManager: HealthManager = viewModel()
    val chatViewModel: ChatViewModel = viewModel()

    var currentMessage by remember { mutableStateOf(SupportMessage.defaultMessage) }
    val showStoryCard by remember { mutableStateOf(true) }

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
                // 左上角还原为粉色小人 (触发跳转设置)
                Surface(
                    onClick = onNavigateToSettings,
                    shape = CircleShape,
                    color = Color(0xFFFFC0CB).copy(alpha = 0.3f), // 粉色背景
                    modifier = Modifier.size(44.dp)
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Settings",
                        tint = Color(0xFFFF69B4), // 粉色小人
                        modifier = Modifier.padding(10.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // 中间的 App Logo
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("With", fontSize = 32.sp, fontWeight = FontWeight.Medium, fontFamily = androidx.compose.ui.text.font.FontFamily.Serif)
                    Text("与你同在", fontSize = 10.sp, color = Color.Gray, letterSpacing = 2.sp)
                }

                Spacer(modifier = Modifier.weight(1f))

                // 右上角的曲别针已彻底删除！
                // 用一个同等大小的 Spacer 占位，保证中间的 Logo 绝对居中对称
                Spacer(modifier = Modifier.size(44.dp))
            }

            Spacer(modifier = Modifier.weight(1f))

            // 2. Character 区域
            CharacterView(character = appState.selectedCharacter, isAnimating = true, modifier = Modifier.size(150.dp))

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
            val suggestions = listOf("我刚才吃了一顿大餐", "感觉压力有点大", "想看看血糖趋势")
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