
package com.withapp.with.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.withapp.with.viewmodels.ReportViewModel

@Composable
fun ReportView(reportViewModel: ReportViewModel) {
    val scrollState = rememberScrollState()
    var selectedTimeRange by remember { mutableStateOf("日") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .verticalScroll(scrollState)
            .statusBarsPadding()
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        // 1. Title & Segmented Control (iOS Style)
        Text("健康简报", fontSize = 28.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(20.dp))

        // Mocking iOS Segmented Control
        Surface(
            modifier = Modifier.fillMaxWidth().height(40.dp),
            shape = RoundedCornerShape(10.dp),
            color = Color(0xFFEEEEEE)
        ) {
            Row(modifier = Modifier.fillMaxSize().padding(2.dp)) {
                listOf("日", "周", "月").forEach { range ->
                    val isSelected = selectedTimeRange == range
                    Surface(
                        modifier = Modifier.weight(1f).fillMaxHeight().clickable { selectedTimeRange = range },
                        shape = RoundedCornerShape(8.dp),
                        color = if (isSelected) Color.White else Color.Transparent,
                        shadowElevation = if (isSelected) 2.dp else 0.dp
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(range, fontSize = 13.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 2. The iOS-Style Smooth Chart
        Text("血糖与情绪趋势", fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(16.dp))

        Surface(
            modifier = Modifier.fillMaxWidth().height(220.dp),
            shape = RoundedCornerShape(20.dp),
            color = Color.White,
            shadowElevation = 2.dp
        ) {
            Box(modifier = Modifier.padding(top = 40.dp, bottom = 20.dp, start = 10.dp, end = 10.dp)) {
                GlucoseSmoothChart(data = reportViewModel.glucoseData)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // 3. Insight Section
        Text("身心洞察", fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(16.dp))

        reportViewModel.insights.forEach { insight ->
            Surface(
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                shadowElevation = 1.dp
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(44.dp).background(Color(0xFFF2F9F7), RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                        Text(insight.icon, fontSize = 20.sp)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(insight.title, fontWeight = FontWeight.Bold, color = Color(0xFF333333))
                        Text(insight.description, fontSize = 13.sp, color = Color.Gray, lineHeight = 18.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Composable
fun GlucoseSmoothChart(data: List<Float>) {
    val tealColor = Color(0xFF008080)

    Spacer(
        modifier = Modifier
            .fillMaxSize()
            .drawWithCache {
                val path = Path()
                val xStep = size.width / (data.size - 1)
                val maxVal = 10f // Max glucose for scaling
                val minVal = 4f

                onDrawBehind {
                    // Draw Grid Lines (iOS style subtle lines)
                    repeat(3) { i ->
                        val y = size.height * (i / 2f)
                        drawLine(Color.LightGray.copy(0.3f), Offset(0f, y), Offset(size.width, y), strokeWidth = 1.dp.toPx())
                    }

                    // Create smooth curve path
                    data.forEachIndexed { i, value ->
                        val x = i * xStep
                        val y = size.height - ((value - minVal) / (maxVal - minVal) * size.height)
                        if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
                    }

                    // Draw the line
                    drawPath(path, color = tealColor, style = Stroke(width = 3.dp.toPx()))

                    // FIXED: Use pure Compose Path for the gradient fill, avoiding Android Graphics conversion
                    val fillPath = Path()
                    fillPath.addPath(path)
                    fillPath.lineTo(size.width, size.height)
                    fillPath.lineTo(0f, size.height)
                    fillPath.close()

                    drawPath(
                        fillPath,
                        brush = Brush.verticalGradient(listOf(tealColor.copy(0.2f), Color.Transparent))
                    )
                }
            }
    )
}