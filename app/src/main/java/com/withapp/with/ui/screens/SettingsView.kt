
//package com.withapp.with.ui.screens
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
//import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
//import androidx.compose.material.icons.filled.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.withapp.with.models.AppState
//import java.text.SimpleDateFormat
//import java.util.Date
//import java.util.Locale
//
//@Composable
//fun SettingsView(
//    appState: AppState,
//    onBack: () -> Unit,
//    onNavigateToDevice: () -> Unit = {}
//) {
//    val scrollState = rememberScrollState()
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color(0xFFF8FAFC)) // iOS 灰色背景
//            .verticalScroll(scrollState)
//            .statusBarsPadding()
//            .padding(horizontal = 20.dp)
//    ) {
//        // 顶部返回按钮与标题
//        Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 8.dp), verticalAlignment = Alignment.CenterVertically) {
//            IconButton(onClick = onBack, modifier = Modifier.size(32.dp).offset(x = (-8).dp)) {
//                Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Back", tint = Color.Gray, modifier = Modifier.size(30.dp))
//            }
//        }
//
//        Text("设置", fontSize = 32.sp, fontWeight = FontWeight.Bold)
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // 1. 个人资料卡片
//        Surface(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), color = Color.White, shadowElevation = 1.dp) {
//            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
//                Box(modifier = Modifier.size(60.dp).clip(CircleShape).background(Color(0xFF008080).copy(alpha = 0.1f)), contentAlignment = Alignment.Center) {
//                    Text(appState.selectedCharacter.emoji, fontSize = 30.sp)
//                }
//                Spacer(modifier = Modifier.width(16.dp))
//                Column(modifier = Modifier.weight(1f)) {
//                    Text(appState.userName, fontSize = 20.sp, fontWeight = FontWeight.Bold)
//                    Spacer(modifier = Modifier.height(4.dp))
//                    Text("与 ${appState.selectedCharacter.displayName} 同行", fontSize = 14.sp, color = Color.Gray)
//                }
//            }
//        }
//
//        Spacer(modifier = Modifier.height(32.dp))
//
//        // 2. 我的健康数据
//        SectionTitle("我的健康数据")
//        SettingsGroup {
//            val genderText = when(appState.genderIndex) { 0 -> "男"; 1 -> "女"; else -> "其他" }
//            val dateStr = SimpleDateFormat("yyyy年MM月", Locale.getDefault()).format(Date(appState.diagnosisDate))
//
//            DataRowItem("性别", genderText)
//            HorizontalDivider(modifier = Modifier.padding(start = 16.dp), thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.5f))
//            DataRowItem("年龄", "${appState.age} 岁")
//            HorizontalDivider(modifier = Modifier.padding(start = 16.dp), thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.5f))
//            DataRowItem("身高 / 体重", "${appState.height} cm / ${appState.weight} kg")
//            HorizontalDivider(modifier = Modifier.padding(start = 16.dp), thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.5f))
//            DataRowItem("确诊时间", dateStr)
//            HorizontalDivider(modifier = Modifier.padding(start = 16.dp), thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.5f))
//            DataRowItem("使用口服药", if (appState.usesMedication) "是" else "否")
//            HorizontalDivider(modifier = Modifier.padding(start = 16.dp), thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.5f))
//            DataRowItem("使用胰岛素", if (appState.usesInsulin) "是" else "否")
//            HorizontalDivider(modifier = Modifier.padding(start = 16.dp), thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.5f))
//            DataRowItem("近期糖化血红蛋白", "${appState.latestHbA1c}%", showChevron = true)
//        }
//
//        Spacer(modifier = Modifier.height(32.dp))
//
//        // 3. 设备 (Devices)
//        SectionTitle("设备")
//        SettingsGroup {
//            SettingsActionRow(
//                icon = Icons.Default.Favorite, iconTint = Color(0xFF2196F3), iconBg = Color(0xFF2196F3).copy(alpha = 0.1f),
//                title = "CGM 设备", value = if (appState.cgmConnected) "已连接" else "未连接", onClick = onNavigateToDevice
//            )
//            HorizontalDivider(modifier = Modifier.padding(start = 56.dp), thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.5f))
//            SettingsActionRow(
//                icon = Icons.Default.CheckCircle, iconTint = Color(0xFFFF9800), iconBg = Color(0xFFFF9800).copy(alpha = 0.1f),
//                title = "智能手表", value = "已连接", onClick = { }
//            )
//            HorizontalDivider(modifier = Modifier.padding(start = 56.dp), thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.5f))
//            SettingsActionRow(
//                icon = Icons.Default.Phone, iconTint = Color(0xFF9E9E9E), iconBg = Color(0xFF9E9E9E).copy(alpha = 0.1f),
//                title = "手机健康", value = "同步已启用", onClick = { }
//            )
//        }
//
//        Spacer(modifier = Modifier.height(32.dp))
//
//        // 4. 通用设置 (General)
//        SectionTitle("通用设置")
//        SettingsGroup {
//            SettingsActionRow(
//                icon = Icons.Default.Person, iconTint = Color(0xFF008080), iconBg = Color(0xFF008080).copy(alpha = 0.1f),
//                title = "角色", value = appState.selectedCharacter.displayName, onClick = { }
//            )
//            HorizontalDivider(modifier = Modifier.padding(start = 56.dp), thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.5f))
//            SettingsToggleRow(
//                icon = Icons.Default.Notifications, iconTint = Color(0xFFFFA500), iconBg = Color(0xFFFFA500).copy(alpha = 0.1f),
//                title = "推送通知", isChecked = appState.notificationsEnabled, onCheckedChange = { appState.notificationsEnabled = it }
//            )
//        }
//
//        Spacer(modifier = Modifier.height(32.dp))
//
//        // 5. 隐私与数据 (Privacy)
//        SectionTitle("隐私与数据")
//        SettingsGroup {
//            SettingsActionRow(
//                icon = Icons.Default.Lock, iconTint = Color(0xFF2196F3), iconBg = Color(0xFF2196F3).copy(alpha = 0.1f),
//                title = "数据权限", onClick = { }
//            )
//            HorizontalDivider(modifier = Modifier.padding(start = 56.dp), thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.5f))
//            SettingsActionRow(
//                icon = Icons.Default.Delete, iconTint = Color(0xFFF44336), iconBg = Color(0xFFF44336).copy(alpha = 0.1f),
//                title = "删除所有数据", onClick = { }
//            )
//            HorizontalDivider(modifier = Modifier.padding(start = 56.dp), thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.5f))
//            SettingsActionRow(
//                icon = Icons.Default.Info, iconTint = Color(0xFF9E9E9E), iconBg = Color(0xFF9E9E9E).copy(alpha = 0.1f),
//                title = "隐私政策", onClick = { }
//            )
//        }
//
//        Spacer(modifier = Modifier.height(32.dp))
//
//        // 6. 退出登录按钮
//        Surface(modifier = Modifier.fillMaxWidth().clickable { appState.isOnboarded = false }, shape = RoundedCornerShape(16.dp), color = Color.White, shadowElevation = 1.dp) {
//            Box(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp), contentAlignment = Alignment.Center) {
//                Text("退出登录", color = Color.Red, fontSize = 17.sp, fontWeight = FontWeight.Medium)
//            }
//        }
//
//        Spacer(modifier = Modifier.height(120.dp))
//    }
//}
//
//// --- 通用UI组件 ---
//@Composable
//fun SectionTitle(title: String) {
//    Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray, modifier = Modifier.padding(start = 16.dp, bottom = 8.dp))
//}
//
//@Composable
//fun SettingsGroup(content: @Composable ColumnScope.() -> Unit) {
//    Surface(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), color = Color.White, shadowElevation = 1.dp) { Column(content = content) }
//}
//
//@Composable
//fun DataRowItem(title: String, value: String, showChevron: Boolean = false) {
//    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 14.dp), verticalAlignment = Alignment.CenterVertically) {
//        Text(title, fontSize = 16.sp, color = Color(0xFF333333), modifier = Modifier.weight(1f))
//        Text(value, fontSize = 16.sp, color = Color.Gray)
//        if (showChevron) { Spacer(modifier = Modifier.width(8.dp)); Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.LightGray, modifier = Modifier.size(18.dp)) }
//    }
//}
//
//@Composable
//fun SettingsActionRow(icon: ImageVector, iconTint: Color, iconBg: Color, title: String, value: String? = null, onClick: () -> Unit) {
//    Row(modifier = Modifier.fillMaxWidth().clickable { onClick() }.padding(horizontal = 16.dp, vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
//        Box(modifier = Modifier.size(32.dp).clip(RoundedCornerShape(8.dp)).background(iconBg), contentAlignment = Alignment.Center) { Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(20.dp)) }
//        Spacer(modifier = Modifier.width(16.dp))
//        Text(title, fontSize = 16.sp, color = Color(0xFF333333), modifier = Modifier.weight(1f))
//        if (value != null) { Text(value, fontSize = 15.sp, color = Color.Gray); Spacer(modifier = Modifier.width(8.dp)) }
//        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.LightGray)
//    }
//}
//
//@Composable
//fun SettingsToggleRow(icon: ImageVector, iconTint: Color, iconBg: Color, title: String, isChecked: Boolean, onCheckedChange: (Boolean) -> Unit) {
//    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
//        Box(modifier = Modifier.size(32.dp).clip(RoundedCornerShape(8.dp)).background(iconBg), contentAlignment = Alignment.Center) { Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(20.dp)) }
//        Spacer(modifier = Modifier.width(16.dp))
//        Text(title, fontSize = 16.sp, color = Color(0xFF333333), modifier = Modifier.weight(1f))
//        Switch(checked = isChecked, onCheckedChange = onCheckedChange, colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF008080), checkedTrackColor = Color(0xFF008080).copy(alpha = 0.5f)))
//    }
//}
package com.withapp.with.ui.screens

import androidx.compose.foundation.BorderStroke // ⬅️ 修复：引入了正确的 BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
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
import com.withapp.with.models.CharacterType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun SettingsView(
    appState: AppState,
    onBack: () -> Unit,
    onNavigateToDevice: () -> Unit = {}
) {
    val scrollState = rememberScrollState()

    // --- 弹窗状态管理 ---
    var infoDialogTitle by remember { mutableStateOf<String?>(null) }
    var infoDialogMessage by remember { mutableStateOf<String?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showCharacterDialog by remember { mutableStateOf(false) }

    // 1. 通用信息提示窗 (智能手表/手机健康/隐私等)
    if (infoDialogTitle != null) {
        AlertDialog(
            onDismissRequest = { infoDialogTitle = null },
            title = { Text(infoDialogTitle!!, fontWeight = FontWeight.Bold, fontSize = 20.sp) },
            text = { Text(infoDialogMessage ?: "", fontSize = 15.sp, color = Color.DarkGray, lineHeight = 22.sp) },
            confirmButton = {
                TextButton(onClick = { infoDialogTitle = null }) {
                    Text("我知道了", color = Color(0xFF008080), fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(16.dp)
        )
    }

    // 2. 删除数据警告窗
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("删除所有数据", fontWeight = FontWeight.Bold, color = Color.Red, fontSize = 20.sp) },
            text = { Text("此操作将永久删除您的所有健康记录、设备绑定状态和对话历史，且无法恢复。您确定要继续吗？", fontSize = 15.sp) },
            confirmButton = {
                TextButton(onClick = {
                    showDeleteDialog = false
                    appState.isOnboarded = false // 模拟删除并退出
                }) {
                    Text("确认删除", color = Color.Red, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("取消", color = Color.Gray, fontSize = 16.sp)
                }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(16.dp)
        )
    }

    // 3. 角色切换面板
    if (showCharacterDialog) {
        AlertDialog(
            onDismissRequest = { showCharacterDialog = false },
            title = { Text("切换伙伴", fontWeight = FontWeight.Bold, fontSize = 20.sp) },
            text = {
                Row(
                    modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CharacterType.entries.forEach { character ->
                        val isSelected = appState.selectedCharacter == character
                        Surface(
                            modifier = Modifier.clickable {
                                appState.selectedCharacter = character
                                showCharacterDialog = false
                            },
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) Color(0xFF008080).copy(alpha = 0.1f) else Color.Transparent,
                            border = if (isSelected) BorderStroke(2.dp, Color(0xFF008080)) else null // ⬅️ 修复：使用正确的 BorderStroke
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(character.emoji, fontSize = 36.sp)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(character.displayName, fontSize = 12.sp, color = if (isSelected) Color(0xFF008080) else Color.Gray, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal)
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showCharacterDialog = false }) {
                    Text("关闭", color = Color.Gray)
                }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(16.dp)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC)) // iOS 灰色背景
            .verticalScroll(scrollState)
            .statusBarsPadding()
            .padding(horizontal = 20.dp)
    ) {
        // 顶部返回按钮与标题
        Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack, modifier = Modifier.size(32.dp).offset(x = (-8).dp)) {
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Back", tint = Color.Gray, modifier = Modifier.size(30.dp))
            }
        }

        Text("设置", fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))

        // 1. 个人资料卡片
        Surface(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), color = Color.White, shadowElevation = 1.dp) {
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(60.dp).clip(CircleShape).background(Color(0xFF008080).copy(alpha = 0.1f)), contentAlignment = Alignment.Center) {
                    Text(appState.selectedCharacter.emoji, fontSize = 30.sp)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(appState.userName, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("与 ${appState.selectedCharacter.displayName} 同行", fontSize = 14.sp, color = Color.Gray)
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // 2. 我的健康数据
        SectionTitle("我的健康数据")
        SettingsGroup {
            val genderText = when(appState.genderIndex) { 0 -> "男"; 1 -> "女"; else -> "其他" }
            val dateStr = SimpleDateFormat("yyyy年MM月", Locale.getDefault()).format(Date(appState.diagnosisDate))

            DataRowItem("性别", genderText)
            HorizontalDivider(modifier = Modifier.padding(start = 16.dp), thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.5f))
            DataRowItem("年龄", "${appState.age} 岁")
            HorizontalDivider(modifier = Modifier.padding(start = 16.dp), thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.5f))
            DataRowItem("身高 / 体重", "${appState.height} cm / ${appState.weight} kg")
            HorizontalDivider(modifier = Modifier.padding(start = 16.dp), thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.5f))
            DataRowItem("确诊时间", dateStr)
            HorizontalDivider(modifier = Modifier.padding(start = 16.dp), thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.5f))
            DataRowItem("使用口服药", if (appState.usesMedication) "是" else "否")
            HorizontalDivider(modifier = Modifier.padding(start = 16.dp), thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.5f))
            DataRowItem("使用胰岛素", if (appState.usesInsulin) "是" else "否")
            HorizontalDivider(modifier = Modifier.padding(start = 16.dp), thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.5f))
            DataRowItem("近期糖化血红蛋白", "${appState.latestHbA1c}%")
        }

        Spacer(modifier = Modifier.height(32.dp))

        // 3. 设备 (Devices)
        SectionTitle("设备")
        SettingsGroup {
            SettingsActionRow(
                icon = Icons.Default.Favorite, iconTint = Color(0xFF2196F3), iconBg = Color(0xFF2196F3).copy(alpha = 0.1f),
                title = "CGM 设备", value = if (appState.cgmConnected) "已连接" else "未连接",
                onClick = onNavigateToDevice // 触发真实跳转
            )
            HorizontalDivider(modifier = Modifier.padding(start = 56.dp), thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.5f))
            SettingsActionRow(
                icon = Icons.Default.CheckCircle, iconTint = Color(0xFFFF9800), iconBg = Color(0xFFFF9800).copy(alpha = 0.1f),
                title = "智能手表", value = "已连接",
                onClick = {
                    infoDialogTitle = "智能手表连接"
                    infoDialogMessage = "您的 Apple Watch / Wear OS 设备已成功连接。With 正在后台安全地读取您的实时心率和日常活动数据。"
                }
            )
            HorizontalDivider(modifier = Modifier.padding(start = 56.dp), thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.5f))
            SettingsActionRow(
                icon = Icons.Default.Phone, iconTint = Color(0xFF9E9E9E), iconBg = Color(0xFF9E9E9E).copy(alpha = 0.1f),
                title = "手机健康", value = "同步已启用",
                onClick = {
                    infoDialogTitle = "手机健康数据同步"
                    infoDialogMessage = "健康数据同步已激活。With 已获得读取步数、睡眠质量和基础体征的权限，帮助您全面评估健康状态。"
                }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // 4. 通用设置 (General)
        SectionTitle("通用设置")
        SettingsGroup {
            SettingsActionRow(
                icon = Icons.Default.Person, iconTint = Color(0xFF008080), iconBg = Color(0xFF008080).copy(alpha = 0.1f),
                title = "角色", value = appState.selectedCharacter.displayName,
                onClick = { showCharacterDialog = true } // 触发角色选择弹窗
            )
            HorizontalDivider(modifier = Modifier.padding(start = 56.dp), thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.5f))
            SettingsToggleRow(
                icon = Icons.Default.Notifications, iconTint = Color(0xFFFFA500), iconBg = Color(0xFFFFA500).copy(alpha = 0.1f),
                title = "推送通知", isChecked = appState.notificationsEnabled,
                onCheckedChange = { appState.notificationsEnabled = it }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // 5. 隐私与数据 (Privacy)
        SectionTitle("隐私与数据")
        SettingsGroup {
            SettingsActionRow(
                icon = Icons.Default.Lock, iconTint = Color(0xFF2196F3), iconBg = Color(0xFF2196F3).copy(alpha = 0.1f),
                title = "数据权限",
                onClick = {
                    infoDialogTitle = "数据权限管理"
                    infoDialogMessage = "您对自己的健康数据拥有 100% 的控制权。您随时可以在手机的系统设置中，关闭 With App 对各项传感器的访问权限。"
                }
            )
            HorizontalDivider(modifier = Modifier.padding(start = 56.dp), thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.5f))
            SettingsActionRow(
                icon = Icons.Default.Delete, iconTint = Color(0xFFF44336), iconBg = Color(0xFFF44336).copy(alpha = 0.1f),
                title = "删除所有数据",
                onClick = { showDeleteDialog = true } // 触发危险警告弹窗
            )
            HorizontalDivider(modifier = Modifier.padding(start = 56.dp), thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.5f))
            SettingsActionRow(
                icon = Icons.Default.Info, iconTint = Color(0xFF9E9E9E), iconBg = Color(0xFF9E9E9E).copy(alpha = 0.1f),
                title = "隐私政策",
                onClick = {
                    infoDialogTitle = "隐私政策声明"
                    infoDialogMessage = "With 承诺严格保护您的隐私。您的所有健康指标、CGM 数据和对话记录均经过端到端加密存储。我们绝不会将您的个人数据用于商业推销或出售给第三方。"
                }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // 6. 退出登录按钮
        Surface(modifier = Modifier.fillMaxWidth().clickable { appState.isOnboarded = false }, shape = RoundedCornerShape(16.dp), color = Color.White, shadowElevation = 1.dp) {
            Box(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp), contentAlignment = Alignment.Center) {
                Text("退出登录", color = Color.Red, fontSize = 17.sp, fontWeight = FontWeight.Medium)
            }
        }

        Spacer(modifier = Modifier.height(120.dp))
    }
}

// --- 通用UI组件 ---
@Composable
fun SectionTitle(title: String) {
    Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray, modifier = Modifier.padding(start = 16.dp, bottom = 8.dp))
}

@Composable
fun SettingsGroup(content: @Composable ColumnScope.() -> Unit) {
    Surface(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), color = Color.White, shadowElevation = 1.dp) { Column(content = content) }
}

@Composable
fun DataRowItem(title: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 14.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(title, fontSize = 16.sp, color = Color(0xFF333333), modifier = Modifier.weight(1f))
        Text(value, fontSize = 16.sp, color = Color.Gray)
    }
}

@Composable
fun SettingsActionRow(icon: ImageVector, iconTint: Color, iconBg: Color, title: String, value: String? = null, onClick: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().clickable { onClick() }.padding(horizontal = 16.dp, vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.size(32.dp).clip(RoundedCornerShape(8.dp)).background(iconBg), contentAlignment = Alignment.Center) { Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(20.dp)) }
        Spacer(modifier = Modifier.width(16.dp))
        Text(title, fontSize = 16.sp, color = Color(0xFF333333), modifier = Modifier.weight(1f))
        if (value != null) { Text(value, fontSize = 15.sp, color = Color.Gray); Spacer(modifier = Modifier.width(8.dp)) }
        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.LightGray)
    }
}

@Composable
fun SettingsToggleRow(icon: ImageVector, iconTint: Color, iconBg: Color, title: String, isChecked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.size(32.dp).clip(RoundedCornerShape(8.dp)).background(iconBg), contentAlignment = Alignment.Center) { Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(20.dp)) }
        Spacer(modifier = Modifier.width(16.dp))
        Text(title, fontSize = 16.sp, color = Color(0xFF333333), modifier = Modifier.weight(1f))
        Switch(checked = isChecked, onCheckedChange = onCheckedChange, colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF008080), checkedTrackColor = Color(0xFF008080).copy(alpha = 0.5f)))
    }
}