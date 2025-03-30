package org.example.timercenter.domain.repositories

import com.example.timercenter.database.model.TimerEntity
import com.example.timercenter.database.model.TimerGroupEntity
import kotlinx.coroutines.flow.Flow

interface TimerGroupRepository {
    fun getAllGroups(): Flow<List<TimerGroupEntity>>
    suspend fun getGroup(id: Int): TimerGroupEntity?
    suspend fun createGroup(
        group: TimerGroupEntity,
        timerIds: List<Int>,
    ): Int
    suspend fun deleteGroup(id: Int)
    fun getTimersInGroup(id: Int): Flow<List<TimerEntity>>
    suspend fun updateGroup(
        group: TimerGroupEntity,
        timerIds: List<Int>,
    )
    suspend fun startGroup(group: TimerGroupEntity)
    suspend fun stopGroup(group: TimerGroupEntity)
}
