package com.withapp.with.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.withapp.with.ui.components.CharacterView
import com.withapp.with.ui.components.SupportMessageBubble

@Composable
fun HomeView(onNavigateToChat: (String) -> Unit) {
    val backgroundBrush = Brush.verticalGradient(
        colors = listOf(Color(0xFFF2F9F7), Color(0xFFE6F2F2))
    )

    // The background Box occupies the full screen
    Box(modifier = Modifier.fillMaxSize().background(backgroundBrush)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                // These paddings keep content safe from camera notch and home bar
                .statusBarsPadding()
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HomeHeader()
            Spacer(modifier = Modifier.weight(1f))

            var isAnimating by remember { mutableStateOf(false) }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { isAnimating = !isAnimating }
            ) {
                CharacterView(modifier = Modifier.size(180.dp), isAnimating = isAnimating)
                Spacer(modifier = Modifier.height(24.dp))
                SupportMessageBubble(message = "不只看血糖数字，也关心你的感受")
            }

            Spacer(modifier = Modifier.weight(1f))
            HomeStoryCard(onClick = { onNavigateToChat("查看今日章节内容") })

            // Passing the navigation action down to the row
            QuickSuggestionsRow(onSuggestionClick = onNavigateToChat)

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun HomeHeader() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(44.dp).clip(CircleShape).background(Color(0xFFFFC0CB).copy(alpha = 0.3f))) {
            Icon(Icons.Default.Person, null, tint = Color(0xFFFF69B4), modifier = Modifier.align(Alignment.Center))
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("With", fontSize = 32.sp, fontWeight = FontWeight.Medium, fontFamily = FontFamily.Serif)
            Text("不只看血糖数字，也关心你的感受", fontSize = 10.sp, color = Color.Gray, letterSpacing = 2.sp)
        }
        Surface(modifier = Modifier.size(44.dp), shape = CircleShape, color = Color.White.copy(alpha = 0.8f)) {
            Box(contentAlignment = Alignment.Center) { Text("☀️") }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeStoryCard(onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        modifier = Modifier.padding(horizontal = 40.dp).fillMaxWidth(),
        shape = RoundedCornerShape(16.dp), color = Color.White, shadowElevation = 4.dp
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Book, null, tint = Color(0xFF008080))
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("你的故事", fontSize = 10.sp, color = Color.Gray)
                Text("今日章节已准备好。", fontSize = 14.sp)
            }
            Box(modifier = Modifier.size(8.dp).background(Color(0xFF008080), CircleShape))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickSuggestionsRow(onSuggestionClick: (String) -> Unit) {
    val suggestions = listOf("今天状态还可以，记录一下", "有点事情，想整理一下思绪", "今天的一个小开心")
    LazyRow(
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(suggestions) { text ->
            Surface(
                onClick = { onSuggestionClick(text) },
                shape = RoundedCornerShape(20.dp), color = Color.White, shadowElevation = 2.dp
            ) {
                Text(text, modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp), fontSize = 13.sp, color = Color.Gray)
            }
        }
    }
}