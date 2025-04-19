package org.example.timercenter.ui.common

/**
 * Перечисление типов ошибок приложения
 * @property message Сообщение об ошибке
 */
enum class ErrorType(val message: String) {
    NOTIFICATION_NULL_VALUE("Notification value is null"),
}