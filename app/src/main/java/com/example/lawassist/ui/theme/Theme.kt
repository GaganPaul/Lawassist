package com.example.lawassist.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Dark professional color scheme
private val DarkColorScheme = darkColorScheme(
    // Primary colors
    primary = Color(0xFF6200EE),          // Vibrant purple for primary actions
    onPrimary = Color.White,              // White text on primary color
    primaryContainer = Color(0xFF3700B3), // Darker purple for containers
    onPrimaryContainer = Color.White,     // White text on primary containers

    // Secondary colors
    secondary = Color(0xFF03DAC6),        // Teal for secondary elements
    onSecondary = Color.Black,            // Black text on secondary color
    secondaryContainer = Color(0xFF252836), // Dark blue-gray for message bubbles
    onSecondaryContainer = Color(0xFFE4E4E5), // Light gray text for readability

    // Background colors
    background = Color(0xFF121212),       // Very dark gray for main background
    onBackground = Color.White,           // White text on background

    // Surface colors
    surface = Color(0xFF1D1D1D),          // Slightly lighter dark for surfaces
    onSurface = Color.White,              // White text on surfaces
    surfaceVariant = Color(0xFF2D2D2D),   // Variant surface color for input area
    onSurfaceVariant = Color(0xFFE4E4E5), // Light gray text on surface variant

    // Error colors
    error = Color(0xFFCF6679),            // Soft red for errors
    onError = Color.Black                 // Black text on error color
)

// Light color scheme (if you decide to add light theme support)
private val LightColorScheme = lightColorScheme(
    // Primary colors
    primary = Color(0xFF6200EE),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE8DDFF),
    onPrimaryContainer = Color(0xFF21005E),

    // Secondary colors
    secondary = Color(0xFF03DAC6),
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFFCEFAF5),
    onSecondaryContainer = Color(0xFF00504D),

    // Background colors
    background = Color.White,
    onBackground = Color(0xFF1C1B1F),

    // Surface colors
    surface = Color(0xFFFFFBFF),
    onSurface = Color(0xFF1C1B1F),
    surfaceVariant = Color(0xFFE7E0EC),
    onSurfaceVariant = Color(0xFF49454E),

    // Error colors
    error = Color(0xFFB3261E),
    onError = Color.White
)

@Composable
fun LawAssistTheme(
    darkTheme: Boolean = true, // Default to dark theme
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}