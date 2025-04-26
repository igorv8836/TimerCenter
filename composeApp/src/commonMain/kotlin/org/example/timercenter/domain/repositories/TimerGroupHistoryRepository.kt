package org.example.timercenter.domain.repositories

import com.example.timercenter.database.model.TimerGroupHistoryEntity
import kotlinx.coroutines.flow.Flow
import org.example.timercenter.ui.model.TimerHistoryModel

interface TimerGroupHistoryRepository {

    fun getHistoryForTimerGroup(timerGroupId: Int): Flow<List<TimerGroupHistoryEntity>>

    fun getAllHistory(): Flow<List<TimerHistoryModel>>

    suspend fun clearAll()

    suspend fun addRecord(groupTimerId: Int, lastStartedTime: Long): Long
}
