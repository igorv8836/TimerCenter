package org.example.timercenter.domain.repositories

import com.example.timercenter.database.model.TimerEntity
import kotlinx.coroutines.flow.Flow

/**
 * Интерфейс репозитория для работы с таймерами
 * Определяет основные операции для управления таймерами
 */
interface TimerRepository {
    /**
     * Получает список всех таймеров
     * @return Flow со списком всех таймеров
     */
    fun getAllTimers(): Flow<List<TimerEntity>>

    /**
     * Получает таймер по идентификатору
     * @param id Идентификатор таймера
     * @return Таймер или null, если не найден
     */
    suspend fun getTimer(id: Int): TimerEntity?

    /**
     * Создает новый таймер
     * @param timer Сущность таймера для создания
     * @return Идентификатор созданного таймера
     */
    suspend fun createTimer(timer: TimerEntity): Int

    /**
     * Обновляет существующий таймер
     * @param timer Обновленная сущность таймера
     */
    suspend fun updateTimer(timer: TimerEntity)

    /**
     * Обновляет идентификатор группы для таймера
     * @param timerId Идентификатор таймера
     * @param groupId Идентификатор группы
     */
    suspend fun updateTimerInGroupId(timerId: Int, groupId: Int)

    /**
     * Сбрасывает идентификатор группы для таймера
     * @param timerId Идентификатор таймера
     * @param groupId Идентификатор группы
     */
    suspend fun resetTimerInGroupId(timerId: Int, groupId: Int)

    /**
     * Удаляет таймер по идентификатору
     * @param id Идентификатор таймера для удаления
     */
    suspend fun deleteTimer(id: Int)

    /**
     * Запускает таймер
     * @param id Идентификатор таймера
     */
    suspend fun startTimer(id: Int)

    /**
     * Останавливает таймер
     * @param id Идентификатор таймера
     */
    suspend fun stopTimer(id: Int)

    /**
     * Приостанавливает таймер
     * @param id Идентификатор таймера
     */
    suspend fun pauseTimer(id: Int)

    /**
     * Создает копию существующего таймера
     * @param id Идентификатор таймера для копирования
     */
    suspend fun copyTimer(id: Int)
}
