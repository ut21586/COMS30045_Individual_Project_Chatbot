package com.withapp.with.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

data class MoodLog(val timeLabel: String, val label: String, val value: String, val numericScore: Float)
data class CgmNode(val timeLabel: String, val value: Double)
data class DietEntry(val time: String, val food: String, val carbs: String)
data class ExerciseEntry(val time: String, val activity: String, val duration: String)
data class HeartRateEntry(val time: String, val bpm: Int, val status: String)

data class ClinicalCgmReport(
    val deviceInfo: String, val dataCoverage: String, val avgGlucose: String, val gmi: String,
    val tirTarget: Int, val tirHigh: Int, val tirLow: Int, val cv: String, val clinicalAdvice: String
)

data class ClinicalMoodReport(
    val meanScore: String, val medianScore: String, val variabilitySD: String, val instabilityIndex: String,
    val positiveAffect: Int, val neutralAffect: Int, val negativeAffect: Int,
    val phq9Score: String, val gad7Score: String, val pssScore: String
)

class ReportViewModel : ViewModel() {
    var currentScale = mutableStateOf("日")
    val dietLogs = mutableStateListOf<DietEntry>()
    val exerciseLogs = mutableStateListOf<ExerciseEntry>()
    val hrLogs = mutableStateListOf<HeartRateEntry>()

    // --- DAILY DATA ---
    val dailyCgmReport = ClinicalCgmReport("With v1 | Day 3", "99%", "6.4", "6.1%", 92, 5, 3, "< 28%", "血糖稳定，TIR 极佳。")
    val dailyMoodReport = ClinicalMoodReport("7.5", "7.8", "0.9", "Low", 70, 20, 10, "2", "3", "12")
    val dailyCgmNodes = mutableStateListOf(CgmNode("08:00", 5.4), CgmNode("12:00", 8.2), CgmNode("18:00", 5.8))
    val dailyMoodNodes = mutableStateListOf(MoodLog("09:00", "清醒", "极佳", 8.5f), MoodLog("15:00", "餐后", "平稳", 7.0f))

    // --- MONTHLY DATA ---
    val monthlyCgmReport = ClinicalCgmReport("With v1 | 2 Sensors", "95%", "6.1", "5.9%", 88, 10, 2, "< 32%", "本月表现平稳，注意晚餐。")
    val monthlyMoodReport = ClinicalMoodReport("6.2", "6.5", "1.8", "Mod", 58, 20, 22, "6", "5", "18")
    val monthlyCgmNodes = mutableStateListOf(CgmNode("1日", 6.2), CgmNode("15日", 5.9), CgmNode("30日", 6.1))
    val monthlyMoodNodes = mutableStateListOf(MoodLog("5日", "工作", "高压", 4.0f), MoodLog("20日", "周末", "愉快", 8.8f))

    init {
        dietLogs.add(DietEntry("08:30", "全麦面包", "30g"))
        exerciseLogs.add(ExerciseEntry("09:00", "散步", "20min"))
        hrLogs.add(HeartRateEntry("10:30", 72, "静息"))
    }

    fun addDiet(f: String, c: String) { dietLogs.add(0, DietEntry("现在", f, c)) }
    fun addExercise(a: String, d: String) { exerciseLogs.add(0, ExerciseEntry("现在", a, d)) }
    fun addHeartRate(b: Int, s: String) { hrLogs.add(0, HeartRateEntry("现在", b, s)) }
    fun addMoodLog(l: String, v: String) { dailyMoodNodes.add(0, MoodLog("现在", l, v, 7.5f)) }
}