package com.example.timercenter.ui_common

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

/**
 * Тема приложения, которая определяет цветовую схему и типографику
 * 
 * @param darkTheme Флаг, определяющий использование темной темы. По умолчанию зависит от системных настроек
 * @param content Компонент, который будет отображаться с примененной темой
 */
@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colorScheme = when {
        darkTheme -> darkScheme
        else -> lightScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}