package com.example.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

enum class AppThemePreset(val displayName: String, val primaryColorHex: String) {
    FROSTED_GLASS("Frosted Glass", "#9C4275"),
    SAKURA_PINK("Sakura Pink", "#FF4081"),
    LAVENDER("Lavender", "#AB47BC"),
    MATCHA("Matcha", "#66BB6A"),
    MINT("Mint", "#26A69A"),
    CORAL("Coral", "#FF7043"),
    PEACH("Peach", "#FFA726"),
    SKY_BLUE("Sky Blue", "#29B6F6"),
    OCEAN("Ocean", "#0288D1"),
    SUNSET("Sunset", "#FF5722"),
    COFFEE("Coffee", "#6D4C41"),
    LATTE("Latte", "#8D6E63"),
    MOCHA("Mocha", "#4E342E"),
    MIDNIGHT("Midnight", "#3F51B5"),
    GRAPHITE("Graphite", "#455A64")
}

fun getPastelColorScheme(preset: AppThemePreset, isDark: Boolean): ColorScheme {
    return when (preset) {
        AppThemePreset.FROSTED_GLASS -> if (isDark) darkColorScheme(
            primary = Color(0xFFFFB0D3),
            onPrimary = Color(0xFF5E1145),
            primaryContainer = Color(0xFF7D2A5E),
            onPrimaryContainer = Color(0xFFFFD9E2),
            secondary = Color(0xFFFFB58A),
            onSecondary = Color(0xFF4F2500),
            secondaryContainer = Color(0xFF713700),
            onSecondaryContainer = Color(0xFFFFDCC7),
            tertiary = Color(0xFFF27D26),
            background = Color(0xFF1E1518),
            onBackground = Color(0xFFFCECEF),
            surface = Color(0xFF261B20),
            onSurface = Color(0xFFFCECEF),
            surfaceVariant = Color(0xFF382730),
            onSurfaceVariant = Color(0xFFE0C4CF),
            outline = Color(0xFF9E8490)
        ) else lightColorScheme(
            primary = Color(0xFF9C4275),
            onPrimary = Color.White,
            primaryContainer = Color(0xFFFFD9E2),
            onPrimaryContainer = Color(0xFF31101D),
            secondary = Color(0xFFF27D26),
            onSecondary = Color.White,
            secondaryContainer = Color(0xFFFFDCC7),
            onSecondaryContainer = Color(0xFF321200),
            tertiary = Color(0xFFF27D26),
            background = Color(0xFFFCF8F9),
            onBackground = Color(0xFF201A1B),
            surface = Color(0x99FFFFFF),
            onSurface = Color(0xFF201A1B),
            surfaceVariant = Color(0xF0FCECEF),
            onSurfaceVariant = Color(0xFF524345),
            outline = Color(0xCCFFFFFF)
        )
        AppThemePreset.SAKURA_PINK -> if (isDark) darkColorScheme(
            primary = Color(0xFFFF80AB),
            onPrimary = Color(0xFF4A0022),
            primaryContainer = Color(0xFF700037),
            onPrimaryContainer = Color(0xFFFFD8E4),
            secondary = Color(0xFFFFB2C9),
            background = Color(0xFF191114),
            surface = Color(0xFF21181B),
            surfaceVariant = Color(0xFF332429),
            onBackground = Color(0xFFF9EFEF),
            onSurface = Color(0xFFF9EFEF)
        ) else lightColorScheme(
            primary = Color(0xFFE91E63),
            onPrimary = Color.White,
            primaryContainer = Color(0xFFFFD8E4),
            onPrimaryContainer = Color(0xFF3D001C),
            secondary = Color(0xFFF06292),
            background = Color(0xFFFFF8F9),
            surface = Color(0xFFFFFFFF),
            surfaceVariant = Color(0xFFF8EAF0),
            onBackground = Color(0xFF2A151D),
            onSurface = Color(0xFF2A151D)
        )

        AppThemePreset.LAVENDER -> if (isDark) darkColorScheme(
            primary = Color(0xFFCE93D8),
            onPrimary = Color(0xFF38004D),
            primaryContainer = Color(0xFF530070),
            secondary = Color(0xFFE1BEE7),
            background = Color(0xFF161218),
            surface = Color(0xFF1E1A22),
            surfaceVariant = Color(0xFF2D2633),
            onBackground = Color(0xFFF5EEF7)
        ) else lightColorScheme(
            primary = Color(0xFF8E24AA),
            onPrimary = Color.White,
            primaryContainer = Color(0xFFF3E5F5),
            secondary = Color(0xFFBA68C8),
            background = Color(0xFFFAF5FC),
            surface = Color(0xFFFFFFFF),
            surfaceVariant = Color(0xFFF0E5F5),
            onBackground = Color(0xFF241528)
        )

        AppThemePreset.MATCHA -> if (isDark) darkColorScheme(
            primary = Color(0xFFA5D6A7),
            onPrimary = Color(0xFF00390E),
            primaryContainer = Color(0xFF005318),
            background = Color(0xFF111712),
            surface = Color(0xFF19211A),
            surfaceVariant = Color(0xFF263228),
            onBackground = Color(0xFFEDF6EE)
        ) else lightColorScheme(
            primary = Color(0xFF43A047),
            onPrimary = Color.White,
            primaryContainer = Color(0xFFE8F5E9),
            background = Color(0xFFF6FBF7),
            surface = Color(0xFFFFFFFF),
            surfaceVariant = Color(0xFFE2F0E4),
            onBackground = Color(0xFF152617)
        )

        AppThemePreset.MINT -> if (isDark) darkColorScheme(
            primary = Color(0xFF80CBC4),
            onPrimary = Color(0xFF003732),
            primaryContainer = Color(0xFF004D40),
            background = Color(0xFF111817),
            surface = Color(0xFF182220),
            surfaceVariant = Color(0xFF243330),
            onBackground = Color(0xFFEDF7F6)
        ) else lightColorScheme(
            primary = Color(0xFF00897B),
            onPrimary = Color.White,
            primaryContainer = Color(0xFFE0F2F1),
            background = Color(0xFFF4FAF9),
            surface = Color(0xFFFFFFFF),
            surfaceVariant = Color(0xFFE0ECEB),
            onBackground = Color(0xFF122624)
        )

        AppThemePreset.CORAL -> if (isDark) darkColorScheme(
            primary = Color(0xFFFFAB91),
            onPrimary = Color(0xFF4E1600),
            primaryContainer = Color(0xFF712400),
            background = Color(0xFF181311),
            surface = Color(0xFF221A17),
            surfaceVariant = Color(0xFF332722),
            onBackground = Color(0xFFF8EFEA)
        ) else lightColorScheme(
            primary = Color(0xFFF4511E),
            onPrimary = Color.White,
            primaryContainer = Color(0xFFFBE9E7),
            background = Color(0xFFFFF8F6),
            surface = Color(0xFFFFFFFF),
            surfaceVariant = Color(0xFFF8E4DF),
            onBackground = Color(0xFF2E1912)
        )

        AppThemePreset.PEACH -> if (isDark) darkColorScheme(
            primary = Color(0xFFFFCC80),
            onPrimary = Color(0xFF452B00),
            primaryContainer = Color(0xFF633F00),
            background = Color(0xFF181511),
            surface = Color(0xFF221D17),
            surfaceVariant = Color(0xFF332B22),
            onBackground = Color(0xFFF8F2EB)
        ) else lightColorScheme(
            primary = Color(0xFFFB8C00),
            onPrimary = Color.White,
            primaryContainer = Color(0xFFFFE0B2),
            background = Color(0xFFFFFBF6),
            surface = Color(0xFFFFFFFF),
            surfaceVariant = Color(0xFFF7EAD9),
            onBackground = Color(0xFF2B1D0C)
        )

        AppThemePreset.SKY_BLUE -> if (isDark) darkColorScheme(
            primary = Color(0xFF81D4FA),
            onPrimary = Color(0xFF00344A),
            primaryContainer = Color(0xFF004C6D),
            background = Color(0xFF11161A),
            surface = Color(0xFF182025),
            surfaceVariant = Color(0xFF243038),
            onBackground = Color(0xFFEDF5FA)
        ) else lightColorScheme(
            primary = Color(0xFF0288D1),
            onPrimary = Color.White,
            primaryContainer = Color(0xFFE1F5FE),
            background = Color(0xFFF5FAFD),
            surface = Color(0xFFFFFFFF),
            surfaceVariant = Color(0xFFE0EEF5),
            onBackground = Color(0xFF11232E)
        )

        AppThemePreset.OCEAN -> if (isDark) darkColorScheme(
            primary = Color(0xFF4FC3F7),
            onPrimary = Color(0xFF00354B),
            primaryContainer = Color(0xFF004D6E),
            background = Color(0xFF0E161A),
            surface = Color(0xFF142026),
            surfaceVariant = Color(0xFF1F3039),
            onBackground = Color(0xFFE8F4FA)
        ) else lightColorScheme(
            primary = Color(0xFF0277BD),
            onPrimary = Color.White,
            primaryContainer = Color(0xFFE1F5FE),
            background = Color(0xFFF4FAF9),
            surface = Color(0xFFFFFFFF),
            surfaceVariant = Color(0xFFDFEFF5),
            onBackground = Color(0xFF0E222D)
        )

        AppThemePreset.SUNSET -> if (isDark) darkColorScheme(
            primary = Color(0xFFFFB74D),
            onPrimary = Color(0xFF452B00),
            primaryContainer = Color(0xFF633F00),
            background = Color(0xFF181310),
            surface = Color(0xFF221A15),
            surfaceVariant = Color(0xFF33271F),
            onBackground = Color(0xFFF8EFEB)
        ) else lightColorScheme(
            primary = Color(0xFFF57C00),
            onPrimary = Color.White,
            primaryContainer = Color(0xFFFFE0B2),
            background = Color(0xFFFFF9F5),
            surface = Color(0xFFFFFFFF),
            surfaceVariant = Color(0xFFF7E7DC),
            onBackground = Color(0xFF2B180D)
        )

        AppThemePreset.COFFEE -> if (isDark) darkColorScheme(
            primary = Color(0xFFBCAAA4),
            onPrimary = Color(0xFF2C1D18),
            primaryContainer = Color(0xFF422C25),
            background = Color(0xFF161312),
            surface = Color(0xFF1E1A18),
            surfaceVariant = Color(0xFF2E2724),
            onBackground = Color(0xFFF4EFEA)
        ) else lightColorScheme(
            primary = Color(0xFF5D4037),
            onPrimary = Color.White,
            primaryContainer = Color(0xFFD7CCC8),
            background = Color(0xFFFAF7F5),
            surface = Color(0xFFFFFFFF),
            surfaceVariant = Color(0xFFEFE8E5),
            onBackground = Color(0xFF271D1A)
        )

        AppThemePreset.LATTE -> if (isDark) darkColorScheme(
            primary = Color(0xFFD7CCC8),
            onPrimary = Color(0xFF3E2723),
            primaryContainer = Color(0xFF5D4037),
            background = Color(0xFF181514),
            surface = Color(0xFF221E1C),
            surfaceVariant = Color(0xFF332E2A),
            onBackground = Color(0xFFF6F2EF)
        ) else lightColorScheme(
            primary = Color(0xFF795548),
            onPrimary = Color.White,
            primaryContainer = Color(0xFFEFEBE9),
            background = Color(0xFFFCFAF9),
            surface = Color(0xFFFFFFFF),
            surfaceVariant = Color(0xFFEFEBE9),
            onBackground = Color(0xFF2A211E)
        )

        AppThemePreset.MOCHA -> if (isDark) darkColorScheme(
            primary = Color(0xFFA1887F),
            onPrimary = Color(0xFF271A15),
            primaryContainer = Color(0xFF3E2723),
            background = Color(0xFF141110),
            surface = Color(0xFF1C1816),
            surfaceVariant = Color(0xFF2A2421),
            onBackground = Color(0xFFF4ECE9)
        ) else lightColorScheme(
            primary = Color(0xFF4E342E),
            onPrimary = Color.White,
            primaryContainer = Color(0xFFD7CCC8),
            background = Color(0xFFF9F6F5),
            surface = Color(0xFFFFFFFF),
            surfaceVariant = Color(0xFFECE5E2),
            onBackground = Color(0xFF241815)
        )

        AppThemePreset.MIDNIGHT -> if (isDark) darkColorScheme(
            primary = Color(0xFF9FA8DA),
            onPrimary = Color(0xFF1A237E),
            primaryContainer = Color(0xFF283593),
            background = Color(0xFF0F121C),
            surface = Color(0xFF151928),
            surfaceVariant = Color(0xFF21273C),
            onBackground = Color(0xFFECEDF9)
        ) else lightColorScheme(
            primary = Color(0xFF283593),
            onPrimary = Color.White,
            primaryContainer = Color(0xFFE8EAF6),
            background = Color(0xFFF5F6FC),
            surface = Color(0xFFFFFFFF),
            surfaceVariant = Color(0xFFE0E3F5),
            onBackground = Color(0xFF13172E)
        )

        AppThemePreset.GRAPHITE -> if (isDark) darkColorScheme(
            primary = Color(0xFFB0BEC5),
            onPrimary = Color(0xFF1C2B33),
            primaryContainer = Color(0xFF263238),
            background = Color(0xFF121618),
            surface = Color(0xFF191F22),
            surfaceVariant = Color(0xFF252E33),
            onBackground = Color(0xFFEDF1F3)
        ) else lightColorScheme(
            primary = Color(0xFF37474F),
            onPrimary = Color.White,
            primaryContainer = Color(0xFFECEFF1),
            background = Color(0xFFF6F8F9),
            surface = Color(0xFFFFFFFF),
            surfaceVariant = Color(0xFFE2E7EA),
            onBackground = Color(0xFF1A2226)
        )
    }
}
