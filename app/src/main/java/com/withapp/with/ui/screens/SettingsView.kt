package com.withapp.with.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
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

    // 换成 .value 写法，彻底消灭 AS 的黄线警告 Bug
    val infoDialogTitle = remember { mutableStateOf<String?>(null) }
    val infoDialogMessage = remember { mutableStateOf<String?>(null) }
    val showDeleteDialog = remember { mutableStateOf(false) }
    val showCharacterDialog = remember { mutableStateOf(false) }

    val editField = remember { mutableStateOf<String?>(null) }
    val tempAge = remember { mutableStateOf("") }
    val tempHeight = remember { mutableStateOf("") }
    val tempWeight = remember { mutableStateOf("") }
    val tempHbA1c = remember { mutableStateOf("") }

    if (infoDialogTitle.value != null) {
        AlertDialog(
            onDismissRequest = { infoDialogTitle.value = null },
            title = { Text(infoDialogTitle.value!!, fontWeight = FontWeight.Bold, fontSize = 20.sp) },
            text = { Text(infoDialogMessage.value ?: "", fontSize = 15.sp, color = Color.DarkGray, lineHeight = 22.sp) },
            confirmButton = { TextButton(onClick = { infoDialogTitle.value = null }) { Text("我知道了", color = Color(0xFF008080), fontWeight = FontWeight.Bold) } },
            containerColor = Color.White, shape = RoundedCornerShape(16.dp)
        )
    }

    if (showDeleteDialog.value) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog.value = false },
            title = { Text("删除所有数据", fontWeight = FontWeight.Bold, color = Color.Red, fontSize = 20.sp) },
            text = { Text("此操作将永久删除您的所有健康记录、设备绑定状态和对话历史，且无法恢复。您确定要继续吗？", fontSize = 15.sp) },
            confirmButton = { TextButton(onClick = { showDeleteDialog.value = false; appState.isOnboarded = false }) { Text("确认删除", color = Color.Red, fontWeight = FontWeight.Bold) } },
            dismissButton = { TextButton(onClick = { showDeleteDialog.value = false }) { Text("取消", color = Color.Gray) } },
            containerColor = Color.White, shape = RoundedCornerShape(16.dp)
        )
    }

    if (showCharacterDialog.value) {
        AlertDialog(
            onDismissRequest = { showCharacterDialog.value = false },
            title = { Text("切换伙伴", fontWeight = FontWeight.Bold, fontSize = 20.sp) },
            text = {
                Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    CharacterType.entries.forEach { character ->
                        val isSelected = appState.selectedCharacter == character
                        Surface(
                            modifier = Modifier.clickable { appState.selectedCharacter = character; showCharacterDialog.value = false },
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) Color(0xFF008080).copy(alpha = 0.1f) else Color.Transparent,
                            border = if (isSelected) BorderStroke(2.dp, Color(0xFF008080)) else null
                        ) {
                            Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(character.emoji, fontSize = 36.sp)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(character.displayName, fontSize = 12.sp, color = if (isSelected) Color(0xFF008080) else Color.Gray, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal)
                            }
                        }
                    }
                }
            },
            confirmButton = { TextButton(onClick = { showCharacterDialog.value = false }) { Text("关闭", color = Color.Gray) } },
            containerColor = Color.White, shape = RoundedCornerShape(16.dp)
        )
    }

    if (editField.value != null) {
        AlertDialog(
            onDismissRequest = { editField.value = null },
            title = {
                val titleStr = when(editField.value) {
                    "gender" -> "选择性别"
                    "age" -> "修改年龄"
                    "hw" -> "修改身高与体重"
                    "hba1c" -> "修改糖化血红蛋白"
                    else -> ""
                }
                Text(titleStr, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    when (editField.value) {
                        "gender" -> {
                            listOf("男", "女", "其他").forEachIndexed { index, label ->
                                OutlinedButton(onClick = { appState.genderIndex = index; editField.value = null }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF008080))) { Text(label, fontSize = 16.sp) }
                            }
                        }
                        "age" -> OutlinedTextField(value = tempAge.value, onValueChange = { tempAge.value = it }, label = { Text("年龄") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
                        "hw" -> {
                            OutlinedTextField(value = tempHeight.value, onValueChange = { tempHeight.value = it }, label = { Text("身高 (cm)") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
                            OutlinedTextField(value = tempWeight.value, onValueChange = { tempWeight.value = it }, label = { Text("体重 (kg)") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
                        }
                        "hba1c" -> OutlinedTextField(value = tempHbA1c.value, onValueChange = { tempHbA1c.value = it }, label = { Text("糖化血红蛋白 (%)") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal), modifier = Modifier.fillMaxWidth())
                    }
                }
            },
            confirmButton = {
                if (editField.value != "gender") {
                    TextButton(onClick = {
                        when (editField.value) {
                            "age" -> appState.age = tempAge.value
                            "hw" -> { appState.height = tempHeight.value; appState.weight = tempWeight.value }
                            "hba1c" -> appState.latestHbA1c = tempHbA1c.value
                        }
                        editField.value = null
                    }) { Text("保存", color = Color(0xFF008080), fontWeight = FontWeight.Bold) }
                }
            },
            dismissButton = { TextButton(onClick = { editField.value = null }) { Text("取消", color = Color.Gray) } },
            containerColor = Color.White, shape = RoundedCornerShape(16.dp)
        )
    }

    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFFF8FAFC)).verticalScroll(scrollState).statusBarsPadding().padding(horizontal = 20.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack, modifier = Modifier.size(32.dp).offset(x = (-8).dp)) {
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Back", tint = Color.Gray, modifier = Modifier.size(30.dp))
            }
        }

        Text("设置", fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))

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

        SectionTitle("我的健康数据")
        SettingsGroup {
            val genderText = when(appState.genderIndex) { 0 -> "男"; 1 -> "女"; else -> "其他" }
            val dateStr = SimpleDateFormat("yyyy年MM月", Locale.getDefault()).format(Date(appState.diagnosisDate))

            DataRowItem("性别", genderText) { editField.value = "gender" }
            HorizontalDivider(modifier = Modifier.padding(start = 16.dp), thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.5f))
            DataRowItem("年龄", "${appState.age} 岁") { tempAge.value = appState.age; editField.value = "age" }
            HorizontalDivider(modifier = Modifier.padding(start = 16.dp), thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.5f))
            DataRowItem("身高 / 体重", "${appState.height} cm / ${appState.weight} kg") { tempHeight.value = appState.height; tempWeight.value = appState.weight; editField.value = "hw" }
            HorizontalDivider(modifier = Modifier.padding(start = 16.dp), thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.5f))
            DataRowItem("确诊时间", dateStr) { infoDialogTitle.value = "确诊时间"; infoDialogMessage.value = "确诊时间目前不支持在应用内修改。" }
            HorizontalDivider(modifier = Modifier.padding(start = 16.dp), thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.5f))
            DataRowItem("使用口服药", if (appState.usesMedication) "是" else "否") { appState.usesMedication = !appState.usesMedication }
            HorizontalDivider(modifier = Modifier.padding(start = 16.dp), thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.5f))
            DataRowItem("使用胰岛素", if (appState.usesInsulin) "是" else "否") { appState.usesInsulin = !appState.usesInsulin }
            HorizontalDivider(modifier = Modifier.padding(start = 16.dp), thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.5f))
            DataRowItem("近期糖化血红蛋白", "${appState.latestHbA1c}%") { tempHbA1c.value = appState.latestHbA1c; editField.value = "hba1c" }
        }

        Spacer(modifier = Modifier.height(32.dp))

        SectionTitle("设备")
        SettingsGroup {
            SettingsActionRow(icon = Icons.Default.Favorite, iconTint = Color(0xFF2196F3), iconBg = Color(0xFF2196F3).copy(alpha = 0.1f), title = "CGM 设备", value = if (appState.cgmConnected) "已连接" else "未连接", onClick = onNavigateToDevice)
            HorizontalDivider(modifier = Modifier.padding(start = 56.dp), thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.5f))
            SettingsActionRow(icon = Icons.Default.CheckCircle, iconTint = Color(0xFFFF9800), iconBg = Color(0xFFFF9800).copy(alpha = 0.1f), title = "智能手表", value = "已连接", onClick = { infoDialogTitle.value = "智能手表连接"; infoDialogMessage.value = "您的 Apple Watch / Wear OS 设备已成功连接。" })
            HorizontalDivider(modifier = Modifier.padding(start = 56.dp), thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.5f))
            SettingsActionRow(icon = Icons.Default.Phone, iconTint = Color(0xFF9E9E9E), iconBg = Color(0xFF9E9E9E).copy(alpha = 0.1f), title = "手机健康", value = "同步已启用", onClick = { infoDialogTitle.value = "手机健康数据同步"; infoDialogMessage.value = "健康数据同步已激活。" })
        }

        Spacer(modifier = Modifier.height(32.dp))

        SectionTitle("通用设置")
        SettingsGroup {
            SettingsActionRow(icon = Icons.Default.Person, iconTint = Color(0xFF008080), iconBg = Color(0xFF008080).copy(alpha = 0.1f), title = "角色", value = appState.selectedCharacter.displayName, onClick = { showCharacterDialog.value = true })
            HorizontalDivider(modifier = Modifier.padding(start = 56.dp), thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.5f))
            SettingsToggleRow(icon = Icons.Default.Notifications, iconTint = Color(0xFFFFA500), iconBg = Color(0xFFFFA500).copy(alpha = 0.1f), title = "推送通知", isChecked = appState.notificationsEnabled, onCheckedChange = { appState.notificationsEnabled = it })
        }

        Spacer(modifier = Modifier.height(32.dp))

        SectionTitle("隐私与数据")
        SettingsGroup {
            SettingsActionRow(icon = Icons.Default.Lock, iconTint = Color(0xFF2196F3), iconBg = Color(0xFF2196F3).copy(alpha = 0.1f), title = "数据权限", onClick = { infoDialogTitle.value = "数据权限管理"; infoDialogMessage.value = "您对自己的健康数据拥有 100% 的控制权。" })
            HorizontalDivider(modifier = Modifier.padding(start = 56.dp), thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.5f))
            SettingsActionRow(icon = Icons.Default.Delete, iconTint = Color(0xFFF44336), iconBg = Color(0xFFF44336).copy(alpha = 0.1f), title = "删除所有数据", onClick = { showDeleteDialog.value = true })
            HorizontalDivider(modifier = Modifier.padding(start = 56.dp), thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.5f))
            SettingsActionRow(icon = Icons.Default.Info, iconTint = Color(0xFF9E9E9E), iconBg = Color(0xFF9E9E9E).copy(alpha = 0.1f), title = "隐私政策", onClick = { infoDialogTitle.value = "隐私政策声明"; infoDialogMessage.value = "With 承诺严格保护您的隐私。" })
        }

        Spacer(modifier = Modifier.height(32.dp))

        Surface(modifier = Modifier.fillMaxWidth().clickable { appState.isOnboarded = false }, shape = RoundedCornerShape(16.dp), color = Color.White, shadowElevation = 1.dp) {
            Box(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp), contentAlignment = Alignment.Center) {
                Text("退出登录", color = Color.Red, fontSize = 17.sp, fontWeight = FontWeight.Medium)
            }
        }

        Spacer(modifier = Modifier.height(120.dp))
    }
}

@Composable
fun SectionTitle(title: String) { Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray, modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)) }

@Composable
fun SettingsGroup(content: @Composable ColumnScope.() -> Unit) { Surface(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), color = Color.White, shadowElevation = 1.dp) { Column(content = content) } }

@Composable
fun DataRowItem(title: String, value: String, onClick: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().clickable { onClick() }.padding(horizontal = 16.dp, vertical = 14.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(title, fontSize = 16.sp, color = Color(0xFF333333), modifier = Modifier.weight(1f))
        Text(value, fontSize = 16.sp, color = Color.Gray)
        Spacer(modifier = Modifier.width(8.dp))
        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.LightGray, modifier = Modifier.size(18.dp))
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