package com.withapp.with.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.withapp.with.R

@Composable
fun HomeView(
    onNavigateToChat: (String) -> Unit
) {
    // Main container for the Home screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F9FA))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        // Top branding and slogan (HP-01: Updated to be more empathetic)
        Text(
            text = "With",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = "陪你看见情绪，也看见血糖",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 4.dp, bottom = 40.dp)
        )

        // Center avatar
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(Color.White, shape = RoundedCornerShape(100.dp)),
            contentAlignment = Alignment.Center
        ) {
            // Placeholder for the bunny avatar
            Text("🐰", fontSize = 100.sp)
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Contextual AI Prompt (HP-05: Translated to Chinese)
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            Text(
                text = "你似乎在餐厅。用餐前需要情感支持吗？\n(双击或滑动)",
                fontSize = 14.sp,
                color = Color.DarkGray,
                modifier = Modifier.padding(16.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Quick input section (HP-03 & HP-04: Fixed click response and positive phrasing)
        Text(
            text = "你的故事：今日章节已准备好。",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            QuickActionButton(
                text = "今天状态还可以，记录一下",
                onClick = { onNavigateToChat("今天状态还可以，记录一下") }
            )
            QuickActionButton(
                text = "有点事情想整理一下思绪",
                onClick = { onNavigateToChat("有点事情想整理一下思绪") }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

// Reusable component for quick action buttons
@Composable
fun QuickActionButton(text: String, onClick: () -> Unit) {
    Surface(
        modifier = Modifier.clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        color = Color(0xFFE0F2F1)
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            color = Color(0xFF00695C),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        )
    }
}