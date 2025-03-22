package org.example.timercenter.domain.repositories

import com.example.timercenter.database.model.TimerHistoryEntity
import kotlinx.coroutines.flow.Flow

interface TimerHistoryRepository {
    fun getHistoryForTimer(timerId: Int): Flow<List<TimerHistoryEntity>>
    fun getAllHistory(): Flow<List<TimerHistoryEntity>>
//    suspend fun addRecord(name: String, lastStartedTime: Long): Long
    suspend fun clearAll()
}
