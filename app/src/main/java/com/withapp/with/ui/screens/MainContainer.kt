
//
//package com.withapp.with.ui.screens
//
//import androidx.compose.foundation.BorderStroke
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
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
//import androidx.compose.material.icons.filled.Mic
//import androidx.compose.material.icons.filled.ArrowDropDown
//import androidx.compose.material.icons.filled.Security
//import androidx.compose.material.icons.filled.DeleteForever
//import androidx.compose.material.icons.filled.Download
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
//import androidx.compose.ui.semantics.* import androidx.lifecycle.viewmodel.compose.viewModel
//import com.withapp.with.viewmodels.ReportViewModel
//import com.withapp.with.viewmodels.UserProfile
//import kotlinx.coroutines.launch
//import java.util.Calendar
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun MainContainer(onRequestPermission: () -> Unit = {}) {
//    val snackbarHostState = remember { SnackbarHostState() }
//    var selectedTab by remember { mutableStateOf("home") }
//    val reportViewModel: ReportViewModel = viewModel()
//
//    Scaffold(
//        snackbarHost = { SnackbarHost(snackbarHostState) },
//        topBar = {
//            CenterAlignedTopAppBar(
//                title = {
//                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.semantics(mergeDescendants = true) {}) {
//                        Text("With", fontWeight = FontWeight.Bold, color = Color(0xFF008080), fontSize = 18.sp)
//                        Text("您的数字健康共伴向导", fontSize = 10.sp, color = Color.Gray)
//                    }
//                }
//            )
//        },
//        bottomBar = {
//            NavigationBar(containerColor = Color.White) {
//                NavigationBarItem(selected = selectedTab == "home", onClick = { selectedTab = "home" }, icon = { Icon(Icons.Default.Home, contentDescription = "切换到主页") }, label = { Text("主页") })
//                NavigationBarItem(selected = selectedTab == "report", onClick = { selectedTab = "report" }, icon = { Icon(Icons.Default.Assessment, contentDescription = "切换到健康回顾页") }, label = { Text("回顾") })
//                NavigationBarItem(selected = selectedTab == "profile", onClick = { selectedTab = "profile" }, icon = { Icon(Icons.Default.AccountCircle, contentDescription = "切换到个人档案与设置页") }, label = { Text("档案") })
//            }
//        }
//    ) { padding ->
//        Box(modifier = Modifier.padding(padding)) {
//            when (selectedTab) {
//                "home" -> HomeV3View(reportViewModel, snackbarHostState)
//                "report" -> ReportView(reportViewModel, snackbarHostState)
//                "profile" -> ProfileView(reportViewModel, snackbarHostState)
//            }
//        }
//    }
//}
//
//@Composable
//fun HomeV3View(reportViewModel: ReportViewModel, snackbarHostState: SnackbarHostState) {
//    val scope = rememberCoroutineScope()
//    var inputText by remember { mutableStateOf("") }
//    val chatMessages = remember { mutableStateListOf(ChatMessage("嗨，今天感觉如何？你可以像聊天一样告诉我刚才吃了什么、心情怎样，我会自动帮你整理。", false, true)) }
//    var showProbe by remember { mutableStateOf(false) }
//    var showRabbitInfo by remember { mutableStateOf(false) }
//
//    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
//    val dynamicAvatar = when {
//        hour in 6..10 -> "🐰✨"
//        hour in 22..23 || hour in 0..5 -> "🦉💤"
//        else -> "🐰"
//    }
//
//    val dynamicPlaceholder = when {
//        hour in 6..9 -> "早安！早餐吃了什么？..."
//        hour in 11..13 -> "午餐时间，记录一下碳水摄入吧..."
//        hour in 17..19 -> "晚餐吃了什么？现在心情如何？..."
//        hour > 20 -> "夜深了，记录一下睡前状态吧..."
//        else -> "用日常对话记录数据..."
//    }
//
//    if (showRabbitInfo) {
//        AlertDialog(onDismissRequest = { showRabbitInfo = false },
//            title = { Text("$dynamicAvatar 关于 With 助手", fontWeight = FontWeight.Bold) },
//            text = { Text("我是您的智能陪伴助手。我会感知您的作息节律。您不需要手动填写冷冰冰的医疗表单，只要像和朋友聊天一样告诉我您的生活点滴，我会自动为您整理成健康回顾。") },
//            confirmButton = { TextButton(onClick = { showRabbitInfo = false }) { Text("了解了", color = Color(0xFF008080)) } }
//        )
//    }
//
//    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF7F9FA))) {
//
//        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
//            Box(modifier = Modifier.size(50.dp).clip(CircleShape).background(Color.White).clickable(onClickLabel = "查看数字向导介绍") { showRabbitInfo = true }, contentAlignment = Alignment.Center) { Text(dynamicAvatar, fontSize = 28.sp) }
//            Spacer(modifier = Modifier.width(12.dp))
//            Column(modifier = Modifier.semantics(mergeDescendants = true) {}) { Text("点击头像了解我", fontSize = 11.sp, color = Color.Gray) }
//        }
//
//        if (reportViewModel.userProfile.value.isVacationMode) {
//            Surface(color = Color(0xFFE8F5E9), modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp).semantics { contentDescription = "休眠模式已开启。您过去的数据完好无损，请放下压力，好好休息。" }, shape = RoundedCornerShape(8.dp)) {
//                Text("🌙 休眠模式已开启。您过去的数据完好无损，请放下压力，好好休息。", fontSize = 12.sp, color = Color(0xFF2E7D32), modifier = Modifier.padding(12.dp))
//            }
//        } else {
//            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//                QuickActionChip("🍱 记饮食", Color(0xFF81C784)) { inputText = "我刚吃了..."; scope.launch { snackbarHostState.showSnackbar("已为您准备好饮食记录模版") } }
//                QuickActionChip("🧘 记心情", Color(0xFFFFB74D)) { inputText = "我现在觉得..."; scope.launch { snackbarHostState.showSnackbar("您可以描述现在的心情") } }
//                QuickActionChip("🩸 记血糖", Color(0xFF4FC3F7)) { inputText = "我刚测了血糖，数值是..." }
//            }
//        }
//
//        LazyColumn(modifier = Modifier.weight(1f).padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
//            items(chatMessages) { msg ->
//                ChatBubble(msg, onUndo = {
//                    val undoMsg = reportViewModel.undoLastAction()
//                    scope.launch { snackbarHostState.showSnackbar(undoMsg) }
//                })
//            }
//
//            if (chatMessages.size <= 1 && !reportViewModel.userProfile.value.isVacationMode) {
//                item {
//                    Column(modifier = Modifier.fillMaxWidth().padding(top = 20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
//                        Text("💡 智能预测建议 (基于您昨天的习惯)：", fontSize = 12.sp, color = Color(0xFF008080), fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
//                        val anticipatorySuggestion = when {
//                            hour in 6..10 -> "「和昨天一样，吃了全麦面包 (30g碳水)」"
//                            hour in 17..20 -> "「今天下班也去慢跑了 20 分钟」"
//                            else -> "「刚才测了血糖，数值和昨天差不多」"
//                        }
//                        SuggestionPill(anticipatorySuggestion) { inputText = anticipatorySuggestion.replace("「", "").replace("」", "") }
//                        SuggestionPill("「开完会好累啊，心情有点低落」") { inputText = "开完会好累啊，心情有点低落" }
//                    }
//                }
//            }
//        }
//
//        if (showProbe) { ResearchProbeCard { showProbe = false } }
//
//        Row(modifier = Modifier.background(Color.White).padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
//            OutlinedTextField(
//                value = inputText, onValueChange = { inputText = it },
//                placeholder = { Text(dynamicPlaceholder, fontSize = 12.sp, color = Color.Gray) },
//                modifier = Modifier.weight(1f).semantics { contentDescription = "数据对话输入框" }, shape = RoundedCornerShape(24.dp),
//                enabled = !reportViewModel.userProfile.value.isVacationMode
//            )
//            IconButton(onClick = { scope.launch { snackbarHostState.showSnackbar("🎙️ 语音输入模块将在未来版本接入") } }) { Icon(Icons.Default.Mic, contentDescription = "使用语音输入（未来版本可用）", tint = Color.Gray) }
//            IconButton(onClick = {
//                if (inputText.isNotBlank()) {
//                    val reply = reportViewModel.processChatInput(inputText)
//                    chatMessages.add(ChatMessage(inputText, true))
//                    chatMessages.add(ChatMessage(reply, false, isSupportive = true, canUndo = true))
//                    inputText = ""; showProbe = true
//                }
//            }, enabled = !reportViewModel.userProfile.value.isVacationMode) { Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "发送您的记录", tint = if(reportViewModel.userProfile.value.isVacationMode) Color.LightGray else Color(0xFF008080)) }
//        }
//    }
//}
//
//@Composable
//fun SuggestionPill(text: String, onClick: () -> Unit) {
//    Surface(onClick = onClick, color = Color.White, shape = RoundedCornerShape(16.dp), border = BorderStroke(1.dp, Color(0xFF008080).copy(alpha=0.3f)), modifier = Modifier.padding(vertical = 4.dp).semantics { contentDescription = "快捷填入建议: $text" }) {
//        Text(text, modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp), fontSize = 11.sp, color = Color(0xFF008080), fontWeight = FontWeight.Medium)
//    }
//}
//
//@Composable
//fun QuickActionChip(label: String, color: Color, onClick: () -> Unit) {
//    Surface(onClick = onClick, color = color.copy(alpha = 0.15f), shape = RoundedCornerShape(16.dp), border = BorderStroke(1.dp, color.copy(alpha = 0.5f)), modifier = Modifier.semantics { contentDescription = "快捷操作: $label" }) {
//        Text(label, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), fontSize = 12.sp, color = color, fontWeight = FontWeight.Bold)
//    }
//}
//
//@Composable
//fun ResearchProbeCard(onDismiss: () -> Unit) {
//    Card(modifier = Modifier.padding(16.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F2F1))) {
//        Column(modifier = Modifier.padding(12.dp).semantics(mergeDescendants = true) {}) {
//            Text("🔬 研究探针：刚才的记录过程让你觉得轻松吗？", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF00695C))
//            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
//                TextButton(onClick = onDismiss) { Text("😊 极简", fontSize = 11.sp) }
//                TextButton(onClick = onDismiss) { Text("😐 一般", fontSize = 11.sp) }
//                TextButton(onClick = onDismiss) { Text("😫 繁琐", fontSize = 11.sp) }
//            }
//        }
//    }
//}
//
//data class ChatMessage(val text: String, val isUser: Boolean, val isSupportive: Boolean = false, val canUndo: Boolean = false)
//
//@Composable
//fun ChatBubble(message: ChatMessage, onUndo: () -> Unit) {
//    var feedbackGiven by remember { mutableStateOf(false) }
//    var hasUndone by remember { mutableStateOf(false) }
//
//    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = if (message.isUser) Alignment.End else Alignment.Start) {
//        Box(modifier = Modifier.padding(8.dp).clip(RoundedCornerShape(12.dp)).background(if (message.isUser) Color(0xFF008080) else Color.White).padding(12.dp).semantics { contentDescription = if (message.isUser) "您发送的记录：${message.text}" else "向导回复：${message.text}" }) {
//            Text(message.text, color = if (message.isUser) Color.White else Color.Black)
//        }
//
//        if (!message.isUser && message.canUndo && !hasUndone) {
//            Text("识别错误？撤销记录", fontSize = 10.sp, color = Color.Red, modifier = Modifier.padding(start = 16.dp).clickable(onClickLabel = "撤销上一条健康数据的系统记录") { onUndo(); hasUndone = true })
//        }
//
//        if (!message.isUser && message.isSupportive && !feedbackGiven) {
//            Row(modifier = Modifier.padding(start = 12.dp), verticalAlignment = Alignment.CenterVertically) {
//                Text("🔬 这个反馈有帮助吗？", fontSize = 10.sp, color = Color.Gray)
//                TextButton(onClick = { feedbackGiven = true }) { Text("👍", fontSize = 10.sp) }
//                TextButton(onClick = { feedbackGiven = true }) { Text("👎", fontSize = 10.sp, color = Color.Gray) }
//            }
//        }
//    }
//}
//
//@Composable
//fun ProfileView(reportViewModel: ReportViewModel, snackbarHostState: SnackbarHostState) {
//    val scrollState = rememberScrollState()
//    val scope = rememberCoroutineScope()
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
//    var isContextAwareAlerts by remember(profile) { mutableStateOf(profile.isContextAwareAlerts) }
//    var isVacationMode by remember(profile) { mutableStateOf(profile.isVacationMode) }
//
//    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF7F9FA)).verticalScroll(scrollState).padding(24.dp)) {
//
//        Text("关怀与系统偏好", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF3F51B5), modifier = Modifier.semantics { heading() })
//        Spacer(Modifier.height(12.dp))
//
//        Column(modifier = Modifier.fillMaxWidth().background(Color(0xFFE8F5E9), RoundedCornerShape(8.dp)).padding(12.dp).semantics(mergeDescendants = true) {}) {
//            Row(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
//                Column(modifier = Modifier.weight(1f)) {
//                    Text("🌙 健康休眠模式", fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32), fontSize = 14.sp)
//                    Text("暂停所有提醒与图表分析，允许自己从数据中喘口气。数据将被安全隐藏。", fontSize = 10.sp, color = Color.Gray, lineHeight = 14.sp)
//                }
//                Switch(checked = isVacationMode, onCheckedChange = { isVacationMode = it; reportViewModel.userProfile.value = reportViewModel.userProfile.value.copy(isVacationMode = it) }, modifier = Modifier.semantics { contentDescription = "开启或关闭健康休眠模式" }, colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF2E7D32), checkedTrackColor = Color(0xFFA5D6A7)))
//            }
//        }
//
//        Spacer(Modifier.height(8.dp))
//        Row(modifier = Modifier.fillMaxWidth().background(Color(0xFFE8EAF6), RoundedCornerShape(8.dp)).padding(12.dp).padding(vertical = 4.dp).semantics(mergeDescendants = true) {}, horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
//            Column(modifier = Modifier.weight(1f)) {
//                Text("开启隐私掩码模式", fontWeight = FontWeight.Bold, fontSize = 13.sp)
//                Text("在公共场合隐藏下方敏感健康数据", fontSize = 10.sp, color = Color.Gray)
//            }
//            Switch(checked = isPrivacyMode, onCheckedChange = { isPrivacyMode = it; reportViewModel.userProfile.value = reportViewModel.userProfile.value.copy(isPrivacyMode = it) }, modifier = Modifier.semantics { contentDescription = "开启或关闭隐私掩码，隐藏敏感数据" }, colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF3F51B5), checkedTrackColor = Color(0xFFC5CAE9)))
//        }
//
//        Spacer(Modifier.height(8.dp))
//        Column(modifier = Modifier.fillMaxWidth().background(Color(0xFFFFF3E0), RoundedCornerShape(8.dp)).padding(12.dp).semantics(mergeDescendants = true) {}) {
//            Row(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
//                Column(modifier = Modifier.weight(1f)) {
//                    Text("🔔 智能感知提醒 (JITAI)", fontWeight = FontWeight.Bold, color = Color(0xFFE65100), fontSize = 14.sp)
//                    Text("根据血糖与心率波动自动预判，取代定时打扰", fontSize = 10.sp, color = Color.Gray, lineHeight = 14.sp)
//                }
//                Switch(checked = isContextAwareAlerts, onCheckedChange = { isContextAwareAlerts = it }, modifier = Modifier.semantics { contentDescription = "开启或关闭基于上下文的智能健康打扰机制" }, colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFFE65100), checkedTrackColor = Color(0xFFFFCC80)), enabled = !isVacationMode)
//            }
//            if (!isContextAwareAlerts && !isVacationMode) {
//                EditableProfileRow("死板提醒时间", reminderTime, isEditing = true, onRowClick = {}) { reminderTime = it }
//                DropdownProfileRow("提醒频率", reminderFrequency, listOf("每天1次", "每天3次", "仅异常时"), isEditing = true) { reminderFrequency = it }
//            }
//        }
//
//        Spacer(Modifier.height(32.dp))
//
//        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
//            Text("个人静态档案", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF008080), modifier = Modifier.semantics { heading() })
//            TextButton(onClick = {
//                if (isEditing) reportViewModel.userProfile.value = UserProfile(age, gender, height, weight, diagnosis, insulin, medication, targetRange, hba1c, reminderTime, reminderFrequency, isPrivacyMode, isContextAwareAlerts, isVacationMode)
//                isEditing = !isEditing
//            }) { Text(if (isEditing) "保存" else "点此编辑档案", color = Color(0xFF008080), fontWeight = FontWeight.Bold) }
//        }
//
//        Spacer(Modifier.height(8.dp))
//        Text("基础体征", fontWeight = FontWeight.Bold, color = Color(0xFF008080), fontSize = 14.sp)
//        val editTrigger = { isEditing = true }
//        EditableProfileRow("年龄", age, isEditing, editTrigger) { age = it }
//        DropdownProfileRow("性别", gender, listOf("男", "女", "其他"), isEditing) { gender = it }
//        EditableProfileRow("身高", height, isEditing, editTrigger) { height = it }
//        EditableProfileRow("体重", weight, isEditing, editTrigger) { weight = it }
//
//        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
//        Text("医学背景", fontWeight = FontWeight.Bold, color = Color(0xFF008080), fontSize = 14.sp)
//        EditableProfileRow("确诊时间", if (isPrivacyMode && !isEditing) "****" else diagnosis, isEditing, editTrigger) { diagnosis = it }
//        DropdownProfileRow("使用胰岛素", insulin, listOf("是", "否"), isEditing) { insulin = it }
//        EditableProfileRow("用药情况", if (isPrivacyMode && !isEditing) "****" else medication, isEditing, editTrigger) { medication = it }
//        EditableProfileRow("目标血糖", targetRange, isEditing, editTrigger) { targetRange = it }
//        EditableProfileRow("最近HbA1c", if (isPrivacyMode && !isEditing) "***%" else hba1c, isEditing, editTrigger) { hba1c = it }
//
//        Spacer(Modifier.height(32.dp))
//
//        Text("数据主权与互操作性", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF455A64), modifier = Modifier.semantics { heading() })
//        Spacer(Modifier.height(12.dp))
//        Column(modifier = Modifier.fillMaxWidth().background(Color.White, RoundedCornerShape(8.dp)).border(1.dp, Color(0xFFCFD8DC), RoundedCornerShape(8.dp)).padding(16.dp)) {
//            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.semantics(mergeDescendants = true) {}) {
//                Icon(Icons.Default.Security, contentDescription = null, tint = Color(0xFF4CAF50), modifier = Modifier.size(20.dp))
//                Spacer(Modifier.width(8.dp))
//                Text("100% 本地端侧加密存储", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF2E7D32))
//            }
//            Text("您的数据仅保存在当前设备，不上传任何云端服务器，保护您的绝对隐私。", fontSize = 10.sp, color = Color.Gray, modifier = Modifier.padding(top = 4.dp, bottom = 12.dp))
//
//            HorizontalDivider(color = Color(0xFFF1F1F1))
//
//            Row(modifier = Modifier.fillMaxWidth().padding(top = 12.dp, bottom = 12.dp).clickable(onClickLabel = "导出原始CSV格式数据") { scope.launch { snackbarHostState.showSnackbar("✅ 已生成 With_HealthData.csv，保存至本地相册") } }.semantics(mergeDescendants = true) {}, verticalAlignment = Alignment.CenterVertically) {
//                Icon(Icons.Default.Download, contentDescription = null, tint = Color(0xFF3F51B5), modifier = Modifier.size(20.dp))
//                Spacer(Modifier.width(8.dp))
//                Column {
//                    Text("导出原始数据 (CSV)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF3F51B5))
//                    Text("支持将患者生成数据 (PGHD) 导入临床系统", fontSize = 10.sp, color = Color(0xFF7986CB))
//                }
//            }
//
//            HorizontalDivider(color = Color(0xFFF1F1F1))
//
//            Row(modifier = Modifier.fillMaxWidth().padding(top = 12.dp).clickable(onClickLabel = "永久且不可逆地擦除所有本地健康数据") { scope.launch { snackbarHostState.showSnackbar("❌ 账号数据已彻底擦除 (模拟)") } }.semantics(mergeDescendants = true) {}, verticalAlignment = Alignment.CenterVertically) {
//                Icon(Icons.Default.DeleteForever, contentDescription = null, tint = Color.Red, modifier = Modifier.size(20.dp))
//                Spacer(Modifier.width(8.dp))
//                Column {
//                    Text("彻底擦除设备数据", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.Red)
//                    Text("一旦执行无法恢复，赋予您随时退出的权利", fontSize = 10.sp, color = Color(0xFFE57373))
//                }
//            }
//        }
//        Spacer(Modifier.height(40.dp))
//    }
//}
//
//@Composable
//fun EditableProfileRow(label: String, value: String, isEditing: Boolean, onRowClick: () -> Unit, onValueChange: (String) -> Unit) {
//    Row(modifier = Modifier.fillMaxWidth().clickable(enabled = !isEditing, onClickLabel = "编辑 $label") { onRowClick() }.padding(vertical = 6.dp).semantics(mergeDescendants = true) {}, horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
//        Text(label, color = Color.Gray, modifier = Modifier.weight(0.45f), fontSize = 12.sp)
//        if (isEditing) {
//            OutlinedTextField(value = value, onValueChange = onValueChange, modifier = Modifier.weight(0.55f).height(50.dp).semantics { contentDescription = "输入 $label" }, singleLine = true, textStyle = TextStyle(fontSize = 12.sp, textAlign = TextAlign.End))
//        } else {
//            Text(value, fontWeight = FontWeight.Medium, modifier = Modifier.weight(0.55f), textAlign = TextAlign.End, fontSize = 12.sp)
//        }
//    }
//}
//
//@Composable
//fun DropdownProfileRow(label: String, value: String, options: List<String>, isEditing: Boolean, onValueChange: (String) -> Unit) {
//    var expanded by remember { mutableStateOf(false) }
//    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp).semantics(mergeDescendants = true) {}, horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
//        Text(label, color = Color.Gray, modifier = Modifier.weight(0.45f), fontSize = 12.sp)
//        Box(modifier = Modifier.weight(0.55f), contentAlignment = Alignment.CenterEnd) {
//            if (isEditing) {
//                Row(modifier = Modifier.clickable(onClickLabel = "选择 $label") { expanded = true }.padding(8.dp).background(Color(0xFFF1F1F1), RoundedCornerShape(4.dp)).padding(horizontal=8.dp, vertical=4.dp), verticalAlignment = Alignment.CenterVertically) {
//                    Text(value, color = Color(0xFF008080), fontWeight = FontWeight.Bold, fontSize = 12.sp)
//                    Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = Color(0xFF008080))
//                }
//                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
//                    options.forEach { opt ->
//                        DropdownMenuItem(text = { Text(opt, fontSize = 12.sp) }, onClick = { onValueChange(opt); expanded = false })
//                    }
//                }
//            } else {
//                Text(value, fontWeight = FontWeight.Medium, textAlign = TextAlign.End, fontSize = 12.sp)
//            }
//        }
//    }
//}

package com.withapp.with.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Download
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
import androidx.compose.ui.semantics.* import androidx.lifecycle.viewmodel.compose.viewModel
import com.withapp.with.viewmodels.ReportViewModel
import com.withapp.with.viewmodels.UserProfile
import kotlinx.coroutines.launch
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContainer(onRequestPermission: () -> Unit = {}) {
    val snackbarHostState = remember { SnackbarHostState() }
    var selectedTab by remember { mutableStateOf("home") }
    val reportViewModel: ReportViewModel = viewModel()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.semantics(mergeDescendants = true) {}) {
                        Text("With", fontWeight = FontWeight.Bold, color = Color(0xFF008080), fontSize = 18.sp)
                        Text("您的数字健康共伴向导", fontSize = 10.sp, color = Color.Gray)
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                NavigationBarItem(selected = selectedTab == "home", onClick = { selectedTab = "home" }, icon = { Icon(Icons.Default.Home, contentDescription = "切换到主页") }, label = { Text("主页") })
                NavigationBarItem(selected = selectedTab == "report", onClick = { selectedTab = "report" }, icon = { Icon(Icons.Default.Assessment, contentDescription = "切换到健康回顾页") }, label = { Text("回顾") })
                NavigationBarItem(selected = selectedTab == "profile", onClick = { selectedTab = "profile" }, icon = { Icon(Icons.Default.AccountCircle, contentDescription = "切换到个人档案与设置页") }, label = { Text("档案") })
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (selectedTab) {
                "home" -> HomeV3View(reportViewModel, snackbarHostState)
                "report" -> ReportView(reportViewModel, snackbarHostState)
                "profile" -> ProfileView(reportViewModel, snackbarHostState)
            }
        }
    }
}

@Composable
fun HomeV3View(reportViewModel: ReportViewModel, snackbarHostState: SnackbarHostState) {
    val scope = rememberCoroutineScope()
    var inputText by remember { mutableStateOf("") }
    val chatMessages = remember { mutableStateListOf(ChatMessage("嗨，今天感觉如何？你可以像聊天一样告诉我刚才吃了什么、心情怎样，我会自动帮你整理。", false, true)) }
    var showProbe by remember { mutableStateOf(false) }
    var showRabbitInfo by remember { mutableStateOf(false) }

    // 🆕 V19: 引导式对话流状态机
    var activeFlow by remember { mutableStateOf<String?>(null) }
    var flowStep by remember { mutableStateOf(0) }
    var flowSelection by remember { mutableStateOf("") }
    var wheelMain by remember { mutableStateOf("50") }
    var wheelSub by remember { mutableStateOf("0") }

    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val dynamicAvatar = when {
        hour in 6..10 -> "🐰✨"
        hour in 22..23 || hour in 0..5 -> "🦉💤"
        else -> "🐰"
    }

    val dynamicPlaceholder = when {
        hour in 6..9 -> "早安！早餐吃了什么？..."
        hour in 11..13 -> "午餐时间，记录一下碳水摄入吧..."
        hour in 17..19 -> "晚餐吃了什么？现在心情如何？..."
        hour > 20 -> "夜深了，记录一下睡前状态吧..."
        else -> "用日常对话记录数据..."
    }

    if (showRabbitInfo) {
        AlertDialog(onDismissRequest = { showRabbitInfo = false },
            title = { Text("$dynamicAvatar 关于 With 助手", fontWeight = FontWeight.Bold) },
            text = { Text("我是您的智能陪伴助手。我会感知您的作息节律。您不需要手动填写冷冰冰的医疗表单，只要像和朋友聊天一样告诉我您的生活点滴，我会自动为您整理成健康回顾。") },
            confirmButton = { TextButton(onClick = { showRabbitInfo = false }) { Text("了解了", color = Color(0xFF008080)) } }
        )
    }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF7F9FA))) {

        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Box(modifier = Modifier.size(50.dp).clip(CircleShape).background(Color.White).clickable(onClickLabel = "查看数字向导介绍") { showRabbitInfo = true }, contentAlignment = Alignment.Center) { Text(dynamicAvatar, fontSize = 28.sp) }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.semantics(mergeDescendants = true) {}) { Text("点击头像了解我", fontSize = 11.sp, color = Color.Gray) }
        }

        if (reportViewModel.userProfile.value.isVacationMode) {
            Surface(color = Color(0xFFE8F5E9), modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp).semantics { contentDescription = "休眠模式已开启。您过去的数据完好无损，请放下压力，好好休息。" }, shape = RoundedCornerShape(8.dp)) {
                Text("🌙 休眠模式已开启。您过去的数据完好无损，请放下压力，好好休息。", fontSize = 12.sp, color = Color(0xFF2E7D32), modifier = Modifier.padding(12.dp))
            }
        } else {
            // 🆕 V19: 全面的结构化记录快捷入口 (四大健康支柱)
            Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()).padding(horizontal = 16.dp, vertical = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                QuickActionChip("🍱 记饮食", Color(0xFF81C784)) {
                    activeFlow = "DIET"; flowStep = 1; chatMessages.add(ChatMessage("准备记录饮食。请问你吃了哪类食物？(请在下方选择)", false, true))
                }
                QuickActionChip("🏃 记运动", Color(0xFF64B5F6)) {
                    activeFlow = "EXERCISE"; flowStep = 1; chatMessages.add(ChatMessage("准备记录运动。你做了什么运动？(请在下方选择)", false, true))
                }
                QuickActionChip("🧘 记心情", Color(0xFFFFB74D)) {
                    activeFlow = "MOOD"; flowStep = 1; chatMessages.add(ChatMessage("现在的心情如何？(请在下方选择)", false, true))
                }
                QuickActionChip("🩸 记血糖", Color(0xFFE57373)) {
                    activeFlow = "BG"; flowStep = 1; wheelMain = "5"; wheelSub = "5"
                    chatMessages.add(ChatMessage("准备记录血糖。现在的数值是多少？(请滑动下方滚轮选择)", false, true))
                }
            }
        }

        LazyColumn(modifier = Modifier.weight(1f).padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(chatMessages) { msg ->
                ChatBubble(msg, onUndo = {
                    val undoMsg = reportViewModel.undoLastAction()
                    scope.launch { snackbarHostState.showSnackbar(undoMsg) }
                })
            }
        }

        if (showProbe) { ResearchProbeCard { showProbe = false } }

        // 🆕 V19: 结构化引导输入区 (Guided Input Area)
        if (activeFlow != null && !reportViewModel.userProfile.value.isVacationMode) {
            Column(modifier = Modifier.fillMaxWidth().background(Color.White).padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("🌟 智能记录向导", fontWeight = FontWeight.Bold, color = Color(0xFF008080), fontSize = 13.sp)
                    Text("取消", color = Color.Gray, fontSize = 12.sp, modifier = Modifier.clickable { activeFlow = null })
                }
                Spacer(modifier = Modifier.height(12.dp))

                when (activeFlow) {
                    "DIET" -> {
                        if (flowStep == 1) {
                            FlowMenu(listOf("🍚 米饭", "🍜 面条", "🍞 面包", "🥗 沙拉", "🥩 牛肉", "🍗 鸡肉", "🥚 鸡蛋", "🥛 牛奶", "🍎 水果", "🍫 零食", "🍔 速食", "☕ 咖啡")) { sel ->
                                chatMessages.add(ChatMessage(sel, true))
                                flowSelection = sel; flowStep = 2; wheelMain = "50"
                                chatMessages.add(ChatMessage("好的，$sel。大约摄入了多少克碳水/卡路里呢？(请滑动下方滚轮)", false, true))
                            }
                        } else if (flowStep == 2) {
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                                WheelPickerLocal(items = (0..500 step 5).map{it.toString()}, initialValue = wheelMain, onValueChange = { wheelMain = it }, modifier = Modifier.weight(1f))
                                Text("克 (g) / 卡", color = Color.Gray, fontSize = 12.sp, modifier = Modifier.padding(start = 16.dp))
                            }
                            Button(onClick = {
                                chatMessages.add(ChatMessage("$wheelMain g/kcal", true))
                                val cleanFood = flowSelection.substring(3)
                                val reply = reportViewModel.processChatInput("吃 $cleanFood $wheelMain")
                                chatMessages.add(ChatMessage(reply, false, true, true))
                                activeFlow = null
                            }, modifier = Modifier.fillMaxWidth().padding(top = 12.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF008080))) { Text("同步至饮食档案") }
                        }
                    }
                    "EXERCISE" -> {
                        if (flowStep == 1) {
                            FlowMenu(listOf("🏃 跑步", "🏊 游泳", "🚴 骑车", "🏋️ 力量训练", "🧘 瑜伽", "🚶 散步", "🏸 羽毛球", "⚽ 足球")) { sel ->
                                chatMessages.add(ChatMessage(sel, true))
                                flowSelection = sel; flowStep = 2; wheelMain = "30"
                                chatMessages.add(ChatMessage("不错的运动！坚持了多少分钟？(请滑动下方滚轮)", false, true))
                            }
                        } else if (flowStep == 2) {
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                                WheelPickerLocal(items = (5..180 step 5).map{it.toString()}, initialValue = wheelMain, onValueChange = { wheelMain = it }, modifier = Modifier.weight(1f))
                                Text("分钟 (min)", color = Color.Gray, fontSize = 12.sp, modifier = Modifier.padding(start = 16.dp))
                            }
                            Button(onClick = {
                                chatMessages.add(ChatMessage("$wheelMain min", true))
                                val cleanEx = flowSelection.substring(3)
                                val reply = reportViewModel.processChatInput("运动 $cleanEx $wheelMain")
                                chatMessages.add(ChatMessage(reply, false, true, true))
                                activeFlow = null
                            }, modifier = Modifier.fillMaxWidth().padding(top = 12.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF008080))) { Text("同步至活动档案") }
                        }
                    }
                    "MOOD" -> {
                        FlowMenu(listOf("🤩 极佳", "🙂 平稳", "😐 疲惫", "😞 低落", "😡 烦躁", "😰 焦虑", "😌 轻松", "😭 难过")) { sel ->
                            chatMessages.add(ChatMessage(sel, true))
                            val cleanMood = sel.substring(3)
                            val reply = reportViewModel.processChatInput("心情 $cleanMood")
                            chatMessages.add(ChatMessage(reply, false, true, true))
                            activeFlow = null
                        }
                    }
                    "BG" -> {
                        Row(modifier = Modifier.fillMaxWidth().background(Color(0xFFFAFAFA), RoundedCornerShape(8.dp)).padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                            WheelPickerLocal(items = (1..30).map { it.toString() }, initialValue = wheelMain, onValueChange = { wheelMain = it }, modifier = Modifier.weight(1f))
                            Text(".", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFF008080), modifier = Modifier.padding(bottom = 8.dp))
                            WheelPickerLocal(items = (0..9).map { it.toString() }, initialValue = wheelSub, onValueChange = { wheelSub = it }, modifier = Modifier.weight(1f))
                            Text("mmol", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.weight(0.5f).padding(start = 8.dp))
                        }
                        Button(onClick = {
                            val valStr = "$wheelMain.$wheelSub"
                            chatMessages.add(ChatMessage("$valStr mmol/L", true))
                            val reply = reportViewModel.processChatInput("血糖 $valStr")
                            chatMessages.add(ChatMessage(reply, false, true, true))
                            activeFlow = null
                        }, modifier = Modifier.fillMaxWidth().padding(top = 12.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF008080))) { Text("同步至血糖图表") }
                    }
                }
            }
        } else {
            // 默认键盘输入区
            Row(modifier = Modifier.background(Color.White).padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = inputText, onValueChange = { inputText = it },
                    placeholder = { Text(dynamicPlaceholder, fontSize = 12.sp, color = Color.Gray) },
                    modifier = Modifier.weight(1f).semantics { contentDescription = "数据对话自由输入框" }, shape = RoundedCornerShape(24.dp),
                    enabled = !reportViewModel.userProfile.value.isVacationMode
                )
                IconButton(onClick = { scope.launch { snackbarHostState.showSnackbar("🎙️ 语音输入模块将在未来版本接入") } }) { Icon(Icons.Default.Mic, contentDescription = "使用语音输入", tint = Color.Gray) }
                IconButton(onClick = {
                    if (inputText.isNotBlank()) {
                        val reply = reportViewModel.processChatInput(inputText)
                        chatMessages.add(ChatMessage(inputText, true))
                        chatMessages.add(ChatMessage(reply, false, isSupportive = true, canUndo = true))
                        inputText = ""; showProbe = true
                    }
                }, enabled = !reportViewModel.userProfile.value.isVacationMode) { Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "发送记录", tint = if(reportViewModel.userProfile.value.isVacationMode) Color.LightGray else Color(0xFF008080)) }
            }
        }
    }
}

// 🆕 V19: 瀑布流自适应菜单，完美复刻 iOS Selection UI
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FlowMenu(items: List<String>, onSelect: (String) -> Unit) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items.forEach { item ->
            Surface(
                onClick = { onSelect(item) },
                color = Color(0xFFE3F2FD),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Color(0xFF90CAF9))
            ) {
                Text(item, modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp), fontSize = 13.sp, color = Color(0xFF1565C0), fontWeight = FontWeight.Medium)
            }
        }
    }
}

// 本地滚轮引擎，确保无缝支持
@OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun WheelPickerLocal(
    items: List<String>,
    initialValue: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val itemHeight = 40.dp
    val visibleCount = 3
    val startIndex = items.indexOf(initialValue).coerceAtLeast(0)
    val listState = androidx.compose.foundation.lazy.rememberLazyListState(initialFirstVisibleItemIndex = startIndex)
    val flingBehavior = androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior(lazyListState = listState)

    LaunchedEffect(listState) {
        androidx.compose.runtime.snapshotFlow { listState.firstVisibleItemIndex }.collect { index ->
            if (index in items.indices) { onValueChange(items[index]) }
        }
    }

    Box(modifier = modifier.height(itemHeight * visibleCount), contentAlignment = Alignment.Center) {
        Box(modifier = Modifier.fillMaxWidth().height(itemHeight).background(Color(0xFF008080).copy(alpha = 0.1f), RoundedCornerShape(8.dp)))
        androidx.compose.foundation.lazy.LazyColumn(
            state = listState,
            flingBehavior = flingBehavior,
            contentPadding = PaddingValues(vertical = itemHeight * (visibleCount / 2)),
            modifier = Modifier.fillMaxSize()
        ) {
            items(items.size) { index ->
                val isSelected = index == listState.firstVisibleItemIndex
                Box(modifier = Modifier.fillMaxWidth().height(itemHeight), contentAlignment = Alignment.Center) {
                    Text(
                        text = items[index],
                        fontSize = if (isSelected) 20.sp else 14.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) Color(0xFF008080) else Color.LightGray
                    )
                }
            }
        }
    }
}

@Composable
fun SuggestionPill(text: String, onClick: () -> Unit) {
    Surface(onClick = onClick, color = Color.White, shape = RoundedCornerShape(16.dp), border = BorderStroke(1.dp, Color(0xFF008080).copy(alpha=0.3f)), modifier = Modifier.padding(vertical = 4.dp).semantics { contentDescription = "快捷填入建议: $text" }) {
        Text(text, modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp), fontSize = 11.sp, color = Color(0xFF008080), fontWeight = FontWeight.Medium)
    }
}

@Composable
fun QuickActionChip(label: String, color: Color, onClick: () -> Unit) {
    Surface(onClick = onClick, color = color.copy(alpha = 0.15f), shape = RoundedCornerShape(16.dp), border = BorderStroke(1.dp, color.copy(alpha = 0.5f)), modifier = Modifier.semantics { contentDescription = "快捷操作: $label" }) {
        Text(label, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), fontSize = 12.sp, color = color, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun ResearchProbeCard(onDismiss: () -> Unit) {
    Card(modifier = Modifier.padding(16.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F2F1))) {
        Column(modifier = Modifier.padding(12.dp).semantics(mergeDescendants = true) {}) {
            Text("🔬 研究探针：刚才的记录过程让你觉得轻松吗？", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF00695C))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                TextButton(onClick = onDismiss) { Text("😊 极简", fontSize = 11.sp) }
                TextButton(onClick = onDismiss) { Text("😐 一般", fontSize = 11.sp) }
                TextButton(onClick = onDismiss) { Text("😫 繁琐", fontSize = 11.sp) }
            }
        }
    }
}

data class ChatMessage(val text: String, val isUser: Boolean, val isSupportive: Boolean = false, val canUndo: Boolean = false)

@Composable
fun ChatBubble(message: ChatMessage, onUndo: () -> Unit) {
    var feedbackGiven by remember { mutableStateOf(false) }
    var hasUndone by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = if (message.isUser) Alignment.End else Alignment.Start) {
        Box(modifier = Modifier.padding(8.dp).clip(RoundedCornerShape(12.dp)).background(if (message.isUser) Color(0xFF008080) else Color.White).padding(12.dp).semantics { contentDescription = if (message.isUser) "您发送的记录：${message.text}" else "向导回复：${message.text}" }) {
            Text(message.text, color = if (message.isUser) Color.White else Color.Black)
        }

        if (!message.isUser && message.canUndo && !hasUndone) {
            Text("识别错误？撤销记录", fontSize = 10.sp, color = Color.Red, modifier = Modifier.padding(start = 16.dp).clickable(onClickLabel = "撤销上一条健康数据的系统记录") { onUndo(); hasUndone = true })
        }

        if (!message.isUser && message.isSupportive && !feedbackGiven) {
            Row(modifier = Modifier.padding(start = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                Text("🔬 这个反馈有帮助吗？", fontSize = 10.sp, color = Color.Gray)
                TextButton(onClick = { feedbackGiven = true }) { Text("👍", fontSize = 10.sp) }
                TextButton(onClick = { feedbackGiven = true }) { Text("👎", fontSize = 10.sp, color = Color.Gray) }
            }
        }
    }
}

@Composable
fun ProfileView(reportViewModel: ReportViewModel, snackbarHostState: SnackbarHostState) {
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
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
    var isVacationMode by remember(profile) { mutableStateOf(profile.isVacationMode) }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF7F9FA)).verticalScroll(scrollState).padding(24.dp)) {

        Text("关怀与系统偏好", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF3F51B5), modifier = Modifier.semantics { heading() })
        Spacer(Modifier.height(12.dp))

        Column(modifier = Modifier.fillMaxWidth().background(Color(0xFFE8F5E9), RoundedCornerShape(8.dp)).padding(12.dp).semantics(mergeDescendants = true) {}) {
            Row(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("🌙 健康休眠模式", fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32), fontSize = 14.sp)
                    Text("暂停所有提醒与图表分析，允许自己从数据中喘口气。数据将被安全隐藏。", fontSize = 10.sp, color = Color.Gray, lineHeight = 14.sp)
                }
                Switch(checked = isVacationMode, onCheckedChange = { isVacationMode = it; reportViewModel.userProfile.value = reportViewModel.userProfile.value.copy(isVacationMode = it) }, modifier = Modifier.semantics { contentDescription = "开启或关闭健康休眠模式" }, colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF2E7D32), checkedTrackColor = Color(0xFFA5D6A7)))
            }
        }

        Spacer(Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth().background(Color(0xFFE8EAF6), RoundedCornerShape(8.dp)).padding(12.dp).padding(vertical = 4.dp).semantics(mergeDescendants = true) {}, horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text("开启隐私掩码模式", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Text("在公共场合隐藏下方敏感健康数据", fontSize = 10.sp, color = Color.Gray)
            }
            Switch(checked = isPrivacyMode, onCheckedChange = { isPrivacyMode = it; reportViewModel.userProfile.value = reportViewModel.userProfile.value.copy(isPrivacyMode = it) }, modifier = Modifier.semantics { contentDescription = "开启或关闭隐私掩码，隐藏敏感数据" }, colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF3F51B5), checkedTrackColor = Color(0xFFC5CAE9)))
        }

        Spacer(Modifier.height(8.dp))
        Column(modifier = Modifier.fillMaxWidth().background(Color(0xFFFFF3E0), RoundedCornerShape(8.dp)).padding(12.dp).semantics(mergeDescendants = true) {}) {
            Row(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("🔔 智能感知提醒 (JITAI)", fontWeight = FontWeight.Bold, color = Color(0xFFE65100), fontSize = 14.sp)
                    Text("根据血糖与心率波动自动预判，取代定时打扰", fontSize = 10.sp, color = Color.Gray, lineHeight = 14.sp)
                }
                Switch(checked = isContextAwareAlerts, onCheckedChange = { isContextAwareAlerts = it }, modifier = Modifier.semantics { contentDescription = "开启或关闭基于上下文的智能健康打扰机制" }, colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFFE65100), checkedTrackColor = Color(0xFFFFCC80)), enabled = !isVacationMode)
            }
            if (!isContextAwareAlerts && !isVacationMode) {
                EditableProfileRow("死板提醒时间", reminderTime, isEditing = true, onRowClick = {}) { reminderTime = it }
                DropdownProfileRow("提醒频率", reminderFrequency, listOf("每天1次", "每天3次", "仅异常时"), isEditing = true) { reminderFrequency = it }
            }
        }

        Spacer(Modifier.height(32.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("个人静态档案", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF008080), modifier = Modifier.semantics { heading() })
            TextButton(onClick = {
                if (isEditing) reportViewModel.userProfile.value = UserProfile(age, gender, height, weight, diagnosis, insulin, medication, targetRange, hba1c, reminderTime, reminderFrequency, isPrivacyMode, isContextAwareAlerts, isVacationMode)
                isEditing = !isEditing
            }) { Text(if (isEditing) "保存" else "点此编辑档案", color = Color(0xFF008080), fontWeight = FontWeight.Bold) }
        }

        Spacer(Modifier.height(8.dp))
        Text("基础体征", fontWeight = FontWeight.Bold, color = Color(0xFF008080), fontSize = 14.sp)
        val editTrigger = { isEditing = true }
        EditableProfileRow("年龄", age, isEditing, editTrigger) { age = it }
        DropdownProfileRow("性别", gender, listOf("男", "女", "其他"), isEditing) { gender = it }
        EditableProfileRow("身高", height, isEditing, editTrigger) { height = it }
        EditableProfileRow("体重", weight, isEditing, editTrigger) { weight = it }

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
        Text("医学背景", fontWeight = FontWeight.Bold, color = Color(0xFF008080), fontSize = 14.sp)
        EditableProfileRow("确诊时间", if (isPrivacyMode && !isEditing) "****" else diagnosis, isEditing, editTrigger) { diagnosis = it }
        DropdownProfileRow("使用胰岛素", insulin, listOf("是", "否"), isEditing) { insulin = it }
        EditableProfileRow("用药情况", if (isPrivacyMode && !isEditing) "****" else medication, isEditing, editTrigger) { medication = it }
        EditableProfileRow("目标血糖", targetRange, isEditing, editTrigger) { targetRange = it }
        EditableProfileRow("最近HbA1c", if (isPrivacyMode && !isEditing) "***%" else hba1c, isEditing, editTrigger) { hba1c = it }

        Spacer(Modifier.height(32.dp))

        Text("数据主权与互操作性", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF455A64), modifier = Modifier.semantics { heading() })
        Spacer(Modifier.height(12.dp))
        Column(modifier = Modifier.fillMaxWidth().background(Color.White, RoundedCornerShape(8.dp)).border(1.dp, Color(0xFFCFD8DC), RoundedCornerShape(8.dp)).padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.semantics(mergeDescendants = true) {}) {
                Icon(Icons.Default.Security, contentDescription = null, tint = Color(0xFF4CAF50), modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
                Text("100% 本地端侧加密存储", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF2E7D32))
            }
            Text("您的数据仅保存在当前设备，不上传任何云端服务器，保护您的绝对隐私。", fontSize = 10.sp, color = Color.Gray, modifier = Modifier.padding(top = 4.dp, bottom = 12.dp))

            HorizontalDivider(color = Color(0xFFF1F1F1))

            Row(modifier = Modifier.fillMaxWidth().padding(top = 12.dp, bottom = 12.dp).clickable(onClickLabel = "导出原始CSV格式数据") { scope.launch { snackbarHostState.showSnackbar("✅ 已生成 With_HealthData.csv，保存至本地相册") } }.semantics(mergeDescendants = true) {}, verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Download, contentDescription = null, tint = Color(0xFF3F51B5), modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
                Column {
                    Text("导出原始数据 (CSV)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF3F51B5))
                    Text("支持将患者生成数据 (PGHD) 导入临床系统", fontSize = 10.sp, color = Color(0xFF7986CB))
                }
            }

            HorizontalDivider(color = Color(0xFFF1F1F1))

            Row(modifier = Modifier.fillMaxWidth().padding(top = 12.dp).clickable(onClickLabel = "永久且不可逆地擦除所有本地健康数据") { scope.launch { snackbarHostState.showSnackbar("❌ 账号数据已彻底擦除 (模拟)") } }.semantics(mergeDescendants = true) {}, verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.DeleteForever, contentDescription = null, tint = Color.Red, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
                Column {
                    Text("彻底擦除设备数据", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.Red)
                    Text("一旦执行无法恢复，赋予您随时退出的权利", fontSize = 10.sp, color = Color(0xFFE57373))
                }
            }
        }
        Spacer(Modifier.height(40.dp))
    }
}

@Composable
fun EditableProfileRow(label: String, value: String, isEditing: Boolean, onRowClick: () -> Unit, onValueChange: (String) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().clickable(enabled = !isEditing, onClickLabel = "编辑 $label") { onRowClick() }.padding(vertical = 6.dp).semantics(mergeDescendants = true) {}, horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(label, color = Color.Gray, modifier = Modifier.weight(0.45f), fontSize = 12.sp)
        if (isEditing) {
            OutlinedTextField(value = value, onValueChange = onValueChange, modifier = Modifier.weight(0.55f).height(50.dp).semantics { contentDescription = "输入 $label" }, singleLine = true, textStyle = TextStyle(fontSize = 12.sp, textAlign = TextAlign.End))
        } else {
            Text(value, fontWeight = FontWeight.Medium, modifier = Modifier.weight(0.55f), textAlign = TextAlign.End, fontSize = 12.sp)
        }
    }
}

@Composable
fun DropdownProfileRow(label: String, value: String, options: List<String>, isEditing: Boolean, onValueChange: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp).semantics(mergeDescendants = true) {}, horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(label, color = Color.Gray, modifier = Modifier.weight(0.45f), fontSize = 12.sp)
        Box(modifier = Modifier.weight(0.55f), contentAlignment = Alignment.CenterEnd) {
            if (isEditing) {
                Row(modifier = Modifier.clickable(onClickLabel = "选择 $label") { expanded = true }.padding(8.dp).background(Color(0xFFF1F1F1), RoundedCornerShape(4.dp)).padding(horizontal=8.dp, vertical=4.dp), verticalAlignment = Alignment.CenterVertically) {
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