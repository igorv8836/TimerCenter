package org.example.timercenter.data.repositories

import com.example.timercenter.database.dao.TimerGroupHistoryDao
import com.example.timercenter.database.model.TimerGroupHistoryEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.example.timercenter.domain.repositories.TimerGroupHistoryRepository
import org.example.timercenter.domain.repositories.TimerGroupRepository
import org.example.timercenter.ui.model.TimerHistoryModel

class TimerGroupHistoryRepositoryImpl(
    private val timerGroupRepository: TimerGroupRepository,
    private val timerGroupHistoryDao: TimerGroupHistoryDao,
    private val ioDispatcher: CoroutineDispatcher
) : TimerGroupHistoryRepository {

    override fun getHistoryForTimerGroup(timerGroupId: Int): Flow<List<TimerGroupHistoryEntity>> =
        timerGroupHistoryDao.getHistoryForTimerGroupFlow(timerGroupId)

    override fun getAllHistory(): Flow<List<TimerHistoryModel>> =
        timerGroupHistoryDao.getAllHistoryFlow().map { groups ->
            groups.mapNotNull { groupHistory ->
                timerGroupRepository.getGroup(groupHistory.id)?.let { group ->
                    TimerHistoryModel(
                        id = groupHistory.timerGroupId,
                        name = "Группа: " + group.name,
                        lastStartedTime = groupHistory.lastStartedTime,
                        isTimer = false,
                    )
                }
            }
        }

    override suspend fun addRecord(groupTimerId: Int, lastStartedTime: Long): Long =
        withContext(ioDispatcher) {
            timerGroupHistoryDao.insertRecord(
                TimerGroupHistoryEntity(
                    timerGroupId = groupTimerId,
                    lastStartedTime = lastStartedTime
                )
            )
        }

    override suspend fun clearAll() = withContext(ioDispatcher) {
        timerGroupHistoryDao.clearHistory()
    }
}