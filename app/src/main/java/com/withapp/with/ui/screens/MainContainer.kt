

package com.withapp.with.ui.screens

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
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
                        Text("您的数字健康向导 / Your Health Companion", fontSize = 10.sp, color = Color.Gray)
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                NavigationBarItem(selected = selectedTab == "home", onClick = { selectedTab = "home" }, icon = { Icon(Icons.Default.Home, null) }, label = { Text("主页 / Home", fontSize = 10.sp) })
                NavigationBarItem(selected = selectedTab == "report", onClick = { selectedTab = "report" }, icon = { Icon(Icons.Default.Assessment, null) }, label = { Text("回顾 / Report", fontSize = 10.sp) })
                NavigationBarItem(selected = selectedTab == "profile", onClick = { selectedTab = "profile" }, icon = { Icon(Icons.Default.AccountCircle, null) }, label = { Text("档案 / Profile", fontSize = 10.sp) })
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
    val context = LocalContext.current
    var inputText by remember { mutableStateOf("") }
    val chatMessages = remember { mutableStateListOf(ChatMessage("嗨！今天感觉如何？随时告诉我你的饮食、运动或心情。\nHi! How are you feeling? Tell me about your food, workout or mood.", false, true)) }
    var showProbe by remember { mutableStateOf(false) }
    var showRabbitInfo by remember { mutableStateOf(false) }

    var activeFlow by remember { mutableStateOf<String?>(null) }
    var flowStep by remember { mutableStateOf(0) }
    var flowSelection by remember { mutableStateOf("") }
    var wheelMain by remember { mutableStateOf("50") }
    var wheelSub by remember { mutableStateOf("0") }

    val speechLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val spokenText = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.firstOrNull()
            if (!spokenText.isNullOrBlank()) { inputText = if (inputText.isBlank()) spokenText else "$inputText $spokenText" }
        }
    }

    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val dynamicAvatar = when {
        hour in 6..10 -> "🐰✨"
        hour in 22..23 || hour in 0..5 -> "🦉💤"
        else -> "🐰"
    }

    val dynamicPlaceholder = when {
        hour in 6..9 -> "早餐吃了什么？/ What's for breakfast?..."
        hour in 11..13 -> "午餐时间！/ Lunch time!..."
        hour in 17..19 -> "现在心情如何？/ How is your mood?..."
        hour > 20 -> "睡前记录一下吧 / Night log..."
        else -> "自由对话 / Type naturally..."
    }

    if (showRabbitInfo) {
        AlertDialog(onDismissRequest = { showRabbitInfo = false },
            title = { Text("$dynamicAvatar 关于 / About With", fontWeight = FontWeight.Bold) },
            text = { Text("我是您的双语数字向导，能够理解中英文输入，并感知您的作息节律。\nI'm your bilingual health companion. Speak to me naturally in EN or ZH.") },
            confirmButton = { TextButton(onClick = { showRabbitInfo = false }) { Text("了解 / Got it", color = Color(0xFF008080)) } }
        )
    }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF7F9FA))) {
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Box(modifier = Modifier.size(50.dp).clip(CircleShape).background(Color.White).clickable { showRabbitInfo = true }, contentAlignment = Alignment.Center) { Text(dynamicAvatar, fontSize = 28.sp) }
            Spacer(modifier = Modifier.width(12.dp))
            Column { Text("点击了解我 / Tap to learn more", fontSize = 11.sp, color = Color.Gray) }
        }

        if (reportViewModel.userProfile.value.isVacationMode) {
            Surface(color = Color(0xFFE8F5E9), modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp), shape = RoundedCornerShape(8.dp)) {
                Text("🌙 休眠模式已开启 / Vacation Mode ON. Take a rest.", fontSize = 12.sp, color = Color(0xFF2E7D32), modifier = Modifier.padding(12.dp))
            }
        } else {
            Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()).padding(horizontal = 16.dp, vertical = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                QuickActionChip("🍱 饮食 / Diet", Color(0xFF81C784)) { activeFlow = "DIET"; flowStep = 1; chatMessages.add(ChatMessage("准备记录饮食。你吃了什么？\nLogging diet. What did you eat?", false, true)) }
                QuickActionChip("🏃 运动 / Workout", Color(0xFF64B5F6)) { activeFlow = "EXERCISE"; flowStep = 1; chatMessages.add(ChatMessage("准备记录运动。你做了什么？\nLogging workout. What did you do?", false, true)) }
                QuickActionChip("🧘 心情 / Mood", Color(0xFFFFB74D)) { activeFlow = "MOOD"; flowStep = 1; chatMessages.add(ChatMessage("现在的心情如何？\nHow are you feeling?", false, true)) }
                QuickActionChip("🩸 血糖 / BG", Color(0xFFE57373)) { activeFlow = "BG"; flowStep = 1; wheelMain = "5"; wheelSub = "5"; chatMessages.add(ChatMessage("准备记录血糖。滑动数值：\nLogging BG. Roll the values:", false, true)) }
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

        if (activeFlow != null && !reportViewModel.userProfile.value.isVacationMode) {
            Column(modifier = Modifier.fillMaxWidth().background(Color.White).padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("🌟 记录向导 / Log Wizard", fontWeight = FontWeight.Bold, color = Color(0xFF008080), fontSize = 13.sp)
                    Text("取消/Cancel", color = Color.Gray, fontSize = 12.sp, modifier = Modifier.clickable { activeFlow = null })
                }
                Spacer(modifier = Modifier.height(12.dp))

                when (activeFlow) {
                    "DIET" -> {
                        if (flowStep == 1) {
                            FlowMenu(listOf("🍚 米饭/Rice", "🍜 面条/Noodles", "🍞 面包/Bread", "🥗 沙拉/Salad", "🥩 牛肉/Beef", "🍗 鸡肉/Chicken", "🥚 鸡蛋/Egg", "🥛 牛奶/Milk", "🍎 水果/Fruit", "🍫 零食/Snack")) { sel ->
                                chatMessages.add(ChatMessage(sel, true))
                                flowSelection = sel; flowStep = 2; wheelMain = "50"
                                chatMessages.add(ChatMessage("摄入了多少克碳水/卡路里？\nHow many carbs/kcal? (g/kcal)", false, true))
                            }
                        } else if (flowStep == 2) {
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                                WheelPickerLocal(items = (0..500 step 5).map{it.toString()}, initialValue = wheelMain, onValueChange = { wheelMain = it }, modifier = Modifier.weight(1f))
                                Text("g / kcal", color = Color.Gray, fontSize = 12.sp, modifier = Modifier.padding(start = 16.dp))
                            }
                            Button(onClick = {
                                chatMessages.add(ChatMessage("$wheelMain g/kcal", true))
                                val reply = reportViewModel.processChatInput("吃 ${flowSelection.split("/")[0].drop(2)} $wheelMain")
                                chatMessages.add(ChatMessage(reply, false, true, true))
                                activeFlow = null
                            }, modifier = Modifier.fillMaxWidth().padding(top = 12.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF008080))) { Text("同步 / Sync") }
                        }
                    }
                    "EXERCISE" -> {
                        if (flowStep == 1) {
                            FlowMenu(listOf("🏃 跑步/Run", "🏊 游泳/Swim", "🚴 骑车/Bike", "🏋️ 力量/Weights", "🧘 瑜伽/Yoga", "🚶 散步/Walk")) { sel ->
                                chatMessages.add(ChatMessage(sel, true))
                                flowSelection = sel; flowStep = 2; wheelMain = "30"
                                chatMessages.add(ChatMessage("运动了多少分钟？\nHow many minutes?", false, true))
                            }
                        } else if (flowStep == 2) {
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                                WheelPickerLocal(items = (5..180 step 5).map{it.toString()}, initialValue = wheelMain, onValueChange = { wheelMain = it }, modifier = Modifier.weight(1f))
                                Text("分钟 / min", color = Color.Gray, fontSize = 12.sp, modifier = Modifier.padding(start = 16.dp))
                            }
                            Button(onClick = {
                                chatMessages.add(ChatMessage("$wheelMain min", true))
                                val reply = reportViewModel.processChatInput("运动 ${flowSelection.split("/")[0].drop(2)} $wheelMain")
                                chatMessages.add(ChatMessage(reply, false, true, true))
                                activeFlow = null
                            }, modifier = Modifier.fillMaxWidth().padding(top = 12.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF008080))) { Text("同步 / Sync") }
                        }
                    }
                    "MOOD" -> {
                        FlowMenu(listOf("🤩 极佳/Great", "🙂 平稳/Calm", "😐 疲惫/Tired", "😞 低落/Down", "😡 烦躁/Angry", "😰 焦虑/Anxious")) { sel ->
                            chatMessages.add(ChatMessage(sel, true))
                            val reply = reportViewModel.processChatInput("心情 ${sel.split("/")[0].drop(2)}")
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
                        }, modifier = Modifier.fillMaxWidth().padding(top = 12.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF008080))) { Text("同步 / Sync") }
                    }
                }
            }
        } else {
            Row(modifier = Modifier.background(Color.White).padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = inputText, onValueChange = { inputText = it },
                    placeholder = { Text(dynamicPlaceholder, fontSize = 12.sp, color = Color.Gray) },
                    modifier = Modifier.weight(1f).semantics { contentDescription = "数据对话自由输入框" }, shape = RoundedCornerShape(24.dp),
                    enabled = !reportViewModel.userProfile.value.isVacationMode
                )
                IconButton(onClick = {
                    try {
                        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                            putExtra(RecognizerIntent.EXTRA_PROMPT, "请说话 / Speak now...")
                        }
                        speechLauncher.launch(intent)
                    } catch (e: Exception) { scope.launch { snackbarHostState.showSnackbar("❌ 您的设备未安装语音识别组件") } }
                }) { Icon(Icons.Default.Mic, null, tint = Color(0xFF008080)) }
                IconButton(onClick = {
                    if (inputText.isNotBlank()) {
                        val reply = reportViewModel.processChatInput(inputText)
                        chatMessages.add(ChatMessage(inputText, true))
                        chatMessages.add(ChatMessage(reply, false, isSupportive = true, canUndo = true))
                        inputText = ""; showProbe = true
                    }
                }, enabled = !reportViewModel.userProfile.value.isVacationMode) { Icon(Icons.AutoMirrored.Filled.Send, null, tint = if(reportViewModel.userProfile.value.isVacationMode) Color.LightGray else Color(0xFF008080)) }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FlowMenu(items: List<String>, onSelect: (String) -> Unit) {
    FlowRow(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items.forEach { item ->
            Surface(onClick = { onSelect(item) }, color = Color(0xFFE3F2FD), shape = RoundedCornerShape(16.dp), border = BorderStroke(1.dp, Color(0xFF90CAF9))) {
                Text(item, modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp), fontSize = 13.sp, color = Color(0xFF1565C0), fontWeight = FontWeight.Medium)
            }
        }
    }
}

@OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun WheelPickerLocal(items: List<String>, initialValue: String, onValueChange: (String) -> Unit, modifier: Modifier = Modifier) {
    val itemHeight = 40.dp
    val startIndex = items.indexOf(initialValue).coerceAtLeast(0)
    val listState = androidx.compose.foundation.lazy.rememberLazyListState(initialFirstVisibleItemIndex = startIndex)
    val flingBehavior = androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior(lazyListState = listState)

    LaunchedEffect(listState) {
        androidx.compose.runtime.snapshotFlow { listState.firstVisibleItemIndex }.collect { index ->
            if (index in items.indices) { onValueChange(items[index]) }
        }
    }

    Box(modifier = modifier.height(itemHeight * 3), contentAlignment = Alignment.Center) {
        Box(modifier = Modifier.fillMaxWidth().height(itemHeight).background(Color(0xFF008080).copy(alpha = 0.1f), RoundedCornerShape(8.dp)))
        androidx.compose.foundation.lazy.LazyColumn(state = listState, flingBehavior = flingBehavior, contentPadding = PaddingValues(vertical = itemHeight), modifier = Modifier.fillMaxSize()) {
            items(items.size) { index ->
                val isSelected = index == listState.firstVisibleItemIndex
                Box(modifier = Modifier.fillMaxWidth().height(itemHeight), contentAlignment = Alignment.Center) {
                    Text(text = items[index], fontSize = if (isSelected) 20.sp else 14.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal, color = if (isSelected) Color(0xFF008080) else Color.LightGray)
                }
            }
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
        Column(modifier = Modifier.padding(12.dp).semantics(mergeDescendants = true) {}) {
            Text("🔬 过程轻松吗？ / Easy to use?", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF00695C))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                TextButton(onClick = onDismiss) { Text("😊 极简/Easy", fontSize = 11.sp) }
                TextButton(onClick = onDismiss) { Text("😐 一般/Ok", fontSize = 11.sp) }
                TextButton(onClick = onDismiss) { Text("😫 繁琐/Hard", fontSize = 11.sp) }
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
        Box(modifier = Modifier.padding(8.dp).clip(RoundedCornerShape(12.dp)).background(if (message.isUser) Color(0xFF008080) else Color.White).padding(12.dp)) {
            Text(message.text, color = if (message.isUser) Color.White else Color.Black)
        }
        if (!message.isUser && message.canUndo && !hasUndone) {
            Text("识别错误？撤销记录 / Undo Error", fontSize = 10.sp, color = Color.Red, modifier = Modifier.padding(start = 16.dp).clickable { onUndo(); hasUndone = true })
        }
        if (!message.isUser && message.isSupportive && !feedbackGiven) {
            Row(modifier = Modifier.padding(start = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                Text("🔬 反馈有用吗？/ Helpful?", fontSize = 10.sp, color = Color.Gray)
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

        Text("关怀与偏好 / Preferences", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF3F51B5))
        Spacer(Modifier.height(12.dp))

        Column(modifier = Modifier.fillMaxWidth().background(Color(0xFFE8F5E9), RoundedCornerShape(8.dp)).padding(12.dp)) {
            Row(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("🌙 休眠模式 / Vacation Mode", fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32), fontSize = 14.sp)
                    Text("暂停图表分析，避免焦虑 / Pause analytics to avoid anxiety", fontSize = 10.sp, color = Color.Gray, lineHeight = 14.sp)
                }
                Switch(checked = isVacationMode, onCheckedChange = { isVacationMode = it; reportViewModel.userProfile.value = reportViewModel.userProfile.value.copy(isVacationMode = it) }, colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF2E7D32), checkedTrackColor = Color(0xFFA5D6A7)))
            }
        }

        Spacer(Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth().background(Color(0xFFE8EAF6), RoundedCornerShape(8.dp)).padding(12.dp).padding(vertical = 4.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text("隐私掩码 / Privacy Mask", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Text("在公共场合隐藏敏感数据 / Hide sensitive data in public", fontSize = 10.sp, color = Color.Gray)
            }
            Switch(checked = isPrivacyMode, onCheckedChange = { isPrivacyMode = it; reportViewModel.userProfile.value = reportViewModel.userProfile.value.copy(isPrivacyMode = it) }, colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF3F51B5), checkedTrackColor = Color(0xFFC5CAE9)))
        }

        Spacer(Modifier.height(8.dp))
        Column(modifier = Modifier.fillMaxWidth().background(Color(0xFFFFF3E0), RoundedCornerShape(8.dp)).padding(12.dp)) {
            Row(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("🔔 智能打扰 / Smart Alerts", fontWeight = FontWeight.Bold, color = Color(0xFFE65100), fontSize = 14.sp)
                    Text("根据体征预判，取代定时打扰 / Context-aware push", fontSize = 10.sp, color = Color.Gray, lineHeight = 14.sp)
                }
                Switch(checked = isContextAwareAlerts, onCheckedChange = { isContextAwareAlerts = it }, colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFFE65100), checkedTrackColor = Color(0xFFFFCC80)), enabled = !isVacationMode)
            }
        }

        Spacer(Modifier.height(32.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("个人档案 / Profile", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF008080))
            TextButton(onClick = {
                if (isEditing) reportViewModel.userProfile.value = UserProfile(age, gender, height, weight, diagnosis, insulin, medication, targetRange, hba1c, reminderTime, reminderFrequency, isPrivacyMode, isContextAwareAlerts, isVacationMode)
                isEditing = !isEditing
            }) { Text(if (isEditing) "保存 / Save" else "编辑 / Edit", color = Color(0xFF008080), fontWeight = FontWeight.Bold) }
        }

        Spacer(Modifier.height(8.dp))
        EditableProfileRow("年龄 / Age", age, isEditing, { isEditing = true }) { age = it }
        DropdownProfileRow("性别 / Gender", gender, listOf("男/M", "女/F", "其他/Other"), isEditing) { gender = it }
        EditableProfileRow("身高 / Height", height, isEditing, { isEditing = true }) { height = it }
        EditableProfileRow("体重 / Weight", weight, isEditing, { isEditing = true }) { weight = it }

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
        EditableProfileRow("确诊 / Diagnosed", if (isPrivacyMode && !isEditing) "****" else diagnosis, isEditing, { isEditing = true }) { diagnosis = it }
        DropdownProfileRow("胰岛素 / Insulin", insulin, listOf("是/Y", "否/N"), isEditing) { insulin = it }
        EditableProfileRow("用药 / Meds", if (isPrivacyMode && !isEditing) "****" else medication, isEditing, { isEditing = true }) { medication = it }
        EditableProfileRow("目标 / Target", targetRange, isEditing, { isEditing = true }) { targetRange = it }
        EditableProfileRow("HbA1c", if (isPrivacyMode && !isEditing) "***%" else hba1c, isEditing, { isEditing = true }) { hba1c = it }

        Spacer(Modifier.height(32.dp))

        Text("主权与安全 / Sovereignty", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF455A64))
        Spacer(Modifier.height(12.dp))
        Column(modifier = Modifier.fillMaxWidth().background(Color.White, RoundedCornerShape(8.dp)).border(1.dp, Color(0xFFCFD8DC), RoundedCornerShape(8.dp)).padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Security, null, tint = Color(0xFF4CAF50), modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
                Text("100% 本地加密 / Local Encrypted", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF2E7D32))
            }

            HorizontalDivider(color = Color(0xFFF1F1F1), modifier = Modifier.padding(vertical = 12.dp))

            Row(modifier = Modifier.fillMaxWidth().clickable { scope.launch { snackbarHostState.showSnackbar("✅ 已生成 CSV 报告 / CSV Generated") } }, verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Download, null, tint = Color(0xFF3F51B5), modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
                Column {
                    Text("导出数据 / Export CSV", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF3F51B5))
                    Text("用于临床系统 / For Clinical Systems", fontSize = 10.sp, color = Color(0xFF7986CB))
                }
            }

            HorizontalDivider(color = Color(0xFFF1F1F1), modifier = Modifier.padding(vertical = 12.dp))

            Row(modifier = Modifier.fillMaxWidth().clickable { scope.launch { snackbarHostState.showSnackbar("❌ 数据已擦除 / Data Erased") } }, verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.DeleteForever, null, tint = Color.Red, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
                Column {
                    Text("彻底擦除 / Erase All", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.Red)
                    Text("一旦执行无法恢复 / Cannot be undone", fontSize = 10.sp, color = Color(0xFFE57373))
                }
            }
        }
        Spacer(Modifier.height(40.dp))
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
                    Icon(Icons.Default.ArrowDropDown, null, tint = Color(0xFF008080))
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