package org.example.timercenter.ui.viewmodels.states

import androidx.compose.runtime.Stable
import com.example.timercenter.database.model.TimerEntity
import com.example.timercenter.database.model.TimerStatus
import org.example.timercenter.TimeAgoManager
import org.example.timercenter.di.KoinFactory
import org.example.timercenter.ui.model.TimerUiModel

/**
 * Состояние экрана создания таймера
 * Класс содержит информацию о текущем состоянии экрана создания таймера
 * @property id идентификатор таймера
 * @property timerInfo информация о таймере
 * @property selectedHours выбранное количество часов
 * @property selectedMinutes выбранное количество минут
 * @property selectedSeconds выбранное количество секунд
 * @property startImmediately флаг немедленного запуска
 * @property showPopup флаг отображения всплывающего окна
 */
@Stable
data class CreateTimerState(
    val id: Int? = null,
    val timerInfo: TimerUiModel = TimerUiModel(),
    val selectedHours: Int = 0,
    val selectedMinutes: Int = 0,
    val selectedSeconds: Int = 0,
    val startImmediately: Boolean = false,
    val showPopup: Boolean = false
)

/**
 * Эффекты экрана создания таймера
 * Интерфейс определяет возможные эффекты, которые могут быть вызваны на экране создания таймера
 */
sealed interface CreateTimerEffect {
    /**
     * Навигация на главный экран
     * Эффект для перехода на главный экран приложения
     */
    data object NavigateToHome : CreateTimerEffect
}

/**
 * Получение времени в миллисекундах
 * Преобразует выбранное время в миллисекунды
 * @return время в миллисекундах
 */
fun CreateTimerState.getMilliseconds(): Long {
    return (selectedHours * 3_600_000L) + (selectedMinutes * 60_000L) + (selectedSeconds * 1_000L)
}

/**
 * Преобразование состояния в сущность таймера
 * Создает сущность таймера на основе текущего состояния
 * @return сущность таймера
 */
fun CreateTimerState.toEntity(): TimerEntity {
    return TimerEntity(
        id = id ?: 0,
        name = timerInfo.timerName,
        durationMillis = getMilliseconds(),
        isRunning = startImmediately,
        startTime = KoinFactory.getDI().get<TimeAgoManager>().currentTimeMillis(),
        status = if (startImmediately) TimerStatus.RUNNING else TimerStatus.NOT_STARTED,
    )
}

/**
 * Преобразование сущности таймера в UI модель
 * Создает UI модель таймера на основе сущности
 * @return UI модель таймера
 */
fun TimerEntity.toUiModel(): TimerUiModel {
    return TimerUiModel(
        id = id,
        timerName = name,
        totalTime = durationMillis,
        lastStartedTime = startTime ?: 0L
    )
}

/**
 * События экрана создания таймера
 * Интерфейс определяет возможные события, которые могут быть вызваны на экране создания таймера
 */
sealed interface CreateTimerEvent {
    /**
     * Установка идентификатора таймера
     * @property id идентификатор таймера
     */
    data class SetTimerId(val id: Int?) : CreateTimerEvent

    /**
     * Установка названия таймера
     * @property text название таймера
     */
    data class SetName(val text: String) : CreateTimerEvent

    /**
     * Установка часов
     * @property value количество часов
     */
    data class SetHours(val value: Int) : CreateTimerEvent

    /**
     * Установка минут
     * @property value количество минут
     */
    data class SetMinutes(val value: Int) : CreateTimerEvent

    /**
     * Установка секунд
     * @property value количество секунд
     */
    data class SetSeconds(val value: Int) : CreateTimerEvent

    /**
     * Установка флага немедленного запуска
     * @property value значение флага
     */
    data class SetStartImmediately(val value: Boolean) : CreateTimerEvent

    /**
     * Установка флага отображения всплывающего окна
     * @property value значение флага
     */
    data class SetShowPopup(val value: Boolean) : CreateTimerEvent

    /**
     * Сохранение таймера
     * Инициирует сохранение созданного таймера
     */
    data object SaveTimer : CreateTimerEvent
}