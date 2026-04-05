package com.withapp.with.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight // 这行是修复报错的关键
import androidx.compose.ui.unit.sp

/**
 * Android version of the main character circle.
 * Displays "With" or a sparkle ✨ based on animation state.
 */
@Composable
fun CharacterView(modifier: Modifier = Modifier, isAnimating: Boolean = false) {
    // Replicating the "With" emerald green color
    val emeraldGreen = Color(0xFF008080)

    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(emeraldGreen),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (isAnimating) "✨" else "With",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold // 引用已修复
        )
    }
}