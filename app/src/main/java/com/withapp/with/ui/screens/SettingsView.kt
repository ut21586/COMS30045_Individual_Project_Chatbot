
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
fun SettingsView(appState: AppState) {
    val scrollState = rememberScrollState()
    val listBackgroundColor = Color(0xFFF8FAFC) // iOS settings background color

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(listBackgroundColor)
            .verticalScroll(scrollState)
            .statusBarsPadding()
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        // 1. Page Title
        Text("设置", fontSize = 32.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(24.dp))

        // 2. iOS-style Profile Card
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            shadowElevation = 1.dp
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Profile Avatar
                Box(
                    modifier = Modifier.size(60.dp).clip(CircleShape).background(Color(0xFF008080).copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🧑‍💻", fontSize = 30.sp)
                }

                Spacer(modifier = Modifier.width(16.dp))

                // User Info from AppState
                Column(modifier = Modifier.weight(1f)) {
                    Text(appState.userName, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("${appState.diabetesType}伙伴", fontSize = 14.sp, color = Color.Gray)
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // 3. First Settings Group (Data & Devices)
        SettingsGroup {
            SettingsRowItem(
                icon = Icons.Default.Favorite,
                iconTint = Color(0xFFFF6B6B),
                iconBgColor = Color(0xFFFF6B6B).copy(alpha = 0.1f),
                title = "健康数据同步",
                showDivider = true,
                onClick = { /* TODO */ }
            )
            SettingsRowItem(
                icon = Icons.Default.Phone,
                iconTint = Color(0xFF008080),
                iconBgColor = Color(0xFF008080).copy(alpha = 0.1f),
                title = "我的设备 (CGM)",
                value = "未连接",
                showDivider = false,
                onClick = { /* TODO */ }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 4. Second Settings Group (Preferences)
        SettingsGroup {
            SettingsRowItem(
                icon = Icons.Default.Notifications,
                iconTint = Color(0xFFFFA500),
                iconBgColor = Color(0xFFFFA500).copy(alpha = 0.1f),
                title = "推送通知",
                showDivider = true,
                onClick = { /* TODO */ }
            )
            SettingsRowItem(
                icon = Icons.Default.Lock,
                iconTint = Color(0xFF4169E1),
                iconBgColor = Color(0xFF4169E1).copy(alpha = 0.1f),
                title = "隐私政策",
                showDivider = false,
                onClick = { /* TODO */ }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // 5. Logout Button (iOS style centered red text)
        Surface(
            modifier = Modifier.fillMaxWidth().clickable {
                // Reset onboarding state to simulate logout
                appState.hasCompletedOnboarding = false
            },
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            shadowElevation = 1.dp
        ) {
            Box(
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("退出登录", color = Color.Red, fontSize = 17.sp, fontWeight = FontWeight.Normal)
            }
        }

        Spacer(modifier = Modifier.height(120.dp)) // Safe padding for bottom bar
    }
}

// Reusable component for the iOS 'Inset Grouped' card style
@Composable
fun SettingsGroup(content: @Composable ColumnScope.() -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        shadowElevation = 1.dp
    ) {
        Column(content = content)
    }
}

// Reusable component for individual setting rows
@Composable
fun SettingsRowItem(
    icon: ImageVector,
    iconTint: Color,
    iconBgColor: Color,
    title: String,
    value: String? = null,
    showDivider: Boolean = true,
    onClick: () -> Unit
) {
    Column(modifier = Modifier.clickable { onClick() }) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon with rounded background
            Box(
                modifier = Modifier.size(32.dp).clip(RoundedCornerShape(8.dp)).background(iconBgColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(20.dp))
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Title
            Text(title, fontSize = 16.sp, color = Color(0xFF333333), modifier = Modifier.weight(1f))

            // Optional Value (e.g., "未连接")
            if (value != null) {
                Text(value, fontSize = 16.sp, color = Color.Gray)
                Spacer(modifier = Modifier.width(8.dp))
            }

            // iOS Style chevron arrow
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Detail", tint = Color.LightGray)
        }

        // Fine divider line (inset to align with text)
        if (showDivider) {
            HorizontalDivider(
                modifier = Modifier.padding(start = 64.dp),
                thickness = 0.5.dp,
                color = Color.LightGray.copy(alpha = 0.5f)
            )
        }
    }
}