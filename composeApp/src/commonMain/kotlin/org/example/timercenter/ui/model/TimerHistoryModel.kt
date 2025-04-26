package org.example.timercenter.ui.model

import androidx.compose.runtime.Stable

/**
 * Модель истории таймера
 * @property id Идентификатор таймера
 * @property name Название таймера
 * @property lastStartedTime Время последнего запуска
 */
@Stable
data class TimerHistoryModel(
    val id: Int,
    val name: String,
    val lastStartedTime: Long,
    val isTimer: Boolean,
)