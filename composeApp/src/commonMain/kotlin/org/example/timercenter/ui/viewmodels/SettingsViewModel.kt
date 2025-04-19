package org.example.timercenter.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.orbit_mvi.viewmodel.container
import com.example.timercenter.datastore.settings.SettingsDataStore
import org.example.timercenter.ui.common.ErrorType
import org.example.timercenter.ui.model.SettingsModel
import org.example.timercenter.ui.model.toInt
import org.example.timercenter.ui.model.toNotificationType
import org.example.timercenter.ui.viewmodels.states.SettingsEffect
import org.example.timercenter.ui.viewmodels.states.SettingsEvent
import org.example.timercenter.ui.viewmodels.states.SettingsState
import org.orbitmvi.orbit.ContainerHost

/**
 * ViewModel для экрана настроек
 * Реализует логику управления настройками приложения
 * @property dataStore Хранилище настроек
 */
class SettingsViewModel(
    private val dataStore: SettingsDataStore
) : ViewModel(), ContainerHost<SettingsState, SettingsEffect> {
    override val container = container<SettingsState, SettingsEffect>(SettingsState.Loading) {
        intent {
            dataStore.notificationType.collect {
                val notificationType = it.toNotificationType()
                reduce {
                    if (notificationType == null) {
                        SettingsState.Error(ErrorType.NOTIFICATION_NULL_VALUE.message)
                    } else {
                        SettingsState.Success(SettingsModel(notificationType))
                    }
                }
            }
        }
    }

    /**
     * Обработка событий
     * @param event Событие для обработки
     */
    fun onEvent(event: SettingsEvent) = intent {
        when (event) {
            is SettingsEvent.ChangeNotification -> {
                dataStore.setNotificationType(event.type.toInt())
            }
        }
    }
}