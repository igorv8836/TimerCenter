package org.example.timercenter.ui.viewmodels.states

import androidx.compose.runtime.Stable
import com.example.timercenter.database.model.TimerGroupEntity
import org.example.timercenter.ui.model.GroupType
import org.example.timercenter.ui.model.TimerGroupUiModel
import org.example.timercenter.ui.model.TimerUiModel
import org.example.timercenter.ui.model.toInt

/**
 * Состояние экрана создания группы таймеров
 * Класс содержит информацию о текущем состоянии экрана создания группы таймеров
 * @property id идентификатор группы
 * @property timerGroupInfo информация о группе таймеров
 * @property allTimers список всех таймеров
 * @property delaySelectedHours выбранное количество часов задержки
 * @property delaySelectedMinutes выбранное количество минут задержки
 * @property delaySelectedSeconds выбранное количество секунд задержки
 * @property showPopup флаг отображения всплывающего окна
 * @property errorMessage сообщение об ошибке
 */
@Stable
data class CreateTimerGroupState(
    val id: Int? = null,
    val timerGroupInfo: TimerGroupUiModel = TimerGroupUiModel(),
    val unSelectedTimers: List<TimerUiModel> = emptyList(),
    val allTimers: List<TimerUiModel> = emptyList(),
    val delaySelectedHours: Int = 0,
    val delaySelectedMinutes: Int = 0,
    val delaySelectedSeconds: Int = 0,
    val showPopup: Boolean = false,
    val errorMessage: String? = null
)

/**
 * Эффекты экрана создания группы таймеров
 * Интерфейс определяет возможные эффекты, которые могут быть вызваны на экране создания группы таймеров
 */
sealed interface CreateTimerGroupEffect {
    /**
     * Навигация на главный экран
     * Эффект для перехода на главный экран приложения
     */
    data object NavigateToHome : CreateTimerGroupEffect
}

/**
 * Получение времени задержки в миллисекундах
 * Преобразует выбранное время задержки в миллисекунды
 * @return время задержки в миллисекундах
 */
fun CreateTimerGroupState.getMilliseconds(): Long {
    return (delaySelectedHours * 3_600_000L) + (delaySelectedMinutes * 60_000L) + (delaySelectedSeconds * 1_000L)
}

/**
 * Преобразование состояния в сущность группы таймеров
 * Создает сущность группы таймеров на основе текущего состояния
 * @return сущность группы таймеров
 */
fun CreateTimerGroupState.toEntity() : TimerGroupEntity {
    return TimerGroupEntity(
        id = id ?: 0,
        name = timerGroupInfo.groupName,
        groupType = timerGroupInfo.groupType.toInt(),
        isRunning = false,
        lastStartedTime = timerGroupInfo.lastStartedTime,
        delayTime = getMilliseconds()
    )
}

/**
 * События экрана создания группы таймеров
 * Интерфейс определяет возможные события, которые могут быть вызваны на экране создания группы таймеров
 */
sealed interface CreateTimerGroupEvent {
    /**
     * Установка идентификатора группы таймеров
     * @property id идентификатор группы
     */
    data class SetTimerGroupId(val id: Int?) : CreateTimerGroupEvent

    /**
     * Установка названия группы
     * @property text название группы
     */
    data class SetName(val text: String) : CreateTimerGroupEvent

    /**
     * Установка типа группы
     * @property groupType тип группы
     */
    data class SetGroupType(val groupType: GroupType) : CreateTimerGroupEvent

    /**
     * Установка часов задержки
     * @property value количество часов
     */
    data class SetDelayHours(val value: Int) : CreateTimerGroupEvent

    /**
     * Установка минут задержки
     * @property value количество минут
     */
    data class SetDelayMinutes(val value: Int) : CreateTimerGroupEvent

    /**
     * Установка секунд задержки
     * @property value количество секунд
     */
    data class SetDelaySeconds(val value: Int) : CreateTimerGroupEvent

    /**
     * Установка флага отображения всплывающего окна
     * @property value значение флага
     */
    data class SetShowPopup(val value: Boolean) : CreateTimerGroupEvent

    /**
     * Сохранение группы таймеров
     * Инициирует сохранение созданной группы таймеров
     */
    data object SaveTimerGroup : CreateTimerGroupEvent

    /**
     * Добавление таймера в группу
     * @property timer таймер для добавления
     */
    data class AddTimerToGroup(val timer: TimerUiModel) : CreateTimerGroupEvent

    /**
     * Удаление таймера из группы
     * @property timer таймер для удаления
     */
    data class DeleteTimerFromGroup(val timer: TimerUiModel) : CreateTimerGroupEvent
}