package org.example.timercenter.data.repositories

import com.example.timercenter.database.dao.TimerDao
import com.example.timercenter.database.dao.TimerGroupDao
import com.example.timercenter.database.model.TimerEntity
import com.example.timercenter.database.model.TimerGroupEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.example.timercenter.domain.repositories.TimerGroupRepository

class TimerGroupRepositoryImpl(
    private val timerGroupDao: TimerGroupDao,
    private val timerDao: TimerDao,
    private val ioDispatcher: CoroutineDispatcher
) : TimerGroupRepository {

    override fun getAllGroups(): Flow<List<TimerGroupEntity>> = timerGroupDao.getAllGroupsFlow()

    override suspend fun getGroup(id: Int): TimerGroupEntity? = withContext(ioDispatcher) {
        timerGroupDao.getGroupById(id = id)
    }

    override suspend fun createGroup(group: TimerGroupEntity): Int = withContext(ioDispatcher) {
        timerGroupDao.insertGroup(group).toInt()
    }

    override suspend fun deleteGroup(id: Int): Unit = withContext(ioDispatcher) {
        timerGroupDao.getGroupById(id)?.let { timerGroupDao.deleteGroup(it) }
    }

    override fun getTimersInGroup(id: Int): Flow<List<TimerEntity>> =
        timerDao.getTimersByGroupFlow(id)

    override suspend fun updateGroup(group: TimerGroupEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun startGroup(group: TimerGroupEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun stopGroup(group: TimerGroupEntity) {
        TODO("Not yet implemented")
    }
}