
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
//import androidx.compose.ui.text.font.FontWeight // FIXED: FontWeight Import
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.withapp.with.viewmodels.ReportViewModel
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
//        drawerContent = { ModalDrawerSheet { ProfileSidebarContent() } }
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
//    // FIXED: Standard state management for Chinese IME support
//    var inputText by remember { mutableStateOf("") }
//    val chatMessages = remember { mutableStateListOf(ChatMessage("嗨！我是 With。输入『吃了午餐』或『心情不错』，我会自动同步报告。", false)) }
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
//                // FIXED: Better keyboard interaction
//                OutlinedTextField(
//                    value = inputText,
//                    onValueChange = { inputText = it },
//                    placeholder = { Text("输入记录...") },
//                    modifier = Modifier.weight(1f),
//                    shape = RoundedCornerShape(24.dp)
//                )
//                IconButton(onClick = {
//                    if (inputText.isNotBlank()) {
//                        val input = inputText
//                        chatMessages.add(ChatMessage(input, true))
//                        reportViewModel.processChatInput(input)
//                        chatMessages.add(ChatMessage("收到！相关信息已自动同步至健康看板。", false))
//                        inputText = ""
//                    }
//                }) { Icon(Icons.AutoMirrored.Filled.Send, null, tint = Color(0xFF008080)) }
//            }
//        }
//    }
//}
//
//@Composable
//fun ProfileSidebarContent() {
//    val scrollState = rememberScrollState()
//    Column(modifier = Modifier.fillMaxSize().verticalScroll(scrollState).padding(24.dp)) {
//        Text("个人档案", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF008080))
//        Spacer(Modifier.height(24.dp))
//        Text("基本信息", fontWeight = FontWeight.Bold, color = Color(0xFF008080), fontSize = 14.sp)
//        ProfileInfoRow("年龄", "28 岁"); ProfileInfoRow("性别", "女"); ProfileInfoRow("身高", "175 cm"); ProfileInfoRow("体重", "70 kg")
//        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
//        Text("健康背景", fontWeight = FontWeight.Bold, color = Color(0xFF008080), fontSize = 14.sp)
//        ProfileInfoRow("确诊时间", "2023 年 5 月"); ProfileInfoRow("使用胰岛素", "是"); ProfileInfoRow("最近 HbA1c", "6.2 %")
//        Spacer(Modifier.weight(1f)); Text("With v2.0 - 始终同行", color = Color.LightGray, fontSize = 12.sp)
//    }
//}
//
//@Composable
//fun ProfileInfoRow(label: String, value: String) {
//    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
//        Text(label, color = Color.Gray); Text(value, fontWeight = FontWeight.Medium)
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
import androidx.compose.ui.text.input.TextFieldValue // 解决中文输入法的关键
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.withapp.with.viewmodels.ReportViewModel
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
        drawerContent = { ModalDrawerSheet { ProfileSidebarContent() } }
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
    // 使用 TextFieldValue 彻底解决中文输入法状态丢失的问题
    var inputText by remember { mutableStateOf(TextFieldValue("")) }
    val chatMessages = remember { mutableStateListOf(ChatMessage("嗨！试着说『吃了面条碳水 60』或『跑了 45 分钟』，我会立刻提取数字并画在图表上！", false)) }

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
                    placeholder = { Text("输入带有数字的记录...") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(24.dp)
                )
                IconButton(onClick = {
                    val text = inputText.text
                    if (text.isNotBlank()) {
                        chatMessages.add(ChatMessage(text, true))
                        reportViewModel.processChatInput(text) // 触发图表和列表实时更新！
                        chatMessages.add(ChatMessage("✅ 已识别并提取数值！去【报告】页看看你的专属图表吧。", false))
                        inputText = TextFieldValue("")
                    }
                }) { Icon(Icons.AutoMirrored.Filled.Send, null, tint = Color(0xFF008080)) }
            }
        }
    }
}

@Composable
fun ProfileSidebarContent() {
    val scrollState = rememberScrollState()
    Column(modifier = Modifier.fillMaxSize().verticalScroll(scrollState).padding(24.dp)) {
        Text("个人档案", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF008080))
        Spacer(Modifier.height(24.dp))
        Text("基本信息", fontWeight = FontWeight.Bold, color = Color(0xFF008080), fontSize = 14.sp)
        ProfileInfoRow("年龄", "28 岁"); ProfileInfoRow("性别", "女"); ProfileInfoRow("身高", "175 cm"); ProfileInfoRow("体重", "70 kg")
        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
        Text("健康背景", fontWeight = FontWeight.Bold, color = Color(0xFF008080), fontSize = 14.sp)
        ProfileInfoRow("确诊时间", "2023 年 5 月"); ProfileInfoRow("使用胰岛素", "是"); ProfileInfoRow("最近 HbA1c", "6.2 %")
        Spacer(Modifier.weight(1f)); Text("With v2.0 - 始终同行", color = Color.LightGray, fontSize = 12.sp)
    }
}

@Composable
fun ProfileInfoRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, color = Color.Gray); Text(value, fontWeight = FontWeight.Medium)
    }
}