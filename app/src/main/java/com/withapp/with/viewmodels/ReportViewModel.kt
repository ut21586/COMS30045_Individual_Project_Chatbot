
package com.withapp.with.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

data class HealthLog(val timeLabel: String, val type: String, val desc: String, val value: String)
data class MoodLog(val timeLabel: String, val label: String, val value: String, val numericScore: Float)
data class DietEntry(val time: String, val food: String, val carbs: String, val numericValue: Float)
data class ExerciseEntry(val time: String, val activity: String, val duration: String, val numericValue: Float)
data class HeartRateEntry(val time: String, val bpm: Int, val status: String)

data class CgmNode(val timeLabel: String, val value: Double, val trend: String = "→", val samplingInterval: String, val rateOfChange: String)

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

data class UserProfile(
    var age: String, var gender: String, var height: String, var weight: String,
    var diagnosisDate: String, var insulinUse: String, var medication: String,
    var targetRange: String, var hba1c: String, var reminderTime: String, var reminderFrequency: String
)

class ReportViewModel : ViewModel() {
    var currentScale = mutableStateOf("日 (Day)")

    val dietLogs = mutableStateListOf<DietEntry>()
    val exerciseLogs = mutableStateListOf<ExerciseEntry>()
    val hrLogs = mutableStateListOf<HeartRateEntry>()
    val cgmNodes = mutableStateListOf<CgmNode>()
    val moodNodes = mutableStateListOf<MoodLog>()

    var cgmReport = mutableStateOf(ClinicalCgmReport(
        deviceInfo = "With v3 (Research) | 3min/次", dataCoverage = "99%",
        avgGlucose = "6.4 mmol/L", medianGlucose = "6.2 mmol/L", percentiles = "IQR: 4.8-7.5",
        sd = "1.2", cv = "18.7% (<36%)", mage = "2.1", gmi = "6.1%",
        tirTarget = 92, tirHigh = 5, tirLow = 3,
        tbrLevel1 = "2%", tbrLevel2 = "1% (<3.0)", tarLevel1 = "4%", tarLevel2 = "1% (>13.9)",
        wearTime = "98%", completeness = "99.5%", signalLoss = "15 min",
        hypoEvents = 1, hyperEvents = 2, alertsTriggered = 3,
        clinicalAdvice = "今日血糖极佳，TIR 达标。趋势平稳，无需调整。"
    ))

    var moodReport = mutableStateOf(ClinicalMoodReport("7.5", "7.8", "0.9", "Low", 70, 20, 10, "2 (极轻)", "3 (极轻)", "12 (轻度)"))

    var userProfile = mutableStateOf(UserProfile(
        "28 岁", "女", "175 cm", "70 kg", "2023/05", "是 (Yes)",
        "二甲双胍 (Metformin)", "3.9 - 10.0 mmol/L", "6.2 %",
        "餐后30分", "每天3次"
    ))

    init {
        cgmNodes.addAll(listOf(
            CgmNode("00:00", 5.2, "→", "3min", "0.00"), CgmNode("00:30", 5.1, "↘", "3min", "-0.01"),
            CgmNode("01:00", 5.1, "→", "3min", "0.00"), CgmNode("01:30", 5.0, "↘", "3min", "-0.01"),
            CgmNode("02:00", 5.2, "↗", "3min", "+0.02"), CgmNode("02:30", 5.3, "↗", "3min", "+0.01"),
            CgmNode("03:00", 5.2, "↘", "3min", "-0.01"), CgmNode("03:30", 5.1, "↘", "3min", "-0.01"),
            CgmNode("04:00", 5.0, "↘", "3min", "-0.01"), CgmNode("04:30", 5.2, "↗", "3min", "+0.02"),
            CgmNode("05:00", 5.3, "↗", "3min", "+0.01"), CgmNode("05:30", 5.4, "↗", "3min", "+0.01"),
            CgmNode("06:00", 5.5, "↗", "3min", "+0.01"), CgmNode("06:30", 5.7, "↗", "3min", "+0.02"),
            CgmNode("07:00", 6.0, "↑", "3min", "+0.03"), CgmNode("07:30", 6.2, "↗", "3min", "+0.02"),
            CgmNode("08:00", 6.5, "↗", "3min", "+0.03"), CgmNode("08:30", 8.2, "↑", "3min", "+0.17"),
            CgmNode("09:00", 9.8, "↑", "3min", "+0.16"), CgmNode("09:30", 8.5, "↓", "3min", "-0.13"),
            CgmNode("10:00", 7.2, "↓", "3min", "-0.13"), CgmNode("10:30", 6.5, "↘", "3min", "-0.07"),
            CgmNode("11:00", 6.1, "↘", "3min", "-0.04"), CgmNode("11:30", 5.9, "↘", "3min", "-0.02"),
            CgmNode("12:00", 5.8, "↘", "3min", "-0.01"), CgmNode("12:30", 7.8, "↑", "3min", "+0.20"),
            CgmNode("13:00", 10.2, "↑", "3min", "+0.24"), CgmNode("13:30", 9.5, "↓", "3min", "-0.07"),
            CgmNode("14:00", 8.1, "↓", "3min", "-0.14"), CgmNode("14:30", 7.0, "↓", "3min", "-0.11"),
            CgmNode("15:00", 6.4, "↘", "3min", "-0.06"), CgmNode("15:30", 6.0, "↘", "3min", "-0.04"),
            CgmNode("16:00", 5.8, "↘", "3min", "-0.02"), CgmNode("16:30", 5.7, "↘", "3min", "-0.01"),
            CgmNode("17:00", 5.5, "↘", "3min", "-0.02"), CgmNode("17:30", 5.4, "↘", "3min", "-0.01"),
            CgmNode("18:00", 5.5, "↗", "3min", "+0.01"), CgmNode("18:30", 7.5, "↑", "3min", "+0.20"),
            CgmNode("19:00", 9.1, "↑", "3min", "+0.16"), CgmNode("19:30", 8.4, "↓", "3min", "-0.07"),
            CgmNode("20:00", 7.2, "↓", "3min", "-0.12"), CgmNode("20:30", 6.6, "↘", "3min", "-0.06"),
            CgmNode("21:00", 6.2, "↘", "3min", "-0.04"), CgmNode("21:30", 5.9, "↘", "3min", "-0.03"),
            CgmNode("22:00", 5.7, "↘", "3min", "-0.02"), CgmNode("22:30", 5.6, "↘", "3min", "-0.01"),
            CgmNode("23:00", 5.5, "↘", "3min", "-0.01"), CgmNode("23:30", 5.4, "↘", "3min", "-0.01")
        ))

        moodNodes.addAll(listOf(
            MoodLog("06:00", "平稳", "晨起", 6.5f), MoodLog("07:00", "极佳", "早餐前", 8.0f),
            MoodLog("08:00", "极佳", "早餐后", 8.5f), MoodLog("09:00", "平稳", "工作", 7.0f),
            MoodLog("10:00", "压力", "会议", 5.5f), MoodLog("11:00", "疲惫", "高耗能", 4.5f),
            MoodLog("12:00", "低落", "餐前饥饿", 4.0f), MoodLog("13:00", "极佳", "午餐满意", 9.0f),
            MoodLog("14:00", "平稳", "午后", 7.5f), MoodLog("15:00", "疲惫", "犯困", 5.0f),
            MoodLog("16:00", "烦躁", "工作堆积", 4.5f), MoodLog("17:00", "低落", "下班前", 4.0f),
            MoodLog("18:00", "开心", "离开公司", 8.0f), MoodLog("19:00", "极佳", "晚餐后", 9.5f),
            MoodLog("20:00", "平稳", "休息", 7.5f), MoodLog("21:00", "平静", "阅读", 8.0f),
            MoodLog("22:00", "平稳", "洗漱", 7.0f), MoodLog("23:00", "平静", "准备入睡", 7.5f)
        ))

        dietLogs.addAll(listOf(DietEntry("08:30", "全麦面包", "30g", 30f), DietEntry("12:30", "沙拉", "15g", 15f)))
        exerciseLogs.addAll(listOf(ExerciseEntry("09:00", "慢跑", "20min", 20f), ExerciseEntry("18:00", "拉伸", "15min", 15f)))
        hrLogs.addAll(listOf(HeartRateEntry("08:00", 72, "静息"), HeartRateEntry("19:00", 110, "运动")))
    }

    fun processChatInput(input: String): String {
        val timeMatch = Regex("([0-1]?[0-9]|2[0-3]):([0-5][0-9])").find(input)?.value
        val time = timeMatch ?: java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"))
        val inputCleaned = if (timeMatch != null) input.replace(timeMatch, "") else input
        val extractedNumber = Regex("(\\d+\\.\\d+|\\d+)").find(inputCleaned)?.value?.toFloatOrNull()

        var matchedSomething = false

        if (Regex("血糖|低血糖|高血糖|测了|mmol").containsMatchIn(inputCleaned)) {
            val bg = extractedNumber?.toDouble() ?: 5.5
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
        return "✅ 已精准提取并存入数据库！可在『报告』中查看。"
    }

    fun addCgmNode(bg: Double, timeInput: String) {
        val finalTime = timeInput.ifBlank { java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm")) }
        cgmNodes.add(CgmNode(finalTime, bg, "↗", "3min", "+0.00"))
    }

    fun addMoodNode(score: Float, label: String, timeInput: String) {
        val finalTime = timeInput.ifBlank { java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm")) }
        moodNodes.add(MoodLog(finalTime, label, "手动记录", score))
    }
}