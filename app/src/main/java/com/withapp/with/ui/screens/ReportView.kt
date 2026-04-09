//
//package com.withapp.with.ui.screens
//
//import androidx.compose.foundation.*
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
//    // Scale-based Data Binding (Strictly from ViewModel)
//    val cgmRep = if(currentScale == "月") reportViewModel.monthlyCgmRep else reportViewModel.dailyCgmRep
//    val moodRep = if(currentScale == "月") reportViewModel.monthlyMoodRep else reportViewModel.dailyMoodRep
//    val cgmNodes = if(currentScale == "月") reportViewModel.monthlyCgmNodes else reportViewModel.dailyCgmNodes
//    val moodNodes = if(currentScale == "月") reportViewModel.monthlyMoodNodes else reportViewModel.dailyMoodNodes
//
//    var showDietDialog by remember { mutableStateOf(false) }
//    var showExerciseDialog by remember { mutableStateOf(false) }
//    var showHrDialog by remember { mutableStateOf(false) }
//
//    Scaffold(
//        topBar = { TopAppBar(title = { Text("高精度全量数据报告", fontWeight = FontWeight.Bold) }, navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) } }) }
//    ) { paddingValues ->
//        Column(modifier = Modifier.fillMaxSize().padding(paddingValues).background(Color(0xFFF7F9FA)).verticalScroll(scrollState).padding(16.dp), verticalArrangement = Arrangement.spacedBy(24.dp)) {
//
//            TimeScaleSelector(selectedScale = currentScale, onScaleSelect = { reportViewModel.currentScale.value = it })
//
//            // 🚀 PART 1: COMPLETE 7-SECTION CGM REPORT
//            Text("📊 CGM 医学标准报告 (${currentScale})", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF008080))
//            ClinicalCgmCard(cgmRep, cgmNodes)
//
//            // 🚀 PART 2: COMPLETE CLINICAL MOOD DASHBOARD (Metrics + Emojis)
//            Text("🧠 核心情绪指标与量表 (${currentScale})", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFFFFB74D))
//            ClinicalMoodCard(moodRep, moodNodes)
//
//            // 🚀 PART 3: INTERACTIVE HEALTH RECORDS
//            Text("🛡️ 实时健康记录 (点击弹出添加)", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF5C6BC0))
//            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//                // FIXED Weight logic
//                HealthMiniCard(modifier = Modifier.weight(1f), title = "🍏 饮食", value = "${reportViewModel.dietLogs.size} 条", accent = Color(0xFF81C784)) { showDietDialog = true }
//                HealthMiniCard(modifier = Modifier.weight(1f), title = "🏃 运动", value = "${reportViewModel.exerciseLogs.size} 次", accent = Color(0xFF4FC3F7)) { showExerciseDialog = true }
//                HealthMiniCard(modifier = Modifier.weight(1f), title = "💓 心率", value = if(reportViewModel.hrLogs.isNotEmpty()) "${reportViewModel.hrLogs.first().bpm}" else "--", accent = Color(0xFFE57373)) { showHrDialog = true }
//            }
//            Spacer(Modifier.height(40.dp))
//        }
//
//        if (showDietDialog) InputDialog("添加饮食记录", "内容", "碳水(g)") { v1, v2 -> reportViewModel.addDiet(v1, v2); showDietDialog = false }
//        if (showExerciseDialog) InputDialog("添加运动记录", "项目", "时长") { v1, v2 -> reportViewModel.addExercise(v1, v2); showExerciseDialog = false }
//        if (showHrDialog) InputDialog("记录心率", "BPM", "状态") { v1, v2 -> reportViewModel.addHeartRate(v1.toIntOrNull() ?: 75, v2); showHrDialog = false }
//    }
//}
//
//@Composable
//fun ClinicalCgmCard(report: ClinicalCgmReport, nodes: List<CgmNode>) {
//    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White)) {
//        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
//            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                Column { Text("1. 设备信息", fontSize = 10.sp, color = Color.Gray); Text(report.deviceInfo, fontSize = 12.sp) }
//                Column(horizontalAlignment = Alignment.End) { Text("2. 质量", fontSize = 10.sp, color = Color.Gray); Text(report.dataCoverage, fontSize = 12.sp, color = Color(0xFF008080)) }
//            }
//            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                MetricItem("平均血糖", report.avgGlucose); MetricItem("GMI 糖化", report.gmi); MetricItem("波动 CV", report.cv)
//            }
//            Row(modifier = Modifier.fillMaxWidth().height(12.dp).clip(CircleShape)) {
//                Box(modifier = Modifier.weight(report.tirLow.toFloat()).fillMaxHeight().background(Color(0xFF64B5F6)))
//                Box(modifier = Modifier.weight(report.tirTarget.toFloat()).fillMaxHeight().background(Color(0xFF4CAF50)))
//                Box(modifier = Modifier.weight(report.tirHigh.toFloat()).fillMaxHeight().background(Color(0xFFE57373)))
//            }
//            Box(modifier = Modifier.horizontalScroll(rememberScrollState())) {
//                Canvas(modifier = Modifier.width(1000.dp).height(160.dp)) {
//                    val w = size.width; val h = size.height; val path = Path()
//                    nodes.forEachIndexed { i, n ->
//                        val x = (i.toFloat() / nodes.size) * w + 50f; val y = h - (n.value.toFloat() / 15f) * h
//                        if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
//                        drawCircle(Color(0xFF008080), 8f, Offset(x, y))
//                        drawContext.canvas.nativeCanvas.drawText("${n.value}", x-20f, y-20f, android.graphics.Paint().apply { textSize = 32f; isFakeBoldText = true })
//                    }
//                    drawPath(path, Color(0xFF008080), style = Stroke(5f))
//                }
//            }
//            Text("7. 临床解读: ${report.clinicalAdvice}", fontSize = 12.sp, color = Color.DarkGray, modifier = Modifier.background(Color(0xFFF1F8E9), RoundedCornerShape(4.dp)).padding(8.dp))
//        }
//    }
//}
//
//@Composable
//fun ClinicalMoodCard(report: ClinicalMoodReport, nodes: List<MoodLog>) {
//    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White)) {
//        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
//            Column(modifier = Modifier.fillMaxWidth().background(Color(0xFFFFF8E1), RoundedCornerShape(8.dp)).padding(12.dp)) {
//                Text("🧠 四、核心情绪指标", fontWeight = FontWeight.Bold, fontSize = 14.sp)
//                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                    MetricItem("Mean Score", report.meanScore); MetricItem("SD", report.variabilitySD); MetricItem("Index", report.instabilityIndex)
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
//// --- SHARED UI HELPERS ---
//@Composable
//fun HealthMiniCard(modifier: Modifier = Modifier, title: String, value: String, accent: Color, onClick: () -> Unit) {
//    Card(modifier = modifier.clickable { onClick() }, colors = CardDefaults.cardColors(containerColor = Color.White)) {
//        Column(modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
//            Text(title, fontSize = 11.sp, fontWeight = FontWeight.Bold); Text(value, fontSize = 14.sp, color = accent, fontWeight = FontWeight.ExtraBold)
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

    // SCALE BINDING (Day/Month switcher)
    val cgmRep = if(currentScale == "月") reportViewModel.monthlyCgmRep else reportViewModel.dailyCgmRep
    val moodRep = if(currentScale == "月") reportViewModel.monthlyMoodRep else reportViewModel.dailyMoodRep
    val cgmNodes = if(currentScale == "月") reportViewModel.monthlyCgmNodes else reportViewModel.dailyCgmNodes
    val moodNodes = if(currentScale == "月") reportViewModel.monthlyMoodNodes else reportViewModel.dailyMoodNodes

    var showDietDialog by remember { mutableStateOf(false) }
    var showExerciseDialog by remember { mutableStateOf(false) }
    var showHrDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("高精度全量数据报告", fontWeight = FontWeight.Bold) }, navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) } }) }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues).background(Color(0xFFF7F9FA)).verticalScroll(scrollState).padding(16.dp), verticalArrangement = Arrangement.spacedBy(24.dp)) {

            TimeScaleSelector(selectedScale = currentScale, onScaleSelect = { reportViewModel.currentScale.value = it })

            // 🚀 板块一：CGM 医疗标准报告 (全量 1-7 部分)
            Text("📊 CGM 医学标准报告 (${currentScale})", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF008080))
            ClinicalCgmCard(cgmRep, cgmNodes)

            // 🚀 板块二：情绪指标与量表 (全量 10 指标 + 量表 + Emoji 轴)
            Text("🧠 核心情绪指标与量表 (${currentScale})", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFFFFB74D))
            ClinicalMoodCard(moodRep, moodNodes)

            // 🚀 板块三：实时健康记录 (饮食/运动/心率卡片)
            Text("🛡️ 实时健康记录 (点击弹出添加)", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF5C6BC0))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                // FIXED Weight by passing explicit modifier
                HealthMiniCard(modifier = Modifier.weight(1f), title = "🍏 饮食", value = "${reportViewModel.dietLogs.size} 条", accent = Color(0xFF81C784)) { showDietDialog = true }
                HealthMiniCard(modifier = Modifier.weight(1f), title = "🏃 运动", value = "${reportViewModel.exerciseLogs.size} 次", accent = Color(0xFF4FC3F7)) { showExerciseDialog = true }
                HealthMiniCard(modifier = Modifier.weight(1f), title = "💓 心率", value = if(reportViewModel.hrLogs.isNotEmpty()) "${reportViewModel.hrLogs.first().bpm}" else "--", accent = Color(0xFFE57373)) { showHrDialog = true }
            }
            Spacer(Modifier.height(40.dp))
        }

        if (showDietDialog) InputDialog("饮食记录", "内容", "碳水(g)") { v1, v2 -> reportViewModel.addDiet(v1, v2); showDietDialog = false }
        if (showExerciseDialog) InputDialog("运动记录", "项目", "时长") { v1, v2 -> reportViewModel.addExercise(v1, v2); showExerciseDialog = false }
        if (showHrDialog) InputDialog("记录心率", "BPM", "状态") { v1, v2 -> reportViewModel.addHeartRate(v1.toIntOrNull() ?: 75, v2); showHrDialog = false }
    }
}

@Composable
fun ClinicalCgmCard(report: ClinicalCgmReport, nodes: List<CgmNode>) {
    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column { Text("1. 患者设备", fontSize = 10.sp, color = Color.Gray); Text(report.deviceInfo, fontSize = 12.sp) }
                Column(horizontalAlignment = Alignment.End) { Text("2. 质量", fontSize = 10.sp, color = Color.Gray); Text(report.dataCoverage, fontSize = 12.sp, color = Color(0xFF008080)) }
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                MetricItem("平均血糖", report.avgGlucose); MetricItem("GMI 糖化", report.gmi); MetricItem("波动 CV", report.cv)
            }
            Row(modifier = Modifier.fillMaxWidth().height(12.dp).clip(CircleShape)) {
                Box(modifier = Modifier.weight(report.tirLow.toFloat()).fillMaxHeight().background(Color(0xFF64B5F6)))
                Box(modifier = Modifier.weight(report.tirTarget.toFloat()).fillMaxHeight().background(Color(0xFF4CAF50)))
                Box(modifier = Modifier.weight(report.tirHigh.toFloat()).fillMaxHeight().background(Color(0xFFE57373)))
            }
            Box(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                Canvas(modifier = Modifier.width(1000.dp).height(160.dp)) {
                    val w = size.width; val h = size.height; val path = Path()
                    nodes.forEachIndexed { i, n ->
                        val x = (i.toFloat() / nodes.size) * w + 50f; val y = h - (n.value.toFloat() / 15f) * h
                        if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
                        drawCircle(Color(0xFF008080), 8f, Offset(x, y))
                        drawContext.canvas.nativeCanvas.drawText("${n.value}", x-20f, y-20f, android.graphics.Paint().apply { textSize = 32f; isFakeBoldText = true })
                    }
                    drawPath(path, Color(0xFF008080), style = Stroke(5f))
                }
            }
            Text("7. 临床建议: ${report.clinicalAdvice}", fontSize = 12.sp, color = Color.DarkGray, modifier = Modifier.background(Color(0xFFF1F8E9), RoundedCornerShape(4.dp)).padding(8.dp))
        }
    }
}

@Composable
fun ClinicalMoodCard(report: ClinicalMoodReport, nodes: List<MoodLog>) {
    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Column(modifier = Modifier.fillMaxWidth().background(Color(0xFFFFF8E1), RoundedCornerShape(8.dp)).padding(12.dp)) {
                Text("🧠 四、核心情绪指标", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    MetricItem("Mean Score", report.meanScore); MetricItem("SD 波动", report.variabilitySD); MetricItem("不稳定指数", report.instabilityIndex)
                }
                Spacer(Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth().height(10.dp).clip(CircleShape)) {
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
                    drawContext.canvas.nativeCanvas.drawText("😊", 0f, 40f, ep)
                    drawContext.canvas.nativeCanvas.drawText("😞", 0f, size.height, ep)
                    val p = Path()
                    nodes.forEachIndexed { i, n ->
                        val x = (i.toFloat() / nodes.size) * size.width + 60f
                        val y = size.height - (n.numericScore / 10f) * size.height
                        if (i == 0) p.moveTo(x, y) else p.lineTo(x, y)
                        drawCircle(Color(0xFF90CAF9), 10f, Offset(x, y))
                        drawContext.canvas.nativeCanvas.drawText(n.numericScore.toString(), x-20f, y-20f, android.graphics.Paint().apply { textSize = 28f })
                    }
                    drawPath(p, Color(0xFF7986CB), style = Stroke(6f))
                }
            }
        }
    }
}

@Composable
fun HealthMiniCard(modifier: Modifier = Modifier, title: String, value: String, accent: Color, onClick: () -> Unit) {
    Card(modifier = modifier.clickable { onClick() }, colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(title, fontSize = 11.sp, fontWeight = FontWeight.Bold); Text(value, fontSize = 14.sp, color = accent, fontWeight = FontWeight.ExtraBold)
        }
    }
}

@Composable
fun MetricItem(label: String, value: String) {
    Column { Text(label, fontSize = 10.sp, color = Color.Gray); Text(value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF008080)) }
}

@Composable
fun InputDialog(title: String, l1: String, l2: String, onConfirm: (String, String) -> Unit) {
    var t1 by remember { mutableStateOf("") }; var t2 by remember { mutableStateOf("") }
    AlertDialog(onDismissRequest = {}, title = { Text(title) }, text = { Column { OutlinedTextField(t1, {t1=it}, label={Text(l1)}); Spacer(Modifier.height(8.dp)); OutlinedTextField(t2, {t2=it}, label={Text(l2)}) } },
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