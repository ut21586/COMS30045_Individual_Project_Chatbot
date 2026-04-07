package com.withapp.with.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

// Enum to manage the iOS-like connection state machine
enum class ScanState { SCANNING, FOUND, CONNECTING, CONNECTED }

@Composable
fun DeviceConnectionView(onBack: () -> Unit) {
    var currentState by remember { mutableStateOf(ScanState.SCANNING) }

    // Simulate the scanning and finding process
    LaunchedEffect(currentState) {
        if (currentState == ScanState.SCANNING) {
            delay(3000) // Simulate 3 seconds of scanning
            currentState = ScanState.FOUND
        } else if (currentState == ScanState.CONNECTING) {
            delay(2000) // Simulate 2 seconds of connecting
            currentState = ScanState.CONNECTED
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 1. Header with AutoMirrored Back Button
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回", tint = Color(0xFF008080))
            }
            Text("添加设备", fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 8.dp))
        }

        Spacer(modifier = Modifier.weight(1f))

        // 2. Radar Animation Area
        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(300.dp)) {
            // Ripple effect only shows when scanning or connecting
            if (currentState == ScanState.SCANNING || currentState == ScanState.CONNECTING) {
                RadarRippleEffect()
            }

            // Central Device Icon (representing a CGM sensor)
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (currentState == ScanState.CONNECTED) Icons.Default.CheckCircle else Icons.Default.Info,
                    contentDescription = "Device",
                    modifier = Modifier.size(50.dp),
                    tint = if (currentState == ScanState.CONNECTED) Color(0xFF34C759) else Color(0xFF008080)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // 3. Dynamic Bottom Card (iOS Modal Style)
        AnimatedContent(
            targetState = currentState,
            transitionSpec = {
                slideInVertically { height -> height } + fadeIn() togetherWith
                        slideOutVertically { height -> height } + fadeOut()
            },
            label = "bottom_card_animation"
        ) { state ->
            Surface(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 32.dp),
                shape = RoundedCornerShape(24.dp),
                color = Color.White,
                shadowElevation = 8.dp
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    when (state) {
                        ScanState.SCANNING -> {
                            Text("正在寻找 CGM 设备...", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Text("请确保设备已开启并靠近手机", fontSize = 14.sp, color = Color.Gray, modifier = Modifier.padding(top = 8.dp))
                        }
                        ScanState.FOUND -> {
                            Text("发现可用设备", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Text("With Sensor - 8A4F", fontSize = 14.sp, color = Color(0xFF008080), modifier = Modifier.padding(top = 8.dp))
                            Spacer(modifier = Modifier.height(24.dp))
                            Button(
                                onClick = { currentState = ScanState.CONNECTING },
                                modifier = Modifier.fillMaxWidth().height(50.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF008080))
                            ) {
                                Text("立即配对", fontSize = 16.sp)
                            }
                        }
                        ScanState.CONNECTING -> {
                            Text("正在配对...", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            LinearProgressIndicator(
                                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                                color = Color(0xFF008080),
                                trackColor = Color(0xFFE6F2F2)
                            )
                        }
                        ScanState.CONNECTED -> {
                            Text("配对成功！", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF34C759))
                            Text("数据将自动同步至您的健康简报", fontSize = 14.sp, color = Color.Gray, modifier = Modifier.padding(top = 8.dp))
                            Spacer(modifier = Modifier.height(24.dp))
                            Button(
                                onClick = onBack,
                                modifier = Modifier.fillMaxWidth().height(50.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF34C759))
                            ) {
                                Text("完成", fontSize = 16.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

// Reusable component for the iOS radar pulse animation
@Composable
fun RadarRippleEffect() {
    val infiniteTransition = rememberInfiniteTransition(label = "radar")

    // Scale animation
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 3f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "radar_scale"
    )

    // Alpha (fade out) animation
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "radar_alpha"
    )

    Box(
        modifier = Modifier
            .size(100.dp)
            .scale(scale)
            .alpha(alpha)
            .clip(CircleShape)
            .background(Color(0xFF008080))
    )
}