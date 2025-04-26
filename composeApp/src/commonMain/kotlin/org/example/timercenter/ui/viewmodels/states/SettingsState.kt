package org.example.timercenter.ui.viewmodels.states

import androidx.compose.runtime.Stable
import com.example.timercenter.datastore.models.NotificationType
import org.example.timercenter.ui.model.SettingsModel

/**
 * Состояние экрана настроек
 * Класс представляет различные состояния экрана настроек приложения
 */
@Stable
sealed class SettingsState {
    /**
     * Состояние загрузки
     * Состояние, когда данные настроек загружаются
     */
    data object Loading : SettingsState()

    /**
     * Состояние успешной загрузки
     * Состояние, когда данные настроек успешно загружены
     * @property data модель настроек
     */
    data class Success(val data: SettingsModel) : SettingsState()

    /**
     * Состояние ошибки
     * Состояние, когда при загрузке настроек произошла ошибка
     * @property text текст ошибки
     */
    data class Error(val text: String) : SettingsState()
}

/**
 * События экрана настроек
 * Класс определяет возможные события, которые могут быть вызваны на экране настроек
 */
sealed class SettingsEvent {
    /**
     * Изменение типа уведомлений
     * @property type тип уведомлений
     */
    data class ChangeNotification(val type: NotificationType) : SettingsEvent()
}

/**
 * Эффекты экрана настроек
 * Класс определяет возможные эффекты, которые могут быть вызваны на экране настроек
 */
sealed class SettingsEffect {
}