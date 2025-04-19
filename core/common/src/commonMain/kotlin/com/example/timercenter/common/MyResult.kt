package com.example.timercenter.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

/**
 * Обертка для результатов операций с возможными ошибками
 * @param T Тип успешного результата
 */
sealed interface MyResult<out T> {
    /**
     * Успешный результат операции
     * @param data Данные результата
     */
    data class Success<T>(val data: T) : MyResult<T>

    /**
     * Ошибка при выполнении операции
     * @param exception Исключение, вызвавшее ошибку
     */
    data class Error(val exception: Throwable) : MyResult<Nothing>

    /**
     * Операция выполняется
     */
    data object Loading : MyResult<Nothing>

    /**
     * Флаг успешности операции
     */
    val success: Boolean
        get() = this is Success

    /**
     * Получение данных или null в случае ошибки
     * @return Данные результата или null
     */
    fun getOrNull(): T? = (this as? Success)?.data

    /**
     * Получение исключения или null в случае успеха
     * @return Исключение или null
     */
    fun exceptionOrNull(): Throwable? = (this as? Error)?.exception
}

/**
 * Преобразует Flow в Flow с оберткой MyResult
 * @return Flow с оберткой MyResult
 */
fun <T> Flow<T>.asResult(): Flow<MyResult<T>> = map<T, MyResult<T>> { MyResult.Success(it) }
    .onStart { emit(MyResult.Loading) }
    .catch { emit(MyResult.Error(it)) }