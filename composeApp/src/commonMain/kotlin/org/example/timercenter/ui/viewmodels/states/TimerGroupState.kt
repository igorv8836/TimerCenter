package org.example.timercenter.ui.viewmodels.states

import com.example.timercenter.database.model.TimerGroupEntity
import org.example.timercenter.ui.model.TimerGroupUiModel

/**
 * Состояние экрана групп таймеров
 * Класс содержит информацию о текущем состоянии экрана групп таймеров
 * @property groups список групп таймеров
 * @property isLoading флаг загрузки
 * @property error текст ошибки
 */
data class TimerGroupState(
    val groups: List<TimerGroupEntity> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

/**
 * Эффекты экрана групп таймеров
 * Класс определяет возможные эффекты, которые могут быть вызваны на экране групп таймеров
 */
sealed class TimerGroupSideEffect {
    /**
     * Отображение уведомления
     * @property message текст уведомления
     */
    data class ShowToast(val message: String) : TimerGroupSideEffect()

    /**
     * Навигация к деталям группы
     * Эффект для перехода к экрану деталей группы таймеров
     */
    data object NavigateToGroupDetail : TimerGroupSideEffect()
}

/**
 * События экрана групп таймеров
 * Класс определяет возможные события, которые могут быть вызваны на экране групп таймеров
 */
sealed class TimerGroupEvent {
    /**
     * Загрузка групп
     * Инициирует загрузку списка групп таймеров
     */
    data object LoadGroups : TimerGroupEvent()

    /**
     * Создание группы
     * @property group модель группы таймеров
     */
    data class CreateGroup(val group: TimerGroupUiModel) : TimerGroupEvent()

    /**
     * Удаление группы
     * @property groupId идентификатор группы
     */
    data class DeleteGroup(val groupId: Int) : TimerGroupEvent()
}