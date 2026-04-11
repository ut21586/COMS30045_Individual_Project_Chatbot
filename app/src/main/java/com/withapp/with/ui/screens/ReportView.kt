//
//
//
//package com.withapp.with.ui.screens
//
//import androidx.compose.foundation.*
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.ArrowBack
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.geometry.Size
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.Path
//import androidx.compose.ui.graphics.drawscope.Stroke
//import androidx.compose.ui.graphics.nativeCanvas
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.withapp.with.viewmodels.*
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ReportView(reportViewModel: ReportViewModel, onBack: () -> Unit = {}) {
//    val scrollState = rememberScrollState()
//    val cgmNodes = reportViewModel.cgmNodes
//    val moodNodes = reportViewModel.moodNodes
//    val cgmRep = reportViewModel.cgmReport.value
//    val moodRep = reportViewModel.moodReport.value
//
//    var showCgmDialog by remember { mutableStateOf(false) }
//    var showMoodDialog by remember { mutableStateOf(false) }
//    var showDietDialog by remember { mutableStateOf(false) }
//    var showExerciseDialog by remember { mutableStateOf(false) }
//    var showHrDialog by remember { mutableStateOf(false) }
//
//    Scaffold(
//        topBar = { TopAppBar(title = { Text("高精度全量数据报告 (Full Data Report)", fontWeight = FontWeight.Bold, fontSize = 16.sp) }, navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) } }) }
//    ) { paddingValues ->
//        Column(modifier = Modifier.fillMaxSize().padding(paddingValues).background(Color(0xFFF7F9FA)).verticalScroll(scrollState).padding(16.dp), verticalArrangement = Arrangement.spacedBy(24.dp)) {
//
//            TimeScaleSelector(selectedScale = reportViewModel.currentScale.value, onScaleSelect = { reportViewModel.currentScale.value = it })
//
//            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
//                Text("📊 CGM 核心数据总览\n(CGM Core Overview)", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF008080))
//                IconButton(onClick = { showCgmDialog = true }, modifier = Modifier.size(28.dp).background(Color(0xFF008080).copy(0.1f), CircleShape)) { Icon(Icons.Default.Add, null, tint = Color(0xFF008080), modifier = Modifier.size(18.dp)) }
//            }
//            ClinicalCgmCard(cgmRep, cgmNodes)
//
//            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
//                Text("🧠 核心情绪追踪与记录\n(Core Mood Tracking)", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFFFFB74D))
//                IconButton(onClick = { showMoodDialog = true }, modifier = Modifier.size(28.dp).background(Color(0xFFFFB74D).copy(0.1f), CircleShape)) { Icon(Icons.Default.Add, null, tint = Color(0xFFFFB74D), modifier = Modifier.size(18.dp)) }
//            }
//            ClinicalMoodCard(moodRep, moodNodes)
//
//            Text("🔄 交叉分析：血糖与情绪关联对比\n(Cross-Analysis: BG & Mood)", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF673AB7))
//            CorrelationChartCard(cgmNodes, moodNodes)
//
//            Text("🛡️ 行为/上下文数据走势\n(Behavior/Context Trends)", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF5C6BC0))
//            HealthListCard("🍏 饮食与胰岛素 (Diet & Insulin)", Color(0xFF81C784), reportViewModel.dietLogs.toList(), { showDietDialog = true }, { it.numericValue }, { "${it.time} - ${it.food} (${it.carbs})" })
//            HealthListCard("🏃 运动与睡眠 (Exercise & Sleep)", Color(0xFF4FC3F7), reportViewModel.exerciseLogs.toList(), { showExerciseDialog = true }, { it.numericValue }, { "${it.time} - ${it.activity} (${it.duration})" })
//            HealthListCard("💓 心率监测 (Heart Rate)", Color(0xFFE57373), reportViewModel.hrLogs.toList(), { showHrDialog = true }, { it.bpm.toFloat() }, { "${it.time} - BPM: ${it.bpm} (${it.status})" })
//
//            Spacer(Modifier.height(40.dp))
//        }
//
//        if (showCgmDialog) {
//            var v1 by remember { mutableStateOf("") }; var v2 by remember { mutableStateOf("") }
//            AlertDialog(onDismissRequest = { showCgmDialog = false },
//                title = { Text("添加实时CGM (Add Real-time CGM)", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
//                text = { Column(verticalArrangement = Arrangement.spacedBy(12.dp)) { OutlinedTextField(v1, {v1=it}, label={Text("血糖值 (Glucose - mmol/L)", fontSize=12.sp)}, modifier = Modifier.fillMaxWidth()); OutlinedTextField(v2, {v2=it}, label={Text("时间戳可选 (Time - e.g. 14:30)", fontSize=12.sp)}, modifier = Modifier.fillMaxWidth()) } },
//                confirmButton = { Button(onClick = { reportViewModel.addCgmNode(v1.toDoubleOrNull() ?: 5.5, v2); showCgmDialog = false }) { Text("添加并推演 (Add & Calc)") } }
//            )
//        }
//        if (showMoodDialog) {
//            var v1 by remember { mutableStateOf("") }; var v2 by remember { mutableStateOf("") }; var v3 by remember { mutableStateOf("") }
//            AlertDialog(onDismissRequest = { showMoodDialog = false },
//                title = { Text("添加精准情绪 (Add Precise Mood)", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
//                text = { Column(verticalArrangement = Arrangement.spacedBy(12.dp)) { OutlinedTextField(v1, {v1=it}, label={Text("评分 (Score 0.0-10.0)", fontSize=12.sp)}, modifier = Modifier.fillMaxWidth()); OutlinedTextField(v2, {v2=it}, label={Text("标签 (Label - e.g. Great/Low)", fontSize=12.sp)}, modifier = Modifier.fillMaxWidth()); OutlinedTextField(v3, {v3=it}, label={Text("时间戳可选 (Time)", fontSize=12.sp)}, modifier = Modifier.fillMaxWidth()) } },
//                confirmButton = { Button(onClick = { reportViewModel.addMoodNode(v1.toFloatOrNull() ?: 7.0f, v2.ifBlank { "平稳" }, v3); showMoodDialog = false }) { Text("精准打分 (Log Mood)") } }
//            )
//        }
//        if (showDietDialog) InputDialog("添加上下文 (Add Context)", "项目 (Item)", "数值 (Value)") { v1, v2 -> reportViewModel.addDiet(v1, v2); showDietDialog = false }
//        if (showExerciseDialog) InputDialog("添加运动 (Add Exercise)", "项目 (Item)", "时长 (Duration-min)") { v1, v2 -> reportViewModel.addExercise(v1, v2); showExerciseDialog = false }
//        if (showHrDialog) InputDialog("添加心率 (Add Heart Rate)", "BPM", "状态 (Status)") { v1, v2 -> reportViewModel.addHeartRate(v1.toIntOrNull() ?: 75, v2); showHrDialog = false }
//    }
//}
//
//// 🆕 极高密度交叉渲染：全量双轴对比图
//@Composable
//fun CorrelationChartCard(cgmNodes: List<CgmNode>, moodNodes: List<MoodLog>) {
//    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White)) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                Text("🟢 血糖 (Glucose mmol/L)", color = Color(0xFF008080), fontWeight = FontWeight.Bold, fontSize = 12.sp)
//                Text("🟣 情绪 (Mood 0-10)", color = Color(0xFF7986CB), fontWeight = FontWeight.Bold, fontSize = 12.sp)
//            }
//            Spacer(Modifier.height(12.dp))
//
//            val allTimes = (cgmNodes.map { it.timeLabel } + moodNodes.map { it.timeLabel }).distinct().sorted()
//            if (allTimes.isEmpty()) {
//                Text("暂无足量数据进行关联分析 (Insufficient data)", color = Color.Gray, fontSize = 12.sp)
//                return@Column
//            }
//
//            Box(modifier = Modifier.fillMaxWidth().height(250.dp).background(Color(0xFFFAFAFA), RoundedCornerShape(8.dp)).padding(8.dp)) {
//                // 🔥 画布超长延伸：点间距 75dp 确保密集但不重叠
//                Canvas(modifier = Modifier.fillMaxSize().horizontalScroll(rememberScrollState()).width((allTimes.size * 75).coerceAtLeast(350).dp)) {
//                    val w = size.width; val h = size.height
//                    fun scaleCgm(v: Double) = h * 0.85f - ((v / 15.0) * (h * 0.7f)).toFloat()
//                    fun scaleMood(v: Float) = h * 0.85f - ((v / 10f) * (h * 0.7f))
//
//                    val gridPaint = android.graphics.Paint().apply { textSize = 20f; color = android.graphics.Color.LTGRAY }
//                    drawLine(Color.LightGray.copy(alpha=0.3f), Offset(0f, scaleCgm(10.0)), Offset(w, scaleCgm(10.0)), strokeWidth=2f)
//                    drawLine(Color.LightGray.copy(alpha=0.3f), Offset(0f, scaleCgm(3.9)), Offset(w, scaleCgm(3.9)), strokeWidth=2f)
//
//                    var lastCgm: Offset? = null; var lastMood: Offset? = null
//                    val textPaint = android.graphics.Paint().apply { textSize = 20f; isFakeBoldText = true }
//
//                    allTimes.forEachIndexed { i, time ->
//                        val x = if (allTimes.size > 1) (i.toFloat() / (allTimes.size - 1)) * (w - 60f) + 30f else w / 2
//                        drawContext.canvas.nativeCanvas.drawText(time, x - 20f, h - 5f, android.graphics.Paint().apply { textSize = 18f; color = android.graphics.Color.GRAY })
//
//                        val cgmNode = cgmNodes.find { it.timeLabel == time }
//                        if (cgmNode != null) {
//                            val y = scaleCgm(cgmNode.value)
//                            val current = Offset(x, y)
//                            if (lastCgm != null) drawLine(Color(0xFF008080).copy(0.7f), lastCgm!!, current, 3f)
//                            drawCircle(Color(0xFF008080), 5f, current)
//                            textPaint.color = android.graphics.Color.parseColor("#008080")
//                            drawContext.canvas.nativeCanvas.drawText("${cgmNode.value}${cgmNode.trend}", x - 18f, y - 10f, textPaint)
//                            lastCgm = current
//                        }
//
//                        val moodNode = moodNodes.find { it.timeLabel == time }
//                        if (moodNode != null) {
//                            val y = scaleMood(moodNode.numericScore)
//                            val current = Offset(x, y)
//                            if (lastMood != null) drawLine(Color(0xFF7986CB).copy(0.7f), lastMood!!, current, 3f)
//                            drawCircle(Color(0xFF7986CB), 5f, current)
//                            textPaint.color = android.graphics.Color.parseColor("#7986CB")
//                            drawContext.canvas.nativeCanvas.drawText("${moodNode.numericScore}", x + 5f, y + 15f, textPaint)
//                            lastMood = current
//                        }
//                    }
//                }
//            }
//            Spacer(Modifier.height(8.dp))
//            Text("💡 提示：在同一时间节点记录的数据越多，关联趋势越精准。\n(Tip: More concurrent data yields better correlation.)", fontSize = 10.sp, color = Color.Gray)
//        }
//    }
//}
//
//@Composable
//fun ClinicalCgmCard(report: ClinicalCgmReport, nodes: List<CgmNode>) {
//    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White)) {
//        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
//            val latest = nodes.lastOrNull()
//            if (latest != null) {
//                Column(modifier = Modifier.fillMaxWidth().background(Color(0xFFE8F5E9), RoundedCornerShape(8.dp)).padding(12.dp)) {
//                    Text("🟢 实时与推算数据 (Real-time & Inferred)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF2E7D32))
//                    Spacer(Modifier.height(10.dp))
//                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                        MetricItem("当前血糖 (Current BG)", "${latest.value} mmol/L")
//                        MetricItem("时间戳 (Time)", latest.timeLabel)
//                        MetricItem("采样间隔 (Interval)", latest.samplingInterval)
//                    }
//                    Spacer(Modifier.height(10.dp))
//                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                        MetricItem("推演趋势 (Trend)", latest.trend)
//                        MetricItem("推演速率 (Rate)", "${latest.rateOfChange} /min")
//                    }
//                }
//            }
//            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                Column { Text("设备状态 (Device)", fontSize = 10.sp, color = Color.Gray); Text(report.deviceInfo, fontSize = 11.sp) }
//                Column(horizontalAlignment = Alignment.End) { Text("完整率 (Completeness)", fontSize = 10.sp, color = Color.Gray); Text(report.completeness, fontSize = 11.sp, color = Color(0xFF008080)) }
//            }
//            HorizontalDivider(color = Color(0xFFF1F1F1))
//            Text("📈 统计汇总数据 (Statistical Summary)", fontWeight = FontWeight.Bold, fontSize = 13.sp)
//            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                MetricItem("平均血糖 (Avg BG)", report.avgGlucose); MetricItem("波动 (CV)", report.cv); MetricItem("MAGE", report.mage)
//            }
//            Column(modifier = Modifier.fillMaxWidth().background(Color(0xFFF1F8E9), RoundedCornerShape(8.dp)).padding(10.dp)) {
//                Text("🎯 TIR体系 (Time in Range)", fontWeight = FontWeight.Bold, fontSize = 13.sp)
//                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp).height(12.dp).clip(CircleShape)) {
//                    Box(modifier = Modifier.weight(report.tirLow.toFloat()).fillMaxHeight().background(Color(0xFF64B5F6)))
//                    Box(modifier = Modifier.weight(report.tirTarget.toFloat()).fillMaxHeight().background(Color(0xFF4CAF50)))
//                    Box(modifier = Modifier.weight(report.tirHigh.toFloat()).fillMaxHeight().background(Color(0xFFE57373)))
//                }
//                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                    Text("低血糖 TBR: ${report.tbrLevel1}", fontSize = 10.sp); Text("达标 TIR: ${report.tirTarget}%", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Color(0xFF4CAF50)); Text("高血糖 TAR: ${report.tarLevel1}", fontSize = 10.sp)
//                }
//            }
//            Text("📍 AGP 专业走势图 (AGP Trend)", fontWeight = FontWeight.Bold, fontSize = 13.sp)
//            Box(modifier = Modifier.fillMaxWidth().height(220.dp).background(Color(0xFFFAFAFA), RoundedCornerShape(8.dp)).padding(8.dp)) {
//                // 🔥 极限长画布展开：支持几十个点位的顺滑连贯呈现
//                Canvas(modifier = Modifier.fillMaxSize().horizontalScroll(rememberScrollState()).width((nodes.size * 75).coerceAtLeast(350).dp)) {
//                    val w = size.width; val h = size.height
//                    val maxBG = 15.0; val minBG = 0.0
//                    fun scaleY(v: Double) = (h - (v / maxBG) * h).toFloat()
//                    drawRect(color = Color(0xFFE8F5E9), topLeft = Offset(0f, scaleY(10.0)), size = Size(w, scaleY(3.9) - scaleY(10.0)))
//                    val gridPaint = android.graphics.Paint().apply { textSize = 20f; color = android.graphics.Color.GRAY }
//                    listOf(3.9, 7.0, 10.0, 15.0).forEach { yVal ->
//                        val y = scaleY(yVal); drawLine(Color.LightGray.copy(alpha=0.5f), Offset(0f, y), Offset(w, y), strokeWidth = 2f)
//                        drawContext.canvas.nativeCanvas.drawText("$yVal", 5f, y - 5f, gridPaint)
//                    }
//                    if (nodes.isNotEmpty()) {
//                        val path = Path()
//                        nodes.forEachIndexed { i, n ->
//                            val x = if(nodes.size > 1) (i.toFloat() / (nodes.size - 1)) * (w - 60f) + 30f else w/2
//                            val y = scaleY(n.value)
//                            if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
//                            drawCircle(Color(0xFF008080), 6f, Offset(x, y)); drawCircle(Color.White, 3f, Offset(x, y))
//                            drawContext.canvas.nativeCanvas.drawText(n.timeLabel, x - 18f, h - 5f, gridPaint)
//                            drawContext.canvas.nativeCanvas.drawText("${n.value} ${n.trend}", x - 20f, y - 10f, android.graphics.Paint().apply { textSize = 20f; isFakeBoldText = true; color = android.graphics.Color.DKGRAY })
//                        }
//                        drawPath(path, Color(0xFF008080), style = Stroke(4f))
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
//                Text("🧠 核心情绪指标 (Core Metrics)", fontWeight = FontWeight.Bold, fontSize = 13.sp)
//                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                    MetricItem("平均分 (Mean)", report.meanScore); MetricItem("标准差 (SD)", report.variabilitySD); MetricItem("不稳定指数 (Index)", report.instabilityIndex)
//                }
//                Spacer(Modifier.height(8.dp)); Row(modifier = Modifier.fillMaxWidth().height(10.dp).clip(CircleShape)) {
//                Box(modifier = Modifier.weight(report.negativeAffect.toFloat()).fillMaxHeight().background(Color(0xFF7986CB)))
//                Box(modifier = Modifier.weight(report.neutralAffect.toFloat()).fillMaxHeight().background(Color(0xFF81C784)))
//                Box(modifier = Modifier.weight(report.positiveAffect.toFloat()).fillMaxHeight().background(Color(0xFFFFB74D)))
//            }
//            }
//            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                Text("抑郁 PHQ-9: ${report.phq9Score}", fontSize = 10.sp); Text("焦虑 GAD-7: ${report.gad7Score}", fontSize = 10.sp); Text("压力 PSS: ${report.pssScore}", fontSize = 10.sp)
//            }
//            Box(modifier = Modifier.horizontalScroll(rememberScrollState())) {
//                // 🔥 匹配的高频紧凑画布
//                Canvas(modifier = Modifier.width((nodes.size * 75).coerceAtLeast(300).dp).height(140.dp)) {
//                    val ep = android.graphics.Paint().apply { textSize = 40f }
//                    drawContext.canvas.nativeCanvas.drawText("😊", 0f, 40f, ep); drawContext.canvas.nativeCanvas.drawText("😞", 0f, size.height, ep)
//                    val p = Path()
//                    nodes.forEachIndexed { i, n ->
//                        val x = if(nodes.size > 1) (i.toFloat() / (nodes.size - 1)) * (size.width - 40f) + 40f else size.width / 2
//                        val y = size.height - (n.numericScore / 10f) * size.height
//                        if (i == 0) p.moveTo(x, y) else p.lineTo(x, y)
//                        drawCircle(Color(0xFF90CAF9), 6f, Offset(x, y))
//                        drawContext.canvas.nativeCanvas.drawText("${n.numericScore}", x-12f, y-12f, android.graphics.Paint().apply { textSize = 20f; color = android.graphics.Color.DKGRAY })
//                    }
//                    drawPath(p, Color(0xFF7986CB), style = Stroke(4f))
//                }
//            }
//            Spacer(Modifier.height(8.dp))
//            Text("情绪历史追溯 (Mood Logs) - ${nodes.size} 条/entries", fontSize = 12.sp, color = Color.Gray)
//            Column(modifier = Modifier.fillMaxWidth().heightIn(max = 140.dp).verticalScroll(rememberScrollState())) {
//                nodes.reversed().forEach { item ->
//                    Text("${item.timeLabel} | [${item.numericScore}] ${item.label} : ${item.value}", fontSize = 12.sp, color = Color.DarkGray, modifier = Modifier.padding(vertical = 8.dp))
//                    HorizontalDivider(color = Color(0xFFF1F1F1))
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
//            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
//                Text(title, fontWeight = FontWeight.Bold, color = accent, fontSize = 13.sp)
//                IconButton(onClick = onAddClick, modifier = Modifier.size(28.dp).background(accent.copy(0.15f), CircleShape)) { Icon(Icons.Default.Add, null, tint = accent, modifier = Modifier.size(18.dp)) }
//            }
//            Spacer(Modifier.height(12.dp))
//            if (items.isNotEmpty()) {
//                val chartData = items.reversed()
//                Box(modifier = Modifier.fillMaxWidth().height(90.dp).background(Color(0xFFFAFAFA), RoundedCornerShape(8.dp)).padding(8.dp)) {
//                    Canvas(modifier = Modifier.fillMaxSize()) {
//                        val w = size.width; val h = size.height
//                        val maxVal = chartData.maxOfOrNull { valueSelector(it) } ?: 100f; val minVal = chartData.minOfOrNull { valueSelector(it) } ?: 0f
//                        val range = if (maxVal == minVal) 1f else (maxVal - minVal)
//                        val path = Path()
//                        chartData.forEachIndexed { i, item ->
//                            val x = if (chartData.size > 1) (i.toFloat() / (chartData.size - 1)) * w else w / 2
//                            val y = h - ((valueSelector(item) - minVal) / range) * (h * 0.7f) - (h * 0.15f)
//                            if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
//                            drawCircle(accent, 6f, Offset(x, y)); drawContext.canvas.nativeCanvas.drawText(valueSelector(item).toInt().toString(), x-10f, y-15f, android.graphics.Paint().apply { textSize=20f; color=android.graphics.Color.DKGRAY })
//                        }
//                        drawPath(path, accent.copy(0.6f), style = Stroke(4f))
//                    }
//                }
//            } else { Text("暂无数据 (No Data)", color = Color.LightGray, fontSize = 12.sp, modifier = Modifier.padding(vertical = 10.dp)) }
//            Spacer(Modifier.height(10.dp))
//            Column(modifier = Modifier.fillMaxWidth().heightIn(max = 140.dp).verticalScroll(rememberScrollState())) {
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
//fun InputDialog(title: String, l1: String, l2: String, onConfirm: (String, String) -> Unit) {
//    var t1 by remember { mutableStateOf("") }; var t2 by remember { mutableStateOf("") }
//    AlertDialog(onDismissRequest = {}, title = { Text(title, fontSize = 16.sp, fontWeight = FontWeight.Bold) }, text = { Column(verticalArrangement = Arrangement.spacedBy(12.dp)) { OutlinedTextField(t1, {t1=it}, label={Text(l1, fontSize=12.sp)}, modifier = Modifier.fillMaxWidth()); OutlinedTextField(t2, {t2=it}, label={Text(l2, fontSize=12.sp)}, modifier = Modifier.fillMaxWidth()) } },
//        confirmButton = { Button(onClick = {onConfirm(t1,t2)}) { Text("添加 (Add)") } }
//    )
//}
//
//@Composable
//fun TimeScaleSelector(selectedScale: String, onScaleSelect: (String) -> Unit) {
//    val scales = listOf("日 (Day)", "月 (Month)", "年 (Year)")
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

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.withapp.with.viewmodels.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportView(reportViewModel: ReportViewModel, onBack: () -> Unit = {}) {
    val scrollState = rememberScrollState()
    val currentScale = reportViewModel.currentScale.value

    val cgmNodes = reportViewModel.cgmNodes
    val moodNodes = reportViewModel.moodNodes
    val cgmRep = reportViewModel.cgmReport.value
    val moodRep = reportViewModel.moodReport.value

    var showCgmDialog by remember { mutableStateOf(false) }
    var showMoodDialog by remember { mutableStateOf(false) }
    var showDietDialog by remember { mutableStateOf(false) }
    var showExerciseDialog by remember { mutableStateOf(false) }
    var showHrDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("高精度全量数据报告 (Full Data Report)", fontWeight = FontWeight.Bold, fontSize = 16.sp) }, navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) } }) }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues).background(Color(0xFFF7F9FA)).verticalScroll(scrollState).padding(16.dp), verticalArrangement = Arrangement.spacedBy(24.dp)) {

            TimeScaleSelector(selectedScale = reportViewModel.currentScale.value, onScaleSelect = { reportViewModel.currentScale.value = it })

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("📊 CGM 核心数据总览\n(CGM Core Overview)", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF008080))
                IconButton(onClick = { showCgmDialog = true }, modifier = Modifier.size(28.dp).background(Color(0xFF008080).copy(0.1f), CircleShape)) { Icon(Icons.Default.Add, null, tint = Color(0xFF008080), modifier = Modifier.size(18.dp)) }
            }
            ClinicalCgmCard(cgmRep, cgmNodes)

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("🧠 核心情绪追踪与记录\n(Core Mood Tracking)", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFFFFB74D))
                IconButton(onClick = { showMoodDialog = true }, modifier = Modifier.size(28.dp).background(Color(0xFFFFB74D).copy(0.1f), CircleShape)) { Icon(Icons.Default.Add, null, tint = Color(0xFFFFB74D), modifier = Modifier.size(18.dp)) }
            }
            ClinicalMoodCard(moodRep, moodNodes)

            Text("🔄 交叉分析：血糖与情绪关联对比\n(Cross-Analysis: BG & Mood)", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF673AB7))
            CorrelationChartCard(cgmNodes, moodNodes)

            Text("🛡️ 行为/上下文数据走势\n(Behavior/Context Trends)", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF5C6BC0))
            HealthListCard("🍏 饮食与胰岛素 (Diet & Insulin)", Color(0xFF81C784), reportViewModel.dietLogs.toList(), { showDietDialog = true }, { it.numericValue }, { "${it.time} - ${it.food} (${it.carbs})" })
            HealthListCard("🏃 运动与睡眠 (Exercise & Sleep)", Color(0xFF4FC3F7), reportViewModel.exerciseLogs.toList(), { showExerciseDialog = true }, { it.numericValue }, { "${it.time} - ${it.activity} (${it.duration})" })
            HealthListCard("💓 心率监测 (Heart Rate)", Color(0xFFE57373), reportViewModel.hrLogs.toList(), { showHrDialog = true }, { it.bpm.toFloat() }, { "${it.time} - BPM: ${it.bpm} (${it.status})" })

            Spacer(Modifier.height(40.dp))
        }

        if (showCgmDialog) {
            var v1 by remember { mutableStateOf("") }; var v2 by remember { mutableStateOf("") }
            AlertDialog(onDismissRequest = { showCgmDialog = false },
                title = { Text("添加实时CGM (Add Real-time CGM)", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
                text = { Column(verticalArrangement = Arrangement.spacedBy(12.dp)) { OutlinedTextField(v1, {v1=it}, label={Text("血糖值 (Glucose - mmol/L)", fontSize=12.sp)}, modifier = Modifier.fillMaxWidth()); OutlinedTextField(v2, {v2=it}, label={Text("时间戳可选 (Time - e.g. 14:30)", fontSize=12.sp)}, modifier = Modifier.fillMaxWidth()) } },
                confirmButton = { Button(onClick = { reportViewModel.addCgmNode(v1.toDoubleOrNull() ?: 5.5, v2); showCgmDialog = false }) { Text("添加并推演 (Add & Calc)") } }
            )
        }
        if (showMoodDialog) {
            var v1 by remember { mutableStateOf("") }; var v2 by remember { mutableStateOf("") }; var v3 by remember { mutableStateOf("") }
            AlertDialog(onDismissRequest = { showMoodDialog = false },
                title = { Text("添加精准情绪 (Add Precise Mood)", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
                text = { Column(verticalArrangement = Arrangement.spacedBy(12.dp)) { OutlinedTextField(v1, {v1=it}, label={Text("评分 (Score 0.0-10.0)", fontSize=12.sp)}, modifier = Modifier.fillMaxWidth()); OutlinedTextField(v2, {v2=it}, label={Text("标签 (Label - e.g. Great/Low)", fontSize=12.sp)}, modifier = Modifier.fillMaxWidth()); OutlinedTextField(v3, {v3=it}, label={Text("时间戳可选 (Time)", fontSize=12.sp)}, modifier = Modifier.fillMaxWidth()) } },
                confirmButton = { Button(onClick = { reportViewModel.addMoodNode(v1.toFloatOrNull() ?: 7.0f, v2.ifBlank { "平稳" }, v3); showMoodDialog = false }) { Text("精准打分 (Log Mood)") } }
            )
        }
        if (showDietDialog) InputDialog("添加上下文 (Add Context)", "项目 (Item)", "数值 (Value)") { v1, v2 -> reportViewModel.addDiet(v1, v2); showDietDialog = false }
        if (showExerciseDialog) InputDialog("添加运动 (Add Exercise)", "项目 (Item)", "时长 (Duration-min)") { v1, v2 -> reportViewModel.addExercise(v1, v2); showExerciseDialog = false }
        if (showHrDialog) InputDialog("添加心率 (Add Heart Rate)", "BPM", "状态 (Status)") { v1, v2 -> reportViewModel.addHeartRate(v1.toIntOrNull() ?: 75, v2); showHrDialog = false }
    }
}

// 🆕 史诗级高密度交叉渲染 (防碰撞交替Y轴算法)
@Composable
fun CorrelationChartCard(cgmNodes: List<CgmNode>, moodNodes: List<MoodLog>) {
    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("🟢 血糖 (Glucose mmol/L)", color = Color(0xFF008080), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                Text("🟣 情绪 (Mood 0-10)", color = Color(0xFF7986CB), fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
            Spacer(Modifier.height(12.dp))

            val allTimes = (cgmNodes.map { it.timeLabel } + moodNodes.map { it.timeLabel }).distinct().sorted()
            if (allTimes.isEmpty()) {
                Text("暂无足量数据进行关联分析 (Insufficient data for correlation)", color = Color.Gray, fontSize = 12.sp)
                return@Column
            }

            Box(modifier = Modifier.fillMaxWidth().height(250.dp).background(Color(0xFFFAFAFA), RoundedCornerShape(8.dp)).padding(8.dp)) {
                // 🔥 密集压缩：点位距离降至 45dp，极大增加了视窗内可见数据量！
                Canvas(modifier = Modifier.fillMaxSize().horizontalScroll(rememberScrollState()).width((allTimes.size * 45).coerceAtLeast(350).dp)) {
                    val w = size.width; val h = size.height
                    fun scaleCgm(v: Double) = h * 0.85f - ((v / 15.0) * (h * 0.7f)).toFloat()
                    fun scaleMood(v: Float) = h * 0.85f - ((v / 10f) * (h * 0.7f))

                    val gridPaint = android.graphics.Paint().apply { textSize = 20f; color = android.graphics.Color.LTGRAY }
                    drawLine(Color.LightGray.copy(alpha=0.3f), Offset(0f, scaleCgm(10.0)), Offset(w, scaleCgm(10.0)), strokeWidth=2f)
                    drawLine(Color.LightGray.copy(alpha=0.3f), Offset(0f, scaleCgm(3.9)), Offset(w, scaleCgm(3.9)), strokeWidth=2f)

                    var lastCgm: Offset? = null; var lastMood: Offset? = null
                    val textPaint = android.graphics.Paint().apply { textSize = 18f; isFakeBoldText = true }

                    allTimes.forEachIndexed { i, time ->
                        val x = if (allTimes.size > 1) (i.toFloat() / (allTimes.size - 1)) * (w - 40f) + 20f else w / 2

                        // 交替 Y 轴，防止密集数字互相碰撞
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
                            val textY = if (isEven) y - 10f else y + 20f
                            drawContext.canvas.nativeCanvas.drawText("${cgmNode.value}${cgmNode.trend}", x - 15f, textY, textPaint)
                            lastCgm = current
                        }

                        val moodNode = moodNodes.find { it.timeLabel == time }
                        if (moodNode != null) {
                            val y = scaleMood(moodNode.numericScore)
                            val current = Offset(x, y)
                            if (lastMood != null) drawLine(Color(0xFF7986CB).copy(0.7f), lastMood!!, current, 3f)
                            drawCircle(Color(0xFF7986CB), 4f, current)
                            textPaint.color = android.graphics.Color.parseColor("#7986CB")
                            val textY = if (isEven) y + 15f else y - 15f
                            drawContext.canvas.nativeCanvas.drawText("${moodNode.numericScore}", x + 5f, textY, textPaint)
                            lastMood = current
                        }
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
            Text("💡 提示：在同一时间节点记录的数据越多，关联趋势越精准。\n(Tip: More concurrent data yields better correlation.)", fontSize = 10.sp, color = Color.Gray)
        }
    }
}

@Composable
fun ClinicalCgmCard(report: ClinicalCgmReport, nodes: List<CgmNode>) {
    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            val latest = nodes.lastOrNull()
            if (latest != null) {
                Column(modifier = Modifier.fillMaxWidth().background(Color(0xFFE8F5E9), RoundedCornerShape(8.dp)).padding(12.dp)) {
                    Text("🟢 实时与推算数据 (Real-time & Inferred)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF2E7D32))
                    Spacer(Modifier.height(10.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        MetricItem("当前血糖 (Current BG)", "${latest.value} mmol/L")
                        MetricItem("时间戳 (Time)", latest.timeLabel)
                        MetricItem("采样间隔 (Interval)", latest.samplingInterval)
                    }
                    Spacer(Modifier.height(10.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        MetricItem("推演趋势 (Trend)", latest.trend)
                        MetricItem("推演速率 (Rate)", "${latest.rateOfChange} /min")
                    }
                }
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column { Text("设备状态 (Device)", fontSize = 10.sp, color = Color.Gray); Text(report.deviceInfo, fontSize = 11.sp) }
                Column(horizontalAlignment = Alignment.End) { Text("完整率 (Completeness)", fontSize = 10.sp, color = Color.Gray); Text(report.completeness, fontSize = 11.sp, color = Color(0xFF008080)) }
            }
            HorizontalDivider(color = Color(0xFFF1F1F1))
            Text("📈 统计汇总数据 (Statistical Summary)", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                MetricItem("平均血糖 (Avg BG)", report.avgGlucose); MetricItem("波动 (CV)", report.cv); MetricItem("MAGE", report.mage)
            }
            Column(modifier = Modifier.fillMaxWidth().background(Color(0xFFF1F8E9), RoundedCornerShape(8.dp)).padding(10.dp)) {
                Text("🎯 TIR体系 (Time in Range)", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp).height(12.dp).clip(CircleShape)) {
                    Box(modifier = Modifier.weight(report.tirLow.toFloat()).fillMaxHeight().background(Color(0xFF64B5F6)))
                    Box(modifier = Modifier.weight(report.tirTarget.toFloat()).fillMaxHeight().background(Color(0xFF4CAF50)))
                    Box(modifier = Modifier.weight(report.tirHigh.toFloat()).fillMaxHeight().background(Color(0xFFE57373)))
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("低血糖 TBR: ${report.tbrLevel1}", fontSize = 10.sp); Text("达标 TIR: ${report.tirTarget}%", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Color(0xFF4CAF50)); Text("高血糖 TAR: ${report.tarLevel1}", fontSize = 10.sp)
                }
            }
            Text("📍 AGP 专业走势图 (AGP Trend)", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            Box(modifier = Modifier.fillMaxWidth().height(220.dp).background(Color(0xFFFAFAFA), RoundedCornerShape(8.dp)).padding(8.dp)) {
                // 🔥 密集压缩：点位距离降至 45dp，画笔极细，文本错位防碰撞
                Canvas(modifier = Modifier.fillMaxSize().horizontalScroll(rememberScrollState()).width((nodes.size * 45).coerceAtLeast(350).dp)) {
                    val w = size.width; val h = size.height
                    val maxBG = 15.0; val minBG = 0.0
                    fun scaleY(v: Double) = (h - (v / maxBG) * h).toFloat()
                    drawRect(color = Color(0xFFE8F5E9), topLeft = Offset(0f, scaleY(10.0)), size = Size(w, scaleY(3.9) - scaleY(10.0)))
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
                            val textY = if (isEven) y - 12f else y + 20f
                            val timeY = if (isEven) h - 5f else h - 20f

                            drawContext.canvas.nativeCanvas.drawText(n.timeLabel, x - 15f, timeY, gridPaint)
                            drawContext.canvas.nativeCanvas.drawText("${n.value} ${n.trend}", x - 18f, textY, android.graphics.Paint().apply { textSize = 18f; isFakeBoldText = true; color = android.graphics.Color.DKGRAY })
                        }
                        drawPath(path, Color(0xFF008080), style = Stroke(3f))
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
                Text("🧠 核心情绪指标 (Core Metrics)", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    MetricItem("平均分 (Mean)", report.meanScore); MetricItem("标准差 (SD)", report.variabilitySD); MetricItem("不稳定指数 (Index)", report.instabilityIndex)
                }
                Spacer(Modifier.height(8.dp)); Row(modifier = Modifier.fillMaxWidth().height(10.dp).clip(CircleShape)) {
                Box(modifier = Modifier.weight(report.negativeAffect.toFloat()).fillMaxHeight().background(Color(0xFF7986CB)))
                Box(modifier = Modifier.weight(report.neutralAffect.toFloat()).fillMaxHeight().background(Color(0xFF81C784)))
                Box(modifier = Modifier.weight(report.positiveAffect.toFloat()).fillMaxHeight().background(Color(0xFFFFB74D)))
            }
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("抑郁 PHQ-9: ${report.phq9Score}", fontSize = 10.sp); Text("焦虑 GAD-7: ${report.gad7Score}", fontSize = 10.sp); Text("压力 PSS: ${report.pssScore}", fontSize = 10.sp)
            }
            Box(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                // 🔥 情绪极密化
                Canvas(modifier = Modifier.width((nodes.size * 45).coerceAtLeast(300).dp).height(140.dp)) {
                    val ep = android.graphics.Paint().apply { textSize = 40f }
                    drawContext.canvas.nativeCanvas.drawText("😊", 0f, 40f, ep); drawContext.canvas.nativeCanvas.drawText("😞", 0f, size.height, ep)
                    val p = Path()
                    nodes.forEachIndexed { i, n ->
                        val x = if(nodes.size > 1) (i.toFloat() / (nodes.size - 1)) * (size.width - 40f) + 40f else size.width / 2
                        val y = size.height - (n.numericScore / 10f) * size.height
                        if (i == 0) p.moveTo(x, y) else p.lineTo(x, y)
                        drawCircle(Color(0xFF90CAF9), 6f, Offset(x, y))

                        val isEven = i % 2 == 0
                        val textY = if (isEven) y - 10f else y + 20f
                        drawContext.canvas.nativeCanvas.drawText("${n.numericScore}", x-10f, textY, android.graphics.Paint().apply { textSize = 18f; color = android.graphics.Color.DKGRAY })
                    }
                    drawPath(p, Color(0xFF7986CB), style = Stroke(3f))
                }
            }
            Spacer(Modifier.height(8.dp))
            Text("情绪历史追溯 (Mood Logs) - ${nodes.size} 条/entries", fontSize = 12.sp, color = Color.Gray)
            Column(modifier = Modifier.fillMaxWidth().heightIn(max = 140.dp).verticalScroll(rememberScrollState())) {
                nodes.reversed().forEach { item ->
                    Text("${item.timeLabel} | [${item.numericScore}] ${item.label} : ${item.value}", fontSize = 12.sp, color = Color.DarkGray, modifier = Modifier.padding(vertical = 8.dp))
                    HorizontalDivider(color = Color(0xFFF1F1F1))
                }
            }
        }
    }
}

@Composable
fun <T> HealthListCard(title: String, accent: Color, items: List<T>, onAddClick: () -> Unit, valueSelector: (T) -> Float, textSelector: (T) -> String) {
    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(title, fontWeight = FontWeight.Bold, color = accent, fontSize = 13.sp)
                IconButton(onClick = onAddClick, modifier = Modifier.size(28.dp).background(accent.copy(0.15f), CircleShape)) { Icon(Icons.Default.Add, null, tint = accent, modifier = Modifier.size(18.dp)) }
            }
            Spacer(Modifier.height(12.dp))
            if (items.isNotEmpty()) {
                val chartData = items.reversed()
                Box(modifier = Modifier.fillMaxWidth().height(90.dp).background(Color(0xFFFAFAFA), RoundedCornerShape(8.dp)).padding(8.dp)) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val w = size.width; val h = size.height
                        val maxVal = chartData.maxOfOrNull { valueSelector(it) } ?: 100f; val minVal = chartData.minOfOrNull { valueSelector(it) } ?: 0f
                        val range = if (maxVal == minVal) 1f else (maxVal - minVal)
                        val path = Path()
                        chartData.forEachIndexed { i, item ->
                            val x = if (chartData.size > 1) (i.toFloat() / (chartData.size - 1)) * w else w / 2
                            val y = h - ((valueSelector(item) - minVal) / range) * (h * 0.7f) - (h * 0.15f)
                            if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
                            drawCircle(accent, 6f, Offset(x, y)); drawContext.canvas.nativeCanvas.drawText(valueSelector(item).toInt().toString(), x-10f, y-15f, android.graphics.Paint().apply { textSize=22f; color=android.graphics.Color.DKGRAY })
                        }
                        drawPath(path, accent.copy(0.6f), style = Stroke(4f))
                    }
                }
            } else { Text("暂无数据 (No Data)", color = Color.LightGray, fontSize = 12.sp, modifier = Modifier.padding(vertical = 10.dp)) }
            Spacer(Modifier.height(10.dp))
            Column(modifier = Modifier.fillMaxWidth().heightIn(max = 140.dp).verticalScroll(rememberScrollState())) {
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
fun InputDialog(title: String, l1: String, l2: String, onConfirm: (String, String) -> Unit) {
    var t1 by remember { mutableStateOf("") }; var t2 by remember { mutableStateOf("") }
    AlertDialog(onDismissRequest = {}, title = { Text(title, fontSize = 16.sp, fontWeight = FontWeight.Bold) }, text = { Column(verticalArrangement = Arrangement.spacedBy(12.dp)) { OutlinedTextField(t1, {t1=it}, label={Text(l1, fontSize=12.sp)}, modifier = Modifier.fillMaxWidth()); OutlinedTextField(t2, {t2=it}, label={Text(l2, fontSize=12.sp)}, modifier = Modifier.fillMaxWidth()) } },
        confirmButton = { Button(onClick = {onConfirm(t1,t2)}) { Text("添加 (Add)") } }
    )
}

@Composable
fun TimeScaleSelector(selectedScale: String, onScaleSelect: (String) -> Unit) {
    val scales = listOf("日 (Day)", "月 (Month)", "年 (Year)")
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