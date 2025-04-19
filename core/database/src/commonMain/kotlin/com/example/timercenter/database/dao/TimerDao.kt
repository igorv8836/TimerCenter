package com.example.timercenter.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.timercenter.database.model.TimerEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object для работы с таймерами
 */
@Dao
interface TimerDao {
    /**
     * Получает все таймеры
     * @return Список всех таймеров
     */
    @Query("SELECT * FROM timers")
    suspend fun getAllTimers(): List<TimerEntity>

    /**
     * Получает все таймеры как Flow
     * @return Flow со списком всех таймеров
     */
    @Query("SELECT * FROM timers")
    fun getAllTimersFlow(): Flow<List<TimerEntity>>

    /**
     * Получает таймер по ID
     * @param id ID таймера
     * @return Таймер или null, если не найден
     */
    @Query("SELECT * FROM timers WHERE id = :id")
    suspend fun getTimerById(id: Int): TimerEntity?

    /**
     * Получает таймеры, принадлежащие группе
     * @param groupId ID группы
     * @return Список таймеров в группе
     */
    @Query("""
           SELECT t.* FROM timers t 
           INNER JOIN timer_group_cross_ref tc ON t.id = tc.timerId 
           WHERE tc.groupId = :groupId
           """)
    suspend fun getTimersByGroup(groupId: Int): List<TimerEntity>

    /**
     * Получает таймеры, принадлежащие группе, как Flow
     * @param groupId ID группы
     * @return Flow со списком таймеров в группе
     */
    @Query("""
           SELECT t.* FROM timers t 
           INNER JOIN timer_group_cross_ref tc ON t.id = tc.timerId 
           WHERE tc.groupId = :groupId
           """)
    fun getTimersByGroupFlow(groupId: Int): Flow<List<TimerEntity>>

    /**
     * Добавляет новый таймер
     * @param timer Таймер для добавления
     * @return ID добавленного таймера
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimer(timer: TimerEntity): Long

    /**
     * Обновляет существующий таймер
     * @param timer Таймер для обновления
     */
    @Update
    suspend fun updateTimer(timer: TimerEntity)

    /**
     * Удаляет таймер
     * @param timer Таймер для удаления
     */
    @Delete
    suspend fun deleteTimer(timer: TimerEntity)
}
