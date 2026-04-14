//
//
//package com.withapp.with.ui.screens
//
//import androidx.compose.foundation.BorderStroke
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.Send
//import androidx.compose.material.icons.filled.AccountCircle
//import androidx.compose.material.icons.filled.Assessment
//import androidx.compose.material.icons.filled.Home
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.withapp.with.viewmodels.ReportViewModel
//import com.withapp.with.viewmodels.UserProfile
//import kotlinx.coroutines.launch
//import java.util.Calendar
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun MainContainer(onRequestPermission: () -> Unit = {}) {
//    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
//    val snackbarHostState = remember { SnackbarHostState() }
//    val scope = rememberCoroutineScope()
//    var selectedTab by remember { mutableStateOf("home") }
//    val reportViewModel: ReportViewModel = viewModel()
//
//    ModalNavigationDrawer(
//        drawerState = drawerState,
//        drawerContent = {
//            ModalDrawerSheet(modifier = Modifier.width(320.dp), drawerContainerColor = Color(0xFFF7F9FA)) {
//                ProfileSidebarContent(reportViewModel)
//            }
//        }
//    ) {
//        Scaffold(
//            snackbarHost = { SnackbarHost(snackbarHostState) },
//            topBar = {
//                CenterAlignedTopAppBar(
//                    title = { Text("With", fontWeight = FontWeight.Bold) },
//                    navigationIcon = {
//                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
//                            Icon(Icons.Default.AccountCircle, contentDescription = null, tint = Color(0xFF008080), modifier = Modifier.size(32.dp))
//                        }
//                    }
//                )
//            },
//            bottomBar = {
//                NavigationBar(containerColor = Color.White) {
//                    NavigationBarItem(selected = selectedTab == "home", onClick = { selectedTab = "home" }, icon = { Icon(Icons.Default.Home, null) }, label = { Text("主页") })
//                    NavigationBarItem(selected = selectedTab == "report", onClick = { selectedTab = "report" }, icon = { Icon(Icons.Default.Assessment, null) }, label = { Text("回顾") }) // 去医疗化：将“报告”改为“回顾”
//                }
//            }
//        ) { padding ->
//            Box(modifier = Modifier.padding(padding)) {
//                when (selectedTab) {
//                    "home" -> HomeV3View(reportViewModel, snackbarHostState)
//                    "report" -> ReportView(reportViewModel)
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun HomeV3View(reportViewModel: ReportViewModel, snackbarHostState: SnackbarHostState) {
//    val scope = rememberCoroutineScope()
//    var inputText by remember { mutableStateOf("") }
//    val chatMessages = remember { mutableStateListOf(ChatMessage("嗨，今天感觉如何？你可以像聊天一样告诉我刚才吃了什么、心情怎样，我会自动帮你记录。", false, true)) }
//    var showProbe by remember { mutableStateOf(false) }
//    var showRabbitInfo by remember { mutableStateOf(false) }
//
//    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
//    val dynamicPlaceholder = when {
//        hour in 6..9 -> "早安！早餐吃了什么？..."
//        hour in 11..13 -> "午餐时间，记录一下碳水摄入吧..."
//        hour in 17..19 -> "晚餐吃了什么？现在心情如何？..."
//        hour > 20 -> "准备休息了吗？记录一下睡前状态..."
//        else -> "用日常对话记录血糖、饮食或情绪..."
//    }
//
//    if (showRabbitInfo) {
//        AlertDialog(
//            onDismissRequest = { showRabbitInfo = false },
//            title = { Text("🐰 关于 With 助手", fontWeight = FontWeight.Bold) },
//            text = { Text("我是您的智能陪伴助手。您不需要手动填写冷冰冰的医疗表单，只要像和朋友聊天一样告诉我您的生活点滴，我会自动为您整理成健康回顾。") },
//            confirmButton = { TextButton(onClick = { showRabbitInfo = false }) { Text("了解了", color = Color(0xFF008080)) } }
//        )
//    }
//
//    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF7F9FA))) {
//
//        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
//            Box(modifier = Modifier.size(50.dp).clip(CircleShape).background(Color.White).clickable { showRabbitInfo = true }, contentAlignment = Alignment.Center) { Text("🐰", fontSize = 28.sp) }
//            Spacer(modifier = Modifier.width(12.dp))
//            Column { Text("点击头像了解我", fontSize = 11.sp, color = Color.Gray) }
//        }
//
//        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//            QuickActionChip("🍱 记饮食", Color(0xFF81C784)) { inputText = "我刚吃了..."; scope.launch { snackbarHostState.showSnackbar("已为您准备好饮食记录模版") } }
//            QuickActionChip("🧘 记心情", Color(0xFFFFB74D)) { inputText = "我现在觉得..."; scope.launch { snackbarHostState.showSnackbar("您可以描述现在的心情") } }
//            QuickActionChip("🩸 记血糖", Color(0xFF4FC3F7)) { inputText = "我刚测了血糖，数值是..." }
//        }
//
//        LazyColumn(modifier = Modifier.weight(1f).padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
//            items(chatMessages) { ChatBubble(it) }
//        }
//
//        if (showProbe) { ResearchProbeCard { showProbe = false } }
//
//        Row(modifier = Modifier.background(Color.White).padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
//            OutlinedTextField(
//                value = inputText, onValueChange = { inputText = it },
//                placeholder = { Text(dynamicPlaceholder, fontSize = 12.sp, color = Color.Gray) },
//                modifier = Modifier.weight(1f), shape = RoundedCornerShape(24.dp)
//            )
//            IconButton(onClick = {
//                if (inputText.isNotBlank()) {
//                    val reply = reportViewModel.processChatInput(inputText)
//                    chatMessages.add(ChatMessage(inputText, true))
//                    chatMessages.add(ChatMessage(reply, false, true))
//                    inputText = ""; showProbe = true
//                    scope.launch { snackbarHostState.showSnackbar("记录已安全保存") }
//                }
//            }) { Icon(Icons.AutoMirrored.Filled.Send, null, tint = Color(0xFF008080)) }
//        }
//    }
//}
//
//@Composable
//fun QuickActionChip(label: String, color: Color, onClick: () -> Unit) {
//    Surface(onClick = onClick, color = color.copy(alpha = 0.15f), shape = RoundedCornerShape(16.dp), border = BorderStroke(1.dp, color.copy(alpha = 0.5f))) {
//        Text(label, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), fontSize = 12.sp, color = color, fontWeight = FontWeight.Bold)
//    }
//}
//
//@Composable
//fun ResearchProbeCard(onDismiss: () -> Unit) {
//    Card(modifier = Modifier.padding(16.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F2F1))) {
//        Column(modifier = Modifier.padding(12.dp)) {
//            Text("🔬 研究探针：刚才的聊天记录方式让你觉得方便吗？", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF00695C))
//            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
//                TextButton(onClick = onDismiss) { Text("😊 极简", fontSize = 11.sp) }
//                TextButton(onClick = onDismiss) { Text("😐 一般", fontSize = 11.sp) }
//                TextButton(onClick = onDismiss) { Text("😫 繁琐", fontSize = 11.sp) }
//            }
//        }
//    }
//}
//
//data class ChatMessage(val text: String, val isUser: Boolean, val isSupportive: Boolean = false)
//
//@Composable
//fun ChatBubble(message: ChatMessage) {
//    var feedbackGiven by remember { mutableStateOf(false) }
//    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = if (message.isUser) Alignment.End else Alignment.Start) {
//        Box(modifier = Modifier.padding(8.dp).clip(RoundedCornerShape(12.dp)).background(if (message.isUser) Color(0xFF008080) else Color.White).padding(12.dp)) {
//            Text(message.text, color = if (message.isUser) Color.White else Color.Black)
//        }
//        if (!message.isUser && message.isSupportive && !feedbackGiven) {
//            Row(modifier = Modifier.padding(start = 12.dp), verticalAlignment = Alignment.CenterVertically) {
//                Text("🔬 这句话对你有帮助吗？", fontSize = 10.sp, color = Color.Gray)
//                TextButton(onClick = { feedbackGiven = true }) { Text("👍 有用", fontSize = 10.sp) }
//                TextButton(onClick = { feedbackGiven = true }) { Text("👎 没用", fontSize = 10.sp, color = Color.Gray) }
//            }
//        }
//    }
//}
//
//@Composable
//fun ProfileSidebarContent(reportViewModel: ReportViewModel) {
//    val scrollState = rememberScrollState()
//    var isEditing by remember { mutableStateOf(false) }
//
//    val profile = reportViewModel.userProfile.value
//    var age by remember(profile) { mutableStateOf(profile.age) }
//    var gender by remember(profile) { mutableStateOf(profile.gender) }
//    var height by remember(profile) { mutableStateOf(profile.height) }
//    var weight by remember(profile) { mutableStateOf(profile.weight) }
//    var diagnosis by remember(profile) { mutableStateOf(profile.diagnosisDate) }
//    var insulin by remember(profile) { mutableStateOf(profile.insulinUse) }
//    var medication by remember(profile) { mutableStateOf(profile.medication) }
//    var targetRange by remember(profile) { mutableStateOf(profile.targetRange) }
//    var hba1c by remember(profile) { mutableStateOf(profile.hba1c) }
//    var reminderTime by remember(profile) { mutableStateOf(profile.reminderTime) }
//    var reminderFrequency by remember(profile) { mutableStateOf(profile.reminderFrequency) }
//    var isPrivacyMode by remember(profile) { mutableStateOf(profile.isPrivacyMode) }
//    var showMockPrompt by remember { mutableStateOf(false) }
//
//    if (showMockPrompt) {
//        AlertDialog(
//            onDismissRequest = { showMockPrompt = false },
//            title = { Text("🐰 With 智能提醒", fontWeight = FontWeight.Bold) },
//            text = { Text("系统检测到您刚吃完午餐。现在方便记录一下血糖和情绪吗？") },
//            confirmButton = { Button(onClick = { showMockPrompt = false }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF008080))) { Text("去记录") } },
//            dismissButton = { Row { TextButton(onClick = { showMockPrompt = false }) { Text("稍后 15m", color = Color.Gray) }; TextButton(onClick = { showMockPrompt = false }) { Text("不方便", color = Color.Red) } } }
//        )
//    }
//
//    Column(modifier = Modifier.fillMaxSize().verticalScroll(scrollState).padding(24.dp)) {
//        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
//            Text("档案与设置", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF008080))
//            TextButton(onClick = {
//                if (isEditing) reportViewModel.userProfile.value = UserProfile(age, gender, height, weight, diagnosis, insulin, medication, targetRange, hba1c, reminderTime, reminderFrequency, isPrivacyMode)
//                isEditing = !isEditing
//            }) { Text(if (isEditing) "保存" else "编辑", color = Color(0xFF008080), fontWeight = FontWeight.Bold) }
//        }
//
//        Row(modifier = Modifier.fillMaxWidth().background(Color(0xFFE8EAF6), RoundedCornerShape(8.dp)).padding(12.dp).padding(vertical = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
//            Column {
//                Text("开启隐私掩码模式", fontWeight = FontWeight.Bold, fontSize = 13.sp)
//                Text("在公共场合隐藏敏感健康数据", fontSize = 10.sp, color = Color.Gray)
//            }
//            Switch(
//                checked = isPrivacyMode,
//                onCheckedChange = {
//                    isPrivacyMode = it
//                    reportViewModel.userProfile.value = reportViewModel.userProfile.value.copy(isPrivacyMode = it)
//                },
//                colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF3F51B5), checkedTrackColor = Color(0xFFC5CAE9))
//            )
//        }
//
//        Spacer(Modifier.height(16.dp))
//        Text("基本信息", fontWeight = FontWeight.Bold, color = Color(0xFF008080), fontSize = 14.sp)
//        EditableProfileRow("年龄", age, isEditing) { age = it }; EditableProfileRow("性别", gender, isEditing) { gender = it }
//        EditableProfileRow("身高", height, isEditing) { height = it }; EditableProfileRow("体重", weight, isEditing) { weight = it }
//
//        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
//        Text("健康背景", fontWeight = FontWeight.Bold, color = Color(0xFF008080), fontSize = 14.sp)
//        EditableProfileRow("确诊时间", if (isPrivacyMode && !isEditing) "****" else diagnosis, isEditing) { diagnosis = it }
//        EditableProfileRow("使用胰岛素", insulin, isEditing) { insulin = it }
//        EditableProfileRow("用药情况", if (isPrivacyMode && !isEditing) "****" else medication, isEditing) { medication = it }
//        EditableProfileRow("目标血糖", targetRange, isEditing) { targetRange = it }
//        EditableProfileRow("最近HbA1c", if (isPrivacyMode && !isEditing) "***%" else hba1c, isEditing) { hba1c = it }
//
//        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
//        Column(modifier = Modifier.fillMaxWidth().background(Color(0xFFFFF3E0), RoundedCornerShape(8.dp)).padding(12.dp)) {
//            Text("🔔 智能提醒设置", fontWeight = FontWeight.Bold, color = Color(0xFFE65100), fontSize = 14.sp)
//            EditableProfileRow("触发时机", reminderTime, isEditing) { reminderTime = it }; EditableProfileRow("提醒频率", reminderFrequency, isEditing) { reminderFrequency = it }
//            Spacer(Modifier.height(12.dp))
//            Button(onClick = { showMockPrompt = true }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE65100))) { Text("🔬 测试打断机制", fontSize = 12.sp) }
//        }
//    }
//}
//
//@Composable
//fun EditableProfileRow(label: String, value: String, isEditing: Boolean, onValueChange: (String) -> Unit) {
//    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
//        Text(label, color = Color.Gray, modifier = Modifier.weight(0.45f), fontSize = 12.sp)
//        if (isEditing) {
//            OutlinedTextField(value = value, onValueChange = onValueChange, modifier = Modifier.weight(0.55f).height(50.dp), singleLine = true, textStyle = TextStyle(fontSize = 12.sp, textAlign = TextAlign.End))
//        } else {
//            Text(value, fontWeight = FontWeight.Medium, modifier = Modifier.weight(0.55f), textAlign = TextAlign.End, fontSize = 12.sp)
//        }
//    }
//}


package com.withapp.with.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.withapp.with.viewmodels.ReportViewModel
import com.withapp.with.viewmodels.UserProfile
import kotlinx.coroutines.launch
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
// 🔴 修复：把这只接权限的“手”加回来了！
fun MainContainer(onRequestPermission: () -> Unit = {}) {
    val snackbarHostState = remember { SnackbarHostState() }
    var selectedTab by remember { mutableStateOf("home") }
    val reportViewModel: ReportViewModel = viewModel()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(title = { Text("With", fontWeight = FontWeight.Bold, color = Color(0xFF008080)) })
        },
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                NavigationBarItem(selected = selectedTab == "home", onClick = { selectedTab = "home" }, icon = { Icon(Icons.Default.Home, null) }, label = { Text("主页") })
                NavigationBarItem(selected = selectedTab == "report", onClick = { selectedTab = "report" }, icon = { Icon(Icons.Default.Assessment, null) }, label = { Text("回顾") })
                NavigationBarItem(selected = selectedTab == "profile", onClick = { selectedTab = "profile" }, icon = { Icon(Icons.Default.AccountCircle, null) }, label = { Text("档案") })
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (selectedTab) {
                "home" -> HomeV3View(reportViewModel, snackbarHostState)
                "report" -> ReportView(reportViewModel)
                "profile" -> ProfileView(reportViewModel)
            }
        }
    }
}

@Composable
fun HomeV3View(reportViewModel: ReportViewModel, snackbarHostState: SnackbarHostState) {
    val scope = rememberCoroutineScope()
    var inputText by remember { mutableStateOf("") }
    val chatMessages = remember { mutableStateListOf(ChatMessage("嗨，今天感觉如何？你可以像聊天一样告诉我刚才吃了什么、心情怎样，我会自动帮你记录。", false, true)) }
    var showProbe by remember { mutableStateOf(false) }
    var showRabbitInfo by remember { mutableStateOf(false) }

    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val dynamicPlaceholder = when {
        hour in 6..9 -> "早安！早餐吃了什么？..."
        hour in 11..13 -> "午餐时间，记录一下碳水摄入吧..."
        hour in 17..19 -> "晚餐吃了什么？现在心情如何？..."
        hour > 20 -> "准备休息了吗？记录一下睡前状态..."
        else -> "用日常对话记录数据..."
    }

    if (showRabbitInfo) {
        AlertDialog(
            onDismissRequest = { showRabbitInfo = false },
            title = { Text("🐰 关于 With 助手", fontWeight = FontWeight.Bold) },
            text = { Text("我是您的智能陪伴助手。您不需要手动填写冷冰冰的医疗表单，只要像和朋友聊天一样告诉我您的生活点滴，我会自动为您整理成健康回顾。") },
            confirmButton = { TextButton(onClick = { showRabbitInfo = false }) { Text("了解了", color = Color(0xFF008080)) } }
        )
    }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF7F9FA))) {

        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Box(modifier = Modifier.size(50.dp).clip(CircleShape).background(Color.White).clickable { showRabbitInfo = true }, contentAlignment = Alignment.Center) { Text("🐰", fontSize = 28.sp) }
            Spacer(modifier = Modifier.width(12.dp))
            Column { Text("点击头像了解我", fontSize = 11.sp, color = Color.Gray) }
        }

        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            QuickActionChip("🍱 记饮食", Color(0xFF81C784)) { inputText = "我刚吃了..."; scope.launch { snackbarHostState.showSnackbar("已为您准备好饮食记录模版") } }
            QuickActionChip("🧘 记心情", Color(0xFFFFB74D)) { inputText = "我现在觉得..."; scope.launch { snackbarHostState.showSnackbar("您可以描述现在的心情") } }
            QuickActionChip("🩸 记血糖", Color(0xFF4FC3F7)) { inputText = "我刚测了血糖，数值是..." }
        }

        LazyColumn(modifier = Modifier.weight(1f).padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(chatMessages) { ChatBubble(it) }
        }

        if (showProbe) { ResearchProbeCard { showProbe = false } }

        Row(modifier = Modifier.background(Color.White).padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = inputText, onValueChange = { inputText = it },
                placeholder = { Text(dynamicPlaceholder, fontSize = 12.sp, color = Color.Gray) },
                modifier = Modifier.weight(1f), shape = RoundedCornerShape(24.dp)
            )
            IconButton(onClick = { scope.launch { snackbarHostState.showSnackbar("🎙️ 语音输入模块将在未来版本接入") } }) {
                Icon(Icons.Default.Mic, contentDescription = "Voice", tint = Color.Gray)
            }
            IconButton(onClick = {
                if (inputText.isNotBlank()) {
                    val reply = reportViewModel.processChatInput(inputText)
                    chatMessages.add(ChatMessage(inputText, true))
                    chatMessages.add(ChatMessage(reply, false, true))
                    inputText = ""; showProbe = true
                    scope.launch { snackbarHostState.showSnackbar("记录已安全保存") }
                }
            }) { Icon(Icons.AutoMirrored.Filled.Send, null, tint = Color(0xFF008080)) }
        }
    }
}

@Composable
fun QuickActionChip(label: String, color: Color, onClick: () -> Unit) {
    Surface(onClick = onClick, color = color.copy(alpha = 0.15f), shape = RoundedCornerShape(16.dp), border = BorderStroke(1.dp, color.copy(alpha = 0.5f))) {
        Text(label, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), fontSize = 12.sp, color = color, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun ResearchProbeCard(onDismiss: () -> Unit) {
    Card(modifier = Modifier.padding(16.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F2F1))) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("🔬 研究探针：刚才的聊天记录方式让你觉得方便吗？", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF00695C))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                TextButton(onClick = onDismiss) { Text("😊 极简", fontSize = 11.sp) }
                TextButton(onClick = onDismiss) { Text("😐 一般", fontSize = 11.sp) }
                TextButton(onClick = onDismiss) { Text("😫 繁琐", fontSize = 11.sp) }
            }
        }
    }
}

data class ChatMessage(val text: String, val isUser: Boolean, val isSupportive: Boolean = false)

@Composable
fun ChatBubble(message: ChatMessage) {
    var feedbackGiven by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = if (message.isUser) Alignment.End else Alignment.Start) {
        Box(modifier = Modifier.padding(8.dp).clip(RoundedCornerShape(12.dp)).background(if (message.isUser) Color(0xFF008080) else Color.White).padding(12.dp)) {
            Text(message.text, color = if (message.isUser) Color.White else Color.Black)
        }
        if (!message.isUser && message.isSupportive && !feedbackGiven) {
            Row(modifier = Modifier.padding(start = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                Text("🔬 这句话对你有帮助吗？", fontSize = 10.sp, color = Color.Gray)
                TextButton(onClick = { feedbackGiven = true }) { Text("👍 有用", fontSize = 10.sp) }
                TextButton(onClick = { feedbackGiven = true }) { Text("👎 没用", fontSize = 10.sp, color = Color.Gray) }
            }
        }
    }
}

@Composable
fun ProfileView(reportViewModel: ReportViewModel) {
    val scrollState = rememberScrollState()
    var isEditing by remember { mutableStateOf(false) }

    val profile = reportViewModel.userProfile.value
    var age by remember(profile) { mutableStateOf(profile.age) }
    var gender by remember(profile) { mutableStateOf(profile.gender) }
    var height by remember(profile) { mutableStateOf(profile.height) }
    var weight by remember(profile) { mutableStateOf(profile.weight) }
    var diagnosis by remember(profile) { mutableStateOf(profile.diagnosisDate) }
    var insulin by remember(profile) { mutableStateOf(profile.insulinUse) }
    var medication by remember(profile) { mutableStateOf(profile.medication) }
    var targetRange by remember(profile) { mutableStateOf(profile.targetRange) }
    var hba1c by remember(profile) { mutableStateOf(profile.hba1c) }
    var reminderTime by remember(profile) { mutableStateOf(profile.reminderTime) }
    var reminderFrequency by remember(profile) { mutableStateOf(profile.reminderFrequency) }
    var isPrivacyMode by remember(profile) { mutableStateOf(profile.isPrivacyMode) }
    var isContextAwareAlerts by remember(profile) { mutableStateOf(profile.isContextAwareAlerts) }

    var showMockPrompt by remember { mutableStateOf(false) }

    if (showMockPrompt) {
        AlertDialog(
            onDismissRequest = { showMockPrompt = false },
            title = { Text("🐰 智能感知提醒 (Context-Aware)", fontWeight = FontWeight.Bold) },
            text = { Text("系统监测到您刚完成一段高强度的会议 (心率波动)。您现在的压力水平如何？需要记录一下情绪吗？") },
            confirmButton = { Button(onClick = { showMockPrompt = false }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF008080))) { Text("去记录") } },
            dismissButton = { Row { TextButton(onClick = { showMockPrompt = false }) { Text("稍后", color = Color.Gray) }; TextButton(onClick = { showMockPrompt = false }) { Text("不需要", color = Color.Red) } } }
        )
    }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF7F9FA)).verticalScroll(scrollState).padding(24.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("档案与偏好设置", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF008080))
            TextButton(onClick = {
                if (isEditing) reportViewModel.userProfile.value = UserProfile(age, gender, height, weight, diagnosis, insulin, medication, targetRange, hba1c, reminderTime, reminderFrequency, isPrivacyMode, isContextAwareAlerts)
                isEditing = !isEditing
            }) { Text(if (isEditing) "保存" else "编辑", color = Color(0xFF008080), fontWeight = FontWeight.Bold) }
        }

        Row(modifier = Modifier.fillMaxWidth().background(Color(0xFFE8EAF6), RoundedCornerShape(8.dp)).padding(12.dp).padding(vertical = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text("开启隐私掩码模式", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Text("在公共场合隐藏敏感健康数据", fontSize = 10.sp, color = Color.Gray)
            }
            Switch(checked = isPrivacyMode, onCheckedChange = { isPrivacyMode = it; reportViewModel.userProfile.value = reportViewModel.userProfile.value.copy(isPrivacyMode = it) }, colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF3F51B5), checkedTrackColor = Color(0xFFC5CAE9)))
        }

        Spacer(Modifier.height(16.dp))
        Text("基本信息", fontWeight = FontWeight.Bold, color = Color(0xFF008080), fontSize = 14.sp)
        val editTrigger = { isEditing = true }
        EditableProfileRow("年龄", age, isEditing, editTrigger) { age = it }
        DropdownProfileRow("性别", gender, listOf("男", "女", "其他"), isEditing) { gender = it }
        EditableProfileRow("身高", height, isEditing, editTrigger) { height = it }
        EditableProfileRow("体重", weight, isEditing, editTrigger) { weight = it }

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
        Text("健康背景", fontWeight = FontWeight.Bold, color = Color(0xFF008080), fontSize = 14.sp)
        EditableProfileRow("确诊时间", if (isPrivacyMode && !isEditing) "****" else diagnosis, isEditing, editTrigger) { diagnosis = it }
        DropdownProfileRow("使用胰岛素", insulin, listOf("是", "否"), isEditing) { insulin = it }
        EditableProfileRow("用药情况", if (isPrivacyMode && !isEditing) "****" else medication, isEditing, editTrigger) { medication = it }
        EditableProfileRow("目标血糖", targetRange, isEditing, editTrigger) { targetRange = it }
        EditableProfileRow("最近HbA1c", if (isPrivacyMode && !isEditing) "***%" else hba1c, isEditing, editTrigger) { hba1c = it }

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
        Column(modifier = Modifier.fillMaxWidth().background(Color(0xFFFFF3E0), RoundedCornerShape(8.dp)).padding(12.dp)) {
            Row(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("🔔 智能感知提醒 (JITAI)", fontWeight = FontWeight.Bold, color = Color(0xFFE65100), fontSize = 14.sp)
                    Text("根据您的血糖趋势和生活习惯自动推送提醒，替代死板的定时闹钟", fontSize = 10.sp, color = Color.Gray, lineHeight = 14.sp)
                }
                Switch(checked = isContextAwareAlerts, onCheckedChange = { isContextAwareAlerts = it }, colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFFE65100), checkedTrackColor = Color(0xFFFFCC80)))
            }

            if (!isContextAwareAlerts) {
                EditableProfileRow("提醒时间", reminderTime, isEditing, editTrigger) { reminderTime = it }
                DropdownProfileRow("提醒频率", reminderFrequency, listOf("每天1次", "每天3次", "仅异常时"), isEditing) { reminderFrequency = it }
            }

            Spacer(Modifier.height(12.dp))
            Button(onClick = { showMockPrompt = true }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE65100))) { Text("🔬 测试智能打断机制", fontSize = 12.sp) }
        }
    }
}

@Composable
fun EditableProfileRow(label: String, value: String, isEditing: Boolean, onRowClick: () -> Unit, onValueChange: (String) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().clickable(enabled = !isEditing) { onRowClick() }.padding(vertical = 6.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(label, color = Color.Gray, modifier = Modifier.weight(0.45f), fontSize = 12.sp)
        if (isEditing) {
            OutlinedTextField(value = value, onValueChange = onValueChange, modifier = Modifier.weight(0.55f).height(50.dp), singleLine = true, textStyle = TextStyle(fontSize = 12.sp, textAlign = TextAlign.End))
        } else {
            Text(value, fontWeight = FontWeight.Medium, modifier = Modifier.weight(0.55f), textAlign = TextAlign.End, fontSize = 12.sp)
        }
    }
}

@Composable
fun DropdownProfileRow(label: String, value: String, options: List<String>, isEditing: Boolean, onValueChange: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(label, color = Color.Gray, modifier = Modifier.weight(0.45f), fontSize = 12.sp)
        Box(modifier = Modifier.weight(0.55f), contentAlignment = Alignment.CenterEnd) {
            if (isEditing) {
                Row(modifier = Modifier.clickable { expanded = true }.padding(8.dp).background(Color(0xFFF1F1F1), RoundedCornerShape(4.dp)).padding(horizontal=8.dp, vertical=4.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(value, color = Color(0xFF008080), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = Color(0xFF008080))
                }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    options.forEach { opt ->
                        DropdownMenuItem(text = { Text(opt, fontSize = 12.sp) }, onClick = { onValueChange(opt); expanded = false })
                    }
                }
            } else {
                Text(value, fontWeight = FontWeight.Medium, textAlign = TextAlign.End, fontSize = 12.sp)
            }
        }
    }
}

