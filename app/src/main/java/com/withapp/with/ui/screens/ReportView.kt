package com.withapp.with.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable // ⬅️ 修复了 clickable 找不到的问题
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.withapp.with.viewmodels.ReportViewModel

@Composable
fun ReportView(reportViewModel: ReportViewModel) {
    val scrollState = rememberScrollState()
    var selectedTimePeriod by remember { mutableIntStateOf(0) }
    val periods = listOf("日", "周", "月")
    var reflectiveText by remember { mutableStateOf("") }
    var isRecording by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .verticalScroll(scrollState)
            .statusBarsPadding()
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        // Header
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("健康简报", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Time Period Selector (分段选择器)
        Surface(
            modifier = Modifier.fillMaxWidth().height(40.dp),
            shape = RoundedCornerShape(20.dp),
            color = Color.LightGray.copy(alpha = 0.2f)
        ) {
            Row(modifier = Modifier.fillMaxSize().padding(4.dp)) {
                periods.forEachIndexed { index, title ->
                    val isSelected = selectedTimePeriod == index
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (isSelected) Color.White else Color.Transparent)
                            .clickable { selectedTimePeriod = index },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = title,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (isSelected) Color.Black else Color.Gray
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Insights Section (身心洞察)
        Text("身心洞察", fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(16.dp))

        reportViewModel.insights.forEach { insight ->
            Surface(
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                shadowElevation = 2.dp
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier.size(44.dp).background(Color(0xFF008080).copy(alpha = 0.1f), RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(insight.icon.ifBlank { "💡" }, fontSize = 20.sp)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(insight.title, fontWeight = FontWeight.Bold, color = Color(0xFF333333))
                        Text(insight.description, fontSize = 13.sp, color = Color.Gray, lineHeight = 18.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Reflective Question (反思日志)
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            color = Color(0xFFFFA500).copy(alpha = 0.1f) // 橙色透明背景
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFA500), modifier = Modifier.size(24.dp))

                Text(
                    "今天有什么让你感到感激的小事吗？",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFFFFA500),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )

                // 输入框
                Box(
                    modifier = Modifier.fillMaxWidth().background(Color.White.copy(alpha = 0.85f), RoundedCornerShape(12.dp)).padding(14.dp)
                ) {
                    if (reflectiveText.isEmpty()) {
                        Text("点击记录...", color = Color(0xFFFFA500).copy(alpha = 0.5f))
                    }
                    BasicTextField(
                        value = reflectiveText,
                        onValueChange = { reflectiveText = it },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = androidx.compose.ui.text.TextStyle(color = Color(0xFFFFA500), fontSize = 16.sp)
                    )
                }

                // 工具栏 (录音 / 图片) - ⬅️ 这里修复了 spacing 问题，改为 horizontalArrangement
                Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                    IconButton(onClick = { isRecording = !isRecording }) {
                        Icon(
                            if (isRecording) Icons.Default.Mic else Icons.Default.MicNone,
                            contentDescription = null,
                            tint = if (isRecording) Color.Red else Color.Gray,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    IconButton(onClick = { /* TODO: 打开相册 */ }) {
                        Icon(Icons.Default.Image, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(24.dp))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Timeline (今日时间线)
        Text("今日时间线", fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(16.dp))

        reportViewModel.timelineEvents.forEach { event ->
            Surface(
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                shadowElevation = 2.dp
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.Top) {
                    Text(event.time, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF008080), modifier = Modifier.width(50.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(event.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            if (event.glucoseValue != null) {
                                Column(horizontalAlignment = Alignment.End) {
                                    Text(String.format("%.1f mmol/L", event.glucoseValue / 18.0), fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF008080))
                                    Text(" 3.9-10.0", fontSize = 10.sp, color = Color.Gray)
                                }
                            }
                        }
                        Text(event.description, fontSize = 14.sp, color = Color.Gray)

                        if (event.mood != null && event.emoji != null) {
                            Surface(color = Color(0xFFFFA500).copy(alpha = 0.1f), shape = RoundedCornerShape(8.dp)) {
                                Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Text("MOOD NOTE", fontSize = 10.sp, color = Color(0xFFFFA500), fontWeight = FontWeight.Medium)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("\"${event.mood}\" ${event.emoji}", fontSize = 14.sp)
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(120.dp))
    }
}