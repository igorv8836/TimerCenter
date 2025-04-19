package com.example.timercenter.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.timercenter.database.model.TimerGroupCrossRef

/**
 * Data Access Object для работы со связями между таймерами и группами
 */
@Dao
interface TimerGroupCrossRefDao {
    /**
     * Добавляет связь между таймером и группой
     * @param crossRef Связь для добавления
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(crossRef: TimerGroupCrossRef)

    /**
     * Удаляет связь между таймером и группой
     * @param crossRef Связь для удаления
     */
    @Delete
    suspend fun delete(crossRef: TimerGroupCrossRef)

    /**
     * Удаляет связь по ID таймера и группы
     * @param timerId ID таймера
     * @param groupId ID группы
     */
    @Query("DELETE FROM timer_group_cross_ref WHERE timerId = :timerId AND groupId = :groupId")
    suspend fun deleteByIds(timerId: Int, groupId: Int)

    /**
     * Удаляет все связи для группы
     * @param groupId ID группы
     */
    @Query("DELETE FROM timer_group_cross_ref WHERE groupId = :groupId")
    suspend fun deleteForGroup(groupId: Int)

    /**
     * Получает все группы для таймера
     * @param timerId ID таймера
     * @return Список связей с группами
     */
    @Query("SELECT * FROM timer_group_cross_ref WHERE timerId = :timerId")
    suspend fun getGroupsForTimer(timerId: Int): List<TimerGroupCrossRef>

    /**
     * Получает все таймеры в группе
     * @param groupId ID группы
     * @return Список связей с таймерами
     */
    @Query("SELECT * FROM timer_group_cross_ref WHERE groupId = :groupId")
    suspend fun getTimersForGroup(groupId: Int): List<TimerGroupCrossRef>
}