//package com.withapp.with.ui.screens
//
//import androidx.compose.animation.*
//import androidx.compose.foundation.BorderStroke // ⬅️ 修复了 BorderStroke 找不到的问题
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.grid.GridCells
//import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
//import androidx.compose.material.icons.filled.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.withapp.with.models.AppState
//import com.withapp.with.models.CharacterType
//import com.withapp.with.ui.components.CharacterView
//import kotlinx.coroutines.delay
//
//@Composable
//fun OnboardingView(appState: AppState) {
//    var currentPage by remember { mutableIntStateOf(0) }
//
//    var userName by remember { mutableStateOf("") }
//    var selectedCharacter by remember { mutableStateOf(CharacterType.ROBOT) }
//    var height by remember { mutableStateOf("") }
//    var weight by remember { mutableStateOf("") }
//    var age by remember { mutableStateOf("") }
//    var genderIndex by remember { mutableIntStateOf(0) }
//    var diagnosisDate by remember { mutableLongStateOf(System.currentTimeMillis()) }
//    var usesMedication by remember { mutableStateOf(false) }
//    var usesInsulin by remember { mutableStateOf(false) }
//    var latestHbA1c by remember { mutableStateOf("") }
//    var moodIndex by remember { mutableIntStateOf(2) }
//    var hasStress by remember { mutableStateOf(false) }
//
//    val bgGradient = Brush.verticalGradient(
//        colors = listOf(Color(0xFFF2F9F7), Color(0xFFE6F2F2))
//    )
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(bgGradient)
//            .statusBarsPadding()
//            .navigationBarsPadding()
//    ) {
//        Column(modifier = Modifier.fillMaxSize()) {
//
//            Box(modifier = Modifier.weight(1f)) {
//                AnimatedContent(
//                    targetState = currentPage,
//                    transitionSpec = {
//                        slideInHorizontally { width -> width } + fadeIn() togetherWith
//                                slideOutHorizontally { width -> -width } + fadeOut()
//                    },
//                    label = "onboarding_pages"
//                ) { page ->
//                    when (page) {
//                        0 -> WelcomePage()
//                        1 -> NameInputPage(userName = userName, onNameChange = { userName = it })
//                        2 -> CharacterPage(selectedCharacter = selectedCharacter, onSelect = { selectedCharacter = it })
//                        3 -> OptionalInfoPage(
//                            height = height, onHeightChange = { height = it },
//                            weight = weight, onWeightChange = { weight = it },
//                            age = age, onAgeChange = { age = it },
//                            genderIndex = genderIndex, onGenderChange = { genderIndex = it },
//                            diagnosisDate = diagnosisDate, onDiagnosisDateChange = { diagnosisDate = it },
//                            usesMedication = usesMedication, onMedicationChange = { usesMedication = it },
//                            usesInsulin = usesInsulin, onInsulinChange = { usesInsulin = it },
//                            latestHbA1c = latestHbA1c, onHbA1cChange = { latestHbA1c = it },
//                            moodIndex = moodIndex, onMoodChange = { moodIndex = it },
//                            hasStress = hasStress, onStressChange = { hasStress = it }
//                        )
//                        4 -> PermissionsPage()
//                        5 -> ConnectDevicePage()
//                        6 -> CompletionPage()
//                    }
//                }
//            }
//
//            // 底部进度点
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(bottom = 20.dp),
//                horizontalArrangement = Arrangement.Center,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                for (i in 0 until 7) {
//                    Box(
//                        modifier = Modifier
//                            .padding(horizontal = 4.dp)
//                            .size(8.dp)
//                            .background(
//                                color = if (currentPage == i) Color(0xFF008080) else Color.Gray.copy(alpha = 0.3f),
//                                shape = CircleShape
//                            )
//                    )
//                }
//            }
//
//            // 底部导航按钮 (⬅️ 修复了 padding 不能混写的问题)
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 40.dp)
//                    .padding(bottom = 40.dp),
//                horizontalArrangement = Arrangement.Center,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                if (currentPage > 0) {
//                    OutlinedButton(
//                        onClick = { currentPage -= 1 },
//                        modifier = Modifier.weight(1f).height(50.dp),
//                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Gray),
//                        border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.3f))
//                    ) {
//                        Text("上一步", fontSize = 16.sp)
//                    }
//                    Spacer(modifier = Modifier.width(16.dp))
//                }
//
//                Button(
//                    onClick = {
//                        if (currentPage == 6) {
//                            appState.userName = userName.ifBlank { "User" }
//                            appState.selectedCharacter = selectedCharacter
//                            appState.applyOnboardingData(height, weight, age, genderIndex, diagnosisDate, usesMedication, usesInsulin, latestHbA1c, moodIndex, hasStress)
//                            appState.isOnboarded = true
//                        } else {
//                            currentPage += 1
//                        }
//                    },
//                    modifier = Modifier.weight(if (currentPage == 0) 1f else 1.5f).height(50.dp),
//                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF008080))
//                ) {
//                    Text(if (currentPage == 6) "开始体验" else "下一步", fontSize = 16.sp, color = Color.White)
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun WelcomePage() {
//    Column(modifier = Modifier.fillMaxSize().padding(40.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
//        Text("With", fontSize = 56.sp, fontWeight = FontWeight.Medium, fontFamily = androidx.compose.ui.text.font.FontFamily.Serif)
//        Text("与你同在", fontSize = 12.sp, color = Color.Gray, letterSpacing = 3.sp)
//        Spacer(modifier = Modifier.height(40.dp))
//        CharacterView(character = CharacterType.ROBOT, isAnimating = true, modifier = Modifier.size(150.dp))
//        Spacer(modifier = Modifier.height(40.dp))
//        Text("欢迎来到 With", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
//        Spacer(modifier = Modifier.height(12.dp))
//        Text("我是你的专属 AI 健康伙伴", fontSize = 16.sp, color = Color.Gray, textAlign = TextAlign.Center)
//    }
//}
//
//@Composable
//fun NameInputPage(userName: String, onNameChange: (String) -> Unit) {
//    Column(modifier = Modifier.fillMaxSize().padding(40.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
//        Text("我该怎么称呼你？", fontSize = 28.sp, fontWeight = FontWeight.Bold)
//        Spacer(modifier = Modifier.height(16.dp))
//        Text("这能让我们的对话更亲切", fontSize = 16.sp, color = Color.Gray, textAlign = TextAlign.Center)
//        Spacer(modifier = Modifier.height(40.dp))
//        OutlinedTextField(
//            value = userName,
//            onValueChange = onNameChange,
//            placeholder = { Text("输入你的名字或昵称", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center) },
//            modifier = Modifier.fillMaxWidth().background(Color.White, RoundedCornerShape(12.dp)),
//            shape = RoundedCornerShape(12.dp),
//            singleLine = true,
//            colors = TextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White, focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent)
//        )
//    }
//}
//
//@Composable
//fun CharacterPage(selectedCharacter: CharacterType, onSelect: (CharacterType) -> Unit) {
//    Column(modifier = Modifier.fillMaxSize().padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
//        Text("选择你的伙伴", fontSize = 28.sp, fontWeight = FontWeight.Bold)
//        Spacer(modifier = Modifier.height(16.dp))
//        Text("它将每天陪伴你的健康旅程", fontSize = 16.sp, color = Color.Gray, textAlign = TextAlign.Center)
//        Spacer(modifier = Modifier.height(40.dp))
//
//        LazyVerticalGrid(columns = GridCells.Fixed(3), horizontalArrangement = Arrangement.spacedBy(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
//            val characters = CharacterType.entries
//            items(characters.size) { index ->
//                val character = characters[index]
//                val isSelected = selectedCharacter == character
//                Surface(
//                    modifier = Modifier.clickable { onSelect(character) },
//                    shape = RoundedCornerShape(16.dp),
//                    color = Color.White,
//                    shadowElevation = if (isSelected) 8.dp else 2.dp,
//                    border = if (isSelected) BorderStroke(2.dp, Color(0xFF008080)) else null
//                ) {
//                    Column(modifier = Modifier.padding(vertical = 20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
//                        Text(character.emoji, fontSize = 40.sp)
//                        Spacer(modifier = Modifier.height(8.dp))
//                        Text(character.displayName, fontSize = 14.sp, fontWeight = FontWeight.Medium)
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun OptionalInfoPage(
//    height: String, onHeightChange: (String) -> Unit, weight: String, onWeightChange: (String) -> Unit, age: String, onAgeChange: (String) -> Unit,
//    genderIndex: Int, onGenderChange: (Int) -> Unit, diagnosisDate: Long, onDiagnosisDateChange: (Long) -> Unit,
//    usesMedication: Boolean, onMedicationChange: (Boolean) -> Unit, usesInsulin: Boolean, onInsulinChange: (Boolean) -> Unit,
//    latestHbA1c: String, onHbA1cChange: (String) -> Unit, moodIndex: Int, onMoodChange: (Int) -> Unit, hasStress: Boolean, onStressChange: (Boolean) -> Unit
//) {
//    val scrollState = rememberScrollState()
//    Column(modifier = Modifier.fillMaxSize().padding(20.dp).verticalScroll(scrollState), horizontalAlignment = Alignment.CenterHorizontally) {
//        Spacer(modifier = Modifier.height(40.dp))
//        Text("完善身体档案", fontSize = 28.sp, fontWeight = FontWeight.Bold)
//        Spacer(modifier = Modifier.height(8.dp))
//        Text("这些信息将帮助 With 提供更精准的建议\n你可以随时在设置中修改", fontSize = 14.sp, color = Color.Gray, textAlign = TextAlign.Center)
//        Spacer(modifier = Modifier.height(30.dp))
//
//        Surface(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), color = Color.White, shadowElevation = 2.dp) {
//            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
//                OutlinedTextField(value = height, onValueChange = onHeightChange, label = { Text("身高 (cm)") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
//                OutlinedTextField(value = weight, onValueChange = onWeightChange, label = { Text("体重 (kg)") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
//                OutlinedTextField(value = age, onValueChange = onAgeChange, label = { Text("年龄") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Text("性别 (0男/1女/2其他): $genderIndex", modifier = Modifier.weight(1f), fontSize = 16.sp)
//                    Slider(value = genderIndex.toFloat(), onValueChange = { onGenderChange(it.toInt()) }, valueRange = 0f..2f, steps = 1, modifier = Modifier.width(100.dp))
//                }
//            }
//        }
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Surface(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), color = Color.White, shadowElevation = 2.dp) {
//            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
//                OutlinedTextField(value = diagnosisDate.toString(), onValueChange = { onDiagnosisDateChange(it.toLongOrNull() ?: diagnosisDate) }, label = { Text("确诊时间 (时间戳)") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Text("使用口服药", modifier = Modifier.weight(1f), fontSize = 16.sp)
//                    Switch(checked = usesMedication, onCheckedChange = onMedicationChange, colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF008080), checkedTrackColor = Color(0xFF008080).copy(alpha = 0.5f)))
//                }
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Text("使用胰岛素", modifier = Modifier.weight(1f), fontSize = 16.sp)
//                    Switch(checked = usesInsulin, onCheckedChange = onInsulinChange, colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF008080), checkedTrackColor = Color(0xFF008080).copy(alpha = 0.5f)))
//                }
//                OutlinedTextField(value = latestHbA1c, onValueChange = onHbA1cChange, label = { Text("近期糖化血红蛋白 (%)") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal), modifier = Modifier.fillMaxWidth())
//            }
//        }
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Surface(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), color = Color.White, shadowElevation = 2.dp) {
//            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Text("心情指数: $moodIndex", modifier = Modifier.weight(1f), fontSize = 16.sp)
//                    Slider(value = moodIndex.toFloat(), onValueChange = { onMoodChange(it.toInt()) }, valueRange = 0f..4f, steps = 3, modifier = Modifier.width(100.dp))
//                }
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Text("近期感到压力大", modifier = Modifier.weight(1f), fontSize = 16.sp)
//                    Switch(checked = hasStress, onCheckedChange = onStressChange, colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF008080), checkedTrackColor = Color(0xFF008080).copy(alpha = 0.5f)))
//                }
//            }
//        }
//        Spacer(modifier = Modifier.height(80.dp))
//    }
//}
//
//@Composable
//fun PermissionsPage() {
//    Column(modifier = Modifier.fillMaxSize().padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
//        Text("开启健康授权", fontSize = 28.sp, fontWeight = FontWeight.Bold)
//        Spacer(modifier = Modifier.height(16.dp))
//        Text("打通数据，让体验更完整", fontSize = 16.sp, color = Color.Gray, textAlign = TextAlign.Center)
//        Spacer(modifier = Modifier.height(40.dp))
//        PermissionRowCard("健康数据", "读取步数与基础指标", Icons.Default.Favorite, Color.Red)
//        Spacer(modifier = Modifier.height(16.dp))
//        PermissionRowCard("消息推送", "及时获取重要提醒", Icons.Default.Notifications, Color.Yellow)
//    }
//}
//
//@Composable
//fun PermissionRowCard(title: String, desc: String, icon: androidx.compose.ui.graphics.vector.ImageVector, tint: Color) {
//    var isGranted by remember { mutableStateOf(false) }
//    Surface(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), color = Color.White, shadowElevation = 2.dp) {
//        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
//            Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(30.dp))
//            Spacer(modifier = Modifier.width(16.dp))
//            Column(modifier = Modifier.weight(1f)) {
//                Text(title, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
//                Text(desc, fontSize = 13.sp, color = Color.Gray)
//            }
//            Switch(checked = isGranted, onCheckedChange = { isGranted = it }, colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF008080), checkedTrackColor = Color(0xFF008080).copy(alpha = 0.5f)))
//        }
//    }
//}
//
//@Composable
//fun ConnectDevicePage() {
//    var isSearching by remember { mutableStateOf(false) }
//    var deviceFound by remember { mutableStateOf(false) }
//
//    LaunchedEffect(isSearching) {
//        if (isSearching) {
//            delay(2000)
//            isSearching = false
//            deviceFound = true
//        }
//    }
//
//    Column(modifier = Modifier.fillMaxSize().padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
//        Text("连接 CGM 设备", fontSize = 28.sp, fontWeight = FontWeight.Bold)
//        Spacer(modifier = Modifier.height(16.dp))
//        Text("支持连接主流动态血糖仪", fontSize = 16.sp, color = Color.Gray, textAlign = TextAlign.Center)
//        Spacer(modifier = Modifier.height(40.dp))
//
//        Surface(modifier = Modifier.fillMaxWidth().clickable { isSearching = true }, shape = RoundedCornerShape(16.dp), color = Color.White, shadowElevation = 2.dp) {
//            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
//                Icon(Icons.Default.Phone, contentDescription = null, tint = Color(0xFF008080), modifier = Modifier.size(30.dp))
//                Spacer(modifier = Modifier.width(16.dp))
//                Text("Dexcom / 硅基 / 三诺", fontSize = 16.sp, fontWeight = FontWeight.Medium, modifier = Modifier.weight(1f))
//                if (deviceFound) {
//                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color.Green)
//                } else if (isSearching) {
//                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color(0xFF008080))
//                } else {
//                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.Gray)
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun CompletionPage() {
//    Column(modifier = Modifier.fillMaxSize().padding(40.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
//        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF008080), modifier = Modifier.size(80.dp))
//        Spacer(modifier = Modifier.height(30.dp))
//        Text("一切准备就绪", fontSize = 28.sp, fontWeight = FontWeight.Bold)
//        Spacer(modifier = Modifier.height(16.dp))
//        Text("你的 AI 健康伙伴已经苏醒", fontSize = 16.sp, color = Color.Gray, textAlign = TextAlign.Center)
//        Spacer(modifier = Modifier.height(40.dp))
//
//        Column(verticalArrangement = Arrangement.spacedBy(16.dp), horizontalAlignment = Alignment.Start) {
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Icon(Icons.Default.Favorite, contentDescription = null, tint = Color(0xFF008080))
//                Spacer(modifier = Modifier.width(12.dp))
//                Text("24小时情绪与血糖陪伴", fontSize = 15.sp)
//            }
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Icon(Icons.Default.Build, contentDescription = null, tint = Color(0xFF008080))
//                Spacer(modifier = Modifier.width(12.dp))
//                Text("智能分析与健康简报", fontSize = 15.sp)
//            }
//        }
//    }
//}

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