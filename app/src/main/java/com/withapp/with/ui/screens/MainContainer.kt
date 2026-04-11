//
//
//package com.withapp.with.ui.screens
//
//import androidx.compose.foundation.background
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
//                    NavigationBarItem(selected = selectedTab == "home", onClick = { selectedTab = "home" }, icon = { Icon(Icons.Default.Home, null) }, label = { Text("主页") })
//                    NavigationBarItem(selected = selectedTab == "report", onClick = { selectedTab = "report" }, icon = { Icon(Icons.Default.Assessment, null) }, label = { Text("报告") })
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
//    val chatMessages = remember { mutableStateListOf(ChatMessage("嗨！我是 With。直接告诉我数据，或者点击左上角头像，去自定义你的专属【提醒时间与频率】吧！", false)) }
//
//    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF7F9FA))) {
//        Column(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
//            Text("陪你看见情绪，也看见血糖", color = Color.Gray, fontSize = 14.sp)
//            Box(modifier = Modifier.size(60.dp).clip(CircleShape).background(Color.White), contentAlignment = Alignment.Center) { Text("🐰", fontSize = 30.sp) }
//        }
//        LazyColumn(modifier = Modifier.weight(1f).padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
//            items(chatMessages) { msg -> ChatBubble(message = msg) }
//        }
//        Column(modifier = Modifier.background(Color.White).padding(16.dp)) {
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                OutlinedTextField(
//                    value = inputText,
//                    onValueChange = { inputText = it },
//                    placeholder = { Text("支持输入时间(如 14:30)和多维数据...") },
//                    modifier = Modifier.weight(1f),
//                    shape = RoundedCornerShape(24.dp)
//                )
//                IconButton(onClick = {
//                    val text = inputText.text
//                    if (text.isNotBlank()) {
//                        chatMessages.add(ChatMessage(text, true))
//                        reportViewModel.processChatInput(text)
//                        chatMessages.add(ChatMessage("✅ 已精准拆解记录！数据未出现记串，已分发至最新的多维关联报表中。", false))
//                        inputText = TextFieldValue("")
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
//
//    // 🔔 提醒功能数据绑定
//    var reminderTime by remember(profile) { mutableStateOf(profile.reminderTime) }
//    var reminderFrequency by remember(profile) { mutableStateOf(profile.reminderFrequency) }
//
//    Column(modifier = Modifier.fillMaxSize().verticalScroll(scrollState).padding(24.dp)) {
//        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
//            Text("个人档案与设置", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF008080))
//            TextButton(onClick = {
//                if (isEditing) reportViewModel.updateProfile(UserProfile(age, gender, height, weight, diagnosis, insulin, hba1c, reminderTime, reminderFrequency))
//                isEditing = !isEditing
//            }) { Text(if (isEditing) "保存" else "编辑", color = Color(0xFF008080), fontWeight = FontWeight.Bold) }
//        }
//
//        Spacer(Modifier.height(16.dp))
//        Text("基本信息", fontWeight = FontWeight.Bold, color = Color(0xFF008080), fontSize = 14.sp)
//        EditableProfileRow("年龄", age, isEditing) { age = it }; EditableProfileRow("性别", gender, isEditing) { gender = it }
//        EditableProfileRow("身高", height, isEditing) { height = it }; EditableProfileRow("体重", weight, isEditing) { weight = it }
//
//        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
//        Text("健康背景", fontWeight = FontWeight.Bold, color = Color(0xFF008080), fontSize = 14.sp)
//        EditableProfileRow("确诊时间", diagnosis, isEditing) { diagnosis = it }
//        EditableProfileRow("使用胰岛素", insulin, isEditing) { insulin = it }
//        EditableProfileRow("最近 HbA1c", hba1c, isEditing) { hba1c = it }
//
//        // 🔔 核心加装：HCI 智能提醒打断管理面板！绝对不会再被隐藏！
//        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
//        Column(modifier = Modifier.fillMaxWidth().background(Color(0xFFFFF3E0), RoundedCornerShape(8.dp)).padding(12.dp)) {
//            Text("🔔 智能提醒与打断设置", fontWeight = FontWeight.Bold, color = Color(0xFFE65100), fontSize = 14.sp)
//            Text("自主设定系统何时打断你", fontSize = 11.sp, color = Color.Gray, modifier = Modifier.padding(bottom = 8.dp))
//            EditableProfileRow("触发时机 (如:饭后30分)", reminderTime, isEditing) { reminderTime = it }
//            EditableProfileRow("提醒频率 (如:每天3次)", reminderFrequency, isEditing) { reminderFrequency = it }
//        }
//
//        // 修复截断Bug：使用固定高度替代弹性的 Modifier.weight(1f)
//        Spacer(Modifier.height(40.dp))
//        Text("With v2.0 - 始终同行", color = Color.LightGray, fontSize = 12.sp, modifier = Modifier.padding(top = 20.dp, bottom = 40.dp))
//    }
//}
//
//@Composable
//fun EditableProfileRow(label: String, value: String, isEditing: Boolean, onValueChange: (String) -> Unit) {
//    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
//        Text(label, color = Color.Gray, modifier = Modifier.weight(0.45f), fontSize = 13.sp)
//        if (isEditing) {
//            OutlinedTextField(value = value, onValueChange = onValueChange, modifier = Modifier.weight(0.55f).height(50.dp), singleLine = true, textStyle = androidx.compose.ui.text.TextStyle(fontSize = 13.sp, textAlign = TextAlign.End))
//        } else {
//            Text(value, fontWeight = FontWeight.Medium, modifier = Modifier.weight(0.55f), textAlign = TextAlign.End, fontSize = 13.sp)
//        }
//    }
//}
//
//data class ChatMessage(val text: String, val isUser: Boolean)
//
//@Composable
//fun ChatBubble(message: ChatMessage) {
//    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start) {
//        Box(modifier = Modifier.padding(8.dp).clip(RoundedCornerShape(12.dp)).background(if (message.isUser) Color(0xFF008080) else Color.White).padding(12.dp)) {
//            Text(message.text, color = if (message.isUser) Color.White else Color.Black)
//        }
//    }
//}

package com.withapp.with.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.withapp.with.viewmodels.ReportViewModel
import com.withapp.with.viewmodels.UserProfile
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContainer(onRequestPermission: () -> Unit = {}) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedTab by remember { mutableStateOf("home") }
    val reportViewModel: ReportViewModel = viewModel()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { ModalDrawerSheet { ProfileSidebarContent(reportViewModel) } }
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("With", fontWeight = FontWeight.Bold) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.AccountCircle, null, tint = Color(0xFF008080), modifier = Modifier.size(32.dp))
                        }
                    }
                )
            },
            bottomBar = {
                NavigationBar(containerColor = Color.White) {
                    NavigationBarItem(selected = selectedTab == "home", onClick = { selectedTab = "home" }, icon = { Icon(Icons.Default.Home, null) }, label = { Text("主页 (Home)") })
                    NavigationBarItem(selected = selectedTab == "report", onClick = { selectedTab = "report" }, icon = { Icon(Icons.Default.Assessment, null) }, label = { Text("报告 (Report)") })
                }
            }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                when (selectedTab) {
                    "home" -> HomeChatCombinedView(reportViewModel)
                    "report" -> ReportView(reportViewModel = reportViewModel, onBack = { selectedTab = "home" })
                }
            }
        }
    }
}

@Composable
fun HomeChatCombinedView(reportViewModel: ReportViewModel) {
    var inputText by remember { mutableStateOf(TextFieldValue("")) }
    val chatMessages = remember { mutableStateListOf(ChatMessage("嗨！我是 With。直接告诉我数据，或者点击左上角头像去自定义【提醒时间与频率 (Reminders)】吧！", false)) }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF7F9FA))) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("陪你看见情绪，也看见血糖 (See your mood, see your glucose)", color = Color.Gray, fontSize = 12.sp)
            Spacer(Modifier.height(8.dp))
            Box(modifier = Modifier.size(60.dp).clip(CircleShape).background(Color.White), contentAlignment = Alignment.Center) { Text("🐰", fontSize = 30.sp) }
        }
        LazyColumn(modifier = Modifier.weight(1f).padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(chatMessages) { msg -> ChatBubble(message = msg) }
        }
        Column(modifier = Modifier.background(Color.White).padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    placeholder = { Text("支持输入时间与多维数据 (Input data & time)...", fontSize = 13.sp) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(24.dp)
                )
                IconButton(onClick = {
                    val text = inputText.text
                    if (text.isNotBlank()) {
                        chatMessages.add(ChatMessage(text, true))
                        reportViewModel.processChatInput(text)
                        chatMessages.add(ChatMessage("✅ 已精准拆解记录！数据未出现记串，已分发至最新的多维关联报表中 (Data synced successfully)。", false))
                        inputText = TextFieldValue("")
                    }
                }) { Icon(Icons.AutoMirrored.Filled.Send, null, tint = Color(0xFF008080)) }
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
    var hba1c by remember(profile) { mutableStateOf(profile.hba1c) }
    var reminderTime by remember(profile) { mutableStateOf(profile.reminderTime) }
    var reminderFrequency by remember(profile) { mutableStateOf(profile.reminderFrequency) }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(scrollState).padding(24.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("个人档案与设置\n(Profile & Config)", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF008080))
            TextButton(onClick = {
                if (isEditing) reportViewModel.updateProfile(UserProfile(age, gender, height, weight, diagnosis, insulin, hba1c, reminderTime, reminderFrequency))
                isEditing = !isEditing
            }) { Text(if (isEditing) "保存 (Save)" else "编辑 (Edit)", color = Color(0xFF008080), fontWeight = FontWeight.Bold) }
        }

        Spacer(Modifier.height(16.dp))
        Text("基本信息 (Basic Info)", fontWeight = FontWeight.Bold, color = Color(0xFF008080), fontSize = 14.sp)
        EditableProfileRow("年龄 (Age)", age, isEditing) { age = it }; EditableProfileRow("性别 (Gender)", gender, isEditing) { gender = it }
        EditableProfileRow("身高 (Height)", height, isEditing) { height = it }; EditableProfileRow("体重 (Weight)", weight, isEditing) { weight = it }

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
        Text("健康背景 (Health Background)", fontWeight = FontWeight.Bold, color = Color(0xFF008080), fontSize = 14.sp)
        EditableProfileRow("确诊时间 (Diagnosis Date)", diagnosis, isEditing) { diagnosis = it }
        EditableProfileRow("使用胰岛素 (Insulin Use)", insulin, isEditing) { insulin = it }
        EditableProfileRow("最近 HbA1c (Latest HbA1c)", hba1c, isEditing) { hba1c = it }

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
        Column(modifier = Modifier.fillMaxWidth().background(Color(0xFFFFF3E0), RoundedCornerShape(8.dp)).padding(12.dp)) {
            Text("🔔 智能提醒与打断设置\n(Smart Reminders)", fontWeight = FontWeight.Bold, color = Color(0xFFE65100), fontSize = 14.sp)
            Text("自主设定系统何时打断你 (Customize prompts)", fontSize = 11.sp, color = Color.Gray, modifier = Modifier.padding(bottom = 8.dp))
            EditableProfileRow("触发时机 (Trigger Timing)", reminderTime, isEditing) { reminderTime = it }
            EditableProfileRow("提醒频率 (Frequency)", reminderFrequency, isEditing) { reminderFrequency = it }
        }

        Spacer(Modifier.height(40.dp))
        Text("With v2.0 - 始终同行 (Always with you)", color = Color.LightGray, fontSize = 12.sp, modifier = Modifier.padding(top = 20.dp, bottom = 40.dp))
    }
}

@Composable
fun EditableProfileRow(label: String, value: String, isEditing: Boolean, onValueChange: (String) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(label, color = Color.Gray, modifier = Modifier.weight(0.45f), fontSize = 12.sp)
        if (isEditing) {
            OutlinedTextField(value = value, onValueChange = onValueChange, modifier = Modifier.weight(0.55f).height(50.dp), singleLine = true, textStyle = androidx.compose.ui.text.TextStyle(fontSize = 12.sp, textAlign = TextAlign.End))
        } else {
            Text(value, fontWeight = FontWeight.Medium, modifier = Modifier.weight(0.55f), textAlign = TextAlign.End, fontSize = 12.sp)
        }
    }
}

data class ChatMessage(val text: String, val isUser: Boolean)

@Composable
fun ChatBubble(message: ChatMessage) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start) {
        Box(modifier = Modifier.padding(8.dp).clip(RoundedCornerShape(12.dp)).background(if (message.isUser) Color(0xFF008080) else Color.White).padding(12.dp)) {
            Text(message.text, color = if (message.isUser) Color.White else Color.Black)
        }
    }
}