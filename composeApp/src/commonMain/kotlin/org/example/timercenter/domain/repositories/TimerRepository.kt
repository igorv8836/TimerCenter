package org.example.timercenter.domain.repositories

import com.example.timercenter.database.model.TimerEntity
import kotlinx.coroutines.flow.Flow

interface TimerRepository {
    fun getAllTimers(): Flow<List<TimerEntity>>
    suspend fun getTimer(id: Int): TimerEntity?
    suspend fun createTimer(timer: TimerEntity): Int
    suspend fun updateTimer(timer: TimerEntity)
    suspend fun deleteTimer(id: Int)
    suspend fun startTimer(timer: TimerEntity)
    suspend fun stopTimer(timer: TimerEntity)
}
