package org.example.timercenter.ui.viewmodels.states

import androidx.compose.runtime.Stable
import org.example.timercenter.ui.model.TimerGroupUiModel

/**
 * Состояние экрана добавления таймеров в группу
 * @property timerGroups список групп таймеров
 * @property timerChooseId идентификатор выбранного таймера
 */
@Stable
data class AddTimersToGroupState(
    val timerGroups: List<TimerGroupUiModel> = emptyList(),
    val timerChooseId: Int? = null,
)

/**
 * Эффекты экрана добавления таймеров в группу
 */
sealed interface AddTimersToGroupEffect {
    /**
     * Навигация к экрану редактирования группы таймеров
     * @property timerGroupId идентификатор группы таймеров
     */
    data class NavigateToEditTimerGroup(val timerGroupId: Int) : AddTimersToGroupEffect
}

/**
 * События экрана добавления таймеров в группу
 */
sealed interface AddTimersToGroupEvent {
    /**
     * Выбор группы для редактирования
     * @property groupId идентификатор группы
     */
    data class ChooseGroupToEdit(val groupId: Int) : AddTimersToGroupEvent

    /**
     * Установка идентификатора выбранного таймера
     * @property groupId идентификатор группы
     */
    data class SetTimerChooseId(val groupId: Int) : AddTimersToGroupEvent

//    data class SetTimerGroupId(val id: Int?) : CreateTimerGroupEvent
//    data class SetName(val text: String) : CreateTimerGroupEvent
//    data class SetGroupType(val groupType: GroupType) : CreateTimerGroupEvent
//    data class SetDelayHours(val value: Int) : CreateTimerGroupEvent
//    data class SetDelayMinutes(val value: Int) : CreateTimerGroupEvent
//    data class SetDelaySeconds(val value: Int) : CreateTimerGroupEvent
//    data class SetShowPopup(val value: Boolean) : CreateTimerGroupEvent
//    data object SaveTimerGroup : CreateTimerGroupEvent
//    data class AddTimerToGroup(val timer: TimerUiModel) : CreateTimerGroupEvent
//    data class DeleteTimerFromGroup(val timer: TimerUiModel) : CreateTimerGroupEvent
}