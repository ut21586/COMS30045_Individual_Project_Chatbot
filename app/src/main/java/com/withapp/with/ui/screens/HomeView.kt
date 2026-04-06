//package com.withapp.with.ui.screens
//
//import androidx.compose.foundation.*
//import androidx.compose.foundation.interaction.MutableInteractionSource
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyRow
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.font.FontFamily
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import kotlinx.coroutines.launch
//import java.util.Locale
//
//// Critical: These must exist in your ui.components package
//import com.withapp.with.services.HealthManager
//import com.withapp.with.ui.components.CharacterView
//import com.withapp.with.ui.components.SupportMessageBubble
//
//@Composable
//fun HomeView(
//    @Suppress("UNUSED_PARAMETER") safePadding: PaddingValues,
//    onNavigateToChat: (String) -> Unit,
//    onRequestPermission: () -> Unit
//) {
//    val context = LocalContext.current
//    val healthManager = remember { HealthManager(context) }
//    val scope = rememberCoroutineScope()
//
//    var steps by remember { mutableLongStateOf(8600L) }
//    var glucose by remember { mutableStateOf(5.6) }
//    var bubbleMessage by remember { mutableStateOf("今天走了 $steps 步，最新血糖 $glucose。不只看数字，我也关心你的感受。") }
//
//    val backgroundBrush = Brush.verticalGradient(
//        colors = listOf(Color(0xFFF2F9F7), Color(0xFFE6F2F2))
//    )
//
//    Box(modifier = Modifier.fillMaxSize().background(backgroundBrush)) {
//        Column(
//            modifier = Modifier.fillMaxSize().statusBarsPadding(),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            HomeHeader()
//
//            Spacer(modifier = Modifier.weight(1.2f))
//
//            // Character Section
//            Column(
//                horizontalAlignment = Alignment.CenterHorizontally,
//                modifier = Modifier.clickable(
//                    interactionSource = remember { MutableInteractionSource() },
//                    indication = null
//                ) {
//                    // Simulate refresh on tap
//                    bubbleMessage = "正在同步最新的健康动态..."
//                }
//            ) {
//                CharacterView(modifier = Modifier.size(180.dp), isAnimating = true)
//                Spacer(modifier = Modifier.height(24.dp))
//                SupportMessageBubble(message = bubbleMessage)
//            }
//
//            Spacer(modifier = Modifier.weight(1f))
//
//            // Integrated components that were previously missing
//            HomeStoryCard(onClick = { onNavigateToChat("查看今日章节内容") })
//            QuickSuggestionsRow(onSuggestionClick = onNavigateToChat)
//
//            Spacer(modifier = Modifier.height(32.dp))
//        }
//    }
//}
//
//@Composable
//fun HomeHeader() {
//    Row(
//        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 10.dp),
//        horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Box(modifier = Modifier.size(44.dp).clip(CircleShape).background(Color(0xFFFFC0CB).copy(alpha = 0.3f))) {
//            Icon(Icons.Default.Person, null, tint = Color(0xFFFF69B4), modifier = Modifier.align(Alignment.Center))
//        }
//        Column(horizontalAlignment = Alignment.CenterHorizontally) {
//            Text("With", fontSize = 32.sp, fontWeight = FontWeight.Medium, fontFamily = FontFamily.Serif)
//            Text("不只看血糖数字，也关心你的感受", fontSize = 10.sp, color = Color.Gray)
//        }
//        Surface(modifier = Modifier.size(44.dp), shape = CircleShape, color = Color.White.copy(alpha = 0.8f)) {
//            Box(contentAlignment = Alignment.Center) { Text("☀️") }
//        }
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun HomeStoryCard(onClick: () -> Unit) {
//    Surface(
//        onClick = onClick,
//        modifier = Modifier.padding(horizontal = 40.dp).fillMaxWidth(),
//        shape = RoundedCornerShape(16.dp), color = Color.White, shadowElevation = 4.dp
//    ) {
//        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
//            Icon(Icons.Default.Book, null, tint = Color(0xFF008080))
//            Spacer(modifier = Modifier.width(12.dp))
//            Column(modifier = Modifier.weight(1f)) {
//                Text("你的故事", fontSize = 10.sp, color = Color.Gray)
//                Text("今日章节已准备好。", fontSize = 14.sp)
//            }
//            Box(modifier = Modifier.size(8.dp).background(Color(0xFF008080), CircleShape))
//        }
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun QuickSuggestionsRow(onSuggestionClick: (String) -> Unit) {
//    val suggestions = listOf("今天状态还可以", "想整理一下思绪", "一点小开心")
//    LazyRow(
//        modifier = Modifier.padding(vertical = 16.dp),
//        contentPadding = PaddingValues(horizontal = 20.dp),
//        horizontalArrangement = Arrangement.spacedBy(10.dp)
//    ) {
//        items(suggestions) { text ->
//            Surface(
//                onClick = { onSuggestionClick(text) },
//                shape = RoundedCornerShape(20.dp), color = Color.White, shadowElevation = 2.dp
//            ) {
//                Text(text, modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp), fontSize = 13.sp, color = Color.Gray)
//            }
//        }
//    }
//}
package com.withapp.with.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// Import custom UI components
import com.withapp.with.ui.components.CharacterView
import com.withapp.with.ui.components.SupportMessageBubble

@Composable
fun HomeView(
    safePadding: PaddingValues, // Required for alignment with MainContainer
    onNavigateToChat: (String) -> Unit,
    onRequestPermission: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // App identity in Chinese
        Text("With", fontSize = 32.sp, modifier = Modifier.padding(top = 20.dp))
        Text("你的贴心健康伙伴", fontSize = 12.sp, color = Color.Gray)

        Spacer(modifier = Modifier.weight(1f))

        // Main animated character
        CharacterView(modifier = Modifier.size(200.dp))

        Spacer(modifier = Modifier.height(30.dp))

        // Supportive message bubble in Chinese
        SupportMessageBubble(message = "你好！今天准备好开始新的一天了吗？")

        Spacer(modifier = Modifier.weight(1f))

        // Action button in Chinese
        Button(
            onClick = { onNavigateToChat("我想记录一下今天的血糖") },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF008080)),
            modifier = Modifier.padding(bottom = 40.dp)
        ) {
            Text("开始对话", color = Color.White)
        }
    }
}