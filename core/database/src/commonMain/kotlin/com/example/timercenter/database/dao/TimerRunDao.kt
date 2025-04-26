package com.example.timercenter.database.dao

import androidx.room.*
import com.example.timercenter.database.model.TimerRunEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TimerRunDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRun(run: TimerRunEntity): Long

    @Update
    suspend fun updateRun(run: TimerRunEntity)

    @Query("SELECT * FROM timer_runs WHERE id = :runId")
    suspend fun getRunById(runId: Int): TimerRunEntity?

    @Query("SELECT * FROM timer_runs WHERE timerId = :timerId")
    fun getRunsForTimer(timerId: Int): Flow<List<TimerRunEntity>>

    @Query("SELECT * FROM timer_runs WHERE groupId = :groupId")
    fun getRunsForGroup(groupId: Int): Flow<List<TimerRunEntity>>
} 