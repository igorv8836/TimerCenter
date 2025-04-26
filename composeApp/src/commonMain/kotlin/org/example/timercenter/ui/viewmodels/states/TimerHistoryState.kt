package org.example.timercenter.ui.viewmodels.states

import org.example.timercenter.ui.model.TimerHistoryModel

/**
 * Состояние экрана истории таймеров
 * Класс содержит информацию о текущем состоянии экрана истории таймеров
 * @property historyItems список истории
 * @property isLoading флаг загрузки
 * @property error текст ошибки
 */
data class TimerHistoryState(
    val historyItems: List<TimerHistoryModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

/**
 * Эффекты экрана истории таймеров
 * Интерфейс определяет возможные эффекты, которые могут быть вызваны на экране истории таймеров
 */
sealed interface TimerHistorySideEffect {
    /**
     * Навигация к главному экрану для перезапуска таймера
     * @property timerId идентификатор таймера
     */
    data class NavigateToHomeRestartTimer(val timerId: Int) : TimerHistorySideEffect

    /**
     * Навигация к главному экрану для перезапуска группы таймеров
     * @property timerGroupId идентификатор группы таймеров
     */
    data class NavigateToHomeRestartTimerGroup(val timerGroupId: Int) : TimerHistorySideEffect
}

/**
 * События экрана истории таймеров
 * Интерфейс определяет возможные события, которые могут быть вызваны на экране истории таймеров
 */
sealed interface TimerHistoryEvent {
    /**
     * Загрузка истории
     * Инициирует загрузку истории таймеров
     */
    data object LoadHistory : TimerHistoryEvent

    /**
     * Загрузка истории для таймера
     * @property timerId идентификатор таймера
     */
    data class LoadHistoryForTimer(val timerId: Int) : TimerHistoryEvent

    /**
     * Очистка истории
     * Инициирует очистку истории таймеров
     */
    data object ClearHistory : TimerHistoryEvent

    /**
     * Перезапуск выбранного
     * Инициирует перезапуск выбранного таймера или группы таймеров
     */
    data object RestartSelected : TimerHistoryEvent

    /**
     * Установка таймера для перезапуска
     * @property timerId идентификатор таймера
     */
    data class SetTimerRestart(val timerId: Int) : TimerHistoryEvent

    /**
     * Установка группы таймеров для перезапуска
     * @property timerGroupId идентификатор группы таймеров
     */
    data class SetTimerGroupRestart(val timerGroupId: Int) : TimerHistoryEvent
}