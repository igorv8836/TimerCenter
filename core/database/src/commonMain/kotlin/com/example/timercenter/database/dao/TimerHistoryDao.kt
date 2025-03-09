package com.example.timercenter.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.timercenter.database.model.TimerHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TimerHistoryDao {
    @Query("SELECT * FROM timer_history WHERE timerId = :timerId")
    suspend fun getHistoryForTimer(timerId: Int): List<TimerHistoryEntity>

    @Query("SELECT * FROM timer_history WHERE timerId = :timerId")
    fun getHistoryForTimerFlow(timerId: Int): Flow<List<TimerHistoryEntity>>

    @Query("SELECT * FROM timer_history")
    suspend fun getAllHistory(): List<TimerHistoryEntity>

    @Query("SELECT * FROM timer_history")
    fun getAllHistoryFlow(): Flow<List<TimerHistoryEntity>>

    @Insert
    suspend fun insertRecord(record: TimerHistoryEntity): Long

    @Query("DELETE FROM timer_history")
    suspend fun clearHistory()
}
