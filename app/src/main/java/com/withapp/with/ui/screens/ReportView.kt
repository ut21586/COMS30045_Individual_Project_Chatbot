//
//
//package com.withapp.with.ui.screens
//
//import androidx.compose.ui.graphics.nativeCanvas
//import androidx.compose.foundation.Canvas
//import androidx.compose.foundation.background
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
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.withapp.with.viewmodels.*
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//// 🆕 V6修复：去除了 onBack 回调参数，因为这页在底部导航栏，不需要全局返回，减少认知负担
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
//    Scaffold(
//        topBar = {
//            // 🆕 V6修复：移除了多余的 navigationIcon
//            TopAppBar(title = { Text("我的健康与情绪回顾", fontWeight = FontWeight.Bold, fontSize = 16.sp) })
//        }
//    ) { paddingValues ->
//        // 🆕 V6修复：将 TimeScaleSelector 抽离出 ScrollState，实现“吸顶(Sticky Header)”效果
//        Column(modifier = Modifier.fillMaxSize().padding(paddingValues).background(Color(0xFFF7F9FA))) {
//
//            // 吸顶的时间切换器
//            Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
//                TimeScaleSelector(selectedScale = reportViewModel.currentScale.value, onScaleSelect = { reportViewModel.currentScale.value = it })
//            }
//
//            // 下方的滚动内容区
//            Column(modifier = Modifier.weight(1f).verticalScroll(scrollState).padding(horizontal = 16.dp, vertical = 8.dp), verticalArrangement = Arrangement.spacedBy(24.dp)) {
//                Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))) {
//                    Column(modifier = Modifier.padding(16.dp)) {
//                        Text("🌟 今日状态摘要", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF1565C0))
//                        Spacer(modifier = Modifier.height(8.dp))
//                        Text("您今天的血糖 TIR 达标率为 92%，整体控制极佳。下午 16:00 记录了一次压力情绪，可能伴随着轻微的血糖波动。请注意劳逸结合。", fontSize = 12.sp, color = Color.DarkGray, lineHeight = 18.sp)
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
//        // --- 字典弹窗 ---
//        if (showGlossaryDialog) {
//            AlertDialog(
//                onDismissRequest = { showGlossaryDialog = false },
//                title = { Text("📖 健康数据小词典", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF008080)) },
//                text = {
//                    Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.verticalScroll(rememberScrollState())) {
//                        Text("• TIR (目标范围内时间)：指您的血糖在理想安全范围内的时间占比。越高越好。", fontSize = 12.sp)
//                        Text("• MAGE (平均血糖波动幅度)：反映您血糖像过山车一样波动的剧烈程度。越低越好。", fontSize = 12.sp)
//                        Text("• 情绪分数 (0-10)：基于 Russell 环形理论，分数越高代表能量越高、越愉悦；分数越低代表越疲惫或低落。", fontSize = 12.sp)
//                    }
//                },
//                confirmButton = { TextButton(onClick = { showGlossaryDialog = false }) { Text("我知道了", color = Color(0xFF008080)) } }
//            )
//        }
//
//        // 🆕 V6修复：给所有的操作弹窗加上了“取消(Cancel)”按钮，赋予用户自由退出的权力
//        if (showCgmDialog) {
//            var v1 by remember { mutableStateOf("") }; var v2 by remember { mutableStateOf("") }
//            AlertDialog(onDismissRequest = { showCgmDialog = false },
//                title = { Text("记录单次血糖", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
//                text = { Column(verticalArrangement = Arrangement.spacedBy(12.dp)) { OutlinedTextField(v1, {v1=it}, label={Text("血糖值 (mmol/L)", fontSize=12.sp)}, modifier = Modifier.fillMaxWidth()); OutlinedTextField(v2, {v2=it}, label={Text("时间戳 (如 14:30)", fontSize=12.sp)}, modifier = Modifier.fillMaxWidth()) } },
//                confirmButton = { Button(onClick = { reportViewModel.addCgmNode(v1.toDoubleOrNull() ?: 5.5, v2); showCgmDialog = false }) { Text("保存") } },
//                dismissButton = { TextButton(onClick = { showCgmDialog = false }) { Text("取消", color = Color.Gray) } } // 添加紧急出口
//            )
//        }
//
//        if (showMoodDialog) {
//            var v1 by remember { mutableStateOf("") }; var v2 by remember { mutableStateOf("") }; var v3 by remember { mutableStateOf("") }
//            AlertDialog(onDismissRequest = { showMoodDialog = false },
//                title = { Text("记录当前情绪", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
//                text = { Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
//                    OutlinedTextField(v1, {v1=it}, label={Text("评分 (0-10)", fontSize=12.sp)}, modifier = Modifier.fillMaxWidth())
//                    Text("💡 评分参考：0-3(疲惫/低落)，4-6(平静/一般)，7-10(愉悦/活力)", fontSize = 10.sp, color = Color.Gray)
//                    OutlinedTextField(v2, {v2=it}, label={Text("标签 (如 开心/压力)", fontSize=12.sp)}, modifier = Modifier.fillMaxWidth())
//                    OutlinedTextField(v3, {v3=it}, label={Text("时间", fontSize=12.sp)}, modifier = Modifier.fillMaxWidth())
//                } },
//                confirmButton = { Button(onClick = { reportViewModel.addMoodNode(v1.toFloatOrNull() ?: 7.0f, v2.ifBlank { "平稳" }, v3); showMoodDialog = false }) { Text("保存") } },
//                dismissButton = { TextButton(onClick = { showMoodDialog = false }) { Text("取消", color = Color.Gray) } } // 添加紧急出口
//            )
//        }
//
//        if (showCgmHistoryDialog) {
//            AlertDialog(onDismissRequest = { showCgmHistoryDialog = false }, title = { Text("📊 血糖历史记录", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF008080)) },
//                text = {
//                    Column(modifier = Modifier.fillMaxWidth().heightIn(max = 400.dp).verticalScroll(rememberScrollState())) {
//                        if (cgmNodes.isEmpty()) { Text("暂无数据记录", fontSize = 13.sp, color = Color.Gray) } else {
//                            cgmNodes.reversed().forEach { node ->
//                                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp), horizontalArrangement = Arrangement.SpaceBetween) {
//                                    Text(node.timeLabel, fontSize = 14.sp, color = Color.Gray, modifier = Modifier.weight(1f))
//                                    Text("${node.value} mmol/L ${node.trend}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
//                                }
//                                HorizontalDivider(color = Color(0xFFF1F1F1))
//                            }
//                        }
//                    }
//                },
//                confirmButton = { TextButton(onClick = { showCgmHistoryDialog = false }) { Text("关闭", color = Color(0xFF008080)) } }
//            )
//        }
//
//        if (showMoodHistoryDialog) {
//            AlertDialog(onDismissRequest = { showMoodHistoryDialog = false }, title = { Text("🧠 情绪历史记录", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFFFFB74D)) },
//                text = {
//                    Column(modifier = Modifier.fillMaxWidth().heightIn(max = 400.dp).verticalScroll(rememberScrollState())) {
//                        if (moodNodes.isEmpty()) { Text("暂无数据记录", fontSize = 13.sp, color = Color.Gray) } else {
//                            moodNodes.reversed().forEach { node ->
//                                Column(modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp)) {
//                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                                        Text(node.timeLabel, fontSize = 14.sp, color = Color.Gray)
//                                        Text("${node.numericScore} 分 | ${node.label}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
//                                    }
//                                    Text("来源: ${node.value}", fontSize = 11.sp, color = Color.LightGray, modifier = Modifier.padding(top = 4.dp))
//                                }
//                                HorizontalDivider(color = Color(0xFFF1F1F1))
//                            }
//                        }
//                    }
//                },
//                confirmButton = { TextButton(onClick = { showMoodHistoryDialog = false }) { Text("关闭", color = Color(0xFFFFB74D)) } }
//            )
//        }
//    }
//}
//
//@Composable
//fun CorrelationChartCard(cgmNodes: List<CgmNode>, moodNodes: List<MoodLog>) {
//    var selectedInsight by remember { mutableStateOf<String?>(null) }
//
//    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White)) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text("🔄 血糖与情绪交叉分析", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF673AB7))
//            Row(modifier = Modifier.fillMaxWidth().padding(top=8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
//                Text("🟢 血糖 (mmol/L)", color = Color(0xFF008080), fontWeight = FontWeight.Bold, fontSize = 12.sp)
//                Text("🟣 情绪 (0-10)", color = Color(0xFF7986CB), fontWeight = FontWeight.Bold, fontSize = 12.sp)
//            }
//
//            if (selectedInsight != null) {
//                Surface(color = Color(0xFFFFF3E0), modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).clickable { selectedInsight = null }, shape = RoundedCornerShape(8.dp)) {
//                    Text("🔬 智能洞察: $selectedInsight \n(点击关闭)", modifier = Modifier.padding(10.dp), fontSize = 11.sp, color = Color(0xFFE65100))
//                }
//            } else {
//                Text("💡 提示：点击下方图表区域，查看系统对数据波动的深度分析。", fontSize = 11.sp, color = Color.Gray, modifier = Modifier.padding(vertical = 8.dp))
//            }
//
//            val allTimes = (cgmNodes.map { it.timeLabel } + moodNodes.map { it.timeLabel }).distinct().sorted()
//            Box(modifier = Modifier.fillMaxWidth().height(250.dp).background(Color(0xFFFAFAFA), RoundedCornerShape(8.dp)).padding(8.dp)) {
//                Canvas(modifier = Modifier.fillMaxSize().horizontalScroll(rememberScrollState()).width((allTimes.size * 45).coerceAtLeast(350).dp).clickable {
//                    selectedInsight = "系统观察到您在 16:00 记录了『压力/烦躁』，随后血糖出现了 +0.2 的异常上升趋势。这说明情绪压力可能导致了皮质醇分泌，引起血糖波动。"
//                }) {
//                    val w = size.width; val h = size.height
//                    fun scaleCgm(v: Double) = h * 0.85f - ((v / 15.0) * (h * 0.7f)).toFloat()
//                    fun scaleMood(v: Float) = h * 0.85f - ((v / 10f) * (h * 0.7f))
//
//                    drawLine(Color.LightGray.copy(alpha=0.3f), Offset(0f, scaleCgm(10.0)), Offset(w, scaleCgm(10.0)), strokeWidth=2f)
//                    drawLine(Color.LightGray.copy(alpha=0.3f), Offset(0f, scaleCgm(3.9)), Offset(w, scaleCgm(3.9)), strokeWidth=2f)
//
//                    var lastCgm: Offset? = null; var lastMood: Offset? = null
//                    val textPaint = android.graphics.Paint().apply { textSize = 18f; isFakeBoldText = true }
//
//                    allTimes.forEachIndexed { i, time ->
//                        val x = if (allTimes.size > 1) (i.toFloat() / (allTimes.size - 1)) * (w - 40f) + 20f else w / 2
//                        val isEven = i % 2 == 0
//                        val timeY = if (isEven) h - 5f else h - 25f
//                        drawContext.canvas.nativeCanvas.drawText(time, x - 18f, timeY, android.graphics.Paint().apply { textSize = 16f; color = android.graphics.Color.GRAY })
//
//                        val cgmNode = cgmNodes.find { it.timeLabel == time }
//                        if (cgmNode != null) {
//                            val y = scaleCgm(cgmNode.value)
//                            val current = Offset(x, y)
//                            if (lastCgm != null) drawLine(Color(0xFF008080).copy(0.7f), lastCgm!!, current, 3f)
//                            drawCircle(Color(0xFF008080), 4f, current)
//                            textPaint.color = android.graphics.Color.parseColor("#008080")
//                            drawContext.canvas.nativeCanvas.drawText("${cgmNode.value}", x - 15f, if (isEven) y - 10f else y + 20f, textPaint)
//                            lastCgm = current
//                        }
//
//                        val moodNode = moodNodes.find { it.timeLabel == time }
//                        if (moodNode != null) {
//                            val y = scaleMood(moodNode.numericScore)
//                            val current = Offset(x, y)
//                            if (lastMood != null) drawLine(Color(0xFF7986CB).copy(0.7f), lastMood!!, current, 3f)
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
//    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White)) {
//        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
//            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                MetricItem("平均血糖", report.avgGlucose); MetricItem("波动 (CV)", report.cv); MetricItem("MAGE指标", report.mage)
//            }
//
//            Column(modifier = Modifier.fillMaxWidth().background(Color(0xFFF1F8E9), RoundedCornerShape(8.dp)).padding(10.dp)) {
//                Text("🎯 TIR 目标达标率", fontWeight = FontWeight.Bold, fontSize = 13.sp)
//                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp).height(12.dp).clip(CircleShape)) {
//                    Box(modifier = Modifier.weight(report.tirLow.toFloat()).fillMaxHeight().background(Color(0xFF64B5F6)))
//                    Box(modifier = Modifier.weight(report.tirTarget.toFloat()).fillMaxHeight().background(Color(0xFF4CAF50)))
//                    Box(modifier = Modifier.weight(report.tirHigh.toFloat()).fillMaxHeight().background(Color(0xFFE57373)))
//                }
//                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                    Text("低血糖: ${report.tbrLevel1}", fontSize = 10.sp); Text("达标: ${report.tirTarget}%", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Color(0xFF4CAF50)); Text("高血糖: ${report.tarLevel1}", fontSize = 10.sp)
//                }
//            }
//            Text("📍 AGP 24小时走势图", fontWeight = FontWeight.Bold, fontSize = 13.sp)
//            Box(modifier = Modifier.fillMaxWidth().height(220.dp).background(Color(0xFFFAFAFA), RoundedCornerShape(8.dp)).padding(8.dp)) {
//                Canvas(modifier = Modifier.fillMaxSize().horizontalScroll(rememberScrollState()).width((nodes.size * 45).coerceAtLeast(350).dp)) {
//                    val w = size.width; val h = size.height
//                    fun scaleY(v: Double) = (h - (v / 15.0) * h).toFloat()
//
//                    drawRect(brush = SolidColor(Color(0xFFE8F5E9)), topLeft = Offset(0f, scaleY(10.0)), size = Size(w, scaleY(3.9) - scaleY(10.0)))
//
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
//            Box(modifier = Modifier.horizontalScroll(rememberScrollState())) {
//                Canvas(modifier = Modifier.width((nodes.size * 45).coerceAtLeast(300).dp).height(140.dp)) {
//                    val ep = android.graphics.Paint().apply { textSize = 40f }
//                    drawContext.canvas.nativeCanvas.drawText("😊", 0f, 40f, ep); drawContext.canvas.nativeCanvas.drawText("😞", 0f, size.height, ep)
//                    val p = Path()
//                    nodes.forEachIndexed { i, n ->
//                        val x = if(nodes.size > 1) (i.toFloat() / (nodes.size - 1)) * (size.width - 40f) + 40f else size.width / 2
//                        val y = size.height - (n.numericScore / 10f) * size.height
//                        if (i == 0) p.moveTo(x, y) else p.lineTo(x, y)
//                        drawCircle(Color(0xFF90CAF9), 6f, Offset(x, y))
//                        drawContext.canvas.nativeCanvas.drawText("${n.numericScore}", x-10f, if(i%2==0) y-10f else y+20f, android.graphics.Paint().apply { textSize=18f; color=android.graphics.Color.DKGRAY })
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.withapp.with.viewmodels.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportView(reportViewModel: ReportViewModel) {
    val scrollState = rememberScrollState()
    val cgmNodes = reportViewModel.cgmNodes
    val moodNodes = reportViewModel.moodNodes
    val cgmRep = reportViewModel.cgmReport.value
    val moodRep = reportViewModel.moodReport.value

    var showCgmDialog by remember { mutableStateOf(false) }
    var showMoodDialog by remember { mutableStateOf(false) }
    var showCgmHistoryDialog by remember { mutableStateOf(false) }
    var showMoodHistoryDialog by remember { mutableStateOf(false) }
    var showGlossaryDialog by remember { mutableStateOf(false) }

    Scaffold { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues).background(Color(0xFFF7F9FA))) {

            Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp).padding(top = 8.dp)) {
                TimeScaleSelector(selectedScale = reportViewModel.currentScale.value, onScaleSelect = { reportViewModel.currentScale.value = it })
            }

            Column(modifier = Modifier.weight(1f).verticalScroll(scrollState).padding(horizontal = 16.dp, vertical = 8.dp), verticalArrangement = Arrangement.spacedBy(24.dp)) {
                Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("🌟 今日状态摘要", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF1565C0))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("您今天的血糖 TIR 达标率为 92%，整体控制极佳。下午 16:00 记录了一次压力情绪，可能伴随着轻微的血糖波动。请注意劳逸结合。", fontSize = 12.sp, color = Color.DarkGray, lineHeight = 18.sp)
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

        if (showGlossaryDialog) {
            AlertDialog(
                onDismissRequest = { showGlossaryDialog = false },
                title = { Text("📖 健康数据小词典", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF008080)) },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.verticalScroll(rememberScrollState())) {
                        Text("• TIR (目标范围内时间)：指您的血糖在理想安全范围内的时间占比。越高越好。", fontSize = 12.sp)
                        Text("• MAGE (平均血糖波动幅度)：反映您血糖像过山车一样波动的剧烈程度。越低越好。", fontSize = 12.sp)
                        Text("• 情绪分数 (0-10)：基于 Russell 环形理论，分数越高代表能量越高、越愉悦；分数越低代表越疲惫或低落。", fontSize = 12.sp)
                    }
                },
                confirmButton = { TextButton(onClick = { showGlossaryDialog = false }) { Text("我知道了", color = Color(0xFF008080)) } }
            )
        }

        if (showCgmDialog) {
            var v1 by remember { mutableStateOf("") }; var v2 by remember { mutableStateOf("") }
            AlertDialog(onDismissRequest = { showCgmDialog = false },
                title = { Text("记录单次血糖", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
                text = { Column(verticalArrangement = Arrangement.spacedBy(12.dp)) { OutlinedTextField(v1, {v1=it}, label={Text("血糖值 (mmol/L)", fontSize=12.sp)}, modifier = Modifier.fillMaxWidth()); OutlinedTextField(v2, {v2=it}, label={Text("时间戳 (如 14:30)", fontSize=12.sp)}, modifier = Modifier.fillMaxWidth()) } },
                confirmButton = { Button(onClick = { reportViewModel.addCgmNode(v1.toDoubleOrNull() ?: 5.5, v2); showCgmDialog = false }) { Text("保存") } },
                dismissButton = { TextButton(onClick = { showCgmDialog = false }) { Text("取消", color = Color.Gray) } }
            )
        }

        if (showMoodDialog) {
            var v1 by remember { mutableStateOf("") }; var v2 by remember { mutableStateOf("") }; var v3 by remember { mutableStateOf("") }
            AlertDialog(onDismissRequest = { showMoodDialog = false },
                title = { Text("记录当前情绪", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
                text = { Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(v1, {v1=it}, label={Text("评分 (0-10)", fontSize=12.sp)}, modifier = Modifier.fillMaxWidth())
                    Text("💡 评分参考：0-3(疲惫/低落)，4-6(平静/一般)，7-10(愉悦/活力)", fontSize = 10.sp, color = Color.Gray)
                    OutlinedTextField(v2, {v2=it}, label={Text("标签 (如 开心/压力)", fontSize=12.sp)}, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(v3, {v3=it}, label={Text("时间", fontSize=12.sp)}, modifier = Modifier.fillMaxWidth())
                } },
                confirmButton = { Button(onClick = { reportViewModel.addMoodNode(v1.toFloatOrNull() ?: 7.0f, v2.ifBlank { "平稳" }, v3); showMoodDialog = false }) { Text("保存") } },
                dismissButton = { TextButton(onClick = { showMoodDialog = false }) { Text("取消", color = Color.Gray) } }
            )
        }

        if (showCgmHistoryDialog) {
            AlertDialog(onDismissRequest = { showCgmHistoryDialog = false }, title = { Text("📊 血糖历史记录", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF008080)) },
                text = {
                    Column(modifier = Modifier.fillMaxWidth().heightIn(max = 400.dp).verticalScroll(rememberScrollState())) {
                        if (cgmNodes.isEmpty()) { Text("暂无数据记录", fontSize = 13.sp, color = Color.Gray) } else {
                            cgmNodes.reversed().forEach { node ->
                                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(node.timeLabel, fontSize = 14.sp, color = Color.Gray, modifier = Modifier.weight(1f))
                                    Text("${node.value} mmol/L ${node.trend}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
                                }
                                HorizontalDivider(color = Color(0xFFF1F1F1))
                            }
                        }
                    }
                },
                confirmButton = { TextButton(onClick = { showCgmHistoryDialog = false }) { Text("关闭", color = Color(0xFF008080)) } }
            )
        }

        if (showMoodHistoryDialog) {
            AlertDialog(onDismissRequest = { showMoodHistoryDialog = false }, title = { Text("🧠 情绪历史记录", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFFFFB74D)) },
                text = {
                    Column(modifier = Modifier.fillMaxWidth().heightIn(max = 400.dp).verticalScroll(rememberScrollState())) {
                        if (moodNodes.isEmpty()) { Text("暂无数据记录", fontSize = 13.sp, color = Color.Gray) } else {
                            moodNodes.reversed().forEach { node ->
                                Column(modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp)) {
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text(node.timeLabel, fontSize = 14.sp, color = Color.Gray)
                                        Text("${node.numericScore} 分 | ${node.label}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
                                    }
                                    Text("来源: ${node.value}", fontSize = 11.sp, color = Color.LightGray, modifier = Modifier.padding(top = 4.dp))
                                }
                                HorizontalDivider(color = Color(0xFFF1F1F1))
                            }
                        }
                    }
                },
                confirmButton = { TextButton(onClick = { showMoodHistoryDialog = false }) { Text("关闭", color = Color(0xFFFFB74D)) } }
            )
        }
    }
}

@Composable
fun CorrelationChartCard(cgmNodes: List<CgmNode>, moodNodes: List<MoodLog>) {
    var selectedInsight by remember { mutableStateOf<String?>(null) }

    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("🔄 血糖与情绪交叉分析", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF673AB7))
            Row(modifier = Modifier.fillMaxWidth().padding(top=8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("🟢 血糖 (mmol/L)", color = Color(0xFF008080), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                Text("🟣 情绪 (0-10)", color = Color(0xFF7986CB), fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }

            if (selectedInsight != null) {
                Surface(color = Color(0xFFFFF3E0), modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).clickable { selectedInsight = null }, shape = RoundedCornerShape(8.dp)) {
                    Text("🔬 智能洞察: $selectedInsight \n(点击关闭)", modifier = Modifier.padding(10.dp), fontSize = 11.sp, color = Color(0xFFE65100))
                }
            } else {
                Text("💡 提示：点击下方图表区域，查看系统对数据波动的深度分析。", fontSize = 11.sp, color = Color.Gray, modifier = Modifier.padding(vertical = 8.dp))
            }

            val allTimes = (cgmNodes.map { it.timeLabel } + moodNodes.map { it.timeLabel }).distinct().sorted()
            Box(modifier = Modifier.fillMaxWidth().height(250.dp).background(Color(0xFFFAFAFA), RoundedCornerShape(8.dp)).padding(8.dp)) {
                Canvas(modifier = Modifier.fillMaxSize().horizontalScroll(rememberScrollState()).width((allTimes.size * 45).coerceAtLeast(350).dp).clickable {
                    selectedInsight = "系统观察到您在 16:00 记录了『压力/烦躁』，随后血糖出现了 +0.2 的异常上升趋势。这说明情绪压力可能导致了皮质醇分泌，引起血糖波动。"
                }) {
                    val w = size.width; val h = size.height
                    fun scaleCgm(v: Double) = h * 0.85f - ((v / 15.0) * (h * 0.7f)).toFloat()
                    fun scaleMood(v: Float) = h * 0.85f - ((v / 10f) * (h * 0.7f))

                    drawLine(Color.LightGray.copy(alpha=0.3f), Offset(0f, scaleCgm(10.0)), Offset(w, scaleCgm(10.0)), strokeWidth=2f)
                    drawLine(Color.LightGray.copy(alpha=0.3f), Offset(0f, scaleCgm(3.9)), Offset(w, scaleCgm(3.9)), strokeWidth=2f)

                    var lastCgm: Offset? = null; var lastMood: Offset? = null
                    val textPaint = android.graphics.Paint().apply { textSize = 18f; isFakeBoldText = true }

                    allTimes.forEachIndexed { i, time ->
                        val x = if (allTimes.size > 1) (i.toFloat() / (allTimes.size - 1)) * (w - 40f) + 20f else w / 2
                        val isEven = i % 2 == 0
                        val timeY = if (isEven) h - 5f else h - 25f
                        drawContext.canvas.nativeCanvas.drawText(time, x - 18f, timeY, android.graphics.Paint().apply { textSize = 16f; color = android.graphics.Color.GRAY })

                        val cgmNode = cgmNodes.find { it.timeLabel == time }
                        if (cgmNode != null) {
                            val y = scaleCgm(cgmNode.value)
                            val current = Offset(x, y)
                            if (lastCgm != null) drawLine(Color(0xFF008080).copy(0.7f), lastCgm!!, current, 3f)
                            drawCircle(Color(0xFF008080), 4f, current)
                            textPaint.color = android.graphics.Color.parseColor("#008080")
                            drawContext.canvas.nativeCanvas.drawText("${cgmNode.value}", x - 15f, if (isEven) y - 10f else y + 20f, textPaint)
                            lastCgm = current
                        }

                        val moodNode = moodNodes.find { it.timeLabel == time }
                        if (moodNode != null) {
                            val y = scaleMood(moodNode.numericScore)
                            val current = Offset(x, y)
                            if (lastMood != null) drawLine(Color(0xFF7986CB).copy(0.7f), lastMood!!, current, 3f)
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
    // 🆕 V8 终极修改：渐进式揭示 (Progressive Disclosure) 控制状态
    var showAdvancedMetrics by remember { mutableStateOf(false) }

    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {

            // 基础信息：始终显示
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                MetricItem("今日平均血糖", report.avgGlucose)
                Text(report.clinicalAdvice, fontSize = 11.sp, color = Color(0xFF008080), modifier = Modifier.weight(1f).padding(start = 12.dp), lineHeight = 16.sp)
            }

            // 📍 24小时走势图 (核心可视化)：始终显示
            Text("📍 AGP 24小时走势图", fontWeight = FontWeight.Bold, fontSize = 13.sp)
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

            // 🆕 展开按钮
            Row(modifier = Modifier.fillMaxWidth().clickable { showAdvancedMetrics = !showAdvancedMetrics }.padding(vertical = 4.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Text(if (showAdvancedMetrics) "收起临床指标" else "查看专业临床指标", fontSize = 12.sp, color = Color(0xFF008080), fontWeight = FontWeight.Bold)
                Icon(if (showAdvancedMetrics) Icons.Default.ExpandLess else Icons.Default.ExpandMore, null, tint = Color(0xFF008080), modifier = Modifier.size(16.dp))
            }

            // 🆕 渐进式揭示的高级指标
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
            Box(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                Canvas(modifier = Modifier.width((nodes.size * 45).coerceAtLeast(300).dp).height(160.dp)) {
                    val ep = android.graphics.Paint().apply { textSize = 35f }

                    // 🆕 V8 终极修改：多极情感效价标尺 (Multi-Valence Scale)，解决 P2 "只有哭笑两个表情"
                    drawContext.canvas.nativeCanvas.drawText("🤩", 0f, 35f, ep) // 10分
                    drawContext.canvas.nativeCanvas.drawText("🙂", 0f, size.height * 0.35f, ep) // 7分
                    drawContext.canvas.nativeCanvas.drawText("😐", 0f, size.height * 0.65f, ep) // 4分
                    drawContext.canvas.nativeCanvas.drawText("😞", 0f, size.height - 5f, ep) // 0分

                    val p = Path()
                    nodes.forEachIndexed { i, n ->
                        // 为表情留出 40f 的左侧边距
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