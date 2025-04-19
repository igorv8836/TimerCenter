package org.example.timercenter.ui.viewmodels.states

import com.example.timercenter.database.model.TimerEntity

/**
 * Представляет состояние экрана деталей таймера
 * Класс содержит информацию о текущем состоянии экрана деталей таймера
 * @property timer Таймер для отображения
 * @property isSaving Флаг, указывающий на сохранение данных
 * @property error Сообщение об ошибке, если она произошла
 */
data class TimerDetailState(
    val timer: TimerEntity? = null,
    val isSaving: Boolean = false,
    val error: String? = null
)

/**
 * Представляет побочные эффекты экрана деталей таймера
 * Класс определяет возможные побочные эффекты, которые могут быть вызваны на экране деталей таймера
 */
sealed class TimerDetailSideEffect {
    /**
     * Показывает всплывающее сообщение пользователю
     * @property message Сообщение для отображения
     */
    data class ShowToast(val message: String) : TimerDetailSideEffect()
    
    /**
     * Возврат на предыдущий экран
     * Эффект для навигации назад
     */
    object NavigateBack : TimerDetailSideEffect()
}

/**
 * Представляет действия пользователя на экране деталей таймера
 * Класс определяет возможные действия пользователя, которые могут быть выполнены на экране деталей таймера
 */
sealed class TimerDetailEvent {
    /**
     * Событие для загрузки таймера
     * @property timerId ID таймера для загрузки
     */
    data class LoadTimer(val timerId: Int) : TimerDetailEvent()
    
    /**
     * Событие для сохранения таймера
     * @property timer Таймер для сохранения
     */
    data class SaveTimer(val timer: TimerEntity) : TimerDetailEvent()
    
    /**
     * Событие для удаления таймера
     * Событие, которое инициирует удаление текущего таймера
     */
    object DeleteTimer : TimerDetailEvent()
}