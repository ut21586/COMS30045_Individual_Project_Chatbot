package com.withapp.with.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.withapp.with.models.AppState
import kotlinx.coroutines.delay

data class CGMDevice(val id: String, val name: String, val type: String)

@Composable
fun DeviceConnectionView(
    appState: AppState = viewModel(), // 自动获取全局状态
    onBack: () -> Unit
) {
    var isSearching by remember { mutableStateOf(false) }
    var foundDevices by remember { mutableStateOf<List<CGMDevice>>(emptyList()) }
    var connectingDevice by remember { mutableStateOf<CGMDevice?>(null) }
    var connectionProgress by remember { mutableFloatStateOf(0f) }

    // 模拟蓝牙搜索延迟
    LaunchedEffect(isSearching) {
        if (isSearching) {
            delay(2000)
            foundDevices = listOf(
                CGMDevice("1", "Dexcom G7", "动态血糖仪 (CGM)"),
                CGMDevice("2", "FreeStyle Libre 3", "动态血糖仪 (CGM)")
            )
            isSearching = false
        }
    }

    // 模拟连接进度动画
    LaunchedEffect(connectingDevice) {
        connectingDevice?.let {
            while (connectionProgress < 1f) {
                delay(50)
                connectionProgress += 0.05f
            }
            appState.cgmConnected = true
            connectingDevice = null
            foundDevices = emptyList()
            connectionProgress = 0f
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC)) // 苹果经典的系统灰背景
            .statusBarsPadding()
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        // 1. 顶部导航栏
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack, modifier = Modifier.size(24.dp)) {
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Back", tint = Color(0xFF008080))
            }
            Spacer(modifier = Modifier.weight(1f))
            Text("CGM 设备", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.size(24.dp)) // 平衡返回按钮的占位
        }

        Spacer(modifier = Modifier.height(32.dp))

        // 2. 状态雷达/连接图标
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(
                        if (appState.cgmConnected) Color.Green.copy(alpha = 0.15f) else Color.Blue.copy(alpha = 0.1f),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                // 连接中的进度圈
                if (connectingDevice != null) {
                    CircularProgressIndicator(
                        progress = { connectionProgress },
                        modifier = Modifier.size(110.dp),
                        color = Color(0xFF008080),
                        strokeWidth = 4.dp,
                        trackColor = Color.Transparent
                    )
                }

                // 中心图标
                Icon(
                    imageVector = if (appState.cgmConnected) Icons.Default.CheckCircle else Icons.Default.Favorite,
                    contentDescription = null,
                    tint = if (appState.cgmConnected) Color.Green else Color(0xFF008080),
                    modifier = Modifier.size(50.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 3. 状态文字
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = if (appState.cgmConnected) "已连接" else "未连接",
                fontSize = 24.sp, fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (appState.cgmConnected) "你的 CGM 数据正在实时同步" else if (isSearching) "正在寻找附近的设备..." else "连接你的 CGM 设备以获取实时血糖数据",
                fontSize = 16.sp, color = Color.Gray, textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // 4. 动态面板内容区 (根据状态切换)
        Box(modifier = Modifier.weight(1f)) {
            if (appState.cgmConnected) {
                // 已连接设备卡片
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp), color = Color.White, shadowElevation = 2.dp
                ) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier.size(50.dp).background(Color(0xFF008080).copy(alpha = 0.1f), RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Favorite, contentDescription = null, tint = Color(0xFF008080), modifier = Modifier.size(24.dp))
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Dexcom G7", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(modifier = Modifier.size(8.dp).background(Color.Green, CircleShape))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("传感器正常运行中", fontSize = 13.sp, color = Color.Gray)
                            }
                        }
                    }
                }
            } else if (isSearching) {
                // 搜索中的波纹/进度
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    CircularProgressIndicator(color = Color(0xFF008080), modifier = Modifier.size(40.dp))
                }
            } else if (foundDevices.isNotEmpty()) {
                // 发现的设备列表
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(foundDevices) { device ->
                        Surface(
                            modifier = Modifier.fillMaxWidth().clickable(enabled = connectingDevice == null) {
                                connectingDevice = device
                            },
                            shape = RoundedCornerShape(16.dp), color = Color.White, shadowElevation = 2.dp
                        ) {
                            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier.size(50.dp).background(Color(0xFF008080).copy(alpha = 0.1f), RoundedCornerShape(12.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.Phone, contentDescription = null, tint = Color(0xFF008080), modifier = Modifier.size(24.dp))
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(device.name, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                                    Text(device.type, fontSize = 13.sp, color = Color.Gray)
                                }
                                if (connectingDevice?.id == device.id) {
                                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color(0xFF008080), strokeWidth = 2.dp)
                                } else {
                                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.Gray)
                                }
                            }
                        }
                    }
                }
            } else {
                // 引导说明
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp), color = Color.White, shadowElevation = 2.dp
                ) {
                    Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        InstructionRow("1", "确保你的 CGM 传感器已佩戴并激活")
                        InstructionRow("2", "打开手机的蓝牙功能")
                        InstructionRow("3", "点击下方按钮进行自动配对搜索")
                    }
                }
            }
        }

        // 5. 底部操作按钮
        Button(
            onClick = {
                if (appState.cgmConnected) {
                    appState.cgmConnected = false // 断开连接
                } else {
                    isSearching = true
                    foundDevices = emptyList()
                }
            },
            modifier = Modifier.fillMaxWidth().height(56.dp).padding(bottom = 8.dp), // 留出一点底部安全距离
            colors = ButtonDefaults.buttonColors(containerColor = if (appState.cgmConnected) Color(0xFFFF3B30) else Color(0xFF008080)),
            enabled = !isSearching && connectingDevice == null
        ) {
            Text(
                text = if (appState.cgmConnected) "断开连接" else if (isSearching) "搜索设备中..." else "搜索设备",
                fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.White
            )
        }
    }
}

@Composable
fun InstructionRow(number: String, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier.size(26.dp).background(Color(0xFF008080), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(number, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(text, fontSize = 15.sp, color = Color(0xFF333333))
    }
}