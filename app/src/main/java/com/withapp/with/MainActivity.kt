package com.withapp.with

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.withapp.with.ui.screens.HomeView
import com.withapp.with.ui.screens.ChatView
import com.withapp.with.viewmodels.ChatViewModel
import com.withapp.with.ui.theme.WithAppTheme

class MainActivity : ComponentActivity() {
    // This annotation tells the compiler: "I know I'm not using innerPadding, leave me alone."
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Enable full-screen immersive mode
        enableEdgeToEdge()

        setContent {
            WithAppTheme {
                val chatViewModel: ChatViewModel = viewModel()
                var currentTab by remember { mutableStateOf("home") }

                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    // No padding applied here so the background can flow behind the status bar
                    Box(modifier = Modifier.fillMaxSize()) {
                        when (currentTab) {
                            "home" -> HomeView(onNavigateToChat = { text ->
                                chatViewModel.sendMessage(text)
                                currentTab = "chat"
                            })
                            "chat" -> ChatView(
                                chatViewModel = chatViewModel,
                                onBack = { currentTab = "home" }
                            )
                        }
                    }
                }
            }
        }
    }
}