
package com.withapp.with.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
fun OnboardingView(
    onComplete: () -> Unit
) {
    val scrollState = rememberScrollState()

    // State variables for user inputs
    var age by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var hba1c by remember { mutableStateOf("") }
    var usesMedication by remember { mutableStateOf(false) }
    var currentMood by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F9FA))
            .verticalScroll(scrollState)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        // OB-01 & OB-02: De-stigmatized titles
        Text("With", fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Text("你的每日健康陪伴", fontSize = 18.sp, fontWeight = FontWeight.Medium, color = Color(0xFF008080), modifier = Modifier.padding(top = 8.dp))
        Text("为你量身定制的情感支持、洞见与指导。", fontSize = 14.sp, color = Color.Gray, modifier = Modifier.padding(top = 8.dp, bottom = 32.dp), textAlign = androidx.compose.ui.text.style.TextAlign.Center)

        // Form Section 1: Basic Demographic Info
        Text("基本信息 (可选)", fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.Start).padding(bottom = 8.dp))
        OutlinedTextField(value = age, onValueChange = { age = it }, label = { Text("年龄") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(value = height, onValueChange = { height = it }, label = { Text("身高 (cm)") }, modifier = Modifier.weight(1f))
            OutlinedTextField(value = weight, onValueChange = { weight = it }, label = { Text("体重 (kg)") }, modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Form Section 2: Clinical Baseline
        Text("健康档案 (可选)", fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.Start).padding(bottom = 8.dp))
        OutlinedTextField(value = hba1c, onValueChange = { hba1c = it }, label = { Text("最近一次 HbA1c (%)") }, modifier = Modifier.fillMaxWidth())
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
            Text("是否使用药物或胰岛素？", modifier = Modifier.weight(1f), fontSize = 14.sp)
            Switch(checked = usesMedication, onCheckedChange = { usesMedication = it })
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Form Section 3: Emotional Baseline
        Text("现在的心情 (可选)", fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.Start).padding(bottom = 8.dp))
        OutlinedTextField(value = currentMood, onValueChange = { currentMood = it }, label = { Text("比如：感觉压力很大，或者很放松") }, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(40.dp))

        // Action Buttons
        Button(
            onClick = onComplete,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF008080))
        ) {
            Text("继续", fontSize = 16.sp)
        }

        TextButton(
            onClick = onComplete, // "Skip" also proceeds to the next screen
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("跳过，以后再填", color = Color.Gray)
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}