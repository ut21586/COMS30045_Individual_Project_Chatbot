
package com.withapp.with.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

// --- GLOBAL PRECISION DATA MODELS (DEFINED ONLY HERE TO FIX DEX ERROR) ---
data class HealthLog(val timeLabel: String, val type: String, val desc: String, val value: String)
data class MoodLog(val timeLabel: String, val label: String, val value: String, val numericScore: Float)
data class CgmNode(val timeLabel: String, val value: Double)
data class DietEntry(val time: String, val food: String, val carbs: String)
data class ExerciseEntry(val time: String, val activity: String, val duration: String)
data class HeartRateEntry(val time: String, val bpm: Int, val status: String)

// 7-Part Medical CGM Report Structure (Screenshot 13:14)
data class ClinicalCgmReport(
    val deviceInfo: String,        // 1. 患者与设备信息
    val dataCoverage: String,      // 2. 数据质量与覆盖
    val avgGlucose: String,        // 3. 核心指标：平均血糖
    val gmi: String,               // 3. 核心指标：GMI 糖化
    val tirTarget: Int,            // 4. TIR 分析 (目标范围内)
    val tirHigh: Int,              // 4. TIR 分析 (偏高)
    val tirLow: Int,               // 4. TIR 分析 (偏低)
    val cv: String,                // 5. 血糖波动性 (CV)
    val clinicalAdvice: String     // 7. 临床解读与建议
)

// 10-Metric Clinical Mood Structure (Screenshots 13:23 & 13:24)
data class ClinicalMoodReport(
    val meanScore: String,         // Mean Mood Score
    val medianScore: String,       // Median Mood Score
    val variabilitySD: String,     // Mood Variability (SD)
    val instabilityIndex: String,  // Mood Instability Index
    val positiveAffect: Int,       // Affect Percentages
    val neutralAffect: Int,
    val negativeAffect: Int,
    val phq9Score: String,         // PHQ-9 Scale
    val gad7Score: String,         // GAD-7 Scale
    val pssScore: String           // PSS Stress Scale
)

class ReportViewModel : ViewModel() {
    var currentScale = mutableStateOf("日")

    // Live Health Interaction Data
    val dietLogs = mutableStateListOf<DietEntry>()
    val exerciseLogs = mutableStateListOf<ExerciseEntry>()
    val hrLogs = mutableStateListOf<HeartRateEntry>()

    // --- DAILY DATA (Precise Values from Screenshots) ---
    val dailyCgmRep = ClinicalCgmReport("With CGM v1 | Day 3", "99%", "6.4", "6.1%", 92, 5, 3, "< 28%", "今日血糖表现极佳，TIR 达标率非常理想。情绪平稳对控糖帮助显著。")
    val dailyMoodRep = ClinicalMoodReport("7.5", "7.8", "0.9", "Low", 70, 20, 10, "2 (极轻度)", "3 (极轻度)", "12 (轻度压力)")
    val dailyCgmNodes = mutableStateListOf(CgmNode("08:00", 5.4), CgmNode("10:00", 6.1), CgmNode("12:00", 8.2), CgmNode("15:00", 7.5), CgmNode("18:00", 5.8))
    val dailyMoodNodes = mutableStateListOf(MoodLog("09:00", "清醒", "极佳", 8.5f), MoodLog("13:00", "午后", "平稳", 7.0f), MoodLog("18:00", "下班", "愉快", 8.2f))

    // --- MONTHLY DATA (Precise Trends) ---
    val monthlyCgmRep = ClinicalCgmReport("With CGM v1 | 2 Sensors", "95%", "6.1", "5.9%", 88, 10, 2, "< 32%", "本月整体优秀。月中出现小范围波动，建议优化晚餐碳水比例。")
    val monthlyMoodRep = ClinicalMoodReport("6.2", "6.5", "1.8", "Moderate", 58, 22, 20, "6 (轻度抑郁)", "5 (轻度焦虑)", "18 (中度压力)")
    val monthlyCgmNodes = mutableStateListOf(CgmNode("1日", 6.2), CgmNode("10日", 6.8), CgmNode("20日", 5.9), CgmNode("30日", 6.1))
    val monthlyMoodNodes = mutableStateListOf(MoodLog("5日", "工作周", "高压", 4.0f), MoodLog("20日", "周末", "放松", 8.5f))

    init {
        // Precise Mock Data Initialization
        dietLogs.add(DietEntry("08:30", "全麦面包 + 牛奶", "35g"))
        exerciseLogs.add(ExerciseEntry("09:00", "慢跑 (晨间)", "20min"))
        hrLogs.add(HeartRateEntry("10:30", 72, "静息状态"))
    }

    // Mapping Functions for Chatbot & Popups
    fun addDiet(f: String, c: String) { dietLogs.add(0, DietEntry("现在", f, c)) }
    fun addExercise(a: String, d: String) { exerciseLogs.add(0, ExerciseEntry("现在", a, d)) }
    fun addHeartRate(b: Int, s: String) { hrLogs.add(0, HeartRateEntry("现在", b, s)) }
    fun addMoodLog(l: String, v: String) { dailyMoodNodes.add(0, MoodLog("现在", l, v, 7.5f)) }
}