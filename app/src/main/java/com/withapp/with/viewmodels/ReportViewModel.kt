//
//package com.withapp.with.viewmodels
//
//import androidx.compose.runtime.mutableStateListOf
//import androidx.lifecycle.ViewModel
//import java.util.UUID
//
//// Data model for health insights
//data class Insight(
//    val id: UUID = UUID.randomUUID(),
//    val title: String,
//    val description: String,
//    val icon: String
//)
//
//class ReportViewModel : ViewModel() {
//    // Observable list for the UI to consume
//    val insights = mutableStateListOf<Insight>()
//
//    // Mock data for the chart: representing glucose levels over time
//    val glucoseData = listOf(
//        5.2f, 5.4f, 5.8f, 7.2f, 8.5f, 7.9f, 6.5f, 5.8f,
//        6.2f, 7.0f, 9.2f, 8.1f, 6.5f, 5.9f, 5.5f, 5.2f,
//        6.1f, 7.5f, 8.8f, 7.2f, 6.0f, 5.4f, 5.3f, 5.1f
//    )
//
//    init {
//        loadMockData()
//    }
//
//    private fun loadMockData() {
//        insights.clear()
//        insights.addAll(
//            listOf(
//                Insight(
//                    title = "身心共鸣点",
//                    description = "今天上午 10:20，你的心情愉悦与血糖稳定达到了高度契合。",
//                    icon = "🎯"
//                ),
//                Insight(
//                    title = "波动预警",
//                    description = "午餐后血糖上升较快，建议下次餐后增加 10 分钟散步。",
//                    icon = "🚶"
//                )
//            )
//        )
//    }
//}

package com.withapp.with.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.random.Random

data class EnergyDataPoint(val id: UUID = UUID.randomUUID(), val timestamp: Long, val value: Double, val label: String)
data class RhythmDataPoint(val id: UUID = UUID.randomUUID(), val timestamp: Long, val label: String, val moodScore: Double, val glucoseMmol: Double, val heartRate: Int?)
data class TimelineEvent(val id: UUID = UUID.randomUUID(), val time: String, val title: String, val description: String, val location: String?, val glucoseValue: Double?, val mood: String?, val emoji: String?)
data class Insight(val id: UUID = UUID.randomUUID(), val title: String, val description: String, val icon: String)

class ReportViewModel : ViewModel() {
    val energyData = mutableStateListOf<EnergyDataPoint>()
    val rhythmData = mutableStateListOf<RhythmDataPoint>()
    val timelineEvents = mutableStateListOf<TimelineEvent>()
    val insights = mutableStateListOf<Insight>()
    var isLoading by mutableStateOf(false)

    init { loadMockData() }

    fun loadData(date: Long) {
        isLoading = true
        viewModelScope.launch {
            delay(500)
            loadMockData()
            isLoading = false
        }
    }

    private fun loadMockData() {
        generateRhythmData()
        generateTimelineEvents()
        generateInsights()
    }

    private fun generateRhythmData() {
        rhythmData.clear()
        val now = System.currentTimeMillis()
        for (hour in 6 until 22) {
            val moodBase = getBaseEnergy(hour)
            val mood = (moodBase + Random.nextDouble(-8.0, 8.0)).coerceIn(15.0, 95.0)
            val glucose = (6.0 + Random.nextDouble(-1.3, 1.7)).coerceIn(3.9, 11.2)
            val heartRate = if (hour in listOf(10, 15, 19)) Random.nextInt(96, 112) else null

            rhythmData.add(RhythmDataPoint(timestamp = now, label = "$hour:00", moodScore = mood, glucoseMmol = glucose, heartRate = heartRate))
        }
    }

    private fun getBaseEnergy(hour: Int): Double = when(hour) {
        in 6..7 -> 40.0; in 8..9 -> 70.0; in 10..11 -> 80.0
        in 12..13 -> 55.0; in 14..16 -> 65.0; in 17..18 -> 60.0
        in 19..21 -> 45.0; else -> 50.0
    }

    private fun generateTimelineEvents() {
        timelineEvents.clear()
        timelineEvents.addAll(listOf(
            TimelineEvent(time = "7:30", title = "早餐", description = "燕麦与咖啡", location = "家", glucoseValue = 95.0, mood = null, emoji = null),
            TimelineEvent(time = "12:30", title = "午餐", description = "沙拉与鸡胸肉", location = "餐厅", glucoseValue = 148.0, mood = "平静", emoji = "😌")
        ))
    }

    private fun generateInsights() {
        insights.clear()
        insights.addAll(listOf(
            Insight(title = "活动达标", description = "今日步数符合预期", icon = "🏃"),
            Insight(title = "血糖平稳", description = "午后血糖控制极佳", icon = "🎯")
        ))
    }
}