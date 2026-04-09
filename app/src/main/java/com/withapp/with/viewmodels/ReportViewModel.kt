
package com.withapp.with.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

// --- GLOBAL DATA MODELS (CRITICAL: DEFINED ONLY HERE) ---
data class HealthLog(val timeLabel: String, val type: String, val desc: String, val value: String)
data class MoodLog(val timeLabel: String, val label: String, val value: String, val numericScore: Float)
data class CgmNode(val timeLabel: String, val value: Double)
data class DietEntry(val time: String, val food: String, val carbs: String)
data class ExerciseEntry(val time: String, val activity: String, val duration: String)
data class HeartRateEntry(val time: String, val bpm: Int, val status: String)

// Clinical CGM Report (7 Parts - Screenshot 13:14)
data class ClinicalCgmReport(
    val deviceInfo: String, val dataCoverage: String, val avgGlucose: String, val gmi: String,
    val tirTarget: Int, val tirHigh: Int, val tirLow: Int, val cv: String, val clinicalAdvice: String
)

// Clinical Mood Metrics (10 Metrics - Screenshot 13:23)
data class ClinicalMoodReport(
    val meanScore: String, val medianScore: String, val variabilitySD: String, val instabilityIndex: String,
    val positiveAffect: Int, val neutralAffect: Int, val negativeAffect: Int,
    val phq9Score: String, val gad7Score: String, val pssScore: String
)

class ReportViewModel : ViewModel() {
    var currentScale = mutableStateOf("日")

    // Live interaction lists
    val dietLogs = mutableStateListOf<DietEntry>()
    val exerciseLogs = mutableStateListOf<ExerciseEntry>()
    val hrLogs = mutableStateListOf<HeartRateEntry>()

    // --- DAILY DATA (Precision Spec) ---
    val dailyCgmRep = ClinicalCgmReport("With CGM v1 | Day 3", "99%", "6.4", "6.1%", 92, 5, 3, "< 28%", "今日血糖表现极佳，TIR 达标率非常理想。情绪平稳对控糖帮助显著。")
    val dailyMoodRep = ClinicalMoodReport("7.5", "7.8", "0.9", "Low", 70, 20, 10, "2 (极轻)", "3 (极轻)", "12 (轻度压力)")
    val dailyCgmNodes = mutableStateListOf(CgmNode("08:00", 5.4), CgmNode("10:00", 6.1), CgmNode("12:00", 8.2), CgmNode("15:00", 7.5), CgmNode("18:00", 5.8))
    val dailyMoodNodes = mutableStateListOf(MoodLog("09:00", "清醒", "极佳", 8.5f), MoodLog("13:00", "午餐", "平稳", 7.0f), MoodLog("18:00", "下班", "愉快", 8.2f))

    // --- MONTHLY DATA (Trend Spec) ---
    val monthlyCgmRep = ClinicalCgmReport("With CGM v1 | 2 Sensors", "95%", "6.1", "5.9%", 88, 10, 2, "< 32%", "本月表现平稳。建议关注月中的餐后波动。")
    val monthlyMoodRep = ClinicalMoodReport("6.2", "6.5", "1.8", "Mod", 58, 20, 22, "6 (轻度)", "5 (轻焦虑)", "18 (中压力)")
    val monthlyCgmNodes = mutableStateListOf(CgmNode("1日", 6.2), CgmNode("10日", 6.8), CgmNode("20日", 5.9), CgmNode("30日", 6.1))
    val monthlyMoodNodes = mutableStateListOf(MoodLog("5日", "工作", "高压", 4.0f), MoodLog("20日", "周末", "放松", 8.5f))

    init {
        dietLogs.add(DietEntry("08:30", "全麦面包", "30g"))
        exerciseLogs.add(ExerciseEntry("09:00", "散步", "20min"))
        hrLogs.add(HeartRateEntry("10:30", 72, "静息"))
    }

    // Mapping Functions
    fun addDiet(f: String, c: String) { dietLogs.add(0, DietEntry("现在", f, c)) }
    fun addExercise(a: String, d: String) { exerciseLogs.add(0, ExerciseEntry("现在", a, d)) }
    fun addHeartRate(b: Int, s: String) { hrLogs.add(0, HeartRateEntry("现在", b, s)) }
    fun addMoodLog(l: String, v: String) { dailyMoodNodes.add(0, MoodLog("现在", l, v, 7.5f)) }
}