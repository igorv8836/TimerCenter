package com.example.timercenter.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.timercenter.database.model.TimerHistoryEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object для работы с историей таймеров
 */
@Dao
interface TimerHistoryDao {
    /**
     * Получает историю для конкретного таймера
     * @param timerId ID таймера
     * @return Список записей истории
     */
    @Query("SELECT * FROM timer_history WHERE timerId = :timerId")
    suspend fun getHistoryForTimer(timerId: Int): List<TimerHistoryEntity>

    /**
     * Получает историю для конкретного таймера как Flow
     * @param timerId ID таймера
     * @return Flow со списком записей истории
     */
    @Query("SELECT * FROM timer_history WHERE timerId = :timerId")
    fun getHistoryForTimerFlow(timerId: Int): Flow<List<TimerHistoryEntity>>

    /**
     * Получает всю историю таймеров
     * @return Список всех записей истории
     */
    @Query("SELECT * FROM timer_history")
    suspend fun getAllHistory(): List<TimerHistoryEntity>

    /**
     * Получает всю историю таймеров как Flow
     * @return Flow со списком всех записей истории
     */
    @Query("SELECT * FROM timer_history")
    fun getAllHistoryFlow(): Flow<List<TimerHistoryEntity>>

    /**
     * Добавляет новую запись в историю
     * @param record Запись для добавления
     * @return ID добавленной записи
     */
    @Insert
    suspend fun insertRecord(record: TimerHistoryEntity): Long

    /**
     * Очищает всю историю таймеров
     */
    @Query("DELETE FROM timer_history")
    suspend fun clearHistory()
}
