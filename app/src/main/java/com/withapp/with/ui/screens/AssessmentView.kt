package com.withapp.with.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssessmentView(onDismiss: () -> Unit) {
    var q1 by remember { mutableFloatStateOf(5f) }
    var q2 by remember { mutableStateOf("") }
    var q3 by remember { mutableStateOf("") }
    var showThankYou by remember { mutableStateOf(false) }


    if (showThankYou) {
        AlertDialog(
            onDismissRequest = {
                showThankYou = false
                onDismiss()
            },
            title = { Text("感谢提交", fontWeight = FontWeight.Bold) },
            text = { Text("你的反馈已记录，With 会陪你一起变得更好。") },
            confirmButton = {
                TextButton(onClick = {
                    showThankYou = false
                    onDismiss()
                }) {
                    Text("确定", color = Color(0xFF008080), fontWeight = FontWeight.Bold)
                }
            },
            containerColor = Color.White
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("健康评估", fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Cancel")
                    }
                },
                actions = {
                    TextButton(onClick = { showThankYou = true }) {
                        Text("完成", color = Color(0xFF008080), fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFF8FAFC))
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8FAFC))
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            AssessmentSection(title = "1. 你今天整体感觉如何？") {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("差", color = Color.Gray, fontSize = 14.sp)
                        Slider(
                            value = q1,
                            onValueChange = { q1 = it },
                            valueRange = 1f..10f,
                            steps = 8,
                            modifier = Modifier.weight(1f).padding(horizontal = 12.dp),
                            colors = SliderDefaults.colors(
                                thumbColor = Color(0xFF008080),
                                activeTrackColor = Color(0xFF008080)
                            )
                        )
                        Text("极好", color = Color.Gray, fontSize = 14.sp)
                    }
                    Text(
                        text = "当前选择: ${q1.toInt()}",
                        color = Color.Gray,
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }


            AssessmentSection(title = "2. 今天有什么特别的感受吗？") {
                OutlinedTextField(
                    value = q2,
                    onValueChange = { q2 = it },
                    placeholder = { Text("简要描述…", color = Color.LightGray) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF008080),
                        unfocusedBorderColor = Color.LightGray,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    )
                )
            }


            AssessmentSection(title = "3. 任何其他的想法或记录？") {
                OutlinedTextField(
                    value = q3,
                    onValueChange = { q3 = it },
                    modifier = Modifier.fillMaxWidth().height(120.dp), // 增加高度变为多行框
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF008080),
                        unfocusedBorderColor = Color.LightGray,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    )
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}


@Composable
fun AssessmentSection(title: String, content: @Composable () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(title, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray)
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            color = Color.White,
            shadowElevation = 1.dp
        ) {
            Box(modifier = Modifier.padding(16.dp)) {
                content()
            }
        }
    }
}