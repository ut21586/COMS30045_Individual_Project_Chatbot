//
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
//data class UserProfile(var age: String, var gender: String, var height: String, var weight: String, var diagnosisDate: String, var insulinUse: String, var hba1c: String, var reminderTime: String, var reminderFrequency: String)
//
//class ReportViewModel : ViewModel() {
//    var currentScale = mutableStateOf("日 (Day)")
//
//    val dietLogs = mutableStateListOf<DietEntry>()
//    val exerciseLogs = mutableStateListOf<ExerciseEntry>()
//    val hrLogs = mutableStateListOf<HeartRateEntry>()
//    val cgmNodes = mutableStateListOf<CgmNode>()
//    val moodNodes = mutableStateListOf<MoodLog>()
//
//    var cgmReport = mutableStateOf(ClinicalCgmReport(
//        deviceInfo = "With v1 | 3min/次 (times)", dataCoverage = "99%",
//        avgGlucose = "6.8 mmol/L", medianGlucose = "6.5 mmol/L", percentiles = "IQR: 5.2-8.1",
//        sd = "1.5", cv = "22.4% (<36%)", mage = "2.8", gmi = "6.3%",
//        tirTarget = 88, tirHigh = 8, tirLow = 4,
//        tbrLevel1 = "3%", tbrLevel2 = "1% (<3.0)", tarLevel1 = "6%", tarLevel2 = "2% (>13.9)",
//        wearTime = "99%", completeness = "99.8%", signalLoss = "5 min",
//        hypoEvents = 1, hyperEvents = 3, alertsTriggered = 4,
//        clinicalAdvice = "餐后存在高血糖波动，TIR 基本达标。建议增加餐后散步。(Post-meal spikes detected, TIR acceptable. Post-meal walk recommended.)"
//    ))
//
//    var moodReport = mutableStateOf(ClinicalMoodReport("6.8", "7.0", "1.2", "Medium", 60, 25, 15, "3 (轻微/Mild)", "4 (轻微/Mild)", "15 (中度/Moderate)"))
//
//    var userProfile = mutableStateOf(UserProfile("28 岁 (Y/O)", "女 (Female)", "175 cm", "70 kg", "2023/05", "是 (Yes)", "6.2 %", "餐后30分 (30m Post-meal)", "每天3次 (3 times/day)"))
//
//    init {
//        // 🔥 极其密集的全天候 CGM 连续数据 (31 个节点)，呈现极高精度的波峰波谷
//        cgmNodes.addAll(listOf(
//            CgmNode("07:00", 5.2, "→", "3min", "0.00"), CgmNode("07:30", 5.4, "↗", "3min", "+0.03"),
//            CgmNode("08:00", 5.8, "↗", "3min", "+0.04"), CgmNode("08:30", 7.1, "↑", "3min", "+0.15"),
//            CgmNode("09:00", 8.4, "↑", "3min", "+0.18"), CgmNode("09:30", 8.1, "↘", "3min", "-0.05"),
//            CgmNode("10:00", 7.2, "↓", "3min", "-0.12"), CgmNode("10:30", 6.4, "↘", "3min", "-0.08"),
//            CgmNode("11:00", 5.9, "→", "3min", "-0.02"), CgmNode("11:30", 5.7, "→", "3min", "-0.01"),
//            CgmNode("12:00", 5.6, "→", "3min", "0.00"), CgmNode("12:30", 6.2, "↗", "3min", "+0.08"),
//            CgmNode("13:00", 8.5, "↑", "3min", "+0.25"), CgmNode("13:30", 9.8, "↑", "3min", "+0.18"),
//            CgmNode("14:00", 9.2, "↘", "3min", "-0.08"), CgmNode("14:30", 8.1, "↓", "3min", "-0.15"),
//            CgmNode("15:00", 7.0, "↘", "3min", "-0.12"), CgmNode("15:30", 6.2, "↘", "3min", "-0.08"),
//            CgmNode("16:00", 5.8, "→", "3min", "-0.03"), CgmNode("16:30", 5.6, "→", "3min", "-0.01"),
//            CgmNode("17:00", 5.4, "→", "3min", "0.00"), CgmNode("17:30", 5.5, "→", "3min", "+0.01"),
//            CgmNode("18:00", 5.7, "↗", "3min", "+0.03"), CgmNode("18:30", 6.5, "↑", "3min", "+0.12"),
//            CgmNode("19:00", 8.2, "↑", "3min", "+0.22"), CgmNode("19:30", 9.1, "↗", "3min", "+0.10"),
//            CgmNode("20:00", 8.5, "↘", "3min", "-0.08"), CgmNode("20:30", 7.4, "↓", "3min", "-0.15"),
//            CgmNode("21:00", 6.5, "↘", "3min", "-0.10"), CgmNode("21:30", 6.0, "→", "3min", "-0.04"),
//            CgmNode("22:00", 5.8, "→", "3min", "-0.01")
//        ))
//
//        // 🔥 高频情绪锚点，完美贴合血糖峰谷变化
//        moodNodes.addAll(listOf(
//            MoodLog("07:30", "平稳 (Stable)", "晨起", 7.0f), MoodLog("08:30", "极佳 (Great)", "早餐后", 8.5f),
//            MoodLog("10:00", "烦躁 (Annoyed)", "工作压力", 5.5f), MoodLog("11:30", "低落 (Low)", "饥饿疲惫", 4.0f),
//            MoodLog("13:00", "极佳 (Great)", "午餐满足", 8.0f), MoodLog("14:30", "疲倦 (Tired)", "餐后犯困", 5.0f),
//            MoodLog("16:00", "平稳 (Stable)", "下午茶", 6.5f), MoodLog("17:30", "低落 (Low)", "下班前", 4.5f),
//            MoodLog("19:00", "极佳 (Great)", "晚餐放松", 8.5f), MoodLog("21:00", "平稳 (Stable)", "睡前", 7.5f)
//        ))
//
//        dietLogs.addAll(listOf(DietEntry("08:00", "全麦面包 (Whole Wheat)", "30g", 30f), DietEntry("12:00", "轻食沙拉 (Salad)", "15g", 15f), DietEntry("18:00", "糙米饭 (Brown Rice)", "45g", 45f)))
//        exerciseLogs.addAll(listOf(ExerciseEntry("09:30", "通勤步行 (Walking)", "15min", 15f), ExerciseEntry("18:30", "瑜伽拉伸 (Yoga)", "20min", 20f)))
//        hrLogs.addAll(listOf(HeartRateEntry("08:00", 72, "静息 (Resting)"), HeartRateEntry("14:00", 95, "压力 (Stress)"), HeartRateEntry("19:00", 115, "运动 (Active)")))
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
//            dietLogs.add(0, DietEntry(time, "注射胰岛素 (Insulin Injection)", "${units} U", units))
//            matchedSomething = true
//        }
//
//        if (Regex("吃|餐|饭|饮食|碳水").containsMatchIn(inputCleaned)) {
//            val carbs = extractedNumber ?: 40f
//            dietLogs.add(0, DietEntry(time, inputCleaned, "约 (Approx) ${carbs}g", carbs))
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
//            hrLogs.add(0, HeartRateEntry(time, bpm, "自动识别 (Auto-detected)"))
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
//            val label = if (score >= 8f) "极佳 (Great)" else if (score <= 4f) "低落 (Low)" else "平稳 (Stable)"
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
//        moodNodes.add(MoodLog(finalTime, label, "手动记录 (Manual Log)", score))
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

data class UserProfile(var age: String, var gender: String, var height: String, var weight: String, var diagnosisDate: String, var insulinUse: String, var hba1c: String, var reminderTime: String, var reminderFrequency: String)

class ReportViewModel : ViewModel() {
    var currentScale = mutableStateOf("日 (Day)")

    val dietLogs = mutableStateListOf<DietEntry>()
    val exerciseLogs = mutableStateListOf<ExerciseEntry>()
    val hrLogs = mutableStateListOf<HeartRateEntry>()
    val cgmNodes = mutableStateListOf<CgmNode>()
    val moodNodes = mutableStateListOf<MoodLog>()

    var cgmReport = mutableStateOf(ClinicalCgmReport(
        deviceInfo = "With v1 | 3min/次 (times)", dataCoverage = "99%",
        avgGlucose = "6.4 mmol/L", medianGlucose = "6.2 mmol/L", percentiles = "IQR: 4.8-7.5",
        sd = "1.2", cv = "18.7% (<36%)", mage = "2.1", gmi = "6.1%",
        tirTarget = 92, tirHigh = 5, tirLow = 3,
        tbrLevel1 = "2%", tbrLevel2 = "1% (<3.0)", tarLevel1 = "4%", tarLevel2 = "1% (>13.9)",
        wearTime = "98%", completeness = "99.5%", signalLoss = "15 min",
        hypoEvents = 1, hyperEvents = 2, alertsTriggered = 3,
        clinicalAdvice = "今日血糖极佳，TIR 达标。趋势平稳，无需调整基础率。(Excellent BG today, TIR on target. Stable trend, no basal adjustment needed.)"
    ))

    var moodReport = mutableStateOf(ClinicalMoodReport("7.5", "7.8", "0.9", "Low", 70, 20, 10, "2 (极轻/Mild)", "3 (极轻/Mild)", "12 (轻度/Moderate)"))

    var userProfile = mutableStateOf(UserProfile("28 岁 (Y/O)", "女 (Female)", "175 cm", "70 kg", "2023/05", "是 (Yes)", "6.2 %", "餐后30分 (30m Post-meal)", "每天3次 (3 times/day)"))

    init {
        // 🔥 极致高密度 CGM 阵列 (48个节点，模拟真实人体24小时代谢起伏)
        cgmNodes.addAll(listOf(
            CgmNode("00:00", 5.2, "→", "3min", "0.00"), CgmNode("00:30", 5.1, "↘", "3min", "-0.01"),
            CgmNode("01:00", 5.1, "→", "3min", "0.00"), CgmNode("01:30", 5.0, "↘", "3min", "-0.01"),
            CgmNode("02:00", 5.2, "↗", "3min", "+0.02"), CgmNode("02:30", 5.3, "↗", "3min", "+0.01"),
            CgmNode("03:00", 5.2, "↘", "3min", "-0.01"), CgmNode("03:30", 5.1, "↘", "3min", "-0.01"),
            CgmNode("04:00", 5.0, "↘", "3min", "-0.01"), CgmNode("04:30", 5.2, "↗", "3min", "+0.02"),
            CgmNode("05:00", 5.3, "↗", "3min", "+0.01"), CgmNode("05:30", 5.4, "↗", "3min", "+0.01"),
            CgmNode("06:00", 5.5, "↗", "3min", "+0.01"), CgmNode("06:30", 5.7, "↗", "3min", "+0.02"),
            CgmNode("07:00", 6.0, "↑", "3min", "+0.03"), CgmNode("07:30", 6.2, "↗", "3min", "+0.02"), // 黎明现象
            CgmNode("08:00", 6.5, "↗", "3min", "+0.03"), CgmNode("08:30", 8.2, "↑", "3min", "+0.17"), // 早餐
            CgmNode("09:00", 9.8, "↑", "3min", "+0.16"), CgmNode("09:30", 8.5, "↓", "3min", "-0.13"),
            CgmNode("10:00", 7.2, "↓", "3min", "-0.13"), CgmNode("10:30", 6.5, "↘", "3min", "-0.07"),
            CgmNode("11:00", 6.1, "↘", "3min", "-0.04"), CgmNode("11:30", 5.9, "↘", "3min", "-0.02"),
            CgmNode("12:00", 5.8, "↘", "3min", "-0.01"), CgmNode("12:30", 7.8, "↑", "3min", "+0.20"), // 午餐
            CgmNode("13:00", 10.2, "↑", "3min", "+0.24"), CgmNode("13:30", 9.5, "↓", "3min", "-0.07"),
            CgmNode("14:00", 8.1, "↓", "3min", "-0.14"), CgmNode("14:30", 7.0, "↓", "3min", "-0.11"),
            CgmNode("15:00", 6.4, "↘", "3min", "-0.06"), CgmNode("15:30", 6.0, "↘", "3min", "-0.04"),
            CgmNode("16:00", 5.8, "↘", "3min", "-0.02"), CgmNode("16:30", 5.7, "↘", "3min", "-0.01"),
            CgmNode("17:00", 5.5, "↘", "3min", "-0.02"), CgmNode("17:30", 5.4, "↘", "3min", "-0.01"),
            CgmNode("18:00", 5.5, "↗", "3min", "+0.01"), CgmNode("18:30", 7.5, "↑", "3min", "+0.20"), // 晚餐
            CgmNode("19:00", 9.1, "↑", "3min", "+0.16"), CgmNode("19:30", 8.4, "↓", "3min", "-0.07"),
            CgmNode("20:00", 7.2, "↓", "3min", "-0.12"), CgmNode("20:30", 6.6, "↘", "3min", "-0.06"),
            CgmNode("21:00", 6.2, "↘", "3min", "-0.04"), CgmNode("21:30", 5.9, "↘", "3min", "-0.03"),
            CgmNode("22:00", 5.7, "↘", "3min", "-0.02"), CgmNode("22:30", 5.6, "↘", "3min", "-0.01"),
            CgmNode("23:00", 5.5, "↘", "3min", "-0.01"), CgmNode("23:30", 5.4, "↘", "3min", "-0.01")
        ))

        // 🔥 高密度情绪阵列 (24个节点，精确对应生理波动曲线)
        moodNodes.addAll(listOf(
            MoodLog("06:00", "平稳 (Stable)", "晨起", 6.5f), MoodLog("07:00", "极佳 (Great)", "早餐前", 8.0f),
            MoodLog("08:00", "极佳 (Great)", "早餐后", 8.5f), MoodLog("09:00", "平稳 (Stable)", "工作", 7.0f),
            MoodLog("10:00", "压力 (Stressed)", "会议", 5.5f), MoodLog("11:00", "疲惫 (Tired)", "高耗能", 4.5f),
            MoodLog("12:00", "低落 (Low)", "餐前饥饿", 4.0f), MoodLog("13:00", "极佳 (Great)", "午餐满意", 9.0f),
            MoodLog("14:00", "平稳 (Stable)", "午后", 7.5f), MoodLog("15:00", "疲惫 (Tired)", "犯困", 5.0f),
            MoodLog("16:00", "烦躁 (Annoyed)", "工作堆积", 4.5f), MoodLog("17:00", "低落 (Low)", "下班前", 4.0f),
            MoodLog("18:00", "开心 (Happy)", "离开公司", 8.0f), MoodLog("19:00", "极佳 (Great)", "晚餐后", 9.5f),
            MoodLog("20:00", "平稳 (Stable)", "休息", 7.5f), MoodLog("21:00", "平静 (Calm)", "阅读", 8.0f),
            MoodLog("22:00", "平稳 (Stable)", "洗漱", 7.0f), MoodLog("23:00", "平静 (Calm)", "准备入睡", 7.5f)
        ))

        dietLogs.addAll(listOf(DietEntry("08:30", "全麦面包 (Whole Wheat)", "30g", 30f), DietEntry("12:30", "沙拉 (Salad)", "15g", 15f)))
        exerciseLogs.addAll(listOf(ExerciseEntry("09:00", "慢跑 (Jogging)", "20min", 20f), ExerciseEntry("18:00", "拉伸 (Stretching)", "15min", 15f)))
        hrLogs.addAll(listOf(HeartRateEntry("08:00", 72, "静息 (Resting)"), HeartRateEntry("19:00", 110, "运动 (Active)")))
    }

    // 🚀 Chatbot 多维自动解析与同步提取引擎
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
            dietLogs.add(0, DietEntry(time, "注射胰岛素 (Insulin Injection)", "${units} U", units))
            matchedSomething = true
        }

        if (Regex("吃|餐|饭|饮食|碳水").containsMatchIn(inputCleaned)) {
            val carbs = extractedNumber ?: 40f
            dietLogs.add(0, DietEntry(time, inputCleaned, "约 (Approx) ${carbs}g", carbs))
            matchedSomething = true
        }

        if (Regex("跑|步|运动|锻炼|健身|游泳|骑车").containsMatchIn(inputCleaned)) {
            val duration = extractedNumber ?: 30f
            exerciseLogs.add(0, ExerciseEntry(time, inputCleaned, "${duration}min", duration))
            matchedSomething = true
        }

        if (Regex("心率|心跳|bpm|BPM").containsMatchIn(inputCleaned)) {
            val bpm = extractedNumber?.toInt() ?: 85
            hrLogs.add(0, HeartRateEntry(time, bpm, "自动识别 (Auto-detected)"))
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
            val label = if (score >= 8f) "极佳 (Great)" else if (score <= 4f) "低落 (Low)" else "平稳 (Stable)"
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
        moodNodes.add(MoodLog(finalTime, label, "手动记录 (Manual Log)", score))
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