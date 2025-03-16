package org.example.timercenter.ui.model

// Enum для типа уведомления
enum class NotificationType(val value: Int) {
    SOUND(1),        // Уведомление со звуком
    VIBRATION(2),    // Уведомление с вибрацией
    TEXT(3),         // Уведомление с текстом
}

// Дата класс для настроек
data class SettingsModel(
    val notificationType: NotificationType
)

fun Int.toNotificationType(): NotificationType? {
    return when (this) {
        NotificationType.SOUND.value -> NotificationType.SOUND
        NotificationType.VIBRATION.value -> NotificationType.VIBRATION
        NotificationType.TEXT.value -> NotificationType.TEXT
        else -> null
    }
}

fun NotificationType.toInt(): Int {
    return this.value
}
