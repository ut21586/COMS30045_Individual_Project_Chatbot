package com.withapp.with.models // 必须完全一致

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AppState : ViewModel() {
    var userName by mutableStateOf("User")
    var selectedCharacter by mutableStateOf("robot")
}