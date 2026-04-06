package com.withapp.with.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp

@Composable
fun CharacterView(
    modifier: Modifier = Modifier,
    isAnimating: Boolean = true
) {
    val infiniteTransition = rememberInfiniteTransition(label = "robot")

    val bounce by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = -12f,
        animationSpec = infiniteRepeatable(tween(1200, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "bounce"
    )

    val blink by infiniteTransition.animateFloat(
        initialValue = 1f, targetValue = 0.1f,
        animationSpec = infiniteRepeatable(
            keyframes { durationMillis = 3000; 1f at 0; 1f at 2800; 0.1f at 2900; 1f at 3000 },
            repeatMode = RepeatMode.Restart
        ), label = "blink"
    )

    Canvas(modifier = modifier.offset(y = bounce.dp)) {
        val teal = Color(0xFF008080)
        // Body
        drawRoundRect(color = teal, size = Size(size.width, size.height), cornerRadius = CornerRadius(40f))
        // Eyes
        drawCircle(color = Color.Cyan, radius = 10f, center = Offset(size.width * 0.35f, size.height * 0.4f * blink))
        drawCircle(color = Color.Cyan, radius = 10f, center = Offset(size.width * 0.65f, size.height * 0.4f * blink))
        // Mouth
        drawRect(color = Color.White.copy(0.8f), topLeft = Offset(size.width * 0.38f, size.height * 0.7f), size = Size(size.width * 0.24f, 4f))
    }
}