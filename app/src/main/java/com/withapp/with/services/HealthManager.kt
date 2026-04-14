package com.withapp.with.services

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.random.Random

enum class GlucoseLevel(val description: String) {
    LOW("Low"), BORDERLINE_LOW("Borderline Low"), NORMAL("Normal"),
    BORDERLINE_HIGH("Borderline High"), HIGH("High"), UNKNOWN("Unknown");

    companion object {
        fun from(value: Double): GlucoseLevel {
            return when {
                value < 70 -> LOW
                value in 70.0..79.9 -> BORDERLINE_LOW
                value in 80.0..139.9 -> NORMAL
                value in 140.0..179.9 -> BORDERLINE_HIGH
                value >= 180 -> HIGH
                else -> UNKNOWN
            }
        }
    }
}

data class GlucoseReading(
    val id: UUID = UUID.randomUUID(), val value: Double, val timestamp: Long, val source: String
) {
    val level: GlucoseLevel get() = GlucoseLevel.from(value)
    val mmolL: Double get() = value / 18.0
}

data class HeartRateReading(val id: UUID = UUID.randomUUID(), val value: Double, val timestamp: Long)
data class ActivityData(val id: UUID = UUID.randomUUID(), val steps: Int, val activeCalories: Double, val distance: Double, val timestamp: Long)

class HealthManager : ViewModel() {
    var isAuthorized by mutableStateOf(false)
    var latestGlucose by mutableStateOf<GlucoseReading?>(null)
    var glucoseHistory by mutableStateOf<List<GlucoseReading>>(emptyList())
    var latestHeartRate by mutableStateOf<HeartRateReading?>(null)
    var heartRateHistory by mutableStateOf<List<HeartRateReading>>(emptyList())
    var todayActivity by mutableStateOf<ActivityData?>(null)
    var glucoseLevel by mutableStateOf(GlucoseLevel.UNKNOWN)

    init { loadMockData() }

    private fun loadMockData() {
        val now = System.currentTimeMillis()
        val mockReadings = mutableListOf<GlucoseReading>()

        for (i in 0 until 24) {
            val timestamp = now - (i * 3600000L) // minus hours in ms
            val baseValue = 105.0
            val variation = Random.nextDouble(-25.0, 35.0)
            mockReadings.add(GlucoseReading(value = baseValue + variation, timestamp = timestamp, source = "Dexcom G7"))
        }

        glucoseHistory = mockReadings
        latestGlucose = mockReadings.firstOrNull()
        glucoseLevel = latestGlucose?.level ?: GlucoseLevel.NORMAL

        latestHeartRate = HeartRateReading(value = Random.nextDouble(65.0, 85.0), timestamp = now)
        todayActivity = ActivityData(steps = Random.nextInt(3000, 8000), activeCalories = Random.nextDouble(150.0, 400.0), distance = Random.nextDouble(2000.0, 6000.0), timestamp = now)
    }

    fun connectToCGM(deviceId: String, completion: (Boolean) -> Unit) {
        viewModelScope.launch {
            delay(2000) // Simulate connection delay
            completion(true)
        }
    }
}