
package com.withapp.with.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Accessing global state and shared components
import com.withapp.with.models.AppState
import com.withapp.with.ui.components.CharacterView
import com.withapp.with.ui.components.SupportMessageBubble

@Composable
fun HomeView(
    appState: AppState,
    @Suppress("UNUSED_PARAMETER") safePadding: PaddingValues, // Suppressing as it's handled by Box/Column
    onNavigateToChat: (String) -> Unit,
    onRequestPermission: () -> Unit // Now used in HomeHeader
) {
    val scrollState = rememberScrollState()

    // Personalized greeting matching iOS logic
    val greeting = "你好, ${appState.userName}! 我是 With。作为${appState.diabetesType}伙伴，今天感觉如何？"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 1. Header Section with Permission Trigger
        HomeHeader(onSyncClick = onRequestPermission)

        Spacer(modifier = Modifier.height(40.dp))

        // 2. Character and Interactive Bubble
        CharacterView(modifier = Modifier.size(180.dp))
        Spacer(modifier = Modifier.height(24.dp))
        SupportMessageBubble(message = greeting)

        Spacer(modifier = Modifier.height(40.dp))

        // 3. Daily Progress / Story Card
        HomeStoryCard(onClick = { onNavigateToChat("查看今日故事") })

        Spacer(modifier = Modifier.height(24.dp))

        // 4. Horizontal Quick Suggestions
        Text(
            text = "你可以这样问我：",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.align(Alignment.Start).padding(start = 40.dp)
        )
        QuickSuggestionsRow(onSuggestionClick = onNavigateToChat)

        Spacer(modifier = Modifier.height(100.dp)) // Safe space for the floating tab bar
    }
}

@Composable
fun HomeHeader(onSyncClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // User Avatar with soft theme color
        Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(Color(0xFFFFC0CB).copy(alpha = 0.3f))) {
            Icon(Icons.Default.Person, null, tint = Color(0xFFFF69B4), modifier = Modifier.align(Alignment.Center))
        }

        Text("With", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF333333))

        // Sync / Permission button: Triggers onRequestPermission when clicked
        Surface(
            onClick = onSyncClick,
            modifier = Modifier.size(40.dp),
            shape = CircleShape,
            color = Color.White.copy(alpha = 0.6f)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text("☀️") // Representing 'Sync/Refresh' or 'Today'
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeStoryCard(onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        modifier = Modifier.padding(horizontal = 32.dp).fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Book, null, tint = Color(0xFF008080))
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("你的故事", fontSize = 10.sp, color = Color.Gray)
                Text("今日章节：平衡的艺术", fontSize = 15.sp, fontWeight = FontWeight.Medium)
            }
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, tint = Color.LightGray)
        }
    }
}

@Composable
fun QuickSuggestionsRow(onSuggestionClick: (String) -> Unit) {
    val suggestions = listOf("我刚才吃了一顿大餐", "感觉压力有点大", "想看看血糖趋势", "打个招呼")
    LazyRow(
        modifier = Modifier.padding(vertical = 12.dp),
        contentPadding = PaddingValues(horizontal = 32.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(suggestions) { text ->
            Surface(
                modifier = Modifier.clickable { onSuggestionClick(text) },
                shape = RoundedCornerShape(18.dp),
                color = Color.White.copy(alpha = 0.8f),
                border = BorderStroke(1.dp, Color.White)
            ) {
                Text(text, modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), fontSize = 13.sp, color = Color(0xFF008080))
            }
        }
    }
}