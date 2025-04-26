package org.example.timercenter.ui.viewmodels.states

import org.example.timercenter.ui.model.TimerGroupUiModel
import org.example.timercenter.ui.model.TimerUiModel

/**
 * Состояние главного экрана
 * Класс содержит информацию о текущем состоянии главного экрана приложения
 * @property timers список таймеров
 * @property timerGroups список групп таймеров
 * @property selectedTimers выбранные таймеры
 * @property selectedTimerGroups выбранные группы таймеров
 * @property timerRestartId идентификатор таймера для перезапуска
 * @property timerGroupRestartId идентификатор группы таймеров для перезапуска
 * @property showDeleteConfirmation флаг отображения подтверждения удаления
 */
data class HomeState(
    val timers: List<TimerUiModel> = emptyList(),
    val timerGroups: List<TimerGroupUiModel> = emptyList(),
    val selectedTimers: Set<TimerUiModel> = emptySet(),
    val selectedTimerGroups: Set<TimerGroupUiModel> = emptySet(),
    val timerRestartId: Int? = null,
    val timerGroupRestartId: Int? = null,
    val showDeleteConfirmation: Boolean = false
)

/**
 * Эффекты главного экрана
 * Интерфейс определяет возможные эффекты, которые могут быть вызваны на главном экране
 */
sealed interface HomeEffect {
    /**
     * Навигация к экрану редактирования таймера
     * @property timerId идентификатор таймера
     */
    data class NavigateToEditTimer(val timerId: Int) : HomeEffect

    /**
     * Навигация к экрану редактирования группы таймеров
     * @property timerGroupId идентификатор группы таймеров
     */
    data class NavigateToEditTimerGroup(val timerGroupId: Int) : HomeEffect
}

/**
 * События главного экрана
 * Интерфейс определяет возможные события, которые могут быть вызваны на главном экране
 */
sealed interface HomeEvent {
    /**
     * Загрузка данных
     * Инициирует загрузку данных для главного экрана
     */
    data object LoadData : HomeEvent

    /**
     * Переключение выбора таймера
     * @property timer таймер
     * @property isLongPress флаг длительного нажатия
     */
    data class ToggleTimerSelection(val timer: TimerUiModel, val isLongPress: Boolean) : HomeEvent

    /**
     * Переключение выбора группы таймеров
     * @property timerGroup группа таймеров
     * @property isLongPress флаг длительного нажатия
     */
    data class ToggleTimerGroupSelection(val timerGroup: TimerGroupUiModel, val isLongPress: Boolean) : HomeEvent

    /**
     * Очистка выбора
     * Инициирует очистку выбранных таймеров и групп
     */
    data object ClearSelection : HomeEvent

    /**
     * Отображение подтверждения удаления
     * Инициирует отображение диалога подтверждения удаления
     */
    data object ShowDeleteConfirmation : HomeEvent

    /**
     * Подтверждение удаления
     * Инициирует процесс удаления выбранных элементов
     */
    data object ConfirmDeletion : HomeEvent

    /**
     * Отмена удаления
     * Отменяет процесс удаления выбранных элементов
     */
    data object CancelDeletion : HomeEvent

    /**
     * Редактирование выбранного
     * Инициирует процесс редактирования выбранных элементов
     */
    data object EditSelected : HomeEvent

    /**
     * Обновление времени последнего запуска таймера
     * @property timerId идентификатор таймера
     * @property lastStartedTime время последнего запуска
     */
    data class UpdateTimerLastStartedTime(val timerId: Int, val lastStartedTime: Long) : HomeEvent

//    /**
//     * Обновление времени последнего запуска группы таймеров
//     * @property timerGroupId идентификатор группы таймеров
//     * @property lastStartedTime время последнего запуска
//     */
//    data class UpdateTimerGroupLastStartedTime(val timerGroupId: Int, val lastStartedTime: Long) : HomeEvent

    /**
     * Создание таймера из истории
     * @property timerId идентификатор таймера
     */
    data class CreateTimerFromHistory(val timerId: Int) : HomeEvent

    /**
     * Создание группы таймеров из истории
     * @property timerGroupId идентификатор группы таймеров
     */
    data class CreateTimerGroupFromHistory(val timerGroupId: Int) : HomeEvent

    /**
     * Пауза таймера
     * @property timerId идентификатор таймера
     */
    data class PauseTimer(val timerId: Int) : HomeEvent

    /**
     * Остановка таймера
     * @property timerId идентификатор таймера
     */
    data class StopTimer(val timerId: Int) : HomeEvent

    /**
     * Запуск таймера
     * @property timerId идентификатор таймера
     */
    data class RunTimer(val timerId: Int) : HomeEvent

    /**
     * Запуск группы таймеров
     * @property timerId идентификатор группы
     */
    data class RunTimerGroup(val timerGroupId: Int) : HomeEvent

    /**
     * Остановка группы таймеров
     * @property timerId идентификатор группы
     */
    data class StopTimerGroup(val timerGroupId: Int) : HomeEvent

    /**
     * Пауза группы таймеров
     * @property timerId идентификатор группы
     */
    data class PauseTimerGroup(val timerGroupId: Int) : HomeEvent
}
