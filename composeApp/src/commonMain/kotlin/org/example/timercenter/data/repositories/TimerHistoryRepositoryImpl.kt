package org.example.timercenter.data.repositories

import com.example.timercenter.database.dao.TimerHistoryDao
import com.example.timercenter.database.model.TimerHistoryEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.example.timercenter.domain.repositories.TimerHistoryRepository

class TimerHistoryRepositoryImpl(
    private val timerHistoryDao: TimerHistoryDao,
    private val ioDispatcher: CoroutineDispatcher
) : TimerHistoryRepository {

    override fun getHistoryForTimer(timerId: Int): Flow<List<TimerHistoryEntity>> =
        timerHistoryDao.getHistoryForTimerFlow(timerId)

    override fun getAllHistory(): Flow<List<TimerHistoryEntity>> = timerHistoryDao.getAllHistoryFlow()

    override suspend fun addRecord(
        timerId: Int,
        startTime: Long,
        endTime: Long?,
        completed: Boolean
    ) = withContext(ioDispatcher) {
        timerHistoryDao.insertRecord(
            TimerHistoryEntity(
                timerId = timerId,
                startTime = startTime,
                endTime = endTime,
                completed = completed
            )
        )
    }

    override suspend fun clearAll() = withContext(ioDispatcher) {
        timerHistoryDao.clearHistory()
    }
}