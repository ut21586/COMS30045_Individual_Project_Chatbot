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
//data class UserProfile(var age: String, var gender: String, var height: String, var weight: String, var diagnosisDate: String, var insulinUse: String, var hba1c: String)
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
//    var userProfile = mutableStateOf(UserProfile("28 岁", "女", "175 cm", "70 kg", "2023 年 5 月", "是", "6.2 %"))
//
//    init {
//        cgmNodes.addAll(listOf(
//            CgmNode("08:00", 5.4, "→", "3min", "0.0"),
//            CgmNode("12:00", 6.1, "↗", "3min", "+0.1"),
//            CgmNode("18:00", 5.8, "↘", "3min", "-0.1")
//        ))
//        moodNodes.addAll(listOf(MoodLog("09:00", "清醒", "极佳", 8.5f), MoodLog("15:00", "餐后", "平稳", 7.0f)))
//        dietLogs.addAll(listOf(DietEntry("08:30", "全麦面包", "30g", 30f), DietEntry("12:30", "沙拉", "15g", 15f)))
//        exerciseLogs.addAll(listOf(ExerciseEntry("09:00", "慢跑", "20min", 20f), ExerciseEntry("18:00", "拉伸", "15min", 15f)))
//        hrLogs.addAll(listOf(HeartRateEntry("08:00", 72, "静息"), HeartRateEntry("19:00", 110, "运动")))
//    }
//
//    // 🚀 终极智能提取引擎：支持同一句话中包含多种意图，全部独立解析，绝不漏记！
//    fun processChatInput(input: String) {
//        val time = java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"))
//        var matchedSomething = false
//
//        // 1. 解析血糖 CGM
//        if (Regex("血糖|低血糖|高血糖|测了|mmol").containsMatchIn(input)) {
//            // 在关键字附近寻找数字
//            val bgMatch = Regex("(\\d+\\.\\d+|\\d+)").find(input.substringAfter(Regex("血糖|测了").find(input)?.value ?: ""))
//            val bg = bgMatch?.value?.toDoubleOrNull() ?: run { Regex("(\\d+\\.\\d+|\\d+)").find(input)?.value?.toDoubleOrNull() ?: 5.5 }
//
//            val trend = if (Regex("上|升|高|↑").containsMatchIn(input)) "↑"
//            else if (Regex("下|降|低|↓").containsMatchIn(input)) "↓" else "→"
//
//            val rateMatch = Regex("(速率|变化)[^\\d+-]*([+-]?\\d+\\.\\d+|[+-]?\\d+)").find(input)
//            val rateVal = rateMatch?.groupValues?.get(2) ?: "0.0"
//            val rateSign = if (trend == "↓" && !rateVal.startsWith("-")) "-" else if (trend == "↑" && !rateVal.startsWith("+") && rateVal != "0.0") "+" else ""
//            val finalRate = if (rateVal == "0.0") "0.0" else if(rateVal.startsWith("+") || rateVal.startsWith("-")) rateVal else "$rateSign$rateVal"
//
//            cgmNodes.add(CgmNode(time, bg, trend, "3min", finalRate))
//            matchedSomething = true
//        }
//
//        // 2. 解析胰岛素 (属于饮食上下文行为)
//        if (Regex("胰岛素|打针|单位|U").containsMatchIn(input)) {
//            val unitMatch = Regex("(\\d+\\.\\d+|\\d+)").find(input.substringAfter(Regex("胰岛素|打针").find(input)?.value ?: ""))
//            val units = unitMatch?.value?.toFloatOrNull() ?: Regex("(\\d+\\.\\d+|\\d+)").find(input)?.value?.toFloatOrNull() ?: 2f
//            dietLogs.add(0, DietEntry(time, "注射胰岛素", "${units} U", units))
//            matchedSomething = true
//        }
//
//        // 3. 解析饮食碳水
//        if (Regex("吃|餐|饭|喝|碳水").containsMatchIn(input)) {
//            val carbMatch = Regex("(\\d+\\.\\d+|\\d+)").find(input.substringAfter(Regex("吃|餐|饭|碳水").find(input)?.value ?: ""))
//            val carbs = carbMatch?.value?.toFloatOrNull() ?: Regex("(\\d+\\.\\d+|\\d+)").find(input)?.value?.toFloatOrNull() ?: 40f
//            dietLogs.add(0, DietEntry(time, "餐饮记录", "约 ${carbs}g", carbs))
//            matchedSomething = true
//        }
//
//        // 4. 解析运动
//        if (Regex("跑|步|动|锻炼|健身|游泳|骑车").containsMatchIn(input)) {
//            val exMatch = Regex("(\\d+\\.\\d+|\\d+)").find(input.substringAfter(Regex("跑|步|动|锻炼").find(input)?.value ?: ""))
//            val duration = exMatch?.value?.toFloatOrNull() ?: Regex("(\\d+\\.\\d+|\\d+)").find(input)?.value?.toFloatOrNull() ?: 30f
//            exerciseLogs.add(0, ExerciseEntry(time, "运动行为", "${duration}min", duration))
//            matchedSomething = true
//        }
//
//        // 5. 解析心率
//        if (Regex("心|跳|bpm|BPM").containsMatchIn(input)) {
//            val hrMatch = Regex("(\\d{2,3})").find(input)
//            val bpm = hrMatch?.value?.toIntOrNull() ?: 85
//            hrLogs.add(0, HeartRateEntry(time, bpm, "自动提取"))
//            matchedSomething = true
//        }
//
//        // 6. 解析情绪 (如果包含了情绪关键字，或者上面啥都没匹配到)
//        val moodKeywords = Regex("心情|情绪|开心|高兴|爽|好|难过|生气|郁闷|压力|累|烦|平稳|平静|差")
//        if (moodKeywords.containsMatchIn(input) || !matchedSomething) {
//            val score = if (Regex("开心|高兴|爽|好").containsMatchIn(input)) 8.5f
//            else if (Regex("难过|生气|郁闷|烦|差").containsMatchIn(input)) 4.0f
//            else 7.0f
//            moodNodes.add(0, MoodLog(time, "状态记录", input, score))
//        }
//    }
//
//    fun addDiet(f: String, c: String) { val cv = c.filter { it.isDigit() || it == '.' }.toFloatOrNull() ?: 0f; dietLogs.add(0, DietEntry("手动", f, "${cv}g", cv)) }
//    fun addExercise(a: String, d: String) { val dv = d.filter { it.isDigit() || it == '.' }.toFloatOrNull() ?: 0f; exerciseLogs.add(0, ExerciseEntry("手动", a, "${dv}min", dv)) }
//    fun addHeartRate(b: Int, s: String) { hrLogs.add(0, HeartRateEntry("手动", b, s)) }
//    fun addCgmNode(bg: Double, time: String, interval: String, trend: String, rate: String) {
//        val finalTime = time.ifBlank { java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm")) }
//        val finalInterval = interval.ifBlank { "3min" }
//        cgmNodes.add(CgmNode(finalTime, bg, trend, finalInterval, rate))
//    }
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

data class UserProfile(var age: String, var gender: String, var height: String, var weight: String, var diagnosisDate: String, var insulinUse: String, var hba1c: String)

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
    var userProfile = mutableStateOf(UserProfile("28 岁", "女", "175 cm", "70 kg", "2023 年 5 月", "是", "6.2 %"))

    init {
        cgmNodes.addAll(listOf(
            CgmNode("08:00", 5.4, "→", "3min", "0.0"),
            CgmNode("12:00", 6.1, "↗", "3min", "+0.1"),
            CgmNode("18:00", 5.8, "↘", "3min", "-0.1")
        ))
        moodNodes.addAll(listOf(MoodLog("09:00", "清醒", "极佳", 8.5f), MoodLog("15:00", "餐后", "平稳", 7.0f)))
        dietLogs.addAll(listOf(DietEntry("08:30", "全麦面包", "30g", 30f), DietEntry("12:30", "沙拉", "15g", 15f)))
        exerciseLogs.addAll(listOf(ExerciseEntry("09:00", "慢跑", "20min", 20f), ExerciseEntry("18:00", "拉伸", "15min", 15f)))
        hrLogs.addAll(listOf(HeartRateEntry("08:00", 72, "静息"), HeartRateEntry("19:00", 110, "运动")))
    }

    // 🚀 并行智能提取引擎：彻底解决“词汇冲突”与“漏记”
    fun processChatInput(input: String) {
        val time = java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"))
        var matchedSomething = false
        val extractedNumber = Regex("(\\d+\\.\\d+|\\d+)").find(input)?.value?.toFloatOrNull()

        // 1. CGM 解析
        if (Regex("血糖|低血糖|高血糖|测了").containsMatchIn(input)) {
            val bg = extractedNumber ?: 5.5f
            val trend = if (Regex("上|升|高|↑").containsMatchIn(input)) "↑" else if (Regex("下|降|低|↓").containsMatchIn(input)) "↓" else "→"
            val rateMatch = Regex("(速率|变化)[^\\d+-]*([+-]?\\d+\\.\\d+|[+-]?\\d+)").find(input)
            val rateVal = rateMatch?.groupValues?.get(2) ?: "0.0"
            val rateSign = if (trend == "↓" && !rateVal.startsWith("-")) "-" else if (trend == "↑" && !rateVal.startsWith("+") && rateVal != "0.0") "+" else ""
            val finalRate = if (rateVal == "0.0") "0.0" else if(rateVal.startsWith("+") || rateVal.startsWith("-")) rateVal else "$rateSign$rateVal"
            cgmNodes.add(CgmNode(time, bg.toDouble(), trend, "3min", finalRate))
            matchedSomething = true
        }

        // 2. 胰岛素解析
        if (Regex("胰岛素|打针|单位|U").containsMatchIn(input)) {
            val units = extractedNumber ?: 2f
            dietLogs.add(0, DietEntry(time, "注射胰岛素", "${units} U", units))
            matchedSomething = true
        }

        // 3. 饮食解析
        if (Regex("吃|餐|饭|饮食|碳水").containsMatchIn(input)) {
            val carbs = extractedNumber ?: 40f
            dietLogs.add(0, DietEntry(time, input, "约 ${carbs}g", carbs))
            matchedSomething = true
        }

        // 4. 运动解析
        if (Regex("跑|步|运动|锻炼|健身|游泳|骑车").containsMatchIn(input)) {
            val duration = extractedNumber ?: 30f
            exerciseLogs.add(0, ExerciseEntry(time, input, "${duration}min", duration))
            matchedSomething = true
        }

        // 5. 心率解析 (🔥已修复：严格匹配心率，不再被“心情”触发)
        if (Regex("心率|心跳|bpm|BPM").containsMatchIn(input)) {
            val bpm = extractedNumber?.toInt() ?: 85
            hrLogs.add(0, HeartRateEntry(time, bpm, "自动识别"))
            matchedSomething = true
        }

        // 6. 情绪解析 (🔥独立解析：如果包含情绪词或啥都没匹配到，自动打分绘图)
        val moodKeywords = Regex("心情|情绪|感觉|状态|开心|高兴|爽|好|难过|生气|郁闷|压力|累|烦|平稳|平静|差|低落")
        if (moodKeywords.containsMatchIn(input) || !matchedSomething) {
            val score = when {
                Regex("极佳|特别好|开心|高兴|爽").containsMatchIn(input) -> 8.5f
                Regex("难过|生气|郁闷|烦|差|压力|累|低落|糟").containsMatchIn(input) -> 3.5f
                Regex("平稳|平静|还行|不错|好").containsMatchIn(input) -> 7.0f
                else -> 6.5f
            }
            // 加在末尾以保证在表情曲线上从左往右绘制
            moodNodes.add(MoodLog(time, "状态记录", input, score))
        }
    }

    fun addDiet(f: String, c: String) { val cv = c.filter { it.isDigit() || it == '.' }.toFloatOrNull() ?: 0f; dietLogs.add(0, DietEntry("手动", f, "${cv}g", cv)) }
    fun addExercise(a: String, d: String) { val dv = d.filter { it.isDigit() || it == '.' }.toFloatOrNull() ?: 0f; exerciseLogs.add(0, ExerciseEntry("手动", a, "${dv}min", dv)) }
    fun addHeartRate(b: Int, s: String) { hrLogs.add(0, HeartRateEntry("手动", b, s)) }
    fun addCgmNode(bg: Double, time: String, interval: String, trend: String, rate: String) {
        val finalTime = time.ifBlank { java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm")) }
        val finalInterval = interval.ifBlank { "3min" }
        cgmNodes.add(CgmNode(finalTime, bg, trend, finalInterval, rate))
    }
    fun updateProfile(newProfile: UserProfile) { userProfile.value = newProfile }
}