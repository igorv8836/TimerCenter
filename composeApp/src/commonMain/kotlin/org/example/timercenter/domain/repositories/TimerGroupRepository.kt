package org.example.timercenter.domain.repositories

import com.example.timercenter.database.model.TimerEntity
import com.example.timercenter.database.model.TimerGroupEntity
import kotlinx.coroutines.flow.Flow

interface TimerGroupRepository {
    fun getAllGroups(): Flow<List<TimerGroupEntity>>
    suspend fun createGroup(name: String): Int
    suspend fun deleteGroup(id: Int)
    fun getTimersInGroup(groupId: Int): Flow<List<TimerEntity>>
}
