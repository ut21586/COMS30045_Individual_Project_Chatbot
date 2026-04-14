package com.withapp.with

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.core.view.WindowCompat
import com.withapp.with.ui.screens.MainContainer

// English: Main entry point of the app, ensuring edge-to-edge display and theme consistency
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // English: Enable immersive edge-to-edge layout
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            // English: Use standard MaterialTheme to fix "Unresolved reference 'WithTheme'" errors
            MaterialTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    // English: Initialize the main container with de-stigmatized logic
                    MainContainer(
                        onRequestPermission = {
                            // English: Implementation for health data permissions can be added here
                        }
                    )
                }
            }
        }
    }
}