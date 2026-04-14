//
//package com.withapp.with.ui.screens
//
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
//import androidx.compose.material.icons.filled.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.TextFieldValue
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.withapp.with.viewmodels.ReportViewModel
//import com.withapp.with.viewmodels.UserProfile
//import kotlinx.coroutines.launch
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun MainContainer(onRequestPermission: () -> Unit = {}) {
//    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
//    val scope = rememberCoroutineScope()
//    var selectedTab by remember { mutableStateOf("home") }
//    val reportViewModel: ReportViewModel = viewModel()
//
//    ModalNavigationDrawer(
//        drawerState = drawerState,
//        drawerContent = { ModalDrawerSheet { ProfileSidebarContent(reportViewModel) } }
//    ) {
//        Scaffold(
//            topBar = {
//                CenterAlignedTopAppBar(
//                    title = { Text("With", fontWeight = FontWeight.Bold) },
//                    navigationIcon = {
//                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
//                            Icon(Icons.Default.AccountCircle, null, tint = Color(0xFF008080), modifier = Modifier.size(32.dp))
//                        }
//                    }
//                )
//            },
//            bottomBar = {
//                NavigationBar(containerColor = Color.White) {
//                    NavigationBarItem(selected = selectedTab == "home", onClick = { selectedTab = "home" }, icon = { Icon(Icons.Default.Home, null) }, label = { Text("主页 (Home)") })
//                    NavigationBarItem(selected = selectedTab == "report", onClick = { selectedTab = "report" }, icon = { Icon(Icons.Default.Assessment, null) }, label = { Text("报告 (Report)") })
//                }
//            }
//        ) { paddingValues ->
//            Box(modifier = Modifier.padding(paddingValues)) {
//                when (selectedTab) {
//                    "home" -> HomeChatCombinedView(reportViewModel)
//                    "report" -> ReportView(reportViewModel = reportViewModel, onBack = { selectedTab = "home" })
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun HomeChatCombinedView(reportViewModel: ReportViewModel) {
//    var inputText by remember { mutableStateOf(TextFieldValue("")) }
//    // 🧬 探针：加入 isSupportive 标志，用于触发情感效能验证
//    val chatMessages = remember { mutableStateListOf(ChatMessage("嗨！我是 With。直接告诉我数据，或者点击左上角头像去自定义【提醒时间与频率 (Reminders)】吧！", false, true)) }
//
//    // 🧬 探针：交互代价（Interaction Cost）收集框
//    var showInteractionProbe by remember { mutableStateOf(false) }
//
//    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF7F9FA))) {
//        Column(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
//            Text("陪你看见情绪，也看见血糖 (See your mood, see your glucose)", color = Color.Gray, fontSize = 12.sp)
//            Spacer(Modifier.height(8.dp))
//            Box(modifier = Modifier.size(60.dp).clip(CircleShape).background(Color.White), contentAlignment = Alignment.Center) { Text("🐰", fontSize = 30.sp) }
//        }
//
//        // 🔬 探针 UI：操作疲劳度收集
//        if (showInteractionProbe) {
//            Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))) {
//                Column(modifier = Modifier.padding(12.dp)) {
//                    Text("🔬 调研探针 (Research Probe): 这次交互让你觉得麻烦吗？\n(Did this interaction feel burdensome?)", fontSize = 11.sp, color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold)
//                    Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
//                        Button(onClick = { showInteractionProbe = false }, colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.DarkGray)) { Text("😊 轻松 (Easy)", fontSize = 12.sp) }
//                        Button(onClick = { showInteractionProbe = false }, colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.DarkGray)) { Text("😐 一般 (Neutral)", fontSize = 12.sp) }
//                        Button(onClick = { showInteractionProbe = false }, colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.DarkGray)) { Text("😫 麻烦 (Hard)", fontSize = 12.sp) }
//                    }
//                }
//            }
//        }
//
//        LazyColumn(modifier = Modifier.weight(1f).padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
//            items(chatMessages) { msg -> ChatBubble(message = msg) }
//        }
//
//        Column(modifier = Modifier.background(Color.White).padding(16.dp)) {
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                OutlinedTextField(
//                    value = inputText,
//                    onValueChange = { inputText = it },
//                    placeholder = { Text("支持输入时间与多维数据 (Input data & time)...", fontSize = 13.sp) },
//                    modifier = Modifier.weight(1f),
//                    shape = RoundedCornerShape(24.dp)
//                )
//                IconButton(onClick = {
//                    val text = inputText.text
//                    if (text.isNotBlank()) {
//                        chatMessages.add(ChatMessage(text, true))
//                        reportViewModel.processChatInput(text)
//                        chatMessages.add(ChatMessage("✅ 已精准拆解记录！数据未出现记串，高密度多维关联报表已更新 (Data synced successfully)。", false, true))
//                        inputText = TextFieldValue("")
//                        // 随机弹出探针
//                        showInteractionProbe = true
//                    }
//                }) { Icon(Icons.AutoMirrored.Filled.Send, null, tint = Color(0xFF008080)) }
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
//    var hba1c by remember(profile) { mutableStateOf(profile.hba1c) }
//    var reminderTime by remember(profile) { mutableStateOf(profile.reminderTime) }
//    var reminderFrequency by remember(profile) { mutableStateOf(profile.reminderFrequency) }
//
//    // 🧬 探针：模拟系统主动打断 (Interruption Management)
//    var showMockPrompt by remember { mutableStateOf(false) }
//
//    if (showMockPrompt) {
//        AlertDialog(
//            onDismissRequest = { showMockPrompt = false },
//            title = { Text("🐰 With 智能提醒 (Smart Prompt)", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
//            text = { Text("系统检测到您刚吃完午餐。现在方便记录一下血糖和情绪吗？\n(Would you like to log your BG and mood now?)", fontSize = 14.sp) },
//            confirmButton = { Button(onClick = { showMockPrompt = false }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF008080))) { Text("去记录 (Log Now)") } },
//            dismissButton = {
//                Row {
//                    TextButton(onClick = { showMockPrompt = false }) { Text("稍后 (Snooze 15m)", color = Color.Gray) }
//                    TextButton(onClick = { showMockPrompt = false }) { Text("不方便 (Dismiss)", color = Color.Red) }
//                }
//            }
//        )
//    }
//
//    Column(modifier = Modifier.fillMaxSize().verticalScroll(scrollState).padding(24.dp)) {
//        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
//            Text("个人档案与设置\n(Profile & Config)", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF008080))
//            TextButton(onClick = {
//                if (isEditing) reportViewModel.updateProfile(UserProfile(age, gender, height, weight, diagnosis, insulin, hba1c, reminderTime, reminderFrequency))
//                isEditing = !isEditing
//            }) { Text(if (isEditing) "保存 (Save)" else "编辑 (Edit)", color = Color(0xFF008080), fontWeight = FontWeight.Bold) }
//        }
//
//        Spacer(Modifier.height(16.dp))
//        Text("基本信息 (Basic Info)", fontWeight = FontWeight.Bold, color = Color(0xFF008080), fontSize = 14.sp)
//        EditableProfileRow("年龄 (Age)", age, isEditing) { age = it }; EditableProfileRow("性别 (Gender)", gender, isEditing) { gender = it }
//        EditableProfileRow("身高 (Height)", height, isEditing) { height = it }; EditableProfileRow("体重 (Weight)", weight, isEditing) { weight = it }
//
//        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
//        Text("健康背景 (Health Background)", fontWeight = FontWeight.Bold, color = Color(0xFF008080), fontSize = 14.sp)
//        EditableProfileRow("确诊时间 (Diagnosis Date)", diagnosis, isEditing) { diagnosis = it }
//        EditableProfileRow("使用胰岛素 (Insulin Use)", insulin, isEditing) { insulin = it }
//        EditableProfileRow("最近 HbA1c (Latest HbA1c)", hba1c, isEditing) { hba1c = it }
//
//        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
//        Column(modifier = Modifier.fillMaxWidth().background(Color(0xFFFFF3E0), RoundedCornerShape(8.dp)).padding(12.dp)) {
//            Text("🔔 智能提醒与打断设置\n(Smart Reminders)", fontWeight = FontWeight.Bold, color = Color(0xFFE65100), fontSize = 14.sp)
//            Text("自主设定系统何时打断你 (Customize prompts)", fontSize = 11.sp, color = Color.Gray, modifier = Modifier.padding(bottom = 8.dp))
//            EditableProfileRow("触发时机 (Trigger Timing)", reminderTime, isEditing) { reminderTime = it }
//            EditableProfileRow("提醒频率 (Frequency)", reminderFrequency, isEditing) { reminderFrequency = it }
//
//            Spacer(Modifier.height(12.dp))
//            Button(onClick = { showMockPrompt = true }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE65100))) {
//                Text("🔬 测试打断协商机制 (Test Notification)", fontSize = 12.sp)
//            }
//        }
//
//        Spacer(Modifier.height(40.dp))
//        Text("With V3 - Research Edition", color = Color.LightGray, fontSize = 12.sp, modifier = Modifier.padding(top = 20.dp, bottom = 40.dp))
//    }
//}
//
//@Composable
//fun EditableProfileRow(label: String, value: String, isEditing: Boolean, onValueChange: (String) -> Unit) {
//    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
//        Text(label, color = Color.Gray, modifier = Modifier.weight(0.45f), fontSize = 12.sp)
//        if (isEditing) {
//            OutlinedTextField(value = value, onValueChange = onValueChange, modifier = Modifier.weight(0.55f).height(50.dp), singleLine = true, textStyle = androidx.compose.ui.text.TextStyle(fontSize = 12.sp, textAlign = TextAlign.End))
//        } else {
//            Text(value, fontWeight = FontWeight.Medium, modifier = Modifier.weight(0.55f), textAlign = TextAlign.End, fontSize = 12.sp)
//        }
//    }
//}
//
//// 🧬 修改 ChatMessage 数据结构以支持探针
//data class ChatMessage(val text: String, val isUser: Boolean, val isSupportive: Boolean = false)
//
//@Composable
//fun ChatBubble(message: ChatMessage) {
//    var feedbackGiven by remember { mutableStateOf(false) }
//
//    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = if (message.isUser) Alignment.End else Alignment.Start) {
//        Box(modifier = Modifier.padding(8.dp).clip(RoundedCornerShape(12.dp)).background(if (message.isUser) Color(0xFF008080) else Color.White).padding(12.dp)) {
//            Text(message.text, color = if (message.isUser) Color.White else Color.Black)
//        }
//        // 🔬 探针 UI：情感支持效能 (Affective Efficacy)
//        if (!message.isUser && message.isSupportive && !feedbackGiven) {
//            Row(modifier = Modifier.padding(start = 12.dp), verticalAlignment = Alignment.CenterVertically) {
//                Text("🔬 这句话对你有安慰吗？", fontSize = 10.sp, color = Color.Gray)
//                TextButton(onClick = { feedbackGiven = true }) { Text("👍 有用", fontSize = 10.sp) }
//                TextButton(onClick = { feedbackGiven = true }) { Text("👎 没用", fontSize = 10.sp, color = Color.Gray) }
//            }
//        }
//    }
//}

package com.withapp.with.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Home
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
// 🔴 这里加好了你要的参数，完美对接 MainActivity！
fun MainContainer(onRequestPermission: () -> Unit = {}) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var selectedTab by remember { mutableStateOf("home") }
    val reportViewModel: ReportViewModel = viewModel()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(modifier = Modifier.width(320.dp), drawerContainerColor = Color(0xFFF7F9FA)) {
                ProfileSidebarContent(reportViewModel)
            }
        }
    ) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("With", fontWeight = FontWeight.Bold) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.AccountCircle, contentDescription = null, tint = Color(0xFF008080), modifier = Modifier.size(32.dp))
                        }
                    }
                )
            },
            bottomBar = {
                NavigationBar(containerColor = Color.White) {
                    NavigationBarItem(selected = selectedTab == "home", onClick = { selectedTab = "home" }, icon = { Icon(Icons.Default.Home, null) }, label = { Text("主页") })
                    NavigationBarItem(selected = selectedTab == "report", onClick = { selectedTab = "report" }, icon = { Icon(Icons.Default.Assessment, null) }, label = { Text("报告") })
                }
            }
        ) { padding ->
            Box(modifier = Modifier.padding(padding)) {
                when (selectedTab) {
                    "home" -> HomeV3View(reportViewModel, snackbarHostState)
                    "report" -> ReportView(reportViewModel, onBack = { selectedTab = "home" })
                }
            }
        }
    }
}

@Composable
fun HomeV3View(reportViewModel: ReportViewModel, snackbarHostState: SnackbarHostState) {
    val scope = rememberCoroutineScope()
    var inputText by remember { mutableStateOf("") }
    val chatMessages = remember { mutableStateListOf(ChatMessage("嗨，今天感觉如何？你可以告诉我刚才吃了什么，或者直接点击下方快捷按钮进行记录。", false, true)) }

    // 🔬 研究探针：交互负荷探测
    var showProbe by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF7F9FA))) {
        // V3 快捷操作区
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            QuickActionChip("🍱 记饮食", Color(0xFF81C784)) {
                inputText = "我刚吃了..."
                scope.launch { snackbarHostState.showSnackbar("已为您准备好饮食记录模版") }
            }
            QuickActionChip("🧘 记心情", Color(0xFFFFB74D)) {
                inputText = "我现在觉得..."
                scope.launch { snackbarHostState.showSnackbar("您可以描述现在的心情得分") }
            }
            QuickActionChip("🩸 记血糖", Color(0xFF4FC3F7)) {
                inputText = "我刚测了血糖，数值是..."
            }
        }

        LazyColumn(modifier = Modifier.weight(1f).padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(chatMessages) { ChatBubble(it) }
        }

        if (showProbe) {
            ResearchProbeCard { showProbe = false }
        }

        Row(modifier = Modifier.background(Color.White).padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                placeholder = { Text("直接描述或记录...", fontSize = 13.sp) },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(24.dp)
            )
            IconButton(onClick = {
                if (inputText.isNotBlank()) {
                    val reply = reportViewModel.processChatInput(inputText)
                    chatMessages.add(ChatMessage(inputText, true))
                    chatMessages.add(ChatMessage(reply, false, true))
                    inputText = ""
                    showProbe = true
                    scope.launch { snackbarHostState.showSnackbar("数据已存入数据库") }
                }
            }) { Icon(Icons.AutoMirrored.Filled.Send, null, tint = Color(0xFF008080)) }
        }
    }
}

@Composable
fun QuickActionChip(label: String, color: Color, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        color = color.copy(alpha = 0.15f),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, color.copy(alpha = 0.5f))
    ) {
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
fun ProfileSidebarContent(reportViewModel: ReportViewModel) {
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

    var showMockPrompt by remember { mutableStateOf(false) }

    if (showMockPrompt) {
        AlertDialog(
            onDismissRequest = { showMockPrompt = false },
            title = { Text("🐰 With 智能提醒", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
            text = { Text("系统检测到您刚吃完午餐。现在方便记录一下血糖和情绪吗？", fontSize = 14.sp) },
            confirmButton = { Button(onClick = { showMockPrompt = false }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF008080))) { Text("去记录") } },
            dismissButton = {
                Row {
                    TextButton(onClick = { showMockPrompt = false }) { Text("稍后 15m", color = Color.Gray) }
                    TextButton(onClick = { showMockPrompt = false }) { Text("不方便", color = Color.Red) }
                }
            }
        )
    }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(scrollState).padding(24.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("个人档案与设置\n(Profile)", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF008080))
            TextButton(onClick = {
                if (isEditing) {
                    reportViewModel.userProfile.value = UserProfile(age, gender, height, weight, diagnosis, insulin, medication, targetRange, hba1c, reminderTime, reminderFrequency)
                }
                isEditing = !isEditing
            }) { Text(if (isEditing) "保存" else "编辑", color = Color(0xFF008080), fontWeight = FontWeight.Bold) }
        }

        Spacer(Modifier.height(16.dp))
        Text("基本信息", fontWeight = FontWeight.Bold, color = Color(0xFF008080), fontSize = 14.sp)
        EditableProfileRow("年龄", age, isEditing) { age = it }
        EditableProfileRow("性别", gender, isEditing) { gender = it }
        EditableProfileRow("身高", height, isEditing) { height = it }
        EditableProfileRow("体重", weight, isEditing) { weight = it }

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
        Text("健康背景", fontWeight = FontWeight.Bold, color = Color(0xFF008080), fontSize = 14.sp)
        EditableProfileRow("确诊时间", diagnosis, isEditing) { diagnosis = it }
        EditableProfileRow("使用胰岛素", insulin, isEditing) { insulin = it }
        EditableProfileRow("用药情况", medication, isEditing) { medication = it }
        EditableProfileRow("目标血糖", targetRange, isEditing) { targetRange = it }
        EditableProfileRow("最近HbA1c", hba1c, isEditing) { hba1c = it }

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
        Column(modifier = Modifier.fillMaxWidth().background(Color(0xFFFFF3E0), RoundedCornerShape(8.dp)).padding(12.dp)) {
            Text("🔔 智能提醒设置", fontWeight = FontWeight.Bold, color = Color(0xFFE65100), fontSize = 14.sp)
            EditableProfileRow("触发时机", reminderTime, isEditing) { reminderTime = it }
            EditableProfileRow("提醒频率", reminderFrequency, isEditing) { reminderFrequency = it }
            Spacer(Modifier.height(12.dp))
            Button(onClick = { showMockPrompt = true }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE65100))) {
                Text("🔬 测试打断机制", fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun EditableProfileRow(label: String, value: String, isEditing: Boolean, onValueChange: (String) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(label, color = Color.Gray, modifier = Modifier.weight(0.45f), fontSize = 12.sp)
        if (isEditing) {
            OutlinedTextField(value = value, onValueChange = onValueChange, modifier = Modifier.weight(0.55f).height(50.dp), singleLine = true, textStyle = TextStyle(fontSize = 12.sp, textAlign = TextAlign.End))
        } else {
            Text(value, fontWeight = FontWeight.Medium, modifier = Modifier.weight(0.55f), textAlign = TextAlign.End, fontSize = 12.sp)
        }
    }
}