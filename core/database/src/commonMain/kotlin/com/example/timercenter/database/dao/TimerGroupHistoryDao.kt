package com.example.timercenter.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.timercenter.database.model.TimerGroupHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TimerGroupHistoryDao {
    @Query("SELECT * FROM timer_group_history WHERE timerGroupId = :timerGroupId")
    suspend fun getHistoryForTimerGroup(timerGroupId: Int): List<TimerGroupHistoryEntity>

    @Query("SELECT * FROM timer_group_history WHERE timerGroupId = :timerGroupId")
    fun getHistoryForTimerGroupFlow(timerGroupId: Int): Flow<List<TimerGroupHistoryEntity>>

    @Query("SELECT * FROM timer_group_history")
    suspend fun getAllHistory(): List<TimerGroupHistoryEntity>

    @Query("SELECT * FROM timer_group_history")
    fun getAllHistoryFlow(): Flow<List<TimerGroupHistoryEntity>>

    @Insert
    suspend fun insertRecord(record: TimerGroupHistoryEntity): Long

    @Query("DELETE FROM timer_group_history")
    suspend fun clearHistory()
}
