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
//    val chatMessages = remember { mutableStateListOf(ChatMessage("嗨！我是 With。我现在能听懂复杂句子了！试试说：『吃了 40g 碳水，测了下血糖 7.2，心情很好』", false)) }
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
//                    placeholder = { Text("输入血糖、饮食、运动或多项记录...") },
//                    modifier = Modifier.weight(1f),
//                    shape = RoundedCornerShape(24.dp)
//                )
//                IconButton(onClick = {
//                    val text = inputText.text
//                    if (text.isNotBlank()) {
//                        chatMessages.add(ChatMessage(text, true))
//                        reportViewModel.processChatInput(text) // ✨ 并行同步引擎
//                        chatMessages.add(ChatMessage("✅ 已多维解析！所有提及的数据点均已分类归档到最新图表。", false))
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
//    val profile = reportViewModel.userProfile.value
//    var age by remember(profile) { mutableStateOf(profile.age) }
//    var gender by remember(profile) { mutableStateOf(profile.gender) }
//    var height by remember(profile) { mutableStateOf(profile.height) }
//    var weight by remember(profile) { mutableStateOf(profile.weight) }
//    var diagnosis by remember(profile) { mutableStateOf(profile.diagnosisDate) }
//    var insulin by remember(profile) { mutableStateOf(profile.insulinUse) }
//    var hba1c by remember(profile) { mutableStateOf(profile.hba1c) }
//
//    Column(modifier = Modifier.fillMaxSize().verticalScroll(scrollState).padding(24.dp)) {
//        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
//            Text("个人档案", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF008080))
//            TextButton(onClick = {
//                if (isEditing) reportViewModel.updateProfile(UserProfile(age, gender, height, weight, diagnosis, insulin, hba1c))
//                isEditing = !isEditing
//            }) { Text(if (isEditing) "保存" else "编辑", color = Color(0xFF008080), fontWeight = FontWeight.Bold) }
//        }
//        Spacer(Modifier.height(16.dp))
//        Text("基本信息", fontWeight = FontWeight.Bold, color = Color(0xFF008080), fontSize = 14.sp)
//        EditableProfileRow("年龄", age, isEditing) { age = it }; EditableProfileRow("性别", gender, isEditing) { gender = it }
//        EditableProfileRow("身高", height, isEditing) { height = it }; EditableProfileRow("体重", weight, isEditing) { weight = it }
//        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
//        Text("健康背景", fontWeight = FontWeight.Bold, color = Color(0xFF008080), fontSize = 14.sp)
//        EditableProfileRow("确诊时间", diagnosis, isEditing) { diagnosis = it }
//        EditableProfileRow("使用胰岛素", insulin, isEditing) { insulin = it }
//        EditableProfileRow("最近 HbA1c", hba1c, isEditing) { hba1c = it }
//        Spacer(Modifier.weight(1f)); Text("With v2.0 - 始终同行", color = Color.LightGray, fontSize = 12.sp, modifier = Modifier.padding(top = 20.dp))
//    }
//}
//
//@Composable
//fun EditableProfileRow(label: String, value: String, isEditing: Boolean, onValueChange: (String) -> Unit) {
//    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
//        Text(label, color = Color.Gray, modifier = Modifier.weight(0.4f))
//        if (isEditing) {
//            OutlinedTextField(value = value, onValueChange = onValueChange, modifier = Modifier.weight(0.6f), singleLine = true, textStyle = androidx.compose.ui.text.TextStyle(fontSize = 14.sp, textAlign = TextAlign.End))
//        } else {
//            Text(value, fontWeight = FontWeight.Medium, modifier = Modifier.weight(0.6f), textAlign = TextAlign.End)
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
                    NavigationBarItem(selected = selectedTab == "home", onClick = { selectedTab = "home" }, icon = { Icon(Icons.Default.Home, null) }, label = { Text("主页") })
                    NavigationBarItem(selected = selectedTab == "report", onClick = { selectedTab = "report" }, icon = { Icon(Icons.Default.Assessment, null) }, label = { Text("报告") })
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
    val chatMessages = remember { mutableStateListOf(ChatMessage("嗨！试试一次发多条：『跑了 30 分钟，测了血糖 6.2，心情很不错』，我会智能拆解到各个图表中！", false)) }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF7F9FA))) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("陪你看见情绪，也看见血糖", color = Color.Gray, fontSize = 14.sp)
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
                    placeholder = { Text("记录生活与健康...") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(24.dp)
                )
                IconButton(onClick = {
                    val text = inputText.text
                    if (text.isNotBlank()) {
                        chatMessages.add(ChatMessage(text, true))
                        reportViewModel.processChatInput(text)
                        chatMessages.add(ChatMessage("✅ 已精准拆解记录！数据未出现记串，已分发至各项报表。", false))
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

    Column(modifier = Modifier.fillMaxSize().verticalScroll(scrollState).padding(24.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("个人档案", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF008080))
            TextButton(onClick = {
                if (isEditing) reportViewModel.updateProfile(UserProfile(age, gender, height, weight, diagnosis, insulin, hba1c))
                isEditing = !isEditing
            }) { Text(if (isEditing) "保存" else "编辑", color = Color(0xFF008080), fontWeight = FontWeight.Bold) }
        }
        Spacer(Modifier.height(16.dp))
        Text("基本信息", fontWeight = FontWeight.Bold, color = Color(0xFF008080), fontSize = 14.sp)
        EditableProfileRow("年龄", age, isEditing) { age = it }; EditableProfileRow("性别", gender, isEditing) { gender = it }
        EditableProfileRow("身高", height, isEditing) { height = it }; EditableProfileRow("体重", weight, isEditing) { weight = it }
        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
        Text("健康背景", fontWeight = FontWeight.Bold, color = Color(0xFF008080), fontSize = 14.sp)
        EditableProfileRow("确诊时间", diagnosis, isEditing) { diagnosis = it }
        EditableProfileRow("使用胰岛素", insulin, isEditing) { insulin = it }
        EditableProfileRow("最近 HbA1c", hba1c, isEditing) { hba1c = it }
        Spacer(Modifier.weight(1f)); Text("With v2.0 - 始终同行", color = Color.LightGray, fontSize = 12.sp, modifier = Modifier.padding(top = 20.dp))
    }
}

@Composable
fun EditableProfileRow(label: String, value: String, isEditing: Boolean, onValueChange: (String) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(label, color = Color.Gray, modifier = Modifier.weight(0.4f))
        if (isEditing) {
            OutlinedTextField(value = value, onValueChange = onValueChange, modifier = Modifier.weight(0.6f), singleLine = true, textStyle = androidx.compose.ui.text.TextStyle(fontSize = 14.sp, textAlign = TextAlign.End))
        } else {
            Text(value, fontWeight = FontWeight.Medium, modifier = Modifier.weight(0.6f), textAlign = TextAlign.End)
        }
    }
}