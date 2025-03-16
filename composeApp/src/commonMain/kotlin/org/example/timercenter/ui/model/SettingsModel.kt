package org.example.timercenter.ui.model

// Enum для типа уведомления
enum class NotificationType {
    SOUND,        // Уведомление со звуком
    VIBRATION,    // Уведомление с вибрацией
    TEXT,         // Уведомление с текстом
}

// Дата класс для настроек
data class SettingsModel(
    val notificationType: NotificationType
)
