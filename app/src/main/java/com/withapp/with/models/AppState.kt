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

class AppState : ViewModel() {
    // Basic user info
    var userName by mutableStateOf("探索者")
    var selectedCharacter by mutableStateOf("robot")

    // Flag to control whether to show Onboarding or Main UI
    var hasCompletedOnboarding by mutableStateOf(false)

    // User health profile collected during onboarding
    var age by mutableStateOf("")
    var diabetesType by mutableStateOf("2型糖尿病")
}