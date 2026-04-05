package com.withapp.with.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SupportMessageBubble(message: String) {
    Box(
        modifier = Modifier
            .padding(horizontal = 32.dp)
            .background(
                color = Color.White.copy(alpha = 0.9f),
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp, bottomEnd = 20.dp, bottomStart = 4.dp)
            )
            .padding(16.dp)
    ) {
        Text(text = message, fontSize = 15.sp, color = Color.Black, lineHeight = 22.sp)
    }
}