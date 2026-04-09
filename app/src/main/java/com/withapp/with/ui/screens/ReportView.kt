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
//    val currentScale = reportViewModel.currentScale.value
//
//    val cgmNodes = reportViewModel.cgmNodes
//    val moodNodes = reportViewModel.moodNodes
//    val cgmRep = reportViewModel.cgmReport.value
//    val moodRep = reportViewModel.moodReport.value
//
//    var showDietDialog by remember { mutableStateOf(false) }
//    var showExerciseDialog by remember { mutableStateOf(false) }
//    var showHrDialog by remember { mutableStateOf(false) }
//    var showCgmDialog by remember { mutableStateOf(false) } // 血糖手动弹窗
//
//    Scaffold(
//        topBar = { TopAppBar(title = { Text("高精度全量数据报告", fontWeight = FontWeight.Bold) }, navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) } }) }
//    ) { paddingValues ->
//        Column(modifier = Modifier.fillMaxSize().padding(paddingValues).background(Color(0xFFF7F9FA)).verticalScroll(scrollState).padding(16.dp), verticalArrangement = Arrangement.spacedBy(24.dp)) {
//
//            TimeScaleSelector(selectedScale = currentScale, onScaleSelect = { reportViewModel.currentScale.value = it })
//
//            // 🩸 SECTION 1: 全体系 CGM 包含全新原始数据！
//            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
//                Text("📊 CGM 核心数据总览 (${currentScale})", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF008080))
//                IconButton(onClick = { showCgmDialog = true }, modifier = Modifier.size(28.dp).background(Color(0xFF008080).copy(0.1f), CircleShape)) { Icon(Icons.Default.Add, null, tint = Color(0xFF008080), modifier = Modifier.size(18.dp)) }
//            }
//            ClinicalCgmCard(cgmRep, cgmNodes)
//
//            // 🧠 SECTION 2: 情绪指标
//            Text("🧠 核心情绪指标与量表 (${currentScale})", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFFFFB74D))
//            ClinicalMoodCard(moodRep, moodNodes)
//
//            // 🛡️ SECTION 3: 实时同步历史图表
//            Text("🛡️ 行为/上下文数据走势", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF5C6BC0))
//            HealthListCard("🍏 饮食与胰岛素 (行为数据)", Color(0xFF81C784), reportViewModel.dietLogs.toList(), { showDietDialog = true }, { it.numericValue }, { "${it.time} - ${it.food} (${it.carbs})" })
//            HealthListCard("🏃 运动与睡眠 (影响因子)", Color(0xFF4FC3F7), reportViewModel.exerciseLogs.toList(), { showExerciseDialog = true }, { it.numericValue }, { "${it.time} - ${it.activity} (${it.duration})" })
//            HealthListCard("💓 心率 (BPM)", Color(0xFFE57373), reportViewModel.hrLogs.toList(), { showHrDialog = true }, { it.bpm.toFloat() }, { "${it.time} - BPM: ${it.bpm} (${it.status})" })
//
//            Spacer(Modifier.height(40.dp))
//        }
//
//        // 🚀 终极手动弹窗 (支持 CGM 所有维度的录入)
//        if (showCgmDialog) {
//            var v1 by remember { mutableStateOf("") }; var v2 by remember { mutableStateOf("") }; var v3 by remember { mutableStateOf("") }
//            AlertDialog(onDismissRequest = { showCgmDialog = false }, title = { Text("添加实时CGM原始数据") },
//                text = { Column {
//                    OutlinedTextField(v1, {v1=it}, label={Text("血糖值 (mmol/L)")}); Spacer(Modifier.height(8.dp))
//                    OutlinedTextField(v2, {v2=it}, label={Text("趋势 (↑, →, ↓)")}); Spacer(Modifier.height(8.dp))
//                    OutlinedTextField(v3, {v3=it}, label={Text("变化速率 (mmol/L/min)")})
//                } },
//                confirmButton = { Button(onClick = { reportViewModel.addCgmNode(v1.toDoubleOrNull() ?: 5.5, v2.ifBlank { "→" }, v3.ifBlank { "0.0" }); showCgmDialog = false }) { Text("添加") } }
//            )
//        }
//        if (showDietDialog) InputDialog("添加上下文数据", "项目(如: 餐食/胰岛素)", "数值") { v1, v2 -> reportViewModel.addDiet(v1, v2); showDietDialog = false }
//        if (showExerciseDialog) InputDialog("添加运动", "项目", "时长(min)") { v1, v2 -> reportViewModel.addExercise(v1, v2); showExerciseDialog = false }
//        if (showHrDialog) InputDialog("添加心率", "BPM", "状态") { v1, v2 -> reportViewModel.addHeartRate(v1.toIntOrNull() ?: 75, v2); showHrDialog = false }
//    }
//}
//
//@Composable
//fun ClinicalCgmCard(report: ClinicalCgmReport, nodes: List<CgmNode>) {
//    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White)) {
//        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
//
//            // 🟢 专属展示：实时与原始数据 (Screenshot 22:22:03 对标)
//            val latest = nodes.lastOrNull()
//            if (latest != null) {
//                Column(modifier = Modifier.fillMaxWidth().background(Color(0xFFE8F5E9), RoundedCornerShape(8.dp)).padding(12.dp)) {
//                    Text("🟢 实时与原始数据 (Raw Data)", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF2E7D32))
//                    Spacer(Modifier.height(8.dp))
//                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                        MetricItem("当前血糖值", "${latest.value} mmol/L")
//                        MetricItem("时间戳", latest.timeLabel)
//                        MetricItem("趋势箭头", latest.trend)
//                    }
//                    Spacer(Modifier.height(8.dp))
//                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                        MetricItem("采样时间间隔", latest.samplingInterval)
//                        MetricItem("血糖变化速率", "${latest.rateOfChange} mmol/L/min")
//                    }
//                }
//            }
//
//            // 下方是保留的原始 7 项数据，绝无删减！
//            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                Column { Text("设备状态", fontSize = 10.sp, color = Color.Gray); Text(report.deviceInfo, fontSize = 12.sp) }
//                Column(horizontalAlignment = Alignment.End) { Text("完整率", fontSize = 10.sp, color = Color.Gray); Text(report.completeness, fontSize = 12.sp, color = Color(0xFF008080)) }
//            }
//            HorizontalDivider(color = Color(0xFFF1F1F1))
//            Text("📈 统计汇总数据", fontWeight = FontWeight.Bold, fontSize = 13.sp)
//            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                MetricItem("平均血糖", report.avgGlucose); MetricItem("波动 CV", report.cv); MetricItem("MAGE", report.mage)
//            }
//            Column(modifier = Modifier.fillMaxWidth().background(Color(0xFFF1F8E9), RoundedCornerShape(8.dp)).padding(10.dp)) {
//                Text("🎯 TIR体系", fontWeight = FontWeight.Bold, fontSize = 13.sp)
//                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp).height(12.dp).clip(CircleShape)) {
//                    Box(modifier = Modifier.weight(report.tirLow.toFloat()).fillMaxHeight().background(Color(0xFF64B5F6)))
//                    Box(modifier = Modifier.weight(report.tirTarget.toFloat()).fillMaxHeight().background(Color(0xFF4CAF50)))
//                    Box(modifier = Modifier.weight(report.tirHigh.toFloat()).fillMaxHeight().background(Color(0xFFE57373)))
//                }
//                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                    Text("TBR (<3.9): ${report.tbrLevel1}", fontSize = 10.sp); Text("TIR: ${report.tirTarget}%", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Color(0xFF4CAF50)); Text("TAR (>10): ${report.tarLevel1}", fontSize = 10.sp)
//                }
//            }
//            Text("📍 AGP 曲线", fontWeight = FontWeight.Bold, fontSize = 13.sp)
//            Box(modifier = Modifier.horizontalScroll(rememberScrollState())) {
//                Canvas(modifier = Modifier.width((nodes.size * 100).coerceAtLeast(300).dp).height(150.dp)) {
//                    val w = size.width; val h = size.height; val path = Path()
//                    val maxVal = nodes.maxOfOrNull { it.value } ?: 15.0; val minVal = nodes.minOfOrNull { it.value } ?: 2.0
//                    val range = if (maxVal == minVal) 1.0 else (maxVal - minVal)
//                    nodes.forEachIndexed { i, n ->
//                        val x = if(nodes.size > 1) (i.toFloat() / (nodes.size - 1)) * w else w/2
//                        val y = h - (((n.value - minVal) / range) * (h * 0.6) + h * 0.2).toFloat()
//                        if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
//                        drawCircle(Color(0xFF008080), 8f, Offset(x, y))
//                        drawContext.canvas.nativeCanvas.drawText("${n.value} ${n.trend}", x-20f, y-20f, android.graphics.Paint().apply { textSize = 28f; isFakeBoldText = true })
//                    }
//                    drawPath(path, Color(0xFF008080), style = Stroke(4f))
//                }
//            }
//            Text("⚠️ 事件与警报: 低血糖 ${report.hypoEvents} 次 | 警报 ${report.alertsTriggered} 次", fontSize = 11.sp, color = Color(0xFFE57373))
//        }
//    }
//}
//
//// ---- 下面是保留的无损情绪卡片与动态图表列表 ----
//@Composable
//fun ClinicalMoodCard(report: ClinicalMoodReport, nodes: List<MoodLog>) {
//    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White)) {
//        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
//            Column(modifier = Modifier.fillMaxWidth().background(Color(0xFFFFF8E1), RoundedCornerShape(8.dp)).padding(12.dp)) {
//                Text("🧠 核心情绪指标", fontWeight = FontWeight.Bold, fontSize = 14.sp)
//                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                    MetricItem("Mean", report.meanScore); MetricItem("SD", report.variabilitySD); MetricItem("Index", report.instabilityIndex)
//                }
//                Spacer(Modifier.height(8.dp)); Row(modifier = Modifier.fillMaxWidth().height(10.dp).clip(CircleShape)) {
//                Box(modifier = Modifier.weight(report.negativeAffect.toFloat()).fillMaxHeight().background(Color(0xFF7986CB)))
//                Box(modifier = Modifier.weight(report.neutralAffect.toFloat()).fillMaxHeight().background(Color(0xFF81C784)))
//                Box(modifier = Modifier.weight(report.positiveAffect.toFloat()).fillMaxHeight().background(Color(0xFFFFB74D)))
//            }
//            }
//            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                Text("PHQ-9: ${report.phq9Score}", fontSize = 11.sp); Text("GAD-7: ${report.gad7Score}", fontSize = 11.sp); Text("PSS: ${report.pssScore}", fontSize = 11.sp)
//            }
//            Box(modifier = Modifier.horizontalScroll(rememberScrollState())) {
//                Canvas(modifier = Modifier.width(800.dp).height(140.dp)) {
//                    val ep = android.graphics.Paint().apply { textSize = 40f }
//                    drawContext.canvas.nativeCanvas.drawText("😊", 0f, 40f, ep); drawContext.canvas.nativeCanvas.drawText("😞", 0f, size.height, ep)
//                    val p = Path()
//                    nodes.forEachIndexed { i, n ->
//                        val x = (i.toFloat() / nodes.size) * size.width + 60f
//                        val y = size.height - (n.numericScore / 10f) * size.height
//                        if (i == 0) p.moveTo(x, y) else p.lineTo(x, y)
//                        drawCircle(Color(0xFF90CAF9), 10f, Offset(x, y))
//                    }
//                    drawPath(p, Color(0xFF7986CB), style = Stroke(6f))
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
//                Text(title, fontWeight = FontWeight.Bold, color = accent, fontSize = 14.sp)
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
//                            drawCircle(accent, 6f, Offset(x, y)); drawContext.canvas.nativeCanvas.drawText(valueSelector(item).toInt().toString(), x-10f, y-15f, android.graphics.Paint().apply { textSize=22f; color=android.graphics.Color.DKGRAY })
//                        }
//                        drawPath(path, accent.copy(0.6f), style = Stroke(4f))
//                    }
//                }
//            } else { Text("暂无数据", color = Color.LightGray, fontSize = 12.sp, modifier = Modifier.padding(vertical = 10.dp)) }
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
//    Column { Text(label, fontSize = 10.sp, color = Color.Gray); Text(value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF008080)) }
//}
//
//@Composable
//fun InputDialog(title: String, l1: String, l2: String, onConfirm: (String, String) -> Unit) {
//    var t1 by remember { mutableStateOf("") }; var t2 by remember { mutableStateOf("") }
//    AlertDialog(onDismissRequest = {}, title = { Text(title) }, text = { Column { OutlinedTextField(t1, {t1=it}, label={Text(l1)}); Spacer(Modifier.height(8.dp)); OutlinedTextField(t2, {t2=it}, label={Text(l2)}) } },
//        confirmButton = { Button(onClick = {onConfirm(t1,t2)}) { Text("添加") } }
//    )
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
//                    Text(s, fontSize = 14.sp, fontWeight = if(isSelected) FontWeight.Bold else FontWeight.Normal, color = if(isSelected) Color(0xFF008080) else Color.Gray)
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

    var showDietDialog by remember { mutableStateOf(false) }
    var showExerciseDialog by remember { mutableStateOf(false) }
    var showHrDialog by remember { mutableStateOf(false) }
    var showCgmDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("高精度全量数据报告", fontWeight = FontWeight.Bold) }, navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) } }) }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues).background(Color(0xFFF7F9FA)).verticalScroll(scrollState).padding(16.dp), verticalArrangement = Arrangement.spacedBy(24.dp)) {

            TimeScaleSelector(selectedScale = currentScale, onScaleSelect = { reportViewModel.currentScale.value = it })

            // 🩸 SECTION 1: 全体系 CGM
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("📊 CGM 核心数据总览 (${currentScale})", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF008080))
                IconButton(onClick = { showCgmDialog = true }, modifier = Modifier.size(28.dp).background(Color(0xFF008080).copy(0.1f), CircleShape)) { Icon(Icons.Default.Add, null, tint = Color(0xFF008080), modifier = Modifier.size(18.dp)) }
            }
            ClinicalCgmCard(cgmRep, cgmNodes)

            // 🧠 SECTION 2: 情绪指标
            Text("🧠 核心情绪指标与量表 (${currentScale})", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFFFFB74D))
            ClinicalMoodCard(moodRep, moodNodes)

            // 🛡️ SECTION 3: 实时同步历史图表
            Text("🛡️ 行为/上下文数据走势", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF5C6BC0))
            HealthListCard("🍏 饮食与胰岛素 (行为数据)", Color(0xFF81C784), reportViewModel.dietLogs.toList(), { showDietDialog = true }, { it.numericValue }, { "${it.time} - ${it.food} (${it.carbs})" })
            HealthListCard("🏃 运动与睡眠 (影响因子)", Color(0xFF4FC3F7), reportViewModel.exerciseLogs.toList(), { showExerciseDialog = true }, { it.numericValue }, { "${it.time} - ${it.activity} (${it.duration})" })
            HealthListCard("💓 心率 (BPM)", Color(0xFFE57373), reportViewModel.hrLogs.toList(), { showHrDialog = true }, { it.bpm.toFloat() }, { "${it.time} - BPM: ${it.bpm} (${it.status})" })

            Spacer(Modifier.height(40.dp))
        }

        // 🚀 终极修复：完美提供 5 个输入框的弹窗
        if (showCgmDialog) {
            var v1 by remember { mutableStateOf("") } // 血糖
            var v2 by remember { mutableStateOf("") } // 时间戳
            var v3 by remember { mutableStateOf("") } // 采样间隔
            var v4 by remember { mutableStateOf("") } // 趋势
            var v5 by remember { mutableStateOf("") } // 变化速率

            AlertDialog(onDismissRequest = { showCgmDialog = false },
                title = { Text("添加实时CGM原始数据", fontWeight = FontWeight.Bold) },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(v1, {v1=it}, label={Text("当前血糖值 (Glucose)")}, modifier = Modifier.fillMaxWidth())
                        OutlinedTextField(v2, {v2=it}, label={Text("时间戳 (Timestamp)")}, modifier = Modifier.fillMaxWidth())
                        OutlinedTextField(v3, {v3=it}, label={Text("采样时间间隔 (Interval)")}, modifier = Modifier.fillMaxWidth())
                        OutlinedTextField(v4, {v4=it}, label={Text("趋势箭头 (Trend)")}, modifier = Modifier.fillMaxWidth())
                        OutlinedTextField(v5, {v5=it}, label={Text("血糖变化速率 (Rate)")}, modifier = Modifier.fillMaxWidth())
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        reportViewModel.addCgmNode(
                            v1.toDoubleOrNull() ?: 5.5,
                            v2, v3,
                            v4.ifBlank { "→" },
                            v5.ifBlank { "0.0" }
                        )
                        showCgmDialog = false
                    }) { Text("添加") }
                }
            )
        }
        if (showDietDialog) InputDialog("添加上下文数据", "项目", "数值") { v1, v2 -> reportViewModel.addDiet(v1, v2); showDietDialog = false }
        if (showExerciseDialog) InputDialog("添加运动", "项目", "时长(min)") { v1, v2 -> reportViewModel.addExercise(v1, v2); showExerciseDialog = false }
        if (showHrDialog) InputDialog("添加心率", "BPM", "状态") { v1, v2 -> reportViewModel.addHeartRate(v1.toIntOrNull() ?: 75, v2); showHrDialog = false }
    }
}

@Composable
fun ClinicalCgmCard(report: ClinicalCgmReport, nodes: List<CgmNode>) {
    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {

            val latest = nodes.lastOrNull()
            if (latest != null) {
                Column(modifier = Modifier.fillMaxWidth().background(Color(0xFFE8F5E9), RoundedCornerShape(8.dp)).padding(12.dp)) {
                    Text("🟢 实时与原始数据 (Raw Data)", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF2E7D32))
                    Spacer(Modifier.height(10.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        MetricItem("当前血糖值", "${latest.value} mmol/L")
                        MetricItem("时间戳", latest.timeLabel)
                        MetricItem("采样间隔", latest.samplingInterval)
                    }
                    Spacer(Modifier.height(10.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        MetricItem("趋势箭头", latest.trend)
                        MetricItem("血糖变化速率", "${latest.rateOfChange} mmol/L/min")
                    }
                }
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column { Text("设备状态", fontSize = 10.sp, color = Color.Gray); Text(report.deviceInfo, fontSize = 12.sp) }
                Column(horizontalAlignment = Alignment.End) { Text("完整率", fontSize = 10.sp, color = Color.Gray); Text(report.completeness, fontSize = 12.sp, color = Color(0xFF008080)) }
            }
            HorizontalDivider(color = Color(0xFFF1F1F1))
            Text("📈 统计汇总数据", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                MetricItem("平均血糖", report.avgGlucose); MetricItem("波动 CV", report.cv); MetricItem("MAGE", report.mage)
            }
            Column(modifier = Modifier.fillMaxWidth().background(Color(0xFFF1F8E9), RoundedCornerShape(8.dp)).padding(10.dp)) {
                Text("🎯 TIR体系", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp).height(12.dp).clip(CircleShape)) {
                    Box(modifier = Modifier.weight(report.tirLow.toFloat()).fillMaxHeight().background(Color(0xFF64B5F6)))
                    Box(modifier = Modifier.weight(report.tirTarget.toFloat()).fillMaxHeight().background(Color(0xFF4CAF50)))
                    Box(modifier = Modifier.weight(report.tirHigh.toFloat()).fillMaxHeight().background(Color(0xFFE57373)))
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("TBR (<3.9): ${report.tbrLevel1}", fontSize = 10.sp); Text("TIR: ${report.tirTarget}%", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Color(0xFF4CAF50)); Text("TAR (>10): ${report.tarLevel1}", fontSize = 10.sp)
                }
            }
            Text("📍 AGP 曲线", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            Box(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                Canvas(modifier = Modifier.width((nodes.size * 100).coerceAtLeast(300).dp).height(150.dp)) {
                    val w = size.width; val h = size.height; val path = Path()
                    val maxVal = nodes.maxOfOrNull { it.value } ?: 15.0; val minVal = nodes.minOfOrNull { it.value } ?: 2.0
                    val range = if (maxVal == minVal) 1.0 else (maxVal - minVal)
                    nodes.forEachIndexed { i, n ->
                        val x = if(nodes.size > 1) (i.toFloat() / (nodes.size - 1)) * w else w/2
                        val y = h - (((n.value - minVal) / range) * (h * 0.6) + h * 0.2).toFloat()
                        if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
                        drawCircle(Color(0xFF008080), 8f, Offset(x, y))
                        drawContext.canvas.nativeCanvas.drawText("${n.value} ${n.trend}", x-20f, y-20f, android.graphics.Paint().apply { textSize = 28f; isFakeBoldText = true })
                    }
                    drawPath(path, Color(0xFF008080), style = Stroke(4f))
                }
            }
            Text("⚠️ 事件与警报: 低血糖 ${report.hypoEvents} 次 | 警报 ${report.alertsTriggered} 次", fontSize = 11.sp, color = Color(0xFFE57373))
        }
    }
}

@Composable
fun ClinicalMoodCard(report: ClinicalMoodReport, nodes: List<MoodLog>) {
    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Column(modifier = Modifier.fillMaxWidth().background(Color(0xFFFFF8E1), RoundedCornerShape(8.dp)).padding(12.dp)) {
                Text("🧠 核心情绪指标", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    MetricItem("Mean", report.meanScore); MetricItem("SD", report.variabilitySD); MetricItem("Index", report.instabilityIndex)
                }
                Spacer(Modifier.height(8.dp)); Row(modifier = Modifier.fillMaxWidth().height(10.dp).clip(CircleShape)) {
                Box(modifier = Modifier.weight(report.negativeAffect.toFloat()).fillMaxHeight().background(Color(0xFF7986CB)))
                Box(modifier = Modifier.weight(report.neutralAffect.toFloat()).fillMaxHeight().background(Color(0xFF81C784)))
                Box(modifier = Modifier.weight(report.positiveAffect.toFloat()).fillMaxHeight().background(Color(0xFFFFB74D)))
            }
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("PHQ-9: ${report.phq9Score}", fontSize = 11.sp); Text("GAD-7: ${report.gad7Score}", fontSize = 11.sp); Text("PSS: ${report.pssScore}", fontSize = 11.sp)
            }
            Box(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                Canvas(modifier = Modifier.width(800.dp).height(140.dp)) {
                    val ep = android.graphics.Paint().apply { textSize = 40f }
                    drawContext.canvas.nativeCanvas.drawText("😊", 0f, 40f, ep); drawContext.canvas.nativeCanvas.drawText("😞", 0f, size.height, ep)
                    val p = Path()
                    nodes.forEachIndexed { i, n ->
                        val x = (i.toFloat() / nodes.size) * size.width + 60f
                        val y = size.height - (n.numericScore / 10f) * size.height
                        if (i == 0) p.moveTo(x, y) else p.lineTo(x, y)
                        drawCircle(Color(0xFF90CAF9), 10f, Offset(x, y))
                    }
                    drawPath(p, Color(0xFF7986CB), style = Stroke(6f))
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
                Text(title, fontWeight = FontWeight.Bold, color = accent, fontSize = 14.sp)
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
            } else { Text("暂无数据", color = Color.LightGray, fontSize = 12.sp, modifier = Modifier.padding(vertical = 10.dp)) }
            Spacer(Modifier.height(10.dp))
            Column(modifier = Modifier.fillMaxWidth().heightIn(max = 140.dp).verticalScroll(rememberScrollState())) {
                items.forEach { item -> Text(textSelector(item), fontSize = 12.sp, color = Color.DarkGray, modifier = Modifier.padding(vertical = 6.dp)); HorizontalDivider(color = Color(0xFFF1F1F1)) }
            }
        }
    }
}

@Composable
fun MetricItem(label: String, value: String) {
    Column { Text(label, fontSize = 10.sp, color = Color.Gray); Text(value, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF008080)) }
}

@Composable
fun InputDialog(title: String, l1: String, l2: String, onConfirm: (String, String) -> Unit) {
    var t1 by remember { mutableStateOf("") }; var t2 by remember { mutableStateOf("") }
    AlertDialog(onDismissRequest = {}, title = { Text(title) }, text = { Column(verticalArrangement = Arrangement.spacedBy(12.dp)) { OutlinedTextField(t1, {t1=it}, label={Text(l1)}, modifier = Modifier.fillMaxWidth()); OutlinedTextField(t2, {t2=it}, label={Text(l2)}, modifier = Modifier.fillMaxWidth()) } },
        confirmButton = { Button(onClick = {onConfirm(t1,t2)}) { Text("添加") } }
    )
}

@Composable
fun TimeScaleSelector(selectedScale: String, onScaleSelect: (String) -> Unit) {
    val scales = listOf("日", "月", "年")
    Surface(modifier = Modifier.fillMaxWidth().height(40.dp), shape = RoundedCornerShape(20.dp), color = Color(0xFFE0E0E0)) {
        Row {
            scales.forEach { s ->
                val isSelected = selectedScale == s
                Box(modifier = Modifier.weight(1f).fillMaxHeight().padding(2.dp).background(if(isSelected) Color.White else Color.Transparent, RoundedCornerShape(18.dp)).clickable { onScaleSelect(s) }, contentAlignment = Alignment.Center) {
                    Text(s, fontSize = 14.sp, fontWeight = if(isSelected) FontWeight.Bold else FontWeight.Normal, color = if(isSelected) Color(0xFF008080) else Color.Gray)
                }
            }
        }
    }
}