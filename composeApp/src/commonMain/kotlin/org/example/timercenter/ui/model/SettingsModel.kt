package org.example.timercenter.ui.model

import com.example.timercenter.datastore.models.NotificationType

/**
 * Модель настроек приложения
 * @property notificationType Тип уведомления
 */
data class SettingsModel(
    val notificationType: NotificationType
)