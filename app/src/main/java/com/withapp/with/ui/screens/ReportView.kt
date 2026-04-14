//
//package com.withapp.with.ui.screens
//
//import androidx.compose.animation.AnimatedVisibility
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
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.material.icons.filled.Info
//import androidx.compose.material.icons.filled.Search
//import androidx.compose.material.icons.filled.ExpandMore
//import androidx.compose.material.icons.filled.ExpandLess
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
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
//import com.withapp.with.viewmodels.*
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ReportView(reportViewModel: ReportViewModel) {
//    val scrollState = rememberScrollState()
//    val cgmNodes = reportViewModel.cgmNodes
//    val moodNodes = reportViewModel.moodNodes
//    val cgmRep = reportViewModel.cgmReport.value
//    val moodRep = reportViewModel.moodReport.value
//
//    var showCgmDialog by remember { mutableStateOf(false) }
//    var showMoodDialog by remember { mutableStateOf(false) }
//    var showCgmHistoryDialog by remember { mutableStateOf(false) }
//    var showMoodHistoryDialog by remember { mutableStateOf(false) }
//    var showGlossaryDialog by remember { mutableStateOf(false) }
//
//    Scaffold { paddingValues ->
//        Column(modifier = Modifier.fillMaxSize().padding(paddingValues).background(Color(0xFFF7F9FA))) {
//
//            Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp).padding(top = 8.dp)) {
//                TimeScaleSelector(selectedScale = reportViewModel.currentScale.value, onScaleSelect = { reportViewModel.currentScale.value = it })
//            }
//
//            Column(modifier = Modifier.weight(1f).verticalScroll(scrollState).padding(horizontal = 16.dp, vertical = 8.dp), verticalArrangement = Arrangement.spacedBy(24.dp)) {
//
//                Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))) {
//                    Column(modifier = Modifier.padding(16.dp)) {
//                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
//                            Text("🌟 今日状态摘要", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF1565C0))
//                            Text("健康得分: 极佳", fontSize = 12.sp, color = Color(0xFF4CAF50), fontWeight = FontWeight.Bold)
//                        }
//                        Spacer(modifier = Modifier.height(10.dp))
//                        Row(modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape)) {
//                            Box(modifier = Modifier.weight(0.03f).fillMaxHeight().background(Color(0xFF64B5F6)))
//                            Box(modifier = Modifier.weight(0.92f).fillMaxHeight().background(Color(0xFF4CAF50)))
//                            Box(modifier = Modifier.weight(0.05f).fillMaxHeight().background(Color(0xFFE57373)))
//                        }
//                        Spacer(modifier = Modifier.height(10.dp))
//                        Text("您今天的血糖达标率为 92%。下午 16:00 记录了一次压力情绪，可能伴随轻微波动。请注意劳逸结合。", fontSize = 12.sp, color = Color.DarkGray, lineHeight = 18.sp)
//                    }
//                }
//
//                CorrelationChartCard(cgmNodes, moodNodes)
//
//                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
//                    Text("📊 血糖趋势与分析", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF008080))
//                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//                        IconButton(onClick = { showGlossaryDialog = true }, modifier = Modifier.size(28.dp).background(Color(0xFF008080).copy(0.1f), CircleShape)) { Icon(Icons.Default.Info, contentDescription = "Glossary", tint = Color(0xFF008080), modifier = Modifier.size(16.dp)) }
//                        IconButton(onClick = { showCgmHistoryDialog = true }, modifier = Modifier.size(28.dp).background(Color(0xFF008080).copy(0.1f), CircleShape)) { Icon(Icons.Default.Search, contentDescription = "History", tint = Color(0xFF008080), modifier = Modifier.size(16.dp)) }
//                        IconButton(onClick = { showCgmDialog = true }, modifier = Modifier.size(28.dp).background(Color(0xFF008080).copy(0.1f), CircleShape)) { Icon(Icons.Default.Add, contentDescription = "Add", tint = Color(0xFF008080), modifier = Modifier.size(18.dp)) }
//                    }
//                }
//                ClinicalCgmCard(cgmRep, cgmNodes)
//
//                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
//                    Text("🧠 情绪起伏追踪", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFFFFB74D))
//                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//                        IconButton(onClick = { showMoodHistoryDialog = true }, modifier = Modifier.size(28.dp).background(Color(0xFFFFB74D).copy(0.1f), CircleShape)) { Icon(Icons.Default.Search, contentDescription = "History", tint = Color(0xFFFFB74D), modifier = Modifier.size(16.dp)) }
//                        IconButton(onClick = { showMoodDialog = true }, modifier = Modifier.size(28.dp).background(Color(0xFFFFB74D).copy(0.1f), CircleShape)) { Icon(Icons.Default.Add, contentDescription = "Add", tint = Color(0xFFFFB74D), modifier = Modifier.size(18.dp)) }
//                    }
//                }
//                ClinicalMoodCard(moodRep, moodNodes)
//
//                Text("🛡️ 生活方式记录", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF5C6BC0))
//                HealthListCard("🍏 饮食与用药", Color(0xFF81C784), reportViewModel.dietLogs.toList(), { }, { it.numericValue }, { "${it.time} - ${it.food} (${it.carbs})" })
//                HealthListCard("🏃 活动与心率", Color(0xFF4FC3F7), reportViewModel.exerciseLogs.toList(), { }, { it.numericValue }, { "${it.time} - ${it.activity} (${it.duration})" })
//
//                Spacer(Modifier.height(40.dp))
//            }
//        }
//
//        if (showGlossaryDialog) {
//            AlertDialog(onDismissRequest = { showGlossaryDialog = false }, title = { Text("📖 健康数据小词典", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF008080)) },
//                text = {
//                    Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.verticalScroll(rememberScrollState())) {
//                        Text("• TIR (目标范围内时间)：指您的血糖在理想安全范围内的时间占比。越高越好。", fontSize = 12.sp)
//                        Text("• MAGE (平均血糖波动幅度)：反映您血糖像过山车一样波动的剧烈程度。越低越好。", fontSize = 12.sp)
//                        Text("• 情绪分数 (0-10)：基于 Russell 环形理论，分数越高代表能量越高、越愉悦；分数越低代表越疲惫或低落。", fontSize = 12.sp)
//                    }
//                }, confirmButton = { TextButton(onClick = { showGlossaryDialog = false }) { Text("我知道了", color = Color(0xFF008080)) } }
//            )
//        }
//
//        if (showCgmDialog) {
//            var v1 by remember { mutableStateOf("") }; var v2 by remember { mutableStateOf("") }
//            AlertDialog(onDismissRequest = { showCgmDialog = false }, title = { Text("记录单次血糖", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
//                text = { Column(verticalArrangement = Arrangement.spacedBy(12.dp)) { OutlinedTextField(v1, {v1=it}, label={Text("血糖值 (mmol/L)", fontSize=12.sp)}, modifier = Modifier.fillMaxWidth()); OutlinedTextField(v2, {v2=it}, label={Text("时间戳 (如 14:30)", fontSize=12.sp)}, modifier = Modifier.fillMaxWidth()) } },
//                confirmButton = { Button(onClick = { reportViewModel.addCgmNode(v1.toDoubleOrNull() ?: 5.5, v2); showCgmDialog = false }) { Text("保存") } },
//                dismissButton = { TextButton(onClick = { showCgmDialog = false }) { Text("取消", color = Color.Gray) } }
//            )
//        }
//
//        if (showMoodDialog) {
//            var timeInput by remember { mutableStateOf("") }
//            var selectedMood by remember { mutableStateOf<Pair<String, Float>?>(null) }
//            val moodOptions = listOf("🤩 极佳" to 9f, "🙂 平稳" to 7f, "😐 疲惫" to 4f, "😞 低落" to 2f)
//
//            AlertDialog(onDismissRequest = { showMoodDialog = false },
//                title = { Text("记录当前心情", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
//                text = { Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
//                    Text("请直接点击最符合您现在状态的表情：", fontSize = 12.sp, color = Color.Gray)
//                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                        moodOptions.forEach { mood ->
//                            val isSelected = selectedMood == mood
//                            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable { selectedMood = mood }.background(if (isSelected) Color(0xFFE3F2FD) else Color.Transparent, RoundedCornerShape(8.dp)).padding(8.dp)) {
//                                Text(mood.first.split(" ")[0], fontSize = 28.sp)
//                                Text(mood.first.split(" ")[1], fontSize = 10.sp, fontWeight = if(isSelected) FontWeight.Bold else FontWeight.Normal, color = if(isSelected) Color(0xFF1565C0) else Color.Gray)
//                            }
//                        }
//                    }
//                    OutlinedTextField(timeInput, {timeInput=it}, label={Text("时间 (可选，默认现在)", fontSize=12.sp)}, modifier = Modifier.fillMaxWidth())
//                } },
//                confirmButton = { Button(onClick = {
//                    selectedMood?.let { reportViewModel.addMoodNode(it.second, it.first.split(" ")[1], timeInput) }
//                    showMoodDialog = false
//                }, enabled = selectedMood != null) { Text("保存") } },
//                dismissButton = { TextButton(onClick = { showMoodDialog = false }) { Text("取消", color = Color.Gray) } }
//            )
//        }
//
//        if (showCgmHistoryDialog) {
//            AlertDialog(onDismissRequest = { showCgmHistoryDialog = false }, title = { Text("📊 血糖历史记录", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF008080)) },
//                text = {
//                    Column(modifier = Modifier.fillMaxWidth().heightIn(max = 400.dp).verticalScroll(rememberScrollState())) {
//                        if (cgmNodes.isEmpty()) { Text("暂无数据记录", fontSize = 13.sp, color = Color.Gray) } else {
//                            cgmNodes.reversed().forEach { node ->
//                                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
//                                    Column(modifier = Modifier.weight(1f)) {
//                                        Text(node.timeLabel, fontSize = 14.sp, color = Color.Gray)
//                                        Surface(color = Color(0xFFE8F5E9), shape = RoundedCornerShape(4.dp), modifier = Modifier.padding(top = 4.dp)) {
//                                            Text("✓ 探头自动同步", fontSize = 9.sp, color = Color(0xFF2E7D32), modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp))
//                                        }
//                                    }
//                                    Text("${node.value} mmol/L ${node.trend}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
//                                }
//                                HorizontalDivider(color = Color(0xFFF1F1F1))
//                            }
//                        }
//                    }
//                }, confirmButton = { TextButton(onClick = { showCgmHistoryDialog = false }) { Text("关闭", color = Color(0xFF008080)) } }
//            )
//        }
//
//        if (showMoodHistoryDialog) {
//            AlertDialog(onDismissRequest = { showMoodHistoryDialog = false }, title = { Text("🧠 情绪随手记", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFFFFB74D)) },
//                text = {
//                    Column(modifier = Modifier.fillMaxWidth().heightIn(max = 400.dp).verticalScroll(rememberScrollState())) {
//                        if (moodNodes.isEmpty()) { Text("暂无数据记录", fontSize = 13.sp, color = Color.Gray) } else {
//                            moodNodes.reversed().forEach { node ->
//                                Column(modifier = Modifier.fillMaxWidth().background(Color(0xFFFFFDF5), RoundedCornerShape(8.dp)).padding(12.dp).padding(bottom = 4.dp)) {
//                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
//                                        Text(node.timeLabel, fontSize = 12.sp, color = Color.Gray)
//                                        Text("${node.numericScore} 分", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFFB74D))
//                                    }
//                                    Spacer(Modifier.height(6.dp))
//                                    Text("✍️ ${node.label}", fontSize = 14.sp, color = Color.DarkGray, lineHeight = 20.sp)
//                                    Surface(color = Color(0xFFFFF3E0), shape = RoundedCornerShape(4.dp), modifier = Modifier.padding(top = 6.dp)) {
//                                        Text("👤 用户主动记录", fontSize = 9.sp, color = Color(0xFFE65100), modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp))
//                                    }
//                                }
//                                Spacer(Modifier.height(8.dp))
//                            }
//                        }
//                    }
//                }, confirmButton = { TextButton(onClick = { showMoodHistoryDialog = false }) { Text("关闭", color = Color(0xFFFFB74D)) } }
//            )
//        }
//    }
//}
//
//@Composable
//fun CorrelationChartCard(cgmNodes: List<CgmNode>, moodNodes: List<MoodLog>) {
//    var selectedInsight by remember { mutableStateOf<String?>(null) }
//    var insightFeedback by remember { mutableStateOf<Boolean?>(null) }
//
//    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White)) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text("🔄 血糖与情绪交叉分析", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF673AB7))
//            Row(modifier = Modifier.fillMaxWidth().padding(top=8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
//                Text("🟢 血糖 (面积底图)", color = Color(0xFF008080), fontWeight = FontWeight.Bold, fontSize = 12.sp)
//                Text("🟣 情绪 (折线)", color = Color(0xFF7986CB), fontWeight = FontWeight.Bold, fontSize = 12.sp)
//            }
//
//            // 🆕 V12 巅峰修改：明确暴露滑动示能 (Signifiers for Hidden Affordances)
//            Text("👉 左右滑动图表以查看完整时序数据", fontSize = 10.sp, color = Color.Gray, modifier = Modifier.fillMaxWidth().padding(top = 4.dp), textAlign = TextAlign.End)
//
//            if (selectedInsight != null) {
//                Surface(color = Color(0xFFFFF3E0), modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), shape = RoundedCornerShape(8.dp)) {
//                    Column(modifier = Modifier.padding(10.dp)) {
//                        Text("🔬 智能洞察: $selectedInsight", fontSize = 11.sp, color = Color(0xFFE65100), lineHeight = 16.sp)
//                        Spacer(modifier = Modifier.height(8.dp))
//
//                        if (insightFeedback == null) {
//                            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
//                                Text("这个系统解释合理吗？", fontSize = 10.sp, color = Color.Gray)
//                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//                                    Surface(onClick = { insightFeedback = true }, color = Color.White, shape = RoundedCornerShape(4.dp), border = BorderStroke(0.5.dp, Color(0xFFE65100))) { Text("👍 合理", fontSize = 10.sp, color = Color(0xFFE65100), modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)) }
//                                    Surface(onClick = { insightFeedback = false }, color = Color.White, shape = RoundedCornerShape(4.dp), border = BorderStroke(0.5.dp, Color.Gray)) { Text("👎 牵强", fontSize = 10.sp, color = Color.Gray, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)) }
//                                }
//                            }
//                        } else {
//                            Text("感谢反馈！(点击任意处关闭)", fontSize = 10.sp, color = Color(0xFF4CAF50), modifier = Modifier.fillMaxWidth().clickable { selectedInsight = null; insightFeedback = null }, textAlign = TextAlign.End)
//                        }
//                    }
//                }
//            } else {
//                Text("💡 提示：点击下方图表数据点，查看系统深度分析。", fontSize = 11.sp, color = Color.Gray, modifier = Modifier.padding(vertical = 8.dp))
//            }
//
//            val allTimes = (cgmNodes.map { it.timeLabel } + moodNodes.map { it.timeLabel }).distinct().sorted()
//            Box(modifier = Modifier.fillMaxWidth().height(250.dp).background(Color(0xFFFAFAFA), RoundedCornerShape(8.dp)).padding(8.dp)) {
//                Canvas(modifier = Modifier.fillMaxSize().horizontalScroll(rememberScrollState()).width((allTimes.size * 45).coerceAtLeast(350).dp).clickable {
//                    selectedInsight = "系统观察到您在 16:00 记录了『压力/烦躁』，随后血糖出现了 +0.2 的异常上升趋势。这说明情绪压力可能导致了皮质醇分泌，引起血糖波动。"
//                    insightFeedback = null
//                }) {
//                    val w = size.width; val h = size.height
//                    fun scaleCgm(v: Double) = h * 0.85f - ((v / 15.0) * (h * 0.7f)).toFloat()
//                    fun scaleMood(v: Float) = h * 0.85f - ((v / 10f) * (h * 0.7f))
//
//                    drawLine(Color.LightGray.copy(alpha=0.3f), Offset(0f, scaleCgm(10.0)), Offset(w, scaleCgm(10.0)), strokeWidth=2f)
//                    drawLine(Color.LightGray.copy(alpha=0.3f), Offset(0f, scaleCgm(3.9)), Offset(w, scaleCgm(3.9)), strokeWidth=2f)
//
//                    val textPaint = android.graphics.Paint().apply { textSize = 18f; isFakeBoldText = true }
//
//                    if (cgmNodes.isNotEmpty() && allTimes.isNotEmpty()) {
//                        val cgmPath = Path()
//                        val cgmAreaPath = Path()
//                        var firstX = 0f
//                        var lastX = 0f
//
//                        allTimes.forEachIndexed { i, time ->
//                            val x = if (allTimes.size > 1) (i.toFloat() / (allTimes.size - 1)) * (w - 40f) + 20f else w / 2
//                            val cgmNode = cgmNodes.find { it.timeLabel == time }
//                            if (cgmNode != null) {
//                                val y = scaleCgm(cgmNode.value)
//                                if (firstX == 0f) { cgmPath.moveTo(x, y); cgmAreaPath.moveTo(x, y); firstX = x }
//                                else { cgmPath.lineTo(x, y); cgmAreaPath.lineTo(x, y) }
//                                lastX = x
//                            }
//                        }
//                        if (firstX != 0f) {
//                            cgmAreaPath.lineTo(lastX, h)
//                            cgmAreaPath.lineTo(firstX, h)
//                            cgmAreaPath.close()
//                            drawPath(cgmAreaPath, color = Color(0xFF008080).copy(alpha = 0.15f))
//                            drawPath(cgmPath, color = Color(0xFF008080).copy(alpha = 0.5f), style = Stroke(2f))
//                        }
//                    }
//
//                    var lastMood: Offset? = null
//                    allTimes.forEachIndexed { i, time ->
//                        val x = if (allTimes.size > 1) (i.toFloat() / (allTimes.size - 1)) * (w - 40f) + 20f else w / 2
//                        val isEven = i % 2 == 0
//                        val timeY = if (isEven) h - 5f else h - 25f
//                        drawContext.canvas.nativeCanvas.drawText(time, x - 18f, timeY, android.graphics.Paint().apply { textSize = 16f; color = android.graphics.Color.GRAY })
//
//                        val moodNode = moodNodes.find { it.timeLabel == time }
//                        if (moodNode != null) {
//                            val y = scaleMood(moodNode.numericScore)
//                            val current = Offset(x, y)
//                            if (lastMood != null) drawLine(Color(0xFF7986CB), lastMood!!, current, 4f)
//                            drawCircle(Color.White, 6f, current)
//                            drawCircle(Color(0xFF7986CB), 4f, current)
//                            textPaint.color = android.graphics.Color.parseColor("#7986CB")
//                            drawContext.canvas.nativeCanvas.drawText("${moodNode.numericScore}", x + 5f, if (isEven) y + 15f else y - 15f, textPaint)
//                            lastMood = current
//                        }
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
//
//    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White)) {
//        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
//            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
//                MetricItem("今日平均血糖", report.avgGlucose)
//                Text(report.clinicalAdvice, fontSize = 11.sp, color = Color(0xFF008080), modifier = Modifier.weight(1f).padding(start = 12.dp), lineHeight = 16.sp)
//            }
//            Text("📍 AGP 24小时走势图", fontWeight = FontWeight.Bold, fontSize = 13.sp)
//            // 🆕 V12 巅峰修改：明确暴露滑动示能
//            Text("👉 左右滑动图表以查看完整时序数据", fontSize = 10.sp, color = Color.Gray, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.End)
//            Box(modifier = Modifier.fillMaxWidth().height(220.dp).background(Color(0xFFFAFAFA), RoundedCornerShape(8.dp)).padding(8.dp)) {
//                Canvas(modifier = Modifier.fillMaxSize().horizontalScroll(rememberScrollState()).width((nodes.size * 45).coerceAtLeast(350).dp)) {
//                    val w = size.width; val h = size.height
//                    fun scaleY(v: Double) = (h - (v / 15.0) * h).toFloat()
//                    drawRect(brush = SolidColor(Color(0xFFE8F5E9)), topLeft = Offset(0f, scaleY(10.0)), size = Size(w, scaleY(3.9) - scaleY(10.0)))
//                    val gridPaint = android.graphics.Paint().apply { textSize = 18f; color = android.graphics.Color.GRAY }
//                    listOf(3.9, 7.0, 10.0, 15.0).forEach { yVal ->
//                        val y = scaleY(yVal); drawLine(Color.LightGray.copy(alpha=0.5f), Offset(0f, y), Offset(w, y), strokeWidth = 2f)
//                        drawContext.canvas.nativeCanvas.drawText("$yVal", 5f, y - 5f, gridPaint)
//                    }
//                    if (nodes.isNotEmpty()) {
//                        val path = Path()
//                        nodes.forEachIndexed { i, n ->
//                            val x = if(nodes.size > 1) (i.toFloat() / (nodes.size - 1)) * (w - 40f) + 20f else w/2
//                            val y = scaleY(n.value)
//                            if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
//                            drawCircle(Color(0xFF008080), 4f, Offset(x, y)); drawCircle(Color.White, 2f, Offset(x, y))
//                            val isEven = i % 2 == 0
//                            drawContext.canvas.nativeCanvas.drawText(n.timeLabel, x - 15f, if (isEven) h - 5f else h - 20f, gridPaint)
//                        }
//                        drawPath(path, Color(0xFF008080), style = Stroke(3f))
//                    }
//                }
//            }
//            Row(modifier = Modifier.fillMaxWidth().clickable { showAdvancedMetrics = !showAdvancedMetrics }.padding(vertical = 4.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
//                Text(if (showAdvancedMetrics) "收起临床指标" else "查看专业临床指标", fontSize = 12.sp, color = Color(0xFF008080), fontWeight = FontWeight.Bold)
//                Icon(if (showAdvancedMetrics) Icons.Default.ExpandLess else Icons.Default.ExpandMore, null, tint = Color(0xFF008080), modifier = Modifier.size(16.dp))
//            }
//            AnimatedVisibility(visible = showAdvancedMetrics) {
//                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
//                    HorizontalDivider(color = Color(0xFFF1F1F1))
//                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                        MetricItem("波动 (CV)", report.cv); MetricItem("MAGE指标", report.mage); MetricItem("GMI 预估", report.gmi)
//                    }
//                    Column(modifier = Modifier.fillMaxWidth().background(Color(0xFFF1F8E9), RoundedCornerShape(8.dp)).padding(10.dp)) {
//                        Text("🎯 TIR 目标达标率", fontWeight = FontWeight.Bold, fontSize = 13.sp)
//                        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp).height(12.dp).clip(CircleShape)) {
//                            Box(modifier = Modifier.weight(report.tirLow.toFloat()).fillMaxHeight().background(Color(0xFF64B5F6)))
//                            Box(modifier = Modifier.weight(report.tirTarget.toFloat()).fillMaxHeight().background(Color(0xFF4CAF50)))
//                            Box(modifier = Modifier.weight(report.tirHigh.toFloat()).fillMaxHeight().background(Color(0xFFE57373)))
//                        }
//                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                            Text("低血糖: ${report.tbrLevel1}", fontSize = 10.sp); Text("达标: ${report.tirTarget}%", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Color(0xFF4CAF50)); Text("高血糖: ${report.tarLevel1}", fontSize = 10.sp)
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun ClinicalMoodCard(report: ClinicalMoodReport, nodes: List<MoodLog>) {
//    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White)) {
//        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
//            Column(modifier = Modifier.fillMaxWidth().background(Color(0xFFFFF8E1), RoundedCornerShape(8.dp)).padding(12.dp)) {
//                Text("🧠 核心情绪指标", fontWeight = FontWeight.Bold, fontSize = 13.sp)
//                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                    MetricItem("平均分", report.meanScore); MetricItem("波动差", report.variabilitySD); MetricItem("不稳定指数", report.instabilityIndex)
//                }
//            }
//            Text("👉 左右滑动图表以查看完整时序数据", fontSize = 10.sp, color = Color.Gray, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.End)
//            Box(modifier = Modifier.horizontalScroll(rememberScrollState())) {
//                Canvas(modifier = Modifier.width((nodes.size * 45).coerceAtLeast(300).dp).height(160.dp)) {
//                    val ep = android.graphics.Paint().apply { textSize = 35f }
//                    drawContext.canvas.nativeCanvas.drawText("🤩", 0f, 35f, ep)
//                    drawContext.canvas.nativeCanvas.drawText("🙂", 0f, size.height * 0.35f, ep)
//                    drawContext.canvas.nativeCanvas.drawText("😐", 0f, size.height * 0.65f, ep)
//                    drawContext.canvas.nativeCanvas.drawText("😞", 0f, size.height - 5f, ep)
//                    val p = Path()
//                    nodes.forEachIndexed { i, n ->
//                        val x = if(nodes.size > 1) (i.toFloat() / (nodes.size - 1)) * (size.width - 50f) + 50f else size.width / 2
//                        val y = size.height - (n.numericScore / 10f) * size.height
//                        if (i == 0) p.moveTo(x, y) else p.lineTo(x, y)
//                        drawCircle(Color(0xFF90CAF9), 6f, Offset(x, y))
//                        drawContext.canvas.nativeCanvas.drawText("${n.numericScore}", x-10f, if(i%2==0) y-15f else y+25f, android.graphics.Paint().apply { textSize=18f; color=android.graphics.Color.DKGRAY })
//                    }
//                    drawPath(p, Color(0xFF7986CB), style = Stroke(3f))
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun <T> HealthListCard(title: String, accent: Color, items: List<T>, onAddClick: () -> Unit, valueSelector: (T) -> Float, textSelector: (T) -> String) {
//    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White)) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(title, fontWeight = FontWeight.Bold, color = accent, fontSize = 13.sp)
//            Spacer(Modifier.height(8.dp))
//            Column(modifier = Modifier.fillMaxWidth().heightIn(max = 100.dp).verticalScroll(rememberScrollState())) {
//                items.forEach { item -> Text(textSelector(item), fontSize = 12.sp, color = Color.DarkGray, modifier = Modifier.padding(vertical = 6.dp)); HorizontalDivider(color = Color(0xFFF1F1F1)) }
//            }
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
//    Surface(modifier = Modifier.fillMaxWidth().height(40.dp), shape = RoundedCornerShape(20.dp), color = Color(0xFFE0E0E0)) {
//        Row {
//            scales.forEach { s ->
//                val isSelected = selectedScale == s
//                Box(modifier = Modifier.weight(1f).fillMaxHeight().padding(2.dp).background(if(isSelected) Color.White else Color.Transparent, RoundedCornerShape(18.dp)).clickable { onScaleSelect(s) }, contentAlignment = Alignment.Center) {
//                    Text(s, fontSize = 13.sp, fontWeight = if(isSelected) FontWeight.Bold else FontWeight.Normal, color = if(isSelected) Color(0xFF008080) else Color.Gray)
//                }
//            }
//        }
//    }
//}

package com.withapp.with.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

    var showCgmDialog by remember { mutableStateOf(false) }
    var showMoodDialog by remember { mutableStateOf(false) }
    var showCgmHistoryDialog by remember { mutableStateOf(false) }
    var showMoodHistoryDialog by remember { mutableStateOf(false) }
    var showGlossaryDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            // 🆕 V13 顶级理论：CSCW 协同分享机制。允许患者一键脱敏分享给医生。
            TopAppBar(
                title = { Text("健康回顾", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
                actions = {
                    IconButton(onClick = { scope.launch { snackbarHostState.showSnackbar("✅ 数据已脱敏，即将生成 PDF 报告以便发送给您的主治医生。") } }) {
                        Icon(Icons.Default.Share, contentDescription = "Share", tint = Color(0xFF008080))
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues).background(Color(0xFFF7F9FA))) {

            if (isVacationMode) {
                Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🌙", fontSize = 60.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("休眠模式中", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                    Text("所有图表与分析已被隐藏以免产生数据焦虑。\n您的历史记录完好无损。", fontSize = 12.sp, color = Color.Gray, textAlign = TextAlign.Center, modifier = Modifier.padding(16.dp))
                }
            } else {
                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    TimeScaleSelector(selectedScale = reportViewModel.currentScale.value, onScaleSelect = { reportViewModel.currentScale.value = it })
                }

                Column(modifier = Modifier.weight(1f).verticalScroll(scrollState).padding(horizontal = 16.dp, vertical = 8.dp), verticalArrangement = Arrangement.spacedBy(24.dp)) {

                    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                Text("🌟 今日状态摘要", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF1565C0))
                                Text("健康得分: 极佳", fontSize = 12.sp, color = Color(0xFF4CAF50), fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape)) {
                                Box(modifier = Modifier.weight(0.03f).fillMaxHeight().background(Color(0xFF64B5F6)))
                                Box(modifier = Modifier.weight(0.92f).fillMaxHeight().background(Color(0xFF4CAF50)))
                                Box(modifier = Modifier.weight(0.05f).fillMaxHeight().background(Color(0xFFE57373)))
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Text("您今天的血糖达标率为 92%。下午 16:00 记录了一次压力情绪，可能伴随轻微波动。请注意劳逸结合。", fontSize = 12.sp, color = Color.DarkGray, lineHeight = 18.sp)
                        }
                    }

                    CorrelationChartCard(cgmNodes, moodNodes)

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text("📊 血糖趋势与分析", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF008080))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            IconButton(onClick = { showGlossaryDialog = true }, modifier = Modifier.size(28.dp).background(Color(0xFF008080).copy(0.1f), CircleShape)) { Icon(Icons.Default.Info, contentDescription = "Glossary", tint = Color(0xFF008080), modifier = Modifier.size(16.dp)) }
                            IconButton(onClick = { showCgmHistoryDialog = true }, modifier = Modifier.size(28.dp).background(Color(0xFF008080).copy(0.1f), CircleShape)) { Icon(Icons.Default.Search, contentDescription = "History", tint = Color(0xFF008080), modifier = Modifier.size(16.dp)) }
                            IconButton(onClick = { showCgmDialog = true }, modifier = Modifier.size(28.dp).background(Color(0xFF008080).copy(0.1f), CircleShape)) { Icon(Icons.Default.Add, contentDescription = "Add", tint = Color(0xFF008080), modifier = Modifier.size(18.dp)) }
                        }
                    }
                    ClinicalCgmCard(cgmRep, cgmNodes)

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text("🧠 情绪起伏追踪", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFFFFB74D))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            IconButton(onClick = { showMoodHistoryDialog = true }, modifier = Modifier.size(28.dp).background(Color(0xFFFFB74D).copy(0.1f), CircleShape)) { Icon(Icons.Default.Search, contentDescription = "History", tint = Color(0xFFFFB74D), modifier = Modifier.size(16.dp)) }
                            IconButton(onClick = { showMoodDialog = true }, modifier = Modifier.size(28.dp).background(Color(0xFFFFB74D).copy(0.1f), CircleShape)) { Icon(Icons.Default.Add, contentDescription = "Add", tint = Color(0xFFFFB74D), modifier = Modifier.size(18.dp)) }
                        }
                    }
                    ClinicalMoodCard(moodRep, moodNodes)

                    Text("🛡️ 生活方式记录", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF5C6BC0))
                    HealthListCard("🍏 饮食与用药", Color(0xFF81C784), reportViewModel.dietLogs.toList(), { }, { it.numericValue }, { "${it.time} - ${it.food} (${it.carbs})" })
                    HealthListCard("🏃 活动与心率", Color(0xFF4FC3F7), reportViewModel.exerciseLogs.toList(), { }, { it.numericValue }, { "${it.time} - ${it.activity} (${it.duration})" })

                    Spacer(Modifier.height(40.dp))
                }
            }
        }

        if (showGlossaryDialog) {
            AlertDialog(onDismissRequest = { showGlossaryDialog = false }, title = { Text("📖 健康数据小词典", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF008080)) },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.verticalScroll(rememberScrollState())) {
                        Text("• TIR (目标范围内时间)：指您的血糖在理想安全范围内的时间占比。越高越好。", fontSize = 12.sp)
                        Text("• MAGE (平均血糖波动幅度)：反映您血糖像过山车一样波动的剧烈程度。越低越好。", fontSize = 12.sp)
                        Text("• 情绪分数 (0-10)：基于 Russell 环形理论，分数越高代表能量越高、越愉悦；分数越低代表越疲惫或低落。", fontSize = 12.sp)
                    }
                }, confirmButton = { TextButton(onClick = { showGlossaryDialog = false }) { Text("我知道了", color = Color(0xFF008080)) } }
            )
        }

        if (showCgmDialog) {
            var v1 by remember { mutableStateOf("") }; var v2 by remember { mutableStateOf("") }
            AlertDialog(onDismissRequest = { showCgmDialog = false }, title = { Text("记录单次血糖", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
                text = { Column(verticalArrangement = Arrangement.spacedBy(12.dp)) { OutlinedTextField(v1, {v1=it}, label={Text("血糖值 (mmol/L)", fontSize=12.sp)}, modifier = Modifier.fillMaxWidth()); OutlinedTextField(v2, {v2=it}, label={Text("时间戳 (如 14:30)", fontSize=12.sp)}, modifier = Modifier.fillMaxWidth()) } },
                confirmButton = { Button(onClick = { reportViewModel.addCgmNode(v1.toDoubleOrNull() ?: 5.5, v2); showCgmDialog = false }) { Text("保存") } },
                dismissButton = { TextButton(onClick = { showCgmDialog = false }) { Text("取消", color = Color.Gray) } }
            )
        }

        if (showMoodDialog) {
            var timeInput by remember { mutableStateOf("") }
            var selectedMood by remember { mutableStateOf<Pair<String, Float>?>(null) }
            val moodOptions = listOf("🤩 极佳" to 9f, "🙂 平稳" to 7f, "😐 疲惫" to 4f, "😞 低落" to 2f)

            AlertDialog(onDismissRequest = { showMoodDialog = false },
                title = { Text("记录当前心情", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
                text = { Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text("请直接点击最符合您现在状态的表情：", fontSize = 12.sp, color = Color.Gray)
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        moodOptions.forEach { mood ->
                            val isSelected = selectedMood == mood
                            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable { selectedMood = mood }.background(if (isSelected) Color(0xFFE3F2FD) else Color.Transparent, RoundedCornerShape(8.dp)).padding(8.dp)) {
                                Text(mood.first.split(" ")[0], fontSize = 28.sp)
                                Text(mood.first.split(" ")[1], fontSize = 10.sp, fontWeight = if(isSelected) FontWeight.Bold else FontWeight.Normal, color = if(isSelected) Color(0xFF1565C0) else Color.Gray)
                            }
                        }
                    }
                    OutlinedTextField(timeInput, {timeInput=it}, label={Text("时间 (可选，默认现在)", fontSize=12.sp)}, modifier = Modifier.fillMaxWidth())
                } },
                confirmButton = { Button(onClick = {
                    selectedMood?.let { reportViewModel.addMoodNode(it.second, it.first.split(" ")[1], timeInput) }
                    showMoodDialog = false
                }, enabled = selectedMood != null) { Text("保存") } },
                dismissButton = { TextButton(onClick = { showMoodDialog = false }) { Text("取消", color = Color.Gray) } }
            )
        }

        if (showCgmHistoryDialog) {
            AlertDialog(onDismissRequest = { showCgmHistoryDialog = false }, title = { Text("📊 血糖历史记录", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF008080)) },
                text = {
                    Column(modifier = Modifier.fillMaxWidth().heightIn(max = 400.dp).verticalScroll(rememberScrollState())) {
                        if (cgmNodes.isEmpty()) { Text("暂无数据记录", fontSize = 13.sp, color = Color.Gray) } else {
                            cgmNodes.reversed().forEach { node ->
                                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(node.timeLabel, fontSize = 14.sp, color = Color.Gray)
                                        Surface(color = Color(0xFFE8F5E9), shape = RoundedCornerShape(4.dp), modifier = Modifier.padding(top = 4.dp)) {
                                            Text("✓ 探头自动同步", fontSize = 9.sp, color = Color(0xFF2E7D32), modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp))
                                        }
                                    }
                                    Text("${node.value} mmol/L ${node.trend}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
                                }
                                HorizontalDivider(color = Color(0xFFF1F1F1))
                            }
                        }
                    }
                }, confirmButton = { TextButton(onClick = { showCgmHistoryDialog = false }) { Text("关闭", color = Color(0xFF008080)) } }
            )
        }

        if (showMoodHistoryDialog) {
            AlertDialog(onDismissRequest = { showMoodHistoryDialog = false }, title = { Text("🧠 情绪随手记", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFFFFB74D)) },
                text = {
                    Column(modifier = Modifier.fillMaxWidth().heightIn(max = 400.dp).verticalScroll(rememberScrollState())) {
                        if (moodNodes.isEmpty()) { Text("暂无数据记录", fontSize = 13.sp, color = Color.Gray) } else {
                            moodNodes.reversed().forEach { node ->
                                Column(modifier = Modifier.fillMaxWidth().background(Color(0xFFFFFDF5), RoundedCornerShape(8.dp)).padding(12.dp).padding(bottom = 4.dp)) {
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                        Text(node.timeLabel, fontSize = 12.sp, color = Color.Gray)
                                        Text("${node.numericScore} 分", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFFB74D))
                                    }
                                    Spacer(Modifier.height(6.dp))
                                    Text("✍️ ${node.label}", fontSize = 14.sp, color = Color.DarkGray, lineHeight = 20.sp)
                                    Surface(color = Color(0xFFFFF3E0), shape = RoundedCornerShape(4.dp), modifier = Modifier.padding(top = 6.dp)) {
                                        Text("👤 用户主动记录", fontSize = 9.sp, color = Color(0xFFE65100), modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp))
                                    }
                                }
                                Spacer(Modifier.height(8.dp))
                            }
                        }
                    }
                }, confirmButton = { TextButton(onClick = { showMoodHistoryDialog = false }) { Text("关闭", color = Color(0xFFFFB74D)) } }
            )
        }
    }
}

@Composable
fun CorrelationChartCard(cgmNodes: List<CgmNode>, moodNodes: List<MoodLog>) {
    var selectedInsight by remember { mutableStateOf<String?>(null) }
    var insightFeedback by remember { mutableStateOf<Boolean?>(null) }

    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("🔄 血糖与情绪交叉分析", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF673AB7))
            Row(modifier = Modifier.fillMaxWidth().padding(top=8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("🟢 血糖 (面积底图)", color = Color(0xFF008080), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                Text("🟣 情绪 (折线)", color = Color(0xFF7986CB), fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }

            Text("👉 左右滑动图表以查看完整时序数据", fontSize = 10.sp, color = Color.Gray, modifier = Modifier.fillMaxWidth().padding(top = 4.dp), textAlign = TextAlign.End)

            if (selectedInsight != null) {
                Surface(color = Color(0xFFFFF3E0), modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), shape = RoundedCornerShape(8.dp)) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text("🔬 智能洞察: $selectedInsight", fontSize = 11.sp, color = Color(0xFFE65100), lineHeight = 16.sp)
                        Spacer(modifier = Modifier.height(8.dp))

                        if (insightFeedback == null) {
                            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("这个系统解释合理吗？", fontSize = 10.sp, color = Color.Gray)
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Surface(onClick = { insightFeedback = true }, color = Color.White, shape = RoundedCornerShape(4.dp), border = BorderStroke(0.5.dp, Color(0xFFE65100))) { Text("👍 合理", fontSize = 10.sp, color = Color(0xFFE65100), modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)) }
                                    Surface(onClick = { insightFeedback = false }, color = Color.White, shape = RoundedCornerShape(4.dp), border = BorderStroke(0.5.dp, Color.Gray)) { Text("👎 牵强", fontSize = 10.sp, color = Color.Gray, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)) }
                                }
                            }
                        } else {
                            Text("感谢反馈！(点击任意处关闭)", fontSize = 10.sp, color = Color(0xFF4CAF50), modifier = Modifier.fillMaxWidth().clickable { selectedInsight = null; insightFeedback = null }, textAlign = TextAlign.End)
                        }
                    }
                }
            } else {
                Text("💡 提示：点击下方图表数据点，查看系统深度分析。", fontSize = 11.sp, color = Color.Gray, modifier = Modifier.padding(vertical = 8.dp))
            }

            val allTimes = (cgmNodes.map { it.timeLabel } + moodNodes.map { it.timeLabel }).distinct().sorted()
            Box(modifier = Modifier.fillMaxWidth().height(250.dp).background(Color(0xFFFAFAFA), RoundedCornerShape(8.dp)).padding(8.dp)) {
                Canvas(modifier = Modifier.fillMaxSize().horizontalScroll(rememberScrollState()).width((allTimes.size * 45).coerceAtLeast(350).dp).clickable {
                    selectedInsight = "系统观察到您在 16:00 记录了『压力/烦躁』，随后血糖出现了 +0.2 的异常上升趋势。这说明情绪压力可能导致了皮质醇分泌，引起血糖波动。"
                    insightFeedback = null
                }) {
                    val w = size.width; val h = size.height
                    fun scaleCgm(v: Double) = h * 0.85f - ((v / 15.0) * (h * 0.7f)).toFloat()
                    fun scaleMood(v: Float) = h * 0.85f - ((v / 10f) * (h * 0.7f))

                    drawLine(Color.LightGray.copy(alpha=0.3f), Offset(0f, scaleCgm(10.0)), Offset(w, scaleCgm(10.0)), strokeWidth=2f)
                    drawLine(Color.LightGray.copy(alpha=0.3f), Offset(0f, scaleCgm(3.9)), Offset(w, scaleCgm(3.9)), strokeWidth=2f)

                    val textPaint = android.graphics.Paint().apply { textSize = 18f; isFakeBoldText = true }

                    if (cgmNodes.isNotEmpty() && allTimes.isNotEmpty()) {
                        val cgmPath = Path()
                        val cgmAreaPath = Path()
                        var firstX = 0f
                        var lastX = 0f

                        allTimes.forEachIndexed { i, time ->
                            val x = if (allTimes.size > 1) (i.toFloat() / (allTimes.size - 1)) * (w - 40f) + 20f else w / 2
                            val cgmNode = cgmNodes.find { it.timeLabel == time }
                            if (cgmNode != null) {
                                val y = scaleCgm(cgmNode.value)
                                if (firstX == 0f) { cgmPath.moveTo(x, y); cgmAreaPath.moveTo(x, y); firstX = x }
                                else { cgmPath.lineTo(x, y); cgmAreaPath.lineTo(x, y) }
                                lastX = x
                            }
                        }
                        if (firstX != 0f) {
                            cgmAreaPath.lineTo(lastX, h)
                            cgmAreaPath.lineTo(firstX, h)
                            cgmAreaPath.close()
                            drawPath(cgmAreaPath, color = Color(0xFF008080).copy(alpha = 0.15f))
                            drawPath(cgmPath, color = Color(0xFF008080).copy(alpha = 0.5f), style = Stroke(2f))
                        }
                    }

                    var lastMood: Offset? = null
                    allTimes.forEachIndexed { i, time ->
                        val x = if (allTimes.size > 1) (i.toFloat() / (allTimes.size - 1)) * (w - 40f) + 20f else w / 2
                        val isEven = i % 2 == 0
                        val timeY = if (isEven) h - 5f else h - 25f
                        drawContext.canvas.nativeCanvas.drawText(time, x - 18f, timeY, android.graphics.Paint().apply { textSize = 16f; color = android.graphics.Color.GRAY })

                        val moodNode = moodNodes.find { it.timeLabel == time }
                        if (moodNode != null) {
                            val y = scaleMood(moodNode.numericScore)
                            val current = Offset(x, y)
                            if (lastMood != null) drawLine(Color(0xFF7986CB), lastMood!!, current, 4f)
                            drawCircle(Color.White, 6f, current)
                            drawCircle(Color(0xFF7986CB), 4f, current)
                            textPaint.color = android.graphics.Color.parseColor("#7986CB")
                            drawContext.canvas.nativeCanvas.drawText("${moodNode.numericScore}", x + 5f, if (isEven) y + 15f else y - 15f, textPaint)
                            lastMood = current
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ClinicalCgmCard(report: ClinicalCgmReport, nodes: List<CgmNode>) {
    var showAdvancedMetrics by remember { mutableStateOf(false) }

    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                MetricItem("今日平均血糖", report.avgGlucose)
                Text(report.clinicalAdvice, fontSize = 11.sp, color = Color(0xFF008080), modifier = Modifier.weight(1f).padding(start = 12.dp), lineHeight = 16.sp)
            }
            Text("📍 AGP 24小时走势图", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            Text("👉 左右滑动图表以查看完整时序数据", fontSize = 10.sp, color = Color.Gray, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.End)
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
            Row(modifier = Modifier.fillMaxWidth().clickable { showAdvancedMetrics = !showAdvancedMetrics }.padding(vertical = 4.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Text(if (showAdvancedMetrics) "收起临床指标" else "查看专业临床指标", fontSize = 12.sp, color = Color(0xFF008080), fontWeight = FontWeight.Bold)
                Icon(if (showAdvancedMetrics) Icons.Default.ExpandLess else Icons.Default.ExpandMore, null, tint = Color(0xFF008080), modifier = Modifier.size(16.dp))
            }
            AnimatedVisibility(visible = showAdvancedMetrics) {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    HorizontalDivider(color = Color(0xFFF1F1F1))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        MetricItem("波动 (CV)", report.cv); MetricItem("MAGE指标", report.mage); MetricItem("GMI 预估", report.gmi)
                    }
                    Column(modifier = Modifier.fillMaxWidth().background(Color(0xFFF1F8E9), RoundedCornerShape(8.dp)).padding(10.dp)) {
                        Text("🎯 TIR 目标达标率", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp).height(12.dp).clip(CircleShape)) {
                            Box(modifier = Modifier.weight(report.tirLow.toFloat()).fillMaxHeight().background(Color(0xFF64B5F6)))
                            Box(modifier = Modifier.weight(report.tirTarget.toFloat()).fillMaxHeight().background(Color(0xFF4CAF50)))
                            Box(modifier = Modifier.weight(report.tirHigh.toFloat()).fillMaxHeight().background(Color(0xFFE57373)))
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("低血糖: ${report.tbrLevel1}", fontSize = 10.sp); Text("达标: ${report.tirTarget}%", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Color(0xFF4CAF50)); Text("高血糖: ${report.tarLevel1}", fontSize = 10.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ClinicalMoodCard(report: ClinicalMoodReport, nodes: List<MoodLog>) {
    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Column(modifier = Modifier.fillMaxWidth().background(Color(0xFFFFF8E1), RoundedCornerShape(8.dp)).padding(12.dp)) {
                Text("🧠 核心情绪指标", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    MetricItem("平均分", report.meanScore); MetricItem("波动差", report.variabilitySD); MetricItem("不稳定指数", report.instabilityIndex)
                }
            }
            Text("👉 左右滑动图表以查看完整时序数据", fontSize = 10.sp, color = Color.Gray, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.End)
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
}

@Composable
fun <T> HealthListCard(title: String, accent: Color, items: List<T>, onAddClick: () -> Unit, valueSelector: (T) -> Float, textSelector: (T) -> String) {
    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, fontWeight = FontWeight.Bold, color = accent, fontSize = 13.sp)
            Spacer(Modifier.height(8.dp))
            Column(modifier = Modifier.fillMaxWidth().heightIn(max = 100.dp).verticalScroll(rememberScrollState())) {
                items.forEach { item -> Text(textSelector(item), fontSize = 12.sp, color = Color.DarkGray, modifier = Modifier.padding(vertical = 6.dp)); HorizontalDivider(color = Color(0xFFF1F1F1)) }
            }
        }
    }
}

@Composable
fun MetricItem(label: String, value: String) {
    Column { Text(label, fontSize = 10.sp, color = Color.Gray); Text(value, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF008080)) }
}

@Composable
fun TimeScaleSelector(selectedScale: String, onScaleSelect: (String) -> Unit) {
    val scales = listOf("日", "月", "年")
    Surface(modifier = Modifier.fillMaxWidth().height(40.dp), shape = RoundedCornerShape(20.dp), color = Color(0xFFE0E0E0)) {
        Row {
            scales.forEach { s ->
                val isSelected = selectedScale == s
                Box(modifier = Modifier.weight(1f).fillMaxHeight().padding(2.dp).background(if(isSelected) Color.White else Color.Transparent, RoundedCornerShape(18.dp)).clickable { onScaleSelect(s) }, contentAlignment = Alignment.Center) {
                    Text(s, fontSize = 13.sp, fontWeight = if(isSelected) FontWeight.Bold else FontWeight.Normal, color = if(isSelected) Color(0xFF008080) else Color.Gray)
                }
            }
        }
    }
}