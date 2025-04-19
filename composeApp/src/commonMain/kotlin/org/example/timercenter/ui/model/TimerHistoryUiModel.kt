package org.example.timercenter.ui.model

import androidx.compose.runtime.Stable

/**
 * Модель истории таймера
 * @property id Идентификатор таймера
 * @property name Название таймера
 * @property lastStartedTimeText Текст времени последнего запуска
 */
@Stable
data class TimerHistoryUiModel(
    val id: Int,
    val name: String,
    val lastStartedTimeText: String
)