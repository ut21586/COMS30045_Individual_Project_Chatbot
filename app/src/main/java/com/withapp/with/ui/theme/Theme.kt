package com.withapp.with.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Defining the color scheme for Light Mode
private val LightColorScheme = lightColorScheme(
    primary = AccentTeal,
    secondary = LightTeal,
    tertiary = DarkTeal,
    background = BackgroundWhite,
    surface = Color.White, // Using the standard White from Compose
    onPrimary = Color.White,
    onBackground = TextDark
)

@Composable
fun WithAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, //strictly use your brand colors
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        // We currently only focus on LightColorScheme to match your primary design
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // This uses the default Typography from Type.kt
        content = content
    )
}