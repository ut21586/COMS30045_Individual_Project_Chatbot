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
import androidx.compose.runtime.*
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
fun SettingsView(appState: AppState, onNavigateToDevice: () -> Unit = {}) {
    val scrollState = rememberScrollState()
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF8FAFC)).verticalScroll(scrollState).statusBarsPadding().padding(horizontal = 20.dp)) {
        Spacer(modifier = Modifier.height(20.dp))
        Text("设置", fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))

        // iOS 风格个人卡片
        Surface(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), color = Color.White, shadowElevation = 1.dp) {
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(60.dp).clip(CircleShape).background(Color(0xFF008080).copy(alpha = 0.1f)), contentAlignment = Alignment.Center) { Text("🧑‍💻", fontSize = 30.sp) }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(appState.userName, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(if (appState.cgmConnected) "CGM 已连接" else "设备未连接", fontSize = 14.sp, color = Color.Gray)
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))

        // 设备与通知
        SettingsGroup {
            SettingsRowItem(icon = Icons.Default.Phone, iconTint = Color(0xFF008080), iconBgColor = Color(0xFF008080).copy(alpha = 0.1f), title = "我的设备 (CGM)", value = if (appState.cgmConnected) "Dexcom G7" else "未连接", showDivider = true, onClick = onNavigateToDevice)
            SettingsRowItem(icon = Icons.Default.Notifications, iconTint = Color(0xFFFFA500), iconBgColor = Color(0xFFFFA500).copy(alpha = 0.1f), title = "推送通知", value = if (appState.notificationsEnabled) "已开启" else "已关闭", showDivider = false, onClick = { appState.notificationsEnabled = !appState.notificationsEnabled })
        }
        Spacer(modifier = Modifier.height(24.dp))

        // 退出登录按钮
        Surface(modifier = Modifier.fillMaxWidth().clickable { appState.isOnboarded = false }, shape = RoundedCornerShape(16.dp), color = Color.White, shadowElevation = 1.dp) {
            Box(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp), contentAlignment = Alignment.Center) { Text("退出登录", color = Color.Red, fontSize = 17.sp) }
        }
        Spacer(modifier = Modifier.height(120.dp))
    }
}

@Composable
fun SettingsGroup(content: @Composable ColumnScope.() -> Unit) {
    Surface(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), color = Color.White, shadowElevation = 1.dp) { Column(content = content) }
}

@Composable
fun SettingsRowItem(icon: ImageVector, iconTint: Color, iconBgColor: Color, title: String, value: String? = null, showDivider: Boolean = true, onClick: () -> Unit) {
    Column(modifier = Modifier.clickable { onClick() }) {
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(32.dp).clip(RoundedCornerShape(8.dp)).background(iconBgColor), contentAlignment = Alignment.Center) { Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(20.dp)) }
            Spacer(modifier = Modifier.width(16.dp))
            Text(title, fontSize = 16.sp, color = Color(0xFF333333), modifier = Modifier.weight(1f))
            if (value != null) { Text(value, fontSize = 16.sp, color = Color.Gray); Spacer(modifier = Modifier.width(8.dp)) }
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Detail", tint = Color.LightGray)
        }
        if (showDivider) HorizontalDivider(modifier = Modifier.padding(start = 64.dp), thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.5f))
    }
}