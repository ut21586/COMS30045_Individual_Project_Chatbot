package com.withapp.with.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsView(
    onBack: () -> Unit,
    onNavigateToDevice: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("设置", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF7F9FA))
        ) {
            item {
                SettingsSectionTitle("通知")
                SettingsRow(title = "推送通知", subtitle = "勿扰时段 22:00-7:00")
                // ST-01: Evaluated haptic feedback. Kept as optional setting.
                SettingsRow(title = "触觉反馈", subtitle = "已启用")
            }

            item {
                SettingsSectionTitle("功能")
                SettingsRow(title = "情感支持", subtitle = "默认开启")
                // ST-02: Removed "Blood Glucose Prediction" (血糖预测) to avoid clinical misinformation
                // Removed "Activity Suggestions" (活动建议) as requested
                SettingsRow(title = "饮食推荐", subtitle = "开启")
                SettingsRow(title = "情境识别", subtitle = "开启")
            }

            item {
                SettingsSectionTitle("设备与数据")
                SettingsRow(title = "CGM 设备连接", subtitle = "未连接", onClick = onNavigateToDevice)
                SettingsRow(title = "隐私与数据", subtitle = "数据权限")
            }
        }
    }
}

// Reusable UI components for Settings
@Composable
fun SettingsSectionTitle(title: String) {
    Text(
        text = title,
        color = Color(0xFF008080),
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 8.dp)
    )
}

@Composable
fun SettingsRow(title: String, subtitle: String = "", onClick: () -> Unit = {}) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 4.dp),
        color = Color.White,
        shape = MaterialTheme.shapes.small,
        shadowElevation = 1.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = title, fontSize = 16.sp, color = Color.Black)
                if (subtitle.isNotEmpty()) {
                    Text(text = subtitle, fontSize = 12.sp, color = Color.Gray)
                }
            }
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Go",
                tint = Color.LightGray
            )
        }
    }
}