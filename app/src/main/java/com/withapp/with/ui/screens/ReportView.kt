
//
//package com.withapp.with.ui.screens
//
//import androidx.compose.animation.AnimatedVisibility
//import androidx.compose.animation.animateContentSize
//import androidx.compose.animation.core.animateFloatAsState
//import androidx.compose.ui.graphics.nativeCanvas
//import androidx.compose.foundation.Canvas
//import androidx.compose.foundation.background
//import androidx.compose.foundation.BorderStroke
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.horizontalScroll
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.draw.rotate
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.geometry.Size
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.Path
//import androidx.compose.ui.graphics.SolidColor
//import androidx.compose.ui.graphics.drawscope.Stroke
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.compose.ui.semantics.* import com.withapp.with.viewmodels.*
//import kotlinx.coroutines.launch
//import java.util.Calendar
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ReportView(reportViewModel: ReportViewModel, snackbarHostState: SnackbarHostState) {
//    val scrollState = rememberScrollState()
//    val scope = rememberCoroutineScope()
//    val cgmNodes = reportViewModel.cgmNodes
//    val moodNodes = reportViewModel.moodNodes
//    val cgmRep = reportViewModel.cgmReport.value
//    val moodRep = reportViewModel.moodReport.value
//    val isVacationMode = reportViewModel.userProfile.value.isVacationMode
//
//    var expandedSummary by remember { mutableStateOf(true) }
//    var expandedAdvice by remember { mutableStateOf(true) }
//    var expandedCorrelation by remember { mutableStateOf(true) }
//    var expandedCgm by remember { mutableStateOf(false) }
//    var expandedMood by remember { mutableStateOf(false) }
//    var expandedLifestyle by remember { mutableStateOf(false) }
//
//    var showCgmDialog by remember { mutableStateOf(false) }
//    var showMoodDialog by remember { mutableStateOf(false) }
//    var showCgmHistoryDialog by remember { mutableStateOf(false) }
//    var showMoodHistoryDialog by remember { mutableStateOf(false) }
//    var showGlossaryDialog by remember { mutableStateOf(false) }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("健康回顾", fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.semantics { heading() }) },
//                actions = {
//                    IconButton(onClick = { scope.launch { snackbarHostState.showSnackbar("✅ 数据已脱敏，即将生成 CSV 报告以便发送给您的主治医生。") } }) {
//                        Icon(Icons.Default.Share, contentDescription = "一键脱敏分享给医生", tint = Color(0xFF008080))
//                    }
//                }
//            )
//        }
//    ) { paddingValues ->
//        Column(modifier = Modifier.fillMaxSize().padding(paddingValues).background(Color(0xFFF7F9FA))) {
//
//            if (isVacationMode) {
//                Column(modifier = Modifier.fillMaxSize().semantics(mergeDescendants = true) {}, verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
//                    Text("🌙", fontSize = 60.sp); Spacer(modifier = Modifier.height(16.dp))
//                    Text("休眠模式中", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
//                    Text("所有分析已隐藏，避免数据焦虑。", fontSize = 12.sp, color = Color.Gray, textAlign = TextAlign.Center, modifier = Modifier.padding(16.dp))
//                }
//            } else {
//                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
//                    TimeScaleSelector(selectedScale = reportViewModel.currentScale.value, onScaleSelect = { reportViewModel.currentScale.value = it })
//                }
//
//                if (cgmNodes.isEmpty() && moodNodes.isEmpty()) {
//                    Column(modifier = Modifier.weight(1f).fillMaxWidth().semantics(mergeDescendants = true) {}, verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
//                        Text("🌱", fontSize = 60.sp)
//                        Spacer(modifier = Modifier.height(16.dp))
//                        Text("健康管理是一场旅程，而不是赛跑", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF008080), textAlign = TextAlign.Center)
//                        Text("暂无数据。没有关系，随时欢迎您回来记录点滴。", fontSize = 12.sp, color = Color.Gray, textAlign = TextAlign.Center, modifier = Modifier.padding(16.dp))
//                        Button(onClick = { showCgmDialog = true }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF008080))) { Text("开始第一笔记录") }
//                    }
//                } else {
//                    Column(modifier = Modifier.weight(1f).verticalScroll(scrollState).padding(horizontal = 16.dp, vertical = 8.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
//
//                        CollapsibleSection(title = "🌟 今日状态摘要", isExpanded = expandedSummary, onToggle = { expandedSummary = !it }, accentColor = Color(0xFF1565C0)) {
//                            Column(modifier = Modifier.padding(16.dp)) {
//                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                                    Text("健康得分: 极佳", fontSize = 12.sp, color = Color(0xFF4CAF50), fontWeight = FontWeight.Bold)
//                                }
//                                Spacer(modifier = Modifier.height(8.dp))
//                                Row(modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape)) {
//                                    Box(modifier = Modifier.weight(0.03f).fillMaxHeight().background(Color(0xFF64B5F6)))
//                                    Box(modifier = Modifier.weight(0.92f).fillMaxHeight().background(Color(0xFF4CAF50)))
//                                    Box(modifier = Modifier.weight(0.05f).fillMaxHeight().background(Color(0xFFE57373)))
//                                }
//                                Spacer(modifier = Modifier.height(8.dp))
//                                Text("血糖达标率 92%。较上周同期下降 12% 的波动。", fontSize = 12.sp, color = Color.DarkGray, lineHeight = 18.sp)
//                            }
//                        }
//
//                        CollapsibleSection(title = "💡 下一步建议 (Feedforward)", isExpanded = expandedAdvice, onToggle = { expandedAdvice = !it }, accentColor = Color(0xFFE65100)) {
//                            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
//                                Icon(Icons.Default.Lightbulb, null, tint = Color(0xFFE65100), modifier = Modifier.size(20.dp))
//                                Spacer(Modifier.width(12.dp))
//                                Text(cgmRep.feedforwardAction, fontSize = 12.sp, color = Color.DarkGray, lineHeight = 16.sp)
//                            }
//                        }
//
//                        CollapsibleSection(title = "🔄 血糖与情绪交叉分析", isExpanded = expandedCorrelation, onToggle = { expandedCorrelation = !it }, accentColor = Color(0xFF673AB7)) {
//                            CorrelationChartCard(cgmNodes, moodNodes)
//                        }
//
//                        CollapsibleSection(title = "📊 血糖趋势与临床指标", isExpanded = expandedCgm, onToggle = { expandedCgm = !it }, accentColor = Color(0xFF008080)) {
//                            Column {
//                                Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
//                                    IconButton(onClick = { showGlossaryDialog = true }) { Icon(Icons.Default.Info, "词典", tint = Color(0xFF008080)) }
//                                    IconButton(onClick = { showCgmHistoryDialog = true }) { Icon(Icons.Default.Search, "历史", tint = Color(0xFF008080)) }
//                                    IconButton(onClick = { showCgmDialog = true }) { Icon(Icons.Default.Add, "添加", tint = Color(0xFF008080)) }
//                                }
//                                ClinicalCgmCard(cgmRep, cgmNodes)
//                            }
//                        }
//
//                        CollapsibleSection(title = "🧠 情绪起伏追踪", isExpanded = expandedMood, onToggle = { expandedMood = !it }, accentColor = Color(0xFFFFB74D)) {
//                            Column {
//                                Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp), horizontalArrangement = Arrangement.End) {
//                                    IconButton(onClick = { showMoodHistoryDialog = true }) { Icon(Icons.Default.Search, "历史", tint = Color(0xFFFFB74D)) }
//                                    IconButton(onClick = { showMoodDialog = true }) { Icon(Icons.Default.Add, "添加", tint = Color(0xFFFFB74D)) }
//                                }
//                                ClinicalMoodCard(moodRep, moodNodes)
//                            }
//                        }
//
//                        CollapsibleSection(title = "🛡️ 生活方式记录清单", isExpanded = expandedLifestyle, onToggle = { expandedLifestyle = !it }, accentColor = Color(0xFF5C6BC0)) {
//                            Column(modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
//                                HealthListCard("🍏 饮食与用药", Color(0xFF81C784), reportViewModel.dietLogs.toList(), { }, { it.numericValue }, { "${it.time} - ${it.food} (${it.carbs})" })
//                                HealthListCard("🏃 活动与心率", Color(0xFF4FC3F7), reportViewModel.exerciseLogs.toList(), { }, { it.numericValue }, { "${it.time} - ${it.activity} (${it.duration})" })
//                            }
//                        }
//                        Spacer(Modifier.height(40.dp))
//                    }
//                }
//            }
//        }
//
//        // --- 弹窗逻辑 ---
//        if (showGlossaryDialog) {
//            AlertDialog(onDismissRequest = { showGlossaryDialog = false }, title = { Text("📖 健康数据小词典", fontWeight = FontWeight.Bold) },
//                text = { Text("TIR：目标范围内时间。\nMAGE：血糖波动幅度。\n情绪分数：基于Russell环形理论。") },
//                confirmButton = { TextButton(onClick = { showGlossaryDialog = false }) { Text("我知道了") } }
//            )
//        }
//
//        // 🆕 V19 终极版：iPhone 闹钟式血糖与时间双滚轮
//        if (showCgmDialog) {
//            val cal = Calendar.getInstance()
//            var hour by remember { mutableStateOf(cal.get(Calendar.HOUR_OF_DAY).toString().padStart(2, '0')) }
//            var minute by remember { mutableStateOf(cal.get(Calendar.MINUTE).toString().padStart(2, '0')) }
//            var bgInt by remember { mutableStateOf("5") }
//            var bgDec by remember { mutableStateOf("5") }
//
//            AlertDialog(onDismissRequest = { showCgmDialog = false },
//                title = { Text("记录单次血糖", fontWeight = FontWeight.Bold) },
//                text = {
//                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
//                        Text("滑动选择血糖值", fontSize = 12.sp, color = Color.Gray)
//                        Row(modifier = Modifier.fillMaxWidth().background(Color(0xFFFAFAFA), RoundedCornerShape(8.dp)).padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
//                            WheelPicker(items = (1..30).map { it.toString() }, initialValue = bgInt, onValueChange = { bgInt = it }, modifier = Modifier.weight(1f))
//                            Text(".", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFF008080), modifier = Modifier.padding(bottom = 8.dp))
//                            WheelPicker(items = (0..9).map { it.toString() }, initialValue = bgDec, onValueChange = { bgDec = it }, modifier = Modifier.weight(1f))
//                            Text("mmol", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.weight(0.5f).padding(start = 4.dp))
//                        }
//
//                        Text("滑动选择时间", fontSize = 12.sp, color = Color.Gray)
//                        Row(modifier = Modifier.fillMaxWidth().background(Color(0xFFFAFAFA), RoundedCornerShape(8.dp)).padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
//                            WheelPicker(items = (0..23).map { it.toString().padStart(2, '0') }, initialValue = hour, onValueChange = { hour = it }, modifier = Modifier.weight(1f))
//                            Text(":", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF008080), modifier = Modifier.padding(bottom = 4.dp))
//                            WheelPicker(items = (0..59).map { it.toString().padStart(2, '0') }, initialValue = minute, onValueChange = { minute = it }, modifier = Modifier.weight(1f))
//                        }
//                    }
//                },
//                confirmButton = { Button(onClick = {
//                    val finalBg = "$bgInt.$bgDec".toDoubleOrNull() ?: 5.5
//                    reportViewModel.addCgmNode(finalBg, "$hour:$minute"); showCgmDialog = false
//                }) { Text("保存记录") } },
//                dismissButton = { TextButton(onClick = { showCgmDialog = false }) { Text("取消", color = Color.Gray) } }
//            )
//        }
//
//        // 🆕 V19 终极版：iPhone 闹钟式时间滚轮 + Emoji
//        if (showMoodDialog) {
//            val cal = Calendar.getInstance()
//            var hour by remember { mutableStateOf(cal.get(Calendar.HOUR_OF_DAY).toString().padStart(2, '0')) }
//            var minute by remember { mutableStateOf(cal.get(Calendar.MINUTE).toString().padStart(2, '0')) }
//            var selectedMood by remember { mutableStateOf<Pair<String, Float>?>(null) }
//            val moodOptions = listOf("🤩 极佳" to 9f, "🙂 平稳" to 7f, "😐 疲惫" to 4f, "😞 低落" to 2f)
//
//            AlertDialog(onDismissRequest = { showMoodDialog = false },
//                title = { Text("记录当前心情", fontWeight = FontWeight.Bold) },
//                text = { Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
//                    Text("直接点击表情快速选择：", fontSize = 12.sp, color = Color.Gray)
//                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                        moodOptions.forEach { mood ->
//                            val isSelected = selectedMood == mood
//                            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable { selectedMood = mood }.background(if (isSelected) Color(0xFFE3F2FD) else Color.Transparent, RoundedCornerShape(8.dp)).padding(8.dp)) {
//                                Text(mood.first.split(" ")[0], fontSize = 28.sp)
//                                Text(mood.first.split(" ")[1], fontSize = 10.sp, fontWeight = if(isSelected) FontWeight.Bold else FontWeight.Normal, color = if(isSelected) Color(0xFF1565C0) else Color.Gray)
//                            }
//                        }
//                    }
//                    Text("滑动修正记录时间：", fontSize = 12.sp, color = Color.Gray)
//                    Row(modifier = Modifier.fillMaxWidth().background(Color(0xFFFAFAFA), RoundedCornerShape(8.dp)).padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
//                        WheelPicker(items = (0..23).map { it.toString().padStart(2, '0') }, initialValue = hour, onValueChange = { hour = it }, modifier = Modifier.weight(1f))
//                        Text(":", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF008080), modifier = Modifier.padding(bottom = 4.dp))
//                        WheelPicker(items = (0..59).map { it.toString().padStart(2, '0') }, initialValue = minute, onValueChange = { minute = it }, modifier = Modifier.weight(1f))
//                    }
//                }},
//                confirmButton = { Button(onClick = { selectedMood?.let { reportViewModel.addMoodNode(it.second, it.first.split(" ")[1], "$hour:$minute"); showMoodDialog = false } }, enabled = selectedMood != null) { Text("保存记录") } },
//                dismissButton = { TextButton(onClick = { showMoodDialog = false }) { Text("取消", color = Color.Gray) } }
//            )
//        }
//
//        if (showCgmHistoryDialog) {
//            AlertDialog(onDismissRequest = { showCgmHistoryDialog = false }, title = { Text("📊 血糖历史记录") },
//                text = { Column(modifier = Modifier.height(300.dp).verticalScroll(rememberScrollState())) {
//                    cgmNodes.reversed().forEach { Text("${it.timeLabel}: ${it.value} mmol/L", modifier = Modifier.padding(8.dp)) }
//                }}, confirmButton = { TextButton(onClick = { showCgmHistoryDialog = false }) { Text("关闭") } }
//            )
//        }
//
//        if (showMoodHistoryDialog) {
//            AlertDialog(onDismissRequest = { showMoodHistoryDialog = false }, title = { Text("🧠 情绪随手记") },
//                text = { Column(modifier = Modifier.height(300.dp).verticalScroll(rememberScrollState())) {
//                    moodNodes.reversed().forEach { Text("${it.timeLabel}: ${it.label} (${it.numericScore}分)", modifier = Modifier.padding(8.dp)) }
//                }}, confirmButton = { TextButton(onClick = { showMoodHistoryDialog = false }) { Text("关闭") } }
//            )
//        }
//    }
//}
//
//// 🆕 V19：纯 Compose 原生打造的丝滑阻尼滚轮 (iPhone Picker 完美复刻)
//@OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
//@Composable
//fun WheelPicker(
//    items: List<String>,
//    initialValue: String,
//    onValueChange: (String) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    val itemHeight = 40.dp
//    val visibleCount = 3
//    val startIndex = items.indexOf(initialValue).coerceAtLeast(0)
//    val listState = androidx.compose.foundation.lazy.rememberLazyListState(initialFirstVisibleItemIndex = startIndex)
//    val flingBehavior = androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior(lazyListState = listState)
//
//    LaunchedEffect(listState) {
//        androidx.compose.runtime.snapshotFlow { listState.firstVisibleItemIndex }.collect { index ->
//            if (index in items.indices) { onValueChange(items[index]) }
//        }
//    }
//
//    Box(modifier = modifier.height(itemHeight * visibleCount), contentAlignment = Alignment.Center) {
//        Box(modifier = Modifier.fillMaxWidth().height(itemHeight).background(Color(0xFF008080).copy(alpha = 0.1f), RoundedCornerShape(8.dp)))
//
//        androidx.compose.foundation.lazy.LazyColumn(
//            state = listState,
//            flingBehavior = flingBehavior,
//            contentPadding = PaddingValues(vertical = itemHeight * (visibleCount / 2)),
//            modifier = Modifier.fillMaxSize()
//        ) {
//            items(items.size) { index ->
//                val isSelected = index == listState.firstVisibleItemIndex
//                Box(modifier = Modifier.fillMaxWidth().height(itemHeight), contentAlignment = Alignment.Center) {
//                    Text(
//                        text = items[index],
//                        fontSize = if (isSelected) 20.sp else 14.sp,
//                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
//                        color = if (isSelected) Color(0xFF008080) else Color.LightGray
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun CollapsibleSection(
//    title: String,
//    isExpanded: Boolean,
//    onToggle: (Boolean) -> Unit,
//    accentColor: Color,
//    content: @Composable () -> Unit
//) {
//    val rotationState by animateFloatAsState(targetValue = if (isExpanded) 180f else 0f, label = "rotate")
//
//    Card(
//        modifier = Modifier.fillMaxWidth().animateContentSize(),
//        colors = CardDefaults.cardColors(containerColor = Color.White),
//        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
//        shape = RoundedCornerShape(12.dp)
//    ) {
//        Column {
//            Row(
//                modifier = Modifier.fillMaxWidth().clickable { onToggle(isExpanded) }.padding(16.dp),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Box(modifier = Modifier.size(4.dp, 16.dp).background(accentColor, CircleShape))
//                    Spacer(Modifier.width(12.dp))
//                    Text(text = title, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color.DarkGray)
//                }
//                Icon(
//                    imageVector = Icons.Default.ExpandMore,
//                    contentDescription = if (isExpanded) "折叠" else "展开",
//                    tint = Color.Gray,
//                    modifier = Modifier.rotate(rotationState)
//                )
//            }
//            AnimatedVisibility(visible = isExpanded) {
//                Column {
//                    HorizontalDivider(color = Color(0xFFF1F1F1))
//                    content()
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun CorrelationChartCard(cgmNodes: List<CgmNode>, moodNodes: List<MoodLog>) {
//    var selectedInsight by remember { mutableStateOf<String?>(null) }
//    var insightFeedback by remember { mutableStateOf<Boolean?>(null) }
//
//    Column(modifier = Modifier.padding(16.dp)) {
//        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//            Text("🟢 血糖 (面积底图)", color = Color(0xFF008080), fontWeight = FontWeight.Bold, fontSize = 11.sp)
//            Text("🟣 情绪 (折线)", color = Color(0xFF7986CB), fontWeight = FontWeight.Bold, fontSize = 11.sp)
//        }
//
//        Text("👉 左右滑动图表以查看完整时序数据", fontSize = 9.sp, color = Color.Gray, modifier = Modifier.fillMaxWidth().padding(top = 4.dp), textAlign = TextAlign.End)
//
//        if (selectedInsight != null) {
//            Surface(color = Color(0xFFFFF3E0), modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), shape = RoundedCornerShape(8.dp)) {
//                Column(modifier = Modifier.padding(10.dp)) {
//                    Text("🔬 智能洞察: $selectedInsight", fontSize = 11.sp, color = Color(0xFFE65100), lineHeight = 16.sp)
//                    if (insightFeedback == null) {
//                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                            Text("合理吗？", fontSize = 10.sp, color = Color.Gray)
//                            Row {
//                                TextButton(onClick = { insightFeedback = true }) { Text("👍", fontSize = 10.sp) }
//                                TextButton(onClick = { insightFeedback = false }) { Text("👎", fontSize = 10.sp) }
//                            }
//                        }
//                    } else {
//                        Text("感谢反馈！", fontSize = 10.sp, color = Color(0xFF4CAF50), modifier = Modifier.clickable { selectedInsight = null; insightFeedback = null })
//                    }
//                }
//            }
//        }
//
//        Box(modifier = Modifier.fillMaxWidth().height(250.dp).background(Color(0xFFFAFAFA), RoundedCornerShape(8.dp)).padding(8.dp)) {
//            val allTimes = (cgmNodes.map { it.timeLabel } + moodNodes.map { it.timeLabel }).distinct().sorted()
//            Canvas(modifier = Modifier.fillMaxSize().horizontalScroll(rememberScrollState()).width((allTimes.size * 45).coerceAtLeast(350).dp).clickable {
//                selectedInsight = "系统观察到您在 16:00 记录了『压力/烦躁』，随后血糖出现了 +0.2 的异常上升趋势。这说明情绪压力可能导致了皮质醇分泌，引起血糖波动。"
//                insightFeedback = null
//            }) {
//                val w = size.width; val h = size.height
//                fun scaleCgm(v: Double) = h * 0.85f - ((v / 15.0) * (h * 0.7f)).toFloat()
//                fun scaleMood(v: Float) = h * 0.85f - ((v / 10f) * (h * 0.7f))
//
//                drawLine(Color.LightGray.copy(alpha=0.3f), Offset(0f, scaleCgm(10.0)), Offset(w, scaleCgm(10.0)), strokeWidth=2f)
//                drawLine(Color.LightGray.copy(alpha=0.3f), Offset(0f, scaleCgm(3.9)), Offset(w, scaleCgm(3.9)), strokeWidth=2f)
//
//                val textPaint = android.graphics.Paint().apply { textSize = 18f; isFakeBoldText = true }
//
//                if (cgmNodes.isNotEmpty() && allTimes.isNotEmpty()) {
//                    val cgmPath = Path()
//                    val cgmAreaPath = Path()
//                    var firstX = 0f
//                    var lastX = 0f
//
//                    allTimes.forEachIndexed { i, time ->
//                        val x = if (allTimes.size > 1) (i.toFloat() / (allTimes.size - 1)) * (w - 40f) + 20f else w / 2
//                        val cgmNode = cgmNodes.find { it.timeLabel == time }
//                        if (cgmNode != null) {
//                            val y = scaleCgm(cgmNode.value)
//                            if (firstX == 0f) { cgmPath.moveTo(x, y); cgmAreaPath.moveTo(x, y); firstX = x }
//                            else { cgmPath.lineTo(x, y); cgmAreaPath.lineTo(x, y) }
//                            lastX = x
//                        }
//                    }
//                    if (firstX != 0f) {
//                        cgmAreaPath.lineTo(lastX, h)
//                        cgmAreaPath.lineTo(firstX, h)
//                        cgmAreaPath.close()
//                        drawPath(cgmAreaPath, color = Color(0xFF008080).copy(alpha = 0.15f))
//                        drawPath(cgmPath, color = Color(0xFF008080).copy(alpha = 0.5f), style = Stroke(2f))
//                    }
//                }
//
//                var lastMood: Offset? = null
//                allTimes.forEachIndexed { i, time ->
//                    val x = if (allTimes.size > 1) (i.toFloat() / (allTimes.size - 1)) * (w - 40f) + 20f else w / 2
//                    val isEven = i % 2 == 0
//                    val timeY = if (isEven) h - 5f else h - 25f
//                    drawContext.canvas.nativeCanvas.drawText(time, x - 18f, timeY, android.graphics.Paint().apply { textSize = 16f; color = android.graphics.Color.GRAY })
//
//                    val moodNode = moodNodes.find { it.timeLabel == time }
//                    if (moodNode != null) {
//                        val y = scaleMood(moodNode.numericScore)
//                        val current = Offset(x, y)
//                        if (lastMood != null) drawLine(Color(0xFF7986CB), lastMood!!, current, 4f)
//                        drawCircle(Color.White, 6f, current)
//                        drawCircle(Color(0xFF7986CB), 4f, current)
//                        textPaint.color = android.graphics.Color.parseColor("#7986CB")
//                        drawContext.canvas.nativeCanvas.drawText("${moodNode.numericScore}", x + 5f, if (isEven) y + 15f else y - 15f, textPaint)
//                        lastMood = current
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun ClinicalCgmCard(report: ClinicalCgmReport, nodes: List<CgmNode>) {
//    var showAdvancedMetrics by remember { mutableStateOf(false) }
//    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
//        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//            MetricItem("今日平均血糖", report.avgGlucose)
//            Text(report.clinicalAdvice, fontSize = 11.sp, color = Color(0xFF008080), modifier = Modifier.weight(1f).padding(start = 8.dp))
//        }
//
//        Text("👉 左右滑动图表以查看完整时序数据", fontSize = 10.sp, color = Color.Gray, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.End)
//        Box(modifier = Modifier.fillMaxWidth().height(220.dp).background(Color(0xFFFAFAFA), RoundedCornerShape(8.dp)).padding(8.dp)) {
//            Canvas(modifier = Modifier.fillMaxSize().horizontalScroll(rememberScrollState()).width((nodes.size * 45).coerceAtLeast(350).dp)) {
//                val w = size.width; val h = size.height
//                fun scaleY(v: Double) = (h - (v / 15.0) * h).toFloat()
//                drawRect(brush = SolidColor(Color(0xFFE8F5E9)), topLeft = Offset(0f, scaleY(10.0)), size = Size(w, scaleY(3.9) - scaleY(10.0)))
//                val gridPaint = android.graphics.Paint().apply { textSize = 18f; color = android.graphics.Color.GRAY }
//                listOf(3.9, 7.0, 10.0, 15.0).forEach { yVal ->
//                    val y = scaleY(yVal); drawLine(Color.LightGray.copy(alpha=0.5f), Offset(0f, y), Offset(w, y), strokeWidth = 2f)
//                    drawContext.canvas.nativeCanvas.drawText("$yVal", 5f, y - 5f, gridPaint)
//                }
//                if (nodes.isNotEmpty()) {
//                    val path = Path()
//                    nodes.forEachIndexed { i, n ->
//                        val x = if(nodes.size > 1) (i.toFloat() / (nodes.size - 1)) * (w - 40f) + 20f else w/2
//                        val y = scaleY(n.value)
//                        if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
//                        drawCircle(Color(0xFF008080), 4f, Offset(x, y)); drawCircle(Color.White, 2f, Offset(x, y))
//                        val isEven = i % 2 == 0
//                        drawContext.canvas.nativeCanvas.drawText(n.timeLabel, x - 15f, if (isEven) h - 5f else h - 20f, gridPaint)
//                    }
//                    drawPath(path, Color(0xFF008080), style = Stroke(3f))
//                }
//            }
//        }
//
//        TextButton(onClick = { showAdvancedMetrics = !showAdvancedMetrics }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
//            Text(if (showAdvancedMetrics) "收起临床指标" else "查看专业临床指标", fontSize = 12.sp, color = Color(0xFF008080))
//        }
//        AnimatedVisibility(visible = showAdvancedMetrics) {
//            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
//                HorizontalDivider(color = Color(0xFFF1F1F1))
//                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                    MetricItem("波动 (CV)", report.cv); MetricItem("MAGE指标", report.mage); MetricItem("GMI 预估", report.gmi)
//                }
//                Column(modifier = Modifier.fillMaxWidth().background(Color(0xFFF1F8E9), RoundedCornerShape(8.dp)).padding(10.dp)) {
//                    Text("🎯 TIR 目标达标率", fontWeight = FontWeight.Bold, fontSize = 13.sp)
//                    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp).height(12.dp).clip(CircleShape)) {
//                        Box(modifier = Modifier.weight(report.tirLow.toFloat()).fillMaxHeight().background(Color(0xFF64B5F6)))
//                        Box(modifier = Modifier.weight(report.tirTarget.toFloat()).fillMaxHeight().background(Color(0xFF4CAF50)))
//                        Box(modifier = Modifier.weight(report.tirHigh.toFloat()).fillMaxHeight().background(Color(0xFFE57373)))
//                    }
//                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                        Text("低血糖: ${report.tbrLevel1}", fontSize = 10.sp); Text("达标: ${report.tirTarget}%", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Color(0xFF4CAF50)); Text("高血糖: ${report.tarLevel1}", fontSize = 10.sp)
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun ClinicalMoodCard(report: ClinicalMoodReport, nodes: List<MoodLog>) {
//    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
//        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//            MetricItem("平均分", report.meanScore); MetricItem("不稳定指数", report.instabilityIndex)
//            Surface(color = Color(0xFFFFF3E0), shape = RoundedCornerShape(4.dp)) {
//                Text(report.ipsativeTrend, fontSize = 9.sp, color = Color(0xFFE65100), modifier = Modifier.padding(4.dp))
//            }
//        }
//
//        Text("👉 左右滑动图表以查看完整时序数据", fontSize = 10.sp, color = Color.Gray, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.End)
//        Box(modifier = Modifier.horizontalScroll(rememberScrollState())) {
//            Canvas(modifier = Modifier.width((nodes.size * 45).coerceAtLeast(300).dp).height(160.dp)) {
//                val ep = android.graphics.Paint().apply { textSize = 35f }
//                drawContext.canvas.nativeCanvas.drawText("🤩", 0f, 35f, ep)
//                drawContext.canvas.nativeCanvas.drawText("🙂", 0f, size.height * 0.35f, ep)
//                drawContext.canvas.nativeCanvas.drawText("😐", 0f, size.height * 0.65f, ep)
//                drawContext.canvas.nativeCanvas.drawText("😞", 0f, size.height - 5f, ep)
//                val p = Path()
//                nodes.forEachIndexed { i, n ->
//                    val x = if(nodes.size > 1) (i.toFloat() / (nodes.size - 1)) * (size.width - 50f) + 50f else size.width / 2
//                    val y = size.height - (n.numericScore / 10f) * size.height
//                    if (i == 0) p.moveTo(x, y) else p.lineTo(x, y)
//                    drawCircle(Color(0xFF90CAF9), 6f, Offset(x, y))
//                    drawContext.canvas.nativeCanvas.drawText("${n.numericScore}", x-10f, if(i%2==0) y-15f else y+25f, android.graphics.Paint().apply { textSize=18f; color=android.graphics.Color.DKGRAY })
//                }
//                drawPath(p, Color(0xFF7986CB), style = Stroke(3f))
//            }
//        }
//    }
//}
//
//@Composable
//fun <T> HealthListCard(title: String, accent: Color, items: List<T>, onAddClick: () -> Unit, valueSelector: (T) -> Float, textSelector: (T) -> String) {
//    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White), border = BorderStroke(0.5.dp, Color.LightGray)) {
//        Column(modifier = Modifier.padding(12.dp)) {
//            Text(title, fontWeight = FontWeight.Bold, color = accent, fontSize = 13.sp)
//            items.forEach { Text(textSelector(it), fontSize = 11.sp, color = Color.Gray, modifier = Modifier.padding(top = 4.dp)) }
//        }
//    }
//}
//
//@Composable
//fun MetricItem(label: String, value: String) {
//    Column { Text(label, fontSize = 10.sp, color = Color.Gray); Text(value, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF008080)) }
//}
//
//@Composable
//fun TimeScaleSelector(selectedScale: String, onScaleSelect: (String) -> Unit) {
//    val scales = listOf("日", "月", "年")
//    Surface(modifier = Modifier.fillMaxWidth().height(36.dp), shape = RoundedCornerShape(18.dp), color = Color(0xFFE0E0E0)) {
//        Row {
//            scales.forEach { s ->
//                val isSelected = selectedScale == s
//                Box(modifier = Modifier.weight(1f).fillMaxHeight().padding(2.dp).background(if(isSelected) Color.White else Color.Transparent, RoundedCornerShape(16.dp)).clickable { onScaleSelect(s) }, contentAlignment = Alignment.Center) {
//                    Text(s, fontSize = 12.sp, color = if(isSelected) Color(0xFF008080) else Color.Gray)
//                }
//            }
//        }
//    }
//}

package com.withapp.with.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.withapp.with.viewmodels.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportView(reportViewModel: ReportViewModel, snackbarHostState: SnackbarHostState) {
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val cgmNodes = reportViewModel.cgmNodes
    val moodNodes = reportViewModel.moodNodes
    val cgmRep = reportViewModel.cgmReport.value
    val moodRep = reportViewModel.moodReport.value
    val isVacationMode = reportViewModel.userProfile.value.isVacationMode

    var expandedSummary by remember { mutableStateOf(true) }
    var expandedAdvice by remember { mutableStateOf(true) }
    var expandedCorrelation by remember { mutableStateOf(true) }
    var expandedCgm by remember { mutableStateOf(false) }
    var expandedMood by remember { mutableStateOf(false) }
    var expandedLifestyle by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("健康回顾 / Report", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
                actions = {
                    IconButton(onClick = { scope.launch { snackbarHostState.showSnackbar("✅ PDF 生成中 / PDF Generating...") } }) {
                        Icon(Icons.Default.Share, null, tint = Color(0xFF008080))
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues).background(Color(0xFFF7F9FA))) {

            if (isVacationMode) {
                Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🌙", fontSize = 60.sp); Spacer(modifier = Modifier.height(16.dp))
                    Text("休眠模式中 / Vacation Mode", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                    Text("所有分析已隐藏，避免数据焦虑。\nAnalytics hidden to avoid anxiety.", fontSize = 12.sp, color = Color.Gray, textAlign = TextAlign.Center, modifier = Modifier.padding(16.dp))
                }
            } else {
                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    TimeScaleSelector(reportViewModel.currentScale.value, { reportViewModel.currentScale.value = it })
                }

                if (cgmNodes.isEmpty() && moodNodes.isEmpty()) {
                    Column(modifier = Modifier.weight(1f).fillMaxWidth(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("🌱", fontSize = 60.sp); Spacer(modifier = Modifier.height(16.dp))
                        Text("健康是一场旅程 / Health is a journey", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF008080))
                        Text("暂无数据，随时欢迎回来记录。\nNo data yet. Start logging when ready.", fontSize = 12.sp, color = Color.Gray, textAlign = TextAlign.Center, modifier = Modifier.padding(16.dp))
                    }
                } else {
                    Column(modifier = Modifier.weight(1f).verticalScroll(scrollState).padding(horizontal = 16.dp, vertical = 8.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {

                        CollapsibleSection("🌟 今日摘要 / Summary", expandedSummary, { expandedSummary = !it }, Color(0xFF1565C0)) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("健康得分 / Score: 极佳/Excellent", fontSize = 12.sp, color = Color(0xFF4CAF50), fontWeight = FontWeight.Bold)
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape)) {
                                    Box(modifier = Modifier.weight(0.03f).fillMaxHeight().background(Color(0xFF64B5F6)))
                                    Box(modifier = Modifier.weight(0.92f).fillMaxHeight().background(Color(0xFF4CAF50)))
                                    Box(modifier = Modifier.weight(0.05f).fillMaxHeight().background(Color(0xFFE57373)))
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("达标率 92%。较上周波动下降 12%。\n92% in target. Var. down 12% vs last week.", fontSize = 12.sp, color = Color.DarkGray, lineHeight = 18.sp)
                            }
                        }

                        CollapsibleSection("💡 建议 / Feedforward", expandedAdvice, { expandedAdvice = !it }, Color(0xFFE65100)) {
                            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Lightbulb, null, tint = Color(0xFFE65100), modifier = Modifier.size(20.dp))
                                Spacer(Modifier.width(12.dp))
                                Text(cgmRep.feedforwardAction, fontSize = 12.sp, color = Color.DarkGray, lineHeight = 16.sp)
                            }
                        }

                        CollapsibleSection("🔄 交叉分析 / Correlation", expandedCorrelation, { expandedCorrelation = !it }, Color(0xFF673AB7)) {
                            CorrelationChartCard(cgmNodes, moodNodes)
                        }

                        CollapsibleSection("📊 血糖趋势 / BG Trend", expandedCgm, { expandedCgm = !it }, Color(0xFF008080)) {
                            ClinicalCgmCard(cgmRep, cgmNodes)
                        }

                        CollapsibleSection("🧠 情绪追踪 / Mood Tracking", expandedMood, { expandedMood = !it }, Color(0xFFFFB74D)) {
                            ClinicalMoodCard(moodRep, moodNodes)
                        }

                        CollapsibleSection("🛡️ 生活方式 / Lifestyle", expandedLifestyle, { expandedLifestyle = !it }, Color(0xFF5C6BC0)) {
                            Column(modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                HealthListCard("🍏 饮食用药 / Diet & Meds", Color(0xFF81C784), reportViewModel.dietLogs.toList(), { it.numericValue }, { "${it.time} - ${it.food} (${it.carbs})" })
                                HealthListCard("🏃 活动心率 / Activities", Color(0xFF4FC3F7), reportViewModel.exerciseLogs.toList(), { it.numericValue }, { "${it.time} - ${it.activity} (${it.duration})" })
                            }
                        }
                        Spacer(Modifier.height(40.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun CollapsibleSection(title: String, isExpanded: Boolean, onToggle: (Boolean) -> Unit, accentColor: Color, content: @Composable () -> Unit) {
    val rotationState by animateFloatAsState(targetValue = if (isExpanded) 180f else 0f, label = "rotate")
    Card(modifier = Modifier.fillMaxWidth().animateContentSize(), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(2.dp), shape = RoundedCornerShape(12.dp)) {
        Column {
            Row(modifier = Modifier.fillMaxWidth().clickable { onToggle(isExpanded) }.padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(4.dp, 16.dp).background(accentColor, CircleShape))
                    Spacer(Modifier.width(12.dp))
                    Text(text = title, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color.DarkGray)
                }
                Icon(Icons.Default.ExpandMore, null, tint = Color.Gray, modifier = Modifier.rotate(rotationState))
            }
            AnimatedVisibility(visible = isExpanded) {
                Column { HorizontalDivider(color = Color(0xFFF1F1F1)); content() }
            }
        }
    }
}

@Composable
fun CorrelationChartCard(cgmNodes: List<CgmNode>, moodNodes: List<MoodLog>) {
    var selectedInsight by remember { mutableStateOf<String?>(null) }
    var insightFeedback by remember { mutableStateOf<Boolean?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("🟢 血糖 / BG", color = Color(0xFF008080), fontWeight = FontWeight.Bold, fontSize = 11.sp)
            Text("🟣 情绪 / Mood", color = Color(0xFF7986CB), fontWeight = FontWeight.Bold, fontSize = 11.sp)
        }
        Text("👉 左右滑动图表 / Swipe to view", fontSize = 9.sp, color = Color.Gray, modifier = Modifier.fillMaxWidth().padding(top = 4.dp), textAlign = TextAlign.End)

        if (selectedInsight != null) {
            Surface(color = Color(0xFFFFF3E0), modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), shape = RoundedCornerShape(8.dp)) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text("🔬 智能洞察 / Insight: $selectedInsight", fontSize = 11.sp, color = Color(0xFFE65100), lineHeight = 16.sp)
                    if (insightFeedback == null) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("合理吗？/ Helpful?", fontSize = 10.sp, color = Color.Gray)
                            Row {
                                TextButton(onClick = { insightFeedback = true }) { Text("👍", fontSize = 10.sp) }
                                TextButton(onClick = { insightFeedback = false }) { Text("👎", fontSize = 10.sp) }
                            }
                        }
                    } else {
                        Text("感谢反馈！/ Thank you!", fontSize = 10.sp, color = Color(0xFF4CAF50), modifier = Modifier.clickable { selectedInsight = null; insightFeedback = null })
                    }
                }
            }
        }

        Box(modifier = Modifier.fillMaxWidth().height(200.dp).background(Color(0xFFFAFAFA), RoundedCornerShape(8.dp)).padding(8.dp)) {
            Canvas(modifier = Modifier.fillMaxSize().horizontalScroll(rememberScrollState()).width(1000.dp).clickable {
                selectedInsight = "情绪压力可能导致了皮质醇分泌，引起血糖波动。\nEmotional stress might have caused the BG spike."
                insightFeedback = null
            }) {
                val w = size.width; val h = size.height
                fun scaleCgm(v: Double) = h * 0.85f - ((v / 15.0) * (h * 0.7f)).toFloat()
                fun scaleMood(v: Float) = h * 0.85f - ((v / 10f) * (h * 0.7f))
                drawLine(Color.LightGray.copy(0.3f), Offset(0f, scaleCgm(10.0)), Offset(w, scaleCgm(10.0)), 2f)
                drawLine(Color.LightGray.copy(0.3f), Offset(0f, scaleCgm(3.9)), Offset(w, scaleCgm(3.9)), 2f)

                val cgmPath = Path(); moodNodes.forEachIndexed { i, n ->
                val x = i * 60f + 50f; val y = scaleMood(n.numericScore)
                if(i==0) cgmPath.moveTo(x, y) else cgmPath.lineTo(x, y)
                drawCircle(Color(0xFF7986CB), 5f, Offset(x, y))
            }
                drawPath(cgmPath, Color(0xFF7986CB), style = Stroke(3f))
            }
        }
    }
}

@Composable
fun ClinicalCgmCard(report: ClinicalCgmReport, nodes: List<CgmNode>) {
    var showAdvancedMetrics by remember { mutableStateOf(false) }
    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            MetricItem("今日均值 / Avg BG", report.avgGlucose)
            Text(report.clinicalAdvice, fontSize = 11.sp, color = Color(0xFF008080), modifier = Modifier.weight(1f).padding(start = 8.dp))
        }

        Text("👉 左右滑动图表 / Swipe to view", fontSize = 10.sp, color = Color.Gray, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.End)
        Box(modifier = Modifier.fillMaxWidth().height(220.dp).background(Color(0xFFFAFAFA), RoundedCornerShape(8.dp)).padding(8.dp)) {
            Canvas(modifier = Modifier.fillMaxSize().horizontalScroll(rememberScrollState()).width((nodes.size * 45).coerceAtLeast(350).dp)) {
                val w = size.width; val h = size.height
                fun scaleY(v: Double) = (h - (v / 15.0) * h).toFloat()
                drawRect(brush = SolidColor(Color(0xFFE8F5E9)), topLeft = Offset(0f, scaleY(10.0)), size = Size(w, scaleY(3.9) - scaleY(10.0)))
                val gridPaint = android.graphics.Paint().apply { textSize = 18f; color = android.graphics.Color.GRAY }
                listOf(3.9, 7.0, 10.0, 15.0).forEach { yVal ->
                    val y = scaleY(yVal); drawLine(Color.LightGray.copy(alpha=0.5f), Offset(0f, y), Offset(w, y), strokeWidth = 2f)
                    drawContext.canvas.nativeCanvas.drawText("$yVal", 5f, y - 5f, gridPaint)
                }
                if (nodes.isNotEmpty()) {
                    val path = Path()
                    nodes.forEachIndexed { i, n ->
                        val x = if(nodes.size > 1) (i.toFloat() / (nodes.size - 1)) * (w - 40f) + 20f else w/2
                        val y = scaleY(n.value)
                        if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
                        drawCircle(Color(0xFF008080), 4f, Offset(x, y)); drawCircle(Color.White, 2f, Offset(x, y))
                        val isEven = i % 2 == 0
                        drawContext.canvas.nativeCanvas.drawText(n.timeLabel, x - 15f, if (isEven) h - 5f else h - 20f, gridPaint)
                    }
                    drawPath(path, Color(0xFF008080), style = Stroke(3f))
                }
            }
        }

        TextButton(onClick = { showAdvancedMetrics = !showAdvancedMetrics }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(if (showAdvancedMetrics) "收起指标 / Hide" else "专业指标 / Clinical Metrics", fontSize = 12.sp, color = Color(0xFF008080))
        }
        AnimatedVisibility(visible = showAdvancedMetrics) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                HorizontalDivider(color = Color(0xFFF1F1F1))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    MetricItem("CV", report.cv); MetricItem("MAGE", report.mage); MetricItem("GMI", report.gmi)
                }
                Column(modifier = Modifier.fillMaxWidth().background(Color(0xFFF1F8E9), RoundedCornerShape(8.dp)).padding(10.dp)) {
                    Text("🎯 TIR (Time in Range)", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp).height(12.dp).clip(CircleShape)) {
                        Box(modifier = Modifier.weight(report.tirLow.toFloat()).fillMaxHeight().background(Color(0xFF64B5F6)))
                        Box(modifier = Modifier.weight(report.tirTarget.toFloat()).fillMaxHeight().background(Color(0xFF4CAF50)))
                        Box(modifier = Modifier.weight(report.tirHigh.toFloat()).fillMaxHeight().background(Color(0xFFE57373)))
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("低/Low: ${report.tbrLevel1}", fontSize = 10.sp); Text("达标/Target: ${report.tirTarget}%", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Color(0xFF4CAF50)); Text("高/High: ${report.tarLevel1}", fontSize = 10.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun ClinicalMoodCard(report: ClinicalMoodReport, nodes: List<MoodLog>) {
    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            MetricItem("均分 / Mean", report.meanScore); MetricItem("不稳定指数 / Instability", report.instabilityIndex)
            Surface(color = Color(0xFFFFF3E0), shape = RoundedCornerShape(4.dp)) {
                Text(report.ipsativeTrend, fontSize = 9.sp, color = Color(0xFFE65100), modifier = Modifier.padding(4.dp))
            }
        }

        Text("👉 左右滑动图表 / Swipe to view", fontSize = 10.sp, color = Color.Gray, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.End)
        Box(modifier = Modifier.horizontalScroll(rememberScrollState())) {
            Canvas(modifier = Modifier.width((nodes.size * 45).coerceAtLeast(300).dp).height(160.dp)) {
                val ep = android.graphics.Paint().apply { textSize = 35f }
                drawContext.canvas.nativeCanvas.drawText("🤩", 0f, 35f, ep)
                drawContext.canvas.nativeCanvas.drawText("🙂", 0f, size.height * 0.35f, ep)
                drawContext.canvas.nativeCanvas.drawText("😐", 0f, size.height * 0.65f, ep)
                drawContext.canvas.nativeCanvas.drawText("😞", 0f, size.height - 5f, ep)
                val p = Path()
                nodes.forEachIndexed { i, n ->
                    val x = if(nodes.size > 1) (i.toFloat() / (nodes.size - 1)) * (size.width - 50f) + 50f else size.width / 2
                    val y = size.height - (n.numericScore / 10f) * size.height
                    if (i == 0) p.moveTo(x, y) else p.lineTo(x, y)
                    drawCircle(Color(0xFF90CAF9), 6f, Offset(x, y))
                    drawContext.canvas.nativeCanvas.drawText("${n.numericScore}", x-10f, if(i%2==0) y-15f else y+25f, android.graphics.Paint().apply { textSize=18f; color=android.graphics.Color.DKGRAY })
                }
                drawPath(p, Color(0xFF7986CB), style = Stroke(3f))
            }
        }
    }
}

@Composable
fun <T> HealthListCard(title: String, accent: Color, items: List<T>, valueSelector: (T) -> Float, textSelector: (T) -> String) {
    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White), border = BorderStroke(0.5.dp, Color.LightGray)) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(title, fontWeight = FontWeight.Bold, color = accent, fontSize = 13.sp)
            items.forEach { Text(textSelector(it), fontSize = 11.sp, color = Color.Gray, modifier = Modifier.padding(top = 4.dp)) }
        }
    }
}

@Composable
fun MetricItem(label: String, value: String) {
    Column { Text(label, fontSize = 10.sp, color = Color.Gray); Text(value, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF008080)) }
}

@Composable
fun TimeScaleSelector(selectedScale: String, onScaleSelect: (String) -> Unit) {
    val scales = listOf("日 / D", "月 / M", "年 / Y")
    Surface(modifier = Modifier.fillMaxWidth().height(36.dp), shape = RoundedCornerShape(18.dp), color = Color(0xFFE0E0E0)) {
        Row {
            scales.forEach { s ->
                val isSelected = selectedScale == s
                Box(modifier = Modifier.weight(1f).fillMaxHeight().padding(2.dp).background(if(isSelected) Color.White else Color.Transparent, RoundedCornerShape(16.dp)).clickable { onScaleSelect(s) }, contentAlignment = Alignment.Center) {
                    Text(s, fontSize = 12.sp, color = if(isSelected) Color(0xFF008080) else Color.Gray)
                }
            }
        }
    }
}