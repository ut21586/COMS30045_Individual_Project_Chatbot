package com.withapp.with.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.withapp.with.models.CharacterType
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun CharacterView(character: CharacterType, isAnimating: Boolean = true, modifier: Modifier = Modifier) {
    var eyeOffset by remember { mutableFloatStateOf(0f) }
    var blinkOpacity by remember { mutableFloatStateOf(1.0f) }

    val infiniteTransition = rememberInfiniteTransition(label = "bounce")
    val bounceOffset by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = -10f,
        animationSpec = infiniteRepeatable(animation = tween(2000, easing = FastOutSlowInEasing), repeatMode = RepeatMode.Reverse),
        label = "bounce"
    )

    LaunchedEffect(isAnimating) {
        if (isAnimating) {
            while (true) {
                delay(3000)
                eyeOffset = Random.nextInt(-3, 3).toFloat()
                delay(1000)
                eyeOffset = 0f
            }
        }
    }

    LaunchedEffect(isAnimating) {
        if (isAnimating) {
            while (true) {
                delay(4000)
                blinkOpacity = 0.1f
                delay(100)
                blinkOpacity = 1.0f
            }
        }
    }

    Box(modifier = modifier.size(150.dp), contentAlignment = androidx.compose.ui.Alignment.Center) {
        Canvas(modifier = Modifier.size(100.dp, 30.dp).graphicsLayer {
            translationY = 70.dp.toPx() + (bounceOffset * 0.5f).dp.toPx()
            alpha = 0.5f
        }) { drawOval(color = Color.Black.copy(alpha = 0.1f)) }

        Box(modifier = Modifier.graphicsLayer { translationY = bounceOffset.dp.toPx() }) {
            when (character) {
                CharacterType.ROBOT -> RobotCharacter(eyeOffset, blinkOpacity)
                CharacterType.CAT -> CatCharacter(eyeOffset, blinkOpacity)
                CharacterType.BEAR -> BearCharacter(eyeOffset, blinkOpacity)
                CharacterType.BUNNY -> BunnyCharacter(eyeOffset, blinkOpacity)
                CharacterType.PANDA -> PandaCharacter(eyeOffset, blinkOpacity)
            }
        }
    }
}

@Composable
fun RobotCharacter(eyeOffset: Float, blinkOpacity: Float) {
    Canvas(modifier = Modifier.size(120.dp)) {
        val center = Offset(size.width / 2, size.height / 2)
        val gradient = Brush.linearGradient(colors = listOf(Color(0.2f, 0.5f, 0.5f), Color(0.15f, 0.4f, 0.45f)), start = Offset.Zero, end = Offset(size.width, size.height))
        drawRoundRect(brush = gradient, topLeft = Offset.Zero, size = size, cornerRadius = CornerRadius(30.dp.toPx()))
        drawRoundRect(color = Color(0.2f, 0.45f, 0.5f), topLeft = Offset(10.dp.toPx(), 15.dp.toPx()), size = Size(100.dp.toPx(), 80.dp.toPx()), cornerRadius = CornerRadius(20.dp.toPx()))
        translate(left = eyeOffset.dp.toPx()) {
            drawCircle(Color.Cyan, radius = 11.dp.toPx(), center = Offset(center.x - 20.dp.toPx(), center.y - 10.dp.toPx()), alpha = blinkOpacity)
            drawCircle(Color.Cyan, radius = 11.dp.toPx(), center = Offset(center.x + 20.dp.toPx(), center.y - 10.dp.toPx()), alpha = blinkOpacity)
        }
        drawRoundRect(color = Color.White.copy(alpha = 0.7f), topLeft = Offset(center.x - 15.dp.toPx(), center.y + 22.dp.toPx()), size = Size(30.dp.toPx(), 6.dp.toPx()), cornerRadius = CornerRadius(3.dp.toPx()))
        drawCircle(Color.Cyan, radius = 5.dp.toPx(), center = Offset(center.x, center.y - 75.dp.toPx()))
        drawRect(Color.Gray, topLeft = Offset(center.x - 1.5f.dp.toPx(), center.y - 70.dp.toPx()), size = Size(3.dp.toPx(), 15.dp.toPx()))
    }
}

@Composable
fun CatCharacter(eyeOffset: Float, blinkOpacity: Float) {
    Canvas(modifier = Modifier.size(120.dp)) {
        val center = Offset(size.width / 2, size.height / 2)
        drawCircle(brush = Brush.linearGradient(listOf(Color(1f, 0.65f, 0f, 0.9f), Color(1f, 0.65f, 0f, 0.7f))), radius = 60.dp.toPx(), center = center)
        translate(left = eyeOffset.dp.toPx()) {
            drawCircle(Color.Black, radius = 8.dp.toPx(), center = Offset(center.x - 20.dp.toPx(), center.y - 5.dp.toPx()), alpha = blinkOpacity)
            drawCircle(Color.Black, radius = 8.dp.toPx(), center = Offset(center.x + 20.dp.toPx(), center.y - 5.dp.toPx()), alpha = blinkOpacity)
        }
        val nosePath = Path().apply { moveTo(center.x, center.y + 20.dp.toPx()); lineTo(center.x - 6.dp.toPx(), center.y + 10.dp.toPx()); lineTo(center.x + 6.dp.toPx(), center.y + 10.dp.toPx()); close() }
        drawPath(nosePath, color = Color.Magenta)
    }
}

@Composable
fun BearCharacter(eyeOffset: Float, blinkOpacity: Float) {
    Canvas(modifier = Modifier.size(120.dp)) {
        val center = Offset(size.width / 2, size.height / 2)
        drawCircle(Color.DarkGray, radius = 20.dp.toPx(), center = Offset(center.x - 40.dp.toPx(), center.y - 40.dp.toPx()))
        drawCircle(Color.DarkGray, radius = 20.dp.toPx(), center = Offset(center.x + 40.dp.toPx(), center.y - 40.dp.toPx()))
        drawCircle(Color.DarkGray, radius = 60.dp.toPx(), center = center)
        translate(left = eyeOffset.dp.toPx()) {
            drawCircle(Color.Black, radius = 7.dp.toPx(), center = Offset(center.x - 20.dp.toPx(), center.y - 10.dp.toPx()), alpha = blinkOpacity)
            drawCircle(Color.Black, radius = 7.dp.toPx(), center = Offset(center.x + 20.dp.toPx(), center.y - 10.dp.toPx()), alpha = blinkOpacity)
        }
    }
}

@Composable
fun BunnyCharacter(eyeOffset: Float, blinkOpacity: Float) {
    Canvas(modifier = Modifier.size(120.dp)) {
        val center = Offset(size.width / 2, size.height / 2)
        drawCircle(Color.White, radius = 60.dp.toPx(), center = center)
        translate(left = eyeOffset.dp.toPx()) {
            drawCircle(Color.Black, radius = 6.dp.toPx(), center = Offset(center.x - 15.dp.toPx(), center.y - 5.dp.toPx()), alpha = blinkOpacity)
            drawCircle(Color.Black, radius = 6.dp.toPx(), center = Offset(center.x + 15.dp.toPx(), center.y - 5.dp.toPx()), alpha = blinkOpacity)
        }
    }
}

@Composable
fun PandaCharacter(eyeOffset: Float, blinkOpacity: Float) {
    Canvas(modifier = Modifier.size(120.dp)) {
        val center = Offset(size.width / 2, size.height / 2)
        drawCircle(Color.White, radius = 60.dp.toPx(), center = center)
        drawOval(Color.Black, topLeft = Offset(center.x - 30.dp.toPx(), center.y - 20.dp.toPx()), size = Size(20.dp.toPx(), 30.dp.toPx()))
        drawOval(Color.Black, topLeft = Offset(center.x + 10.dp.toPx(), center.y - 20.dp.toPx()), size = Size(20.dp.toPx(), 30.dp.toPx()))
    }
}