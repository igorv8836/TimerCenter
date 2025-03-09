package com.example.timercenter.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.timercenter.database.model.TimerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TimerDao {
    @Query("SELECT * FROM timers")
    suspend fun getAllTimers(): List<TimerEntity>

    @Query("SELECT * FROM timers")
    fun getAllTimersFlow(): Flow<List<TimerEntity>>

    @Query("SELECT * FROM timers WHERE id = :id")
    suspend fun getTimerById(id: Int): TimerEntity?

    @Query("SELECT * FROM timers WHERE groupId = :groupId")
    suspend fun getTimersByGroup(groupId: Int): List<TimerEntity>

    @Query("SELECT * FROM timers WHERE groupId = :groupId")
    fun getTimersByGroupFlow(groupId: Int): Flow<List<TimerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimer(timer: TimerEntity): Long

    @Update
    suspend fun updateTimer(timer: TimerEntity)

    @Delete
    suspend fun deleteTimer(timer: TimerEntity)
}
