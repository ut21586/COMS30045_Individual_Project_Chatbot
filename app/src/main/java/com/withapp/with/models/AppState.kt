//package com.withapp.with.models // 必须完全一致
//
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.lifecycle.ViewModel
//
//class AppState : ViewModel() {
//    var userName by mutableStateOf("User")
//    var selectedCharacter by mutableStateOf("robot")
//}

package com.withapp.with.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

enum class AppLanguage(val value: String, val displayName: String) {
    CHINESE("zh-Hans", "中文"),
    ENGLISH("en", "English")
}

enum class CharacterType(val value: String, val displayName: String, val emoji: String) {
    ROBOT("robot", "With", "🤖"),
    CAT("cat", "Miao", "🐱"),
    BEAR("bear", "Bear", "🐻"),
    BUNNY("bunny", "Bunny", "🐰"),
    PANDA("panda", "Panda", "🐼")
}

enum class ContextMode(val value: String, val displayName: String, val icon: String) {
    PRIVATE("private", "私人", "House"), // Mapped to standard Compose Material Icons where possible in UI
    PUBLIC("public", "公开", "Public"),
    WORK("work", "工作", "Work"),
    SOCIAL("social", "社交", "Groups"),
    RESTAURANT("restaurant", "餐厅", "Restaurant")
}

class AppState : ViewModel() {
    var isOnboarded by mutableStateOf(false)
    var selectedCharacter by mutableStateOf(CharacterType.ROBOT)
    var userName by mutableStateOf("User")
    var appLanguage by mutableStateOf(AppLanguage.CHINESE)
    var contextMode by mutableStateOf(ContextMode.PRIVATE)
    var notificationsEnabled by mutableStateOf(true)
    var cgmConnected by mutableStateOf(false)
    var showVideoAvatar by mutableStateOf(false)

    // Onboarding Optional Data (from OnboardingDataReceivable pattern)
    var height by mutableStateOf("")
    var weight by mutableStateOf("")
    var age by mutableStateOf("")
    var genderIndex by mutableStateOf(0)
    var diagnosisDate by mutableStateOf(System.currentTimeMillis())
    var usesMedication by mutableStateOf(false)
    var usesInsulin by mutableStateOf(false)
    var latestHbA1c by mutableStateOf("")
    var moodIndex by mutableStateOf(2)
    var hasStress by mutableStateOf(false)

    fun applyOnboardingData(
        h: String, w: String, a: String, g: Int, d: Long,
        meds: Boolean, ins: Boolean, a1c: String, mood: Int, stress: Boolean
    ) {
        height = h; weight = w; age = a; genderIndex = g; diagnosisDate = d
        usesMedication = meds; usesInsulin = ins; latestHbA1c = a1c
        moodIndex = mood; hasStress = stress
    }
}