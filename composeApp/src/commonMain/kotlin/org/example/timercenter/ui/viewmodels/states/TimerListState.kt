package org.example.timercenter.ui.viewmodels.states

import com.example.timercenter.database.model.TimerEntity

/**
 * Представляет состояние экрана списка таймеров
 * Класс содержит информацию о текущем состоянии экрана списка таймеров
 * @property timers Список таймеров для отображения
 * @property isLoading Флаг, указывающий на загрузку данных
 * @property error Сообщение об ошибке, если она произошла
 */
data class TimerListState(
    val timers: List<TimerEntity> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

/**
 * Представляет побочные эффекты экрана списка таймеров
 * Класс определяет возможные побочные эффекты, которые могут быть вызваны на экране списка таймеров
 */
sealed class TimerListSideEffect {
    /**
     * Показывает всплывающее сообщение пользователю
     * @property message Сообщение для отображения
     */
    data class ShowToast(val message: String) : TimerListSideEffect()
    
    /**
     * Переход на экран деталей таймера
     * @property timerId ID таймера для отображения деталей
     */
    data class NavigateToTimerDetail(val timerId: Int) : TimerListSideEffect()
}

/**
 * Представляет действия пользователя на экране списка таймеров
 * Класс определяет возможные действия пользователя, которые могут быть выполнены на экране списка таймеров
 */
sealed class TimerListEvent {
    /**
     * Событие для загрузки всех таймеров
     * Инициирует загрузку списка всех таймеров
     */
    object LoadTimers : TimerListEvent()
    
    /**
     * Событие для запуска конкретного таймера
     * @property timer Таймер для запуска
     */
    data class StartTimer(val timer: TimerEntity) : TimerListEvent()
    
    /**
     * Событие для остановки конкретного таймера
     * @property timer Таймер для остановки
     */
    data class StopTimer(val timer: TimerEntity) : TimerListEvent()
    
    /**
     * Событие для удаления конкретного таймера
     * @property timerId ID таймера для удаления
     */
    data class DeleteTimer(val timerId: Int) : TimerListEvent()
    
    /**
     * Событие для создания нового таймера
     * Инициирует процесс создания нового таймера
     */
    object CreateTimer : TimerListEvent()
}