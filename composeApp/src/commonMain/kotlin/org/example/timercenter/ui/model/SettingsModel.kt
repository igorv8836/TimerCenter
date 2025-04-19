package org.example.timercenter.ui.model

/**
 * Перечисление типов уведомлений
 * @property value Числовое значение типа уведомления
 */
enum class NotificationType(val value: Int) {
    SOUND(1),        // Уведомление со звуком
    VIBRATION(2),    // Уведомление с вибрацией
    TEXT(3),         // Уведомление с текстом
}

/**
 * Модель настроек приложения
 * @property notificationType Тип уведомления
 */
data class SettingsModel(
    val notificationType: NotificationType
)

/**
 * Преобразует целое число в тип уведомления
 * @return Тип уведомления или null, если значение не соответствует ни одному типу
 */
fun Int.toNotificationType(): NotificationType? {
    return when (this) {
        NotificationType.SOUND.value -> NotificationType.SOUND
        NotificationType.VIBRATION.value -> NotificationType.VIBRATION
        NotificationType.TEXT.value -> NotificationType.TEXT
        else -> null
    }
}

/**
 * Преобразует тип уведомления в целое число
 * @return Числовое значение типа уведомления
 */
fun NotificationType.toInt(): Int {
    return this.value
}
