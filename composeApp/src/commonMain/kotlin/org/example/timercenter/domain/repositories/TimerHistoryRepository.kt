package org.example.timercenter.domain.repositories

import com.example.timercenter.database.model.TimerHistoryEntity
import kotlinx.coroutines.flow.Flow

/**
 * Интерфейс репозитория для работы с историей таймеров
 * Определяет основные операции для управления историей выполнения таймеров
 */
interface TimerHistoryRepository {
    /**
     * Получает историю для конкретного таймера
     * @param timerId Идентификатор таймера
     * @return Flow со списком записей истории для указанного таймера
     */
    fun getHistoryForTimer(timerId: Int): Flow<List<TimerHistoryEntity>>

    /**
     * Получает всю историю таймеров
     * @return Flow со списком всех записей истории
     */
    fun getAllHistory(): Flow<List<TimerHistoryEntity>>
//    suspend fun addRecord(name: String, lastStartedTime: Long): Long
    /**
     * Очищает всю историю таймеров
     */
    suspend fun clearAll()
}
