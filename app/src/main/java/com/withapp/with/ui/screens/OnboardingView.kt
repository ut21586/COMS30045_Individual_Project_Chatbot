package com.withapp.with.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.withapp.with.models.AppState

@Composable
fun OnboardingView(appState: AppState) {
    // Stage 0: Intro Slides, Stage 1: Name, Stage 2: Diabetes Type, Stage 3: Permissions
    var currentStage by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F9F7))
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        AnimatedContent(
            targetState = currentStage,
            transitionSpec = {
                slideInHorizontally { it } + fadeIn() togetherWith slideOutHorizontally { -it } + fadeOut()
            },
            label = "onboarding_stages"
        ) { stage ->
            when (stage) {
                0 -> IntroSlides { currentStage = 1 }
                1 -> NameInputStage(
                    currentName = appState.userName,
                    onNameChange = { appState.userName = it },
                    onNext = { currentStage = 2 }
                )
                2 -> DiabetesTypeStage(
                    selectedType = appState.diabetesType,
                    onTypeSelect = { appState.diabetesType = it },
                    onNext = { currentStage = 3 }
                )
                3 -> PermissionStage(onComplete = { appState.hasCompletedOnboarding = true })
            }
        }
    }
}

@Composable
fun IntroSlides(onNext: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("👋", fontSize = 80.sp)
        Spacer(modifier = Modifier.height(40.dp))
        Text("你好，我是 With", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Text("我与你同在。", textAlign = TextAlign.Center, color = Color.Gray)
        Spacer(modifier = Modifier.height(60.dp))
        Button(
            onClick = onNext,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF008080)),
            shape = RoundedCornerShape(28.dp)
        ) {
            Text("开始我的旅程", color = Color.White, fontSize = 18.sp)
        }
    }
}

@Composable
fun NameInputStage(currentName: String, onNameChange: (String) -> Unit, onNext: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(40.dp), verticalArrangement = Arrangement.Center) {
        Text("我该怎么称呼你？", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(30.dp))
        OutlinedTextField(
            value = currentName,
            onValueChange = onNameChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("请输入你的昵称") },
            shape = RoundedCornerShape(16.dp),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = onNext,
            enabled = currentName.isNotBlank(),
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF008080))
        ) {
            Text("下一步")
        }
    }
}

@Composable
fun DiabetesTypeStage(selectedType: String, onTypeSelect: (String) -> Unit, onNext: () -> Unit) {
    val types = listOf("1型糖尿病", "2型糖尿病", "妊娠糖尿病", "糖尿病前期")
    Column(modifier = Modifier.fillMaxSize().padding(30.dp), verticalArrangement = Arrangement.Center) {
        Text("你的糖尿病类型是？", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))
        types.forEach { type ->
            val isSelected = selectedType == type
            Surface(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).clickable { onTypeSelect(type) },
                shape = RoundedCornerShape(16.dp),
                color = if (isSelected) Color(0xFF008080) else Color.White,
                shadowElevation = 2.dp
            ) {
                Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(type, color = if (isSelected) Color.White else Color.Black, modifier = Modifier.weight(1f))
                    if (isSelected) Icon(Icons.Default.Check, null, tint = Color.White)
                }
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(onClick = onNext, modifier = Modifier.fillMaxWidth().height(56.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF008080))) {
            Text("确认选择")
        }
    }
}

@Composable
fun PermissionStage(onComplete: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(40.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("🧪", fontSize = 60.sp)
        Spacer(modifier = Modifier.height(30.dp))
        Text("连接健康数据", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Text("With 需要读取你的步数和血糖数据，以便为你提供更精准的洞察。这相当于 iOS 上的健康连接。", textAlign = TextAlign.Center, color = Color.Gray)
        Spacer(modifier = Modifier.height(40.dp))
        Button(onClick = onComplete, modifier = Modifier.fillMaxWidth().height(56.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF008080))) {
            Text("开启连接")
        }
    }
}