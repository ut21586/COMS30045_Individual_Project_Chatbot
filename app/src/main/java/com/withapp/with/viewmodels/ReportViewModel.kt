//package com.withapp.with.viewmodels
//
//import androidx.compose.runtime.mutableStateListOf
//import androidx.lifecycle.ViewModel
//import java.time.LocalDateTime
//import java.util.UUID
//
//// Data models for the report view
//data class RhythmDataPoint(
//    val id: UUID = UUID.randomUUID(),
//    val timestamp: LocalDateTime,
//    val label: String,
//    val moodScore: Float,
//    val glucoseMmol: Float
//)
//
//data class TimelineEvent(
//    val id: UUID = UUID.randomUUID(),
//    val time: String,
//    val title: String,
//    val description: String,
//    val glucoseValue: Float? = null,
//    val mood: String? = null,
//    val emoji: String? = null
//)
//
//data class Insight(
//    val title: String,
//    val description: String,
//    val icon: String
//)
//
//class ReportViewModel : ViewModel() {
//    val rhythmData = mutableStateListOf<RhythmDataPoint>()
//    val timelineEvents = mutableStateListOf<TimelineEvent>()
//    val insights = mutableStateListOf<Insight>()
//
//    init {
//        loadMockData()
//    }
//
//    private fun loadMockData() {
//        val now = LocalDateTime.now()
//        rhythmData.add(RhythmDataPoint(now, "14:00", 85f, 5.6f))
//
//        timelineEvents.add(TimelineEvent("08:30", "早餐", "燕麦粥与无糖豆浆", 5.8f, "平静", "😌"))
//
//        insights.add(Insight("情绪与血糖的共鸣", "早晨的平静情绪帮助血糖保持平稳。", "☀️"))
//    }
//}
package com.withapp.with.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import java.util.UUID

// Data model for health insights
data class Insight(
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val description: String,
    val icon: String
)

class ReportViewModel : ViewModel() {

    // Observable list for the UI to consume
    val insights = mutableStateListOf<Insight>()

    init {
        loadMockData()
    }

    private fun loadMockData() {
        // Clearing existing data and adding fresh mock data
        insights.clear()
        insights.addAll(
            listOf(
                Insight(
                    title = "情绪与血糖的共鸣",
                    description = "早晨的平静情绪帮助你的餐后血糖保持在完美区间。",
                    icon = "☀️"
                ),
                Insight(
                    title = "运动正反馈",
                    description = "上午的快走让你的基础代谢有了显著提升。",
                    icon = "🏃"
                )
            )
        )
    }
}