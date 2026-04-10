//
//package com.withapp.with.viewmodels
//
//import androidx.compose.runtime.mutableStateListOf
//import androidx.compose.runtime.mutableStateOf
//import androidx.lifecycle.ViewModel
//
//// --- 🚫 全局唯一数据模型 ---
//data class HealthLog(val timeLabel: String, val type: String, val desc: String, val value: String)
//data class MoodLog(val timeLabel: String, val label: String, val value: String, val numericScore: Float)
//data class DietEntry(val time: String, val food: String, val carbs: String, val numericValue: Float)
//data class ExerciseEntry(val time: String, val activity: String, val duration: String, val numericValue: Float)
//data class HeartRateEntry(val time: String, val bpm: Int, val status: String)
//
//data class CgmNode(
//    val timeLabel: String,
//    val value: Double,
//    val trend: String = "→",
//    val samplingInterval: String,
//    val rateOfChange: String
//)
//
//data class ClinicalCgmReport(
//    val deviceInfo: String, val dataCoverage: String,
//    val avgGlucose: String, val medianGlucose: String, val percentiles: String,
//    val sd: String, val cv: String, val mage: String, val gmi: String,
//    val tirTarget: Int, val tirHigh: Int, val tirLow: Int,
//    val tbrLevel1: String, val tbrLevel2: String,
//    val tarLevel1: String, val tarLevel2: String,
//    val wearTime: String, val completeness: String, val signalLoss: String,
//    val hypoEvents: Int, val hyperEvents: Int, val alertsTriggered: Int,
//    val clinicalAdvice: String
//)
//
//data class ClinicalMoodReport(
//    val meanScore: String, val medianScore: String, val variabilitySD: String, val instabilityIndex: String,
//    val positiveAffect: Int, val neutralAffect: Int, val negativeAffect: Int,
//    val phq9Score: String, val gad7Score: String, val pssScore: String
//)
//
//// 🔔 完美对标截图：加入了开关(isReminderEnabled)、界面性格(persona)和数据维度(dataDetail)
//data class UserProfile(
//    var age: String, var gender: String, var height: String, var weight: String,
//    var diagnosisDate: String, var insulinUse: String, var hba1c: String,
//    var reminderTime: String, var reminderFrequency: String,
//    var isReminderEnabled: Boolean, var persona: String, var dataDetail: String
//)
//
//class ReportViewModel : ViewModel() {
//    var currentScale = mutableStateOf("日")
//
//    val dietLogs = mutableStateListOf<DietEntry>()
//    val exerciseLogs = mutableStateListOf<ExerciseEntry>()
//    val hrLogs = mutableStateListOf<HeartRateEntry>()
//    val cgmNodes = mutableStateListOf<CgmNode>()
//    val moodNodes = mutableStateListOf<MoodLog>()
//
//    var cgmReport = mutableStateOf(ClinicalCgmReport(
//        deviceInfo = "With v1 | 采样率 3min/次", dataCoverage = "99%",
//        avgGlucose = "6.4 mmol/L", medianGlucose = "6.2 mmol/L", percentiles = "IQR: 4.8-7.5",
//        sd = "1.2", cv = "18.7% (<36%)", mage = "2.1", gmi = "6.1%",
//        tirTarget = 92, tirHigh = 5, tirLow = 3,
//        tbrLevel1 = "2%", tbrLevel2 = "1% (<3.0)", tarLevel1 = "4%", tarLevel2 = "1% (>13.9)",
//        wearTime = "98%", completeness = "99.5%", signalLoss = "15 min",
//        hypoEvents = 1, hyperEvents = 2, alertsTriggered = 3,
//        clinicalAdvice = "今日血糖极佳，TIR 达标。趋势平稳，无需调整基础率。"
//    ))
//
//    var moodReport = mutableStateOf(ClinicalMoodReport("7.5", "7.8", "0.9", "Low", 70, 20, 10, "2 (极轻)", "3 (极轻)", "12 (轻度)"))
//
//    // 🔔 初始化截图中的设置参数
//    var userProfile = mutableStateOf(UserProfile(
//        "28 岁", "女", "175 cm", "70 kg", "2023 年 5 月", "是", "6.2 %",
//        "餐后 30 分钟", "每天 3 次", true, "温暖治愈", "详细图表"
//    ))
//
//    init {
//        cgmNodes.addAll(listOf(
//            CgmNode("08:00", 5.4, "→", "3min", "0.00"),
//            CgmNode("12:00", 6.1, "↗", "3min", "+0.23"),
//            CgmNode("18:00", 5.8, "↘", "3min", "-0.10")
//        ))
//        moodNodes.addAll(listOf(MoodLog("09:00", "清醒", "极佳", 8.5f), MoodLog("12:00", "餐前", "疲惫", 4.5f), MoodLog("15:00", "餐后", "平稳", 7.0f)))
//        dietLogs.addAll(listOf(DietEntry("08:30", "全麦面包", "30g", 30f), DietEntry("12:30", "沙拉", "15g", 15f)))
//        exerciseLogs.addAll(listOf(ExerciseEntry("09:00", "慢跑", "20min", 20f), ExerciseEntry("18:00", "拉伸", "15min", 15f)))
//        hrLogs.addAll(listOf(HeartRateEntry("08:00", 72, "静息"), HeartRateEntry("19:00", 110, "运动")))
//    }
//
//    fun processChatInput(input: String) {
//        val timeMatch = Regex("([0-1]?[0-9]|2[0-3]):([0-5][0-9])").find(input)?.value
//        val time = timeMatch ?: java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"))
//        val inputCleaned = if (timeMatch != null) input.replace(timeMatch, "") else input
//        val extractedNumber = Regex("(\\d+\\.\\d+|\\d+)").find(inputCleaned)?.value?.toFloatOrNull()
//
//        var matchedSomething = false
//
//        if (Regex("血糖|低血糖|高血糖|测了|mmol").containsMatchIn(inputCleaned)) {
//            val bgMatch = Regex("(\\d+\\.\\d+|\\d+)").find(inputCleaned.substringAfter(Regex("血糖|测了").find(inputCleaned)?.value ?: ""))
//            val bg = bgMatch?.value?.toDoubleOrNull() ?: extractedNumber?.toDouble() ?: 5.5
//            addCgmNode(bg, time)
//            matchedSomething = true
//        }
//
//        if (Regex("胰岛素|打针|单位|U").containsMatchIn(inputCleaned)) {
//            val units = extractedNumber ?: 2f
//            dietLogs.add(0, DietEntry(time, "注射胰岛素", "${units} U", units))
//            matchedSomething = true
//        }
//
//        if (Regex("吃|餐|饭|饮食|碳水").containsMatchIn(inputCleaned)) {
//            val carbs = extractedNumber ?: 40f
//            dietLogs.add(0, DietEntry(time, inputCleaned, "约 ${carbs}g", carbs))
//            matchedSomething = true
//        }
//
//        if (Regex("跑|步|运动|锻炼|健身|游泳|骑车").containsMatchIn(inputCleaned)) {
//            val duration = extractedNumber ?: 30f
//            exerciseLogs.add(0, ExerciseEntry(time, inputCleaned, "${duration}min", duration))
//            matchedSomething = true
//        }
//
//        if (Regex("心率|心跳|bpm|BPM").containsMatchIn(inputCleaned)) {
//            val bpm = extractedNumber?.toInt() ?: 85
//            hrLogs.add(0, HeartRateEntry(time, bpm, "自动识别"))
//            matchedSomething = true
//        }
//
//        val moodKeywords = Regex("心情|情绪|感觉|状态|开心|高兴|爽|好|难过|生气|郁闷|压力|累|烦|平稳|平静|差|低落")
//        if (moodKeywords.containsMatchIn(inputCleaned) || !matchedSomething) {
//            val score = when {
//                Regex("极佳|特别好|开心|高兴|爽").containsMatchIn(inputCleaned) -> 8.5f
//                Regex("难过|生气|郁闷|烦|差|压力|累|低落|糟").containsMatchIn(inputCleaned) -> 3.5f
//                Regex("平稳|平静|还行|不错|好").containsMatchIn(inputCleaned) -> 7.0f
//                else -> extractedNumber ?: 6.5f
//            }
//            val label = if (score >= 8f) "极佳" else if (score <= 4f) "低落" else "平稳"
//            addMoodNode(score, label, time)
//        }
//    }
//
//    fun addCgmNode(bg: Double, timeInput: String) {
//        val finalTime = timeInput.ifBlank { java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm")) }
//        val lastNode = cgmNodes.lastOrNull()
//        val trend: String; val rateStr: String
//        if (lastNode != null) {
//            val diff = bg - lastNode.value
//            trend = when { diff >= 0.2 -> "↑"; diff > 0.0 -> "↗"; diff <= -0.2 -> "↓"; diff < 0.0 -> "↘"; else -> "→" }
//            val rateVal = diff / 3.0; val formattedRate = String.format("%.2f", rateVal)
//            rateStr = if (rateVal > 0) "+$formattedRate" else formattedRate
//        } else { trend = "→"; rateStr = "0.00" }
//        cgmNodes.add(CgmNode(finalTime, bg, trend, "3min", rateStr))
//    }
//
//    fun addMoodNode(score: Float, label: String, timeInput: String) {
//        val finalTime = timeInput.ifBlank { java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm")) }
//        moodNodes.add(MoodLog(finalTime, label, "手动记录", score))
//    }
//
//    fun addDiet(f: String, c: String) {
//        val cv = c.filter { it.isDigit() || it == '.' }.toFloatOrNull() ?: 0f
//        val time = java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"))
//        dietLogs.add(0, DietEntry(time, f, "${cv}g", cv))
//    }
//
//    fun addExercise(a: String, d: String) {
//        val dv = d.filter { it.isDigit() || it == '.' }.toFloatOrNull() ?: 0f
//        val time = java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"))
//        exerciseLogs.add(0, ExerciseEntry(time, a, "${dv}min", dv))
//    }
//
//    fun addHeartRate(b: Int, s: String) {
//        val time = java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"))
//        hrLogs.add(0, HeartRateEntry(time, b, s))
//    }
//
//    fun updateProfile(newProfile: UserProfile) { userProfile.value = newProfile }
//}

package com.withapp.with.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

// --- 🚫 全局唯一数据模型 ---
data class HealthLog(val timeLabel: String, val type: String, val desc: String, val value: String)
data class MoodLog(val timeLabel: String, val label: String, val value: String, val numericScore: Float)
data class DietEntry(val time: String, val food: String, val carbs: String, val numericValue: Float)
data class ExerciseEntry(val time: String, val activity: String, val duration: String, val numericValue: Float)
data class HeartRateEntry(val time: String, val bpm: Int, val status: String)

data class CgmNode(
    val timeLabel: String,
    val value: Double,
    val trend: String = "→",
    val samplingInterval: String,
    val rateOfChange: String
)

data class ClinicalCgmReport(
    val deviceInfo: String, val dataCoverage: String,
    val avgGlucose: String, val medianGlucose: String, val percentiles: String,
    val sd: String, val cv: String, val mage: String, val gmi: String,
    val tirTarget: Int, val tirHigh: Int, val tirLow: Int,
    val tbrLevel1: String, val tbrLevel2: String,
    val tarLevel1: String, val tarLevel2: String,
    val wearTime: String, val completeness: String, val signalLoss: String,
    val hypoEvents: Int, val hyperEvents: Int, val alertsTriggered: Int,
    val clinicalAdvice: String
)

data class ClinicalMoodReport(
    val meanScore: String, val medianScore: String, val variabilitySD: String, val instabilityIndex: String,
    val positiveAffect: Int, val neutralAffect: Int, val negativeAffect: Int,
    val phq9Score: String, val gad7Score: String, val pssScore: String
)

// 🔔 核心加装：提醒时间和提醒频率
data class UserProfile(var age: String, var gender: String, var height: String, var weight: String, var diagnosisDate: String, var insulinUse: String, var hba1c: String, var reminderTime: String, var reminderFrequency: String)

class ReportViewModel : ViewModel() {
    var currentScale = mutableStateOf("日")

    val dietLogs = mutableStateListOf<DietEntry>()
    val exerciseLogs = mutableStateListOf<ExerciseEntry>()
    val hrLogs = mutableStateListOf<HeartRateEntry>()
    val cgmNodes = mutableStateListOf<CgmNode>()
    val moodNodes = mutableStateListOf<MoodLog>()

    var cgmReport = mutableStateOf(ClinicalCgmReport(
        deviceInfo = "With v1 | 采样率 3min/次", dataCoverage = "99%",
        avgGlucose = "6.4 mmol/L", medianGlucose = "6.2 mmol/L", percentiles = "IQR: 4.8-7.5",
        sd = "1.2", cv = "18.7% (<36%)", mage = "2.1", gmi = "6.1%",
        tirTarget = 92, tirHigh = 5, tirLow = 3,
        tbrLevel1 = "2%", tbrLevel2 = "1% (<3.0)", tarLevel1 = "4%", tarLevel2 = "1% (>13.9)",
        wearTime = "98%", completeness = "99.5%", signalLoss = "15 min",
        hypoEvents = 1, hyperEvents = 2, alertsTriggered = 3,
        clinicalAdvice = "今日血糖极佳，TIR 达标。趋势平稳，无需调整基础率。"
    ))

    var moodReport = mutableStateOf(ClinicalMoodReport("7.5", "7.8", "0.9", "Low", 70, 20, 10, "2 (极轻)", "3 (极轻)", "12 (轻度)"))

    // 🔔 初始设定：默认餐后 30 分钟，每天 3 次
    var userProfile = mutableStateOf(UserProfile("28 岁", "女", "175 cm", "70 kg", "2023 年 5 月", "是", "6.2 %", "餐后 30 分钟", "每天 3 次"))

    init {
        cgmNodes.addAll(listOf(
            CgmNode("08:00", 5.4, "→", "3min", "0.00"),
            CgmNode("12:00", 6.1, "↗", "3min", "+0.23"),
            CgmNode("18:00", 5.8, "↘", "3min", "-0.10")
        ))
        moodNodes.addAll(listOf(MoodLog("09:00", "清醒", "极佳", 8.5f), MoodLog("12:00", "餐前", "疲惫", 4.5f), MoodLog("15:00", "餐后", "平稳", 7.0f)))
        dietLogs.addAll(listOf(DietEntry("08:30", "全麦面包", "30g", 30f), DietEntry("12:30", "沙拉", "15g", 15f)))
        exerciseLogs.addAll(listOf(ExerciseEntry("09:00", "慢跑", "20min", 20f), ExerciseEntry("18:00", "拉伸", "15min", 15f)))
        hrLogs.addAll(listOf(HeartRateEntry("08:00", 72, "静息"), HeartRateEntry("19:00", 110, "运动")))
    }

    // 🚀 Chatbot 自动解析与推算引擎
    fun processChatInput(input: String) {
        val timeMatch = Regex("([0-1]?[0-9]|2[0-3]):([0-5][0-9])").find(input)?.value
        val time = timeMatch ?: java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"))
        val inputCleaned = if (timeMatch != null) input.replace(timeMatch, "") else input
        val extractedNumber = Regex("(\\d+\\.\\d+|\\d+)").find(inputCleaned)?.value?.toFloatOrNull()

        var matchedSomething = false

        if (Regex("血糖|低血糖|高血糖|测了|mmol").containsMatchIn(inputCleaned)) {
            val bgMatch = Regex("(\\d+\\.\\d+|\\d+)").find(inputCleaned.substringAfter(Regex("血糖|测了").find(inputCleaned)?.value ?: ""))
            val bg = bgMatch?.value?.toDoubleOrNull() ?: extractedNumber?.toDouble() ?: 5.5
            addCgmNode(bg, time)
            matchedSomething = true
        }

        if (Regex("胰岛素|打针|单位|U").containsMatchIn(inputCleaned)) {
            val units = extractedNumber ?: 2f
            dietLogs.add(0, DietEntry(time, "注射胰岛素", "${units} U", units))
            matchedSomething = true
        }

        if (Regex("吃|餐|饭|饮食|碳水").containsMatchIn(inputCleaned)) {
            val carbs = extractedNumber ?: 40f
            dietLogs.add(0, DietEntry(time, inputCleaned, "约 ${carbs}g", carbs))
            matchedSomething = true
        }

        if (Regex("跑|步|运动|锻炼|健身|游泳|骑车").containsMatchIn(inputCleaned)) {
            val duration = extractedNumber ?: 30f
            exerciseLogs.add(0, ExerciseEntry(time, inputCleaned, "${duration}min", duration))
            matchedSomething = true
        }

        if (Regex("心率|心跳|bpm|BPM").containsMatchIn(inputCleaned)) {
            val bpm = extractedNumber?.toInt() ?: 85
            hrLogs.add(0, HeartRateEntry(time, bpm, "自动识别"))
            matchedSomething = true
        }

        val moodKeywords = Regex("心情|情绪|感觉|状态|开心|高兴|爽|好|难过|生气|郁闷|压力|累|烦|平稳|平静|差|低落")
        if (moodKeywords.containsMatchIn(inputCleaned) || !matchedSomething) {
            val score = when {
                Regex("极佳|特别好|开心|高兴|爽").containsMatchIn(inputCleaned) -> 8.5f
                Regex("难过|生气|郁闷|烦|差|压力|累|低落|糟").containsMatchIn(inputCleaned) -> 3.5f
                Regex("平稳|平静|还行|不错|好").containsMatchIn(inputCleaned) -> 7.0f
                else -> extractedNumber ?: 6.5f
            }
            val label = if (score >= 8f) "极佳" else if (score <= 4f) "低落" else "平稳"
            addMoodNode(score, label, time)
        }
    }

    fun addCgmNode(bg: Double, timeInput: String) {
        val finalTime = timeInput.ifBlank { java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm")) }
        val lastNode = cgmNodes.lastOrNull()
        val trend: String; val rateStr: String
        if (lastNode != null) {
            val diff = bg - lastNode.value
            trend = when { diff >= 0.2 -> "↑"; diff > 0.0 -> "↗"; diff <= -0.2 -> "↓"; diff < 0.0 -> "↘"; else -> "→" }
            val rateVal = diff / 3.0; val formattedRate = String.format("%.2f", rateVal)
            rateStr = if (rateVal > 0) "+$formattedRate" else formattedRate
        } else { trend = "→"; rateStr = "0.00" }
        cgmNodes.add(CgmNode(finalTime, bg, trend, "3min", rateStr))
    }

    fun addMoodNode(score: Float, label: String, timeInput: String) {
        val finalTime = timeInput.ifBlank { java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm")) }
        moodNodes.add(MoodLog(finalTime, label, "手动记录", score))
    }

    fun addDiet(f: String, c: String) {
        val cv = c.filter { it.isDigit() || it == '.' }.toFloatOrNull() ?: 0f
        val time = java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"))
        dietLogs.add(0, DietEntry(time, f, "${cv}g", cv))
    }

    fun addExercise(a: String, d: String) {
        val dv = d.filter { it.isDigit() || it == '.' }.toFloatOrNull() ?: 0f
        val time = java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"))
        exerciseLogs.add(0, ExerciseEntry(time, a, "${dv}min", dv))
    }

    fun addHeartRate(b: Int, s: String) {
        val time = java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"))
        hrLogs.add(0, HeartRateEntry(time, b, s))
    }

    fun updateProfile(newProfile: UserProfile) { userProfile.value = newProfile }
}