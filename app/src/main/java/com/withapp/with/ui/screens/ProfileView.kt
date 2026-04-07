package com.withapp.with.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.withapp.with.models.AppState

@Composable
fun ProfileView(
    appState: AppState,
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC)) // iOS 灰色背景
            .verticalScroll(scrollState)
            .statusBarsPadding()
            .padding(horizontal = 20.dp)
    ) {
        // 顶部导航
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack, modifier = Modifier.size(24.dp)) {
                Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Back", tint = Color(0xFF008080))
            }
            Spacer(modifier = Modifier.weight(1f))
        }

        // Profile Header
        Column(
            modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFFC0CB).copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Person, contentDescription = null, tint = Color(0xFFFF69B4), modifier = Modifier.size(40.dp))
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(appState.userName, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text("2026 年 1 月加入", fontSize = 14.sp, color = Color.Gray)
            }
        }

        // 设备连接状态
        Text("设备状态", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray)
        Spacer(modifier = Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            DeviceStatusCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.Favorite,
                name = "CGM 设备",
                status = if (appState.cgmConnected) "已连接" else "未连接",
                isConnected = appState.cgmConnected
            )
            DeviceStatusCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.Watch,
                name = "智能手表",
                status = "已同步",
                isConnected = true
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 快捷设置菜单
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            shadowElevation = 2.dp
        ) {
            Column {
                ProfileMenuItem(icon = Icons.Default.PersonOutline, iconColor = Color(0xFF008080), title = "个人资料", action = {})
                HorizontalDivider(modifier = Modifier.padding(start = 56.dp), color = Color.LightGray.copy(alpha = 0.3f))
                ProfileMenuItem(icon = Icons.Default.Notifications, iconColor = Color(0xFF008080), title = "通知设置", action = {})
                HorizontalDivider(modifier = Modifier.padding(start = 56.dp), color = Color.LightGray.copy(alpha = 0.3f))
                ProfileMenuItem(icon = Icons.Default.ExitToApp, iconColor = Color.Red, title = "退出登录", action = { appState.isOnboarded = false })
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 统计数据
        Text("我的数据", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray)
        Spacer(modifier = Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatCard(modifier = Modifier.weight(1f), title = "对话次数", value = "12", icon = Icons.Default.MailOutline)
            StatCard(modifier = Modifier.weight(1f), title = "达标天数", value = "28", icon = Icons.Default.CheckCircle)
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatCard(modifier = Modifier.weight(1f), title = "生成报告", value = "5", icon = Icons.Default.DateRange)
            StatCard(modifier = Modifier.weight(1f), title = "情绪指数", value = "稳定", icon = Icons.Default.Face)
        }

        Spacer(modifier = Modifier.height(60.dp))
    }
}

@Composable
fun DeviceStatusCard(modifier: Modifier = Modifier, icon: ImageVector, name: String, status: String, isConnected: Boolean) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(icon, contentDescription = null, tint = if (isConnected) Color(0xFF008080) else Color.Gray, modifier = Modifier.size(28.dp))
            Text(name, fontSize = 14.sp, fontWeight = FontWeight.Medium)
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Box(modifier = Modifier.size(6.dp).background(if (isConnected) Color.Green else Color.Gray, CircleShape))
                Text(status, fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun ProfileMenuItem(icon: ImageVector, iconColor: Color, title: String, action: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable { action() }.padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(36.dp).background(iconColor.copy(alpha = 0.1f), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(title, fontSize = 16.sp, color = if (iconColor == Color.Red) Color.Red else Color.Black, modifier = Modifier.weight(1f))
        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(16.dp))
    }
}

@Composable
fun StatCard(modifier: Modifier = Modifier, title: String, value: String, icon: ImageVector) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Icon(icon, contentDescription = null, tint = Color(0xFF008080), modifier = Modifier.size(20.dp))
            }
            Text(value, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text(title, fontSize = 12.sp, color = Color.Gray)
        }
    }
}