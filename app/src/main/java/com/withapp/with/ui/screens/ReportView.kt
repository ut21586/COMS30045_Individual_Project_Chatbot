//
//package com.withapp.with.ui.screens
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color // CRITICAL: Added the missing Color import
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.withapp.with.viewmodels.ReportViewModel
//
//@Composable
//fun ReportView(
//    reportViewModel: ReportViewModel // Shared ViewModel injected from MainContainer
//) {
//    val scrollState = rememberScrollState()
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color(0xFFF8FAFC)) // Using fixed hex color for background
//            .verticalScroll(scrollState)
//            .statusBarsPadding()
//            .padding(20.dp)
//    ) {
//        // Report header in Chinese
//        Text(
//            text = "健康简报",
//            fontSize = 28.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color.Black
//        )
//
//        Text(
//            text = "查看你的血糖与情绪同步分析",
//            fontSize = 14.sp,
//            color = Color.Gray,
//            modifier = Modifier.padding(top = 8.dp)
//        )
//
//        Spacer(modifier = Modifier.height(30.dp))
//
//        // Placeholder for data visualization (Charts)
//        Surface(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(240.dp),
//            shape = RoundedCornerShape(16.dp),
//            color = Color.White,
//            shadowElevation = 2.dp
//        ) {
//            Box(contentAlignment = Alignment.Center) {
//                // Chinese UI text
//                Text(
//                    text = "身心节奏图表（开发中）",
//                    color = Color.LightGray,
//                    fontSize = 14.sp
//                )
//            }
//        }
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // Insight Card section
//        Text(
//            text = "今日分析",
//            fontSize = 18.sp,
//            fontWeight = FontWeight.SemiBold,
//            color = Color.Black
//        )
//
//        Spacer(modifier = Modifier.height(12.dp))
//
//        Surface(
//            modifier = Modifier.fillMaxWidth(),
//            shape = RoundedCornerShape(16.dp),
//            color = Color.White,
//            shadowElevation = 2.dp
//        ) {
//            Column(modifier = Modifier.padding(16.dp)) {
//                Text(
//                    text = "情绪共鸣：",
//                    fontWeight = FontWeight.Bold,
//                    color = Color(0xFF008080)
//                )
//                Text(
//                    text = "你早上的平静情绪帮助你维持了非常健康的血糖区间。继续保持！",
//                    fontSize = 14.sp,
//                    modifier = Modifier.padding(top = 4.dp),
//                    color = Color.DarkGray
//                )
//            }
//        }
//    }
//}

package com.withapp.with.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.withapp.with.viewmodels.ReportViewModel

@Composable
fun ReportView(
    reportViewModel: ReportViewModel
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .verticalScroll(scrollState)
            .statusBarsPadding()
            .padding(20.dp)
    ) {
        // App UI in Chinese
        Text("健康简报", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text("今日洞察：身体节奏很稳健。", fontSize = 16.sp, color = Color.Gray, modifier = Modifier.padding(top = 8.dp))

        Spacer(modifier = Modifier.height(24.dp))

        // Visual placeholder for rhythm charts
        Surface(
            modifier = Modifier.fillMaxWidth().height(200.dp),
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            shadowElevation = 2.dp
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text("身心共鸣图表展示区", color = Color.LightGray)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text("核心发现", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(16.dp))

        // Rendering the insights list from ViewModel
        reportViewModel.insights.forEach { insight ->
            Surface(
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                shadowElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = insight.icon, fontSize = 24.sp)
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        // Accessing title and description defined in the ViewModel
                        Text(text = insight.title, fontWeight = FontWeight.Bold, color = Color(0xFF008080))
                        Text(text = insight.description, fontSize = 14.sp, color = Color.DarkGray)
                    }
                }
            }
        }
    }
}