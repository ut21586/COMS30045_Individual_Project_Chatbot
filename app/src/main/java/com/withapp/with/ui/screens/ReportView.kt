package com.withapp.with.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.withapp.with.viewmodels.ReportViewModel

@Composable
fun ReportView(reportViewModel: ReportViewModel) {
    val scrollState = rememberScrollState()
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF8FAFC)).verticalScroll(scrollState).statusBarsPadding().padding(horizontal = 20.dp)) {
        Spacer(modifier = Modifier.height(20.dp))
        Text("健康简报", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))

        Text("身心洞察", fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(16.dp))

        reportViewModel.insights.forEach { insight ->
            Surface(modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp), shape = RoundedCornerShape(16.dp), color = Color.White, shadowElevation = 1.dp) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(44.dp).background(Color(0xFFF2F9F7), RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                        Text(insight.icon.ifBlank { "💡" }, fontSize = 20.sp)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(insight.title, fontWeight = FontWeight.Bold, color = Color(0xFF333333))
                        Text(insight.description, fontSize = 13.sp, color = Color.Gray, lineHeight = 18.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text("今日时间线", fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(16.dp))

        reportViewModel.timelineEvents.forEach { event ->
            Surface(modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp), shape = RoundedCornerShape(16.dp), color = Color.White, shadowElevation = 1.dp) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(event.time, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF008080), modifier = Modifier.width(50.dp))
                    Column {
                        Text(event.title, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        Text(event.description, fontSize = 13.sp, color = Color.Gray)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    if (event.glucoseValue != null) {
                        Text("${event.glucoseValue} mg/dL", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF008080))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(100.dp))
    }
}