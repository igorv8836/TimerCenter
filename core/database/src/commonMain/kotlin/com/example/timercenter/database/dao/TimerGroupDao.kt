package com.example.timercenter.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.timercenter.database.model.TimerGroupEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object для работы с группами таймеров
 */
@Dao
interface TimerGroupDao {
    /**
     * Получает все группы таймеров
     * @return Список всех групп
     */
    @Query("SELECT * FROM timer_groups")
    suspend fun getAllGroups(): List<TimerGroupEntity>

    /**
     * Получает все группы таймеров как Flow
     * @return Flow со списком всех групп
     */
    @Query("SELECT * FROM timer_groups")
    fun getAllGroupsFlow(): Flow<List<TimerGroupEntity>>

    /**
     * Получает группу по ID
     * @param id ID группы
     * @return Группа или null, если не найдена
     */
    @Query("SELECT * FROM timer_groups WHERE id = :id")
    suspend fun getGroupById(id: Int): TimerGroupEntity?

    /**
     * Добавляет новую группу
     * @param group Группа для добавления
     * @return ID добавленной группы
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroup(group: TimerGroupEntity): Long

    /**
     * Обновляет существующую группу
     * @param group Группа для обновления
     */
    @Update
    suspend fun updateGroup(group: TimerGroupEntity)

    /**
     * Удаляет группу
     * @param group Группа для удаления
     */
    @Delete
    suspend fun deleteGroup(group: TimerGroupEntity)
}
