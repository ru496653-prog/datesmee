package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun DatesMeTheme(
    preset: AppThemePreset = AppThemePreset.FROSTED_GLASS,
    isDarkMode: Boolean = isSystemInDarkTheme(),
    isDynamicColor: Boolean = false,
    cornerRadiusDp: Int = 28,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val colorScheme = when {
        isDynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (isDarkMode) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        else -> getPastelColorScheme(preset, isDarkMode)
    }

    val customShapes = Shapes(
        extraSmall = RoundedCornerShape((cornerRadiusDp / 4).dp),
        small = RoundedCornerShape((cornerRadiusDp / 3).dp),
        medium = RoundedCornerShape((cornerRadiusDp / 2).dp),
        large = RoundedCornerShape(cornerRadiusDp.dp),
        extraLarge = RoundedCornerShape((cornerRadiusDp + 4).dp)
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = customShapes,
        content = content
    )
}

// Backward compatibility alias
@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    DatesMeTheme(
        preset = AppThemePreset.SAKURA_PINK,
        isDarkMode = darkTheme,
        isDynamicColor = dynamicColor,
        content = content
    )
}
