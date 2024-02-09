package com.example.weatherapp.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Color





fun clearNightScheme(
): ColorScheme{
    return darkColorScheme(
        primary = Color(0xFFECEFF2),
        primaryContainer = Color(0xFF272A37),
        surface = Color(0xFF1C1E2A),
        secondary = Color(0xFFEBEBF0),
        secondaryContainer = Color(0xFF333747),
        tertiary = Color(0xFFE6E8EB),
        tertiaryContainer = Color(0xFF3F4855),
        surfaceVariant = Color.DarkGray.copy(alpha = 0.6f)
    )
}
fun clearDayScheme(
): ColorScheme{
    return darkColorScheme(
        primary = Color.White,
        primaryContainer = Color(0xFF6BAFE0),
        surface = Color(0xFF87CEEB ),
        secondary = Color.White,
        secondaryContainer = Color(0xFF5791C2),
        tertiary = Color.White,
        tertiaryContainer = Color(0xFF2B5476),
        surfaceVariant = Color.DarkGray.copy(alpha = 0.6f)
    )
}
fun greyDayScheme(
): ColorScheme{
    return darkColorScheme(
        primary = Color(0xFF0A1C2B),
        primaryContainer = Color(0xFF6C7B8B),
        surface = Color(0xFFA2B4C8),
        secondary = Color(0xFF0A1C2B),
        secondaryContainer = Color(0xFF4B5763),
        tertiary = Color(0xFFECEFF2),
        tertiaryContainer = Color(0xFF364052),
        surfaceVariant = Color.DarkGray.copy(alpha = 0.6f)
    )
}
fun greyNightScheme(
): ColorScheme{
    return darkColorScheme(
        primary = Color(0xFFECEFF2),
        primaryContainer = Color(0xFF3C3C4B),
        surface = Color(0xFF1A1A21),
        secondary = Color(0xFFECEFF2),
        secondaryContainer = Color(0xFF5A5A69),
        tertiary = Color(0xFFECEFF2),
        tertiaryContainer = Color(0xFF706D83),
        surfaceVariant = Color.DarkGray.copy(alpha = 0.6f)
    )
}



