package com.muahmmadfadhillaharrobbi0021.studytimer.utils

import androidx.compose.ui.graphics.Color

object ThemeColor {
    val Cyan = Color(0xFF00E5FF)
    val Purple = Color(0xFFBB86FC)
    val Green = Color(0xFF69F0AE)
    val Orange = Color(0xFFFFAB40)

    fun fromString(name: String): Color = when (name) {
        "Purple" -> Purple
        "Green" -> Green
        "Orange" -> Orange
        else -> Cyan
    }

    val options = listOf("Cyan", "Purple", "Green", "Orange")
}