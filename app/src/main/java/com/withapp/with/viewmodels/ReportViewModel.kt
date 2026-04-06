
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

    // Mock data for the chart: representing glucose levels over time
    val glucoseData = listOf(
        5.2f, 5.4f, 5.8f, 7.2f, 8.5f, 7.9f, 6.5f, 5.8f,
        6.2f, 7.0f, 9.2f, 8.1f, 6.5f, 5.9f, 5.5f, 5.2f,
        6.1f, 7.5f, 8.8f, 7.2f, 6.0f, 5.4f, 5.3f, 5.1f
    )

    init {
        loadMockData()
    }

    private fun loadMockData() {
        insights.clear()
        insights.addAll(
            listOf(
                Insight(
                    title = "身心共鸣点",
                    description = "今天上午 10:20，你的心情愉悦与血糖稳定达到了高度契合。",
                    icon = "🎯"
                ),
                Insight(
                    title = "波动预警",
                    description = "午餐后血糖上升较快，建议下次餐后增加 10 分钟散步。",
                    icon = "🚶"
                )
            )
        )
    }
}