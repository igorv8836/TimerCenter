package org.example.timercenter.ui.viewmodels.states

import androidx.compose.runtime.Stable
import org.example.timercenter.ui.model.NotificationType
import org.example.timercenter.ui.model.SettingsModel

@Stable
sealed class SettingsState {
    data object Loading : SettingsState()
    data class Success(val data: SettingsModel) : SettingsState()
    data class Error(val text: String) : SettingsState()
}

sealed class SettingsEvent {
    data class ChangeNotification(val type: NotificationType) : SettingsEvent()
}

sealed class SettingsEffect {
}