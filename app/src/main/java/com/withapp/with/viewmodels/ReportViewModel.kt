//
//package com.withapp.with.viewmodels
//
//import androidx.compose.runtime.mutableStateListOf
//import androidx.compose.runtime.mutableStateOf
//import androidx.lifecycle.ViewModel
//
//// --- 🚫 严禁在其他文件定义这些类，彻底解决 Duplicate Class 报错 ---
//data class HealthLog(val timeLabel: String, val type: String, val desc: String, val value: String)
//data class MoodLog(val timeLabel: String, val label: String, val value: String, val numericScore: Float)
//data class CgmNode(val timeLabel: String, val value: Double)
//
//// 升级：加入 numericValue 以支持实时画图
//data class DietEntry(val time: String, val food: String, val carbs: String, val numericValue: Float)
//data class ExerciseEntry(val time: String, val activity: String, val duration: String, val numericValue: Float)
//data class HeartRateEntry(val time: String, val bpm: Int, val status: String)
//
//data class ClinicalCgmReport(
//    val deviceInfo: String, val dataCoverage: String, val avgGlucose: String, val gmi: String,
//    val tirTarget: Int, val tirHigh: Int, val tirLow: Int, val cv: String, val clinicalAdvice: String
//)
//
//data class ClinicalMoodReport(
//    val meanScore: String, val medianScore: String, val variabilitySD: String, val instabilityIndex: String,
//    val positiveAffect: Int, val neutralAffect: Int, val negativeAffect: Int,
//    val phq9Score: String, val gad7Score: String, val pssScore: String
//)
//
//class ReportViewModel : ViewModel() {
//    var currentScale = mutableStateOf("日")
//
//    // Live Sync Lists (支持图表与列表的实时更新)
//    val dietLogs = mutableStateListOf<DietEntry>()
//    val exerciseLogs = mutableStateListOf<ExerciseEntry>()
//    val hrLogs = mutableStateListOf<HeartRateEntry>()
//    val cgmNodes = mutableStateListOf<CgmNode>()
//    val moodNodes = mutableStateListOf<MoodLog>()
//
//    // 临床状态 (完整保留精确数值)
//    var cgmReport = mutableStateOf(ClinicalCgmReport("With v1 | Day 3", "99%", "6.4", "6.1%", 92, 5, 3, "< 28%", "今日血糖表现极佳，TIR 达标率非常理想。情绪平稳有效支持了血糖控制。"))
//    var moodReport = mutableStateOf(ClinicalMoodReport("7.5", "7.8", "0.9", "Low", 70, 20, 10, "2 (极轻)", "3 (极轻)", "12 (轻度压力)"))
//
//    init {
//        // 初始精确 Mock 数据 (包含历史记录用于绘制走势图)
//        cgmNodes.addAll(listOf(CgmNode("08:00", 5.4), CgmNode("12:00", 6.1), CgmNode("18:00", 5.8)))
//        moodNodes.addAll(listOf(MoodLog("09:00", "清醒", "极佳", 8.5f), MoodLog("15:00", "餐后", "平稳", 7.0f)))
//
//        dietLogs.addAll(listOf(DietEntry("08:30", "全麦面包", "30g", 30f), DietEntry("12:30", "沙拉", "15g", 15f)))
//        exerciseLogs.addAll(listOf(ExerciseEntry("09:00", "慢跑", "20min", 20f), ExerciseEntry("18:00", "拉伸", "15min", 15f)))
//        hrLogs.addAll(listOf(HeartRateEntry("08:00", 72, "静息"), HeartRateEntry("19:00", 110, "运动")))
//    }
//
//    // 🚀 CHAT 自动解析与同步引擎
//    fun processChatInput(input: String) {
//        val time = java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"))
//        // 智能提取输入中的数字用于画图，如果没有则给默认值
//        val extractedNumber = input.filter { it.isDigit() || it == '.' }.toFloatOrNull()
//
//        when {
//            input.contains("吃") || input.contains("餐") || input.contains("饭") -> {
//                val carbs = extractedNumber ?: 40f
//                dietLogs.add(0, DietEntry(time, input, "约 ${carbs}g", carbs))
//            }
//            input.contains("跑") || input.contains("步") || input.contains("动") -> {
//                val duration = extractedNumber ?: 30f
//                exerciseLogs.add(0, ExerciseEntry(time, input, "${duration}min", duration))
//            }
//            input.contains("心") || input.contains("跳") -> {
//                val bpm = extractedNumber?.toInt() ?: 85
//                hrLogs.add(0, HeartRateEntry(time, bpm, "自动识别"))
//            }
//            else -> moodNodes.add(0, MoodLog(time, "对话分析", input, 7.8f))
//        }
//    }
//
//    // 弹窗手动添加逻辑
//    fun addDiet(f: String, c: String) { val cv = c.filter { it.isDigit() }.toFloatOrNull() ?: 0f; dietLogs.add(0, DietEntry("现在", f, "${cv}g", cv)) }
//    fun addExercise(a: String, d: String) { val dv = d.filter { it.isDigit() }.toFloatOrNull() ?: 0f; exerciseLogs.add(0, ExerciseEntry("现在", a, "${dv}min", dv)) }
//    fun addHeartRate(b: Int, s: String) { hrLogs.add(0, HeartRateEntry("现在", b, s)) }
//}

package com.withapp.with.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

// --- GLOBAL MODELS (CRITICAL: DEFINED ONLY HERE) ---
data class HealthLog(val timeLabel: String, val type: String, val desc: String, val value: String)
data class MoodLog(val timeLabel: String, val label: String, val value: String, val numericScore: Float)
data class CgmNode(val timeLabel: String, val value: Double)
data class DietEntry(val time: String, val food: String, val carbs: String, val numericValue: Float)
data class ExerciseEntry(val time: String, val activity: String, val duration: String, val numericValue: Float)
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

data class UserProfile(
    var age: String, var gender: String, var height: String, var weight: String,
    var diagnosisDate: String, var insulinUse: String, var hba1c: String
)

class ReportViewModel : ViewModel() {
    var currentScale = mutableStateOf("日")

    val dietLogs = mutableStateListOf<DietEntry>()
    val exerciseLogs = mutableStateListOf<ExerciseEntry>()
    val hrLogs = mutableStateListOf<HeartRateEntry>()
    val cgmNodes = mutableStateListOf<CgmNode>()
    val moodNodes = mutableStateListOf<MoodLog>()

    var cgmReport = mutableStateOf(ClinicalCgmReport("With v1 | Day 3", "99%", "6.4", "6.1%", 92, 5, 3, "< 28%", "今日血糖表现极佳，TIR 达标率非常理想。情绪平稳有效支持了血糖控制。"))
    var moodReport = mutableStateOf(ClinicalMoodReport("7.5", "7.8", "0.9", "Low", 70, 20, 10, "2 (极轻)", "3 (极轻)", "12 (轻度)"))

    var userProfile = mutableStateOf(UserProfile("28 岁", "女", "175 cm", "70 kg", "2023 年 5 月", "是", "6.2 %"))

    init {
        cgmNodes.addAll(listOf(CgmNode("08:00", 5.4), CgmNode("12:00", 6.1), CgmNode("18:00", 5.8)))
        moodNodes.addAll(listOf(MoodLog("09:00", "清醒", "极佳", 8.5f), MoodLog("15:00", "餐后", "平稳", 7.0f)))
        dietLogs.addAll(listOf(DietEntry("08:30", "全麦面包", "30g", 30f), DietEntry("12:30", "沙拉", "15g", 15f)))
        exerciseLogs.addAll(listOf(ExerciseEntry("09:00", "慢跑", "20min", 20f), ExerciseEntry("18:00", "拉伸", "15min", 15f)))
        hrLogs.addAll(listOf(HeartRateEntry("08:00", 72, "静息"), HeartRateEntry("19:00", 110, "运动")))
    }

    // 🚀 CHAT 自动解析与作图引擎
    fun processChatInput(input: String) {
        val time = java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"))
        val extractedNumber = input.filter { it.isDigit() || it == '.' }.toFloatOrNull()

        when {
            input.contains("吃") || input.contains("餐") || input.contains("饭") -> {
                val carbs = extractedNumber ?: 40f
                dietLogs.add(0, DietEntry(time, input, "约 ${carbs}g", carbs))
            }
            input.contains("跑") || input.contains("步") || input.contains("动") -> {
                val duration = extractedNumber ?: 30f
                exerciseLogs.add(0, ExerciseEntry(time, input, "${duration}min", duration))
            }
            input.contains("心") || input.contains("跳") -> {
                val bpm = extractedNumber?.toInt() ?: 85
                hrLogs.add(0, HeartRateEntry(time, bpm, "自动识别"))
            }
            else -> moodNodes.add(0, MoodLog(time, "记录", input, 7.8f))
        }
    }

    fun addDiet(f: String, c: String) { val cv = c.filter { it.isDigit() }.toFloatOrNull() ?: 0f; dietLogs.add(0, DietEntry("现在", f, "${cv}g", cv)) }
    fun addExercise(a: String, d: String) { val dv = d.filter { it.isDigit() }.toFloatOrNull() ?: 0f; exerciseLogs.add(0, ExerciseEntry("现在", a, "${dv}min", dv)) }
    fun addHeartRate(b: Int, s: String) { hrLogs.add(0, HeartRateEntry("现在", b, s)) }
    fun updateProfile(newProfile: UserProfile) { userProfile.value = newProfile }
}