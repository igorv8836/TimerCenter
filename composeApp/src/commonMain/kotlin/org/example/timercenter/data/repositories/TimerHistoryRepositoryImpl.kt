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

    override suspend fun addRecord(name: String, lastStartedTime: Long) =
        withContext(ioDispatcher) {
            timerHistoryDao.insertRecord(
                TimerHistoryEntity(
                    name = name,
                    lastStartedTime = lastStartedTime
                )
            )
        }

    override suspend fun clearAll() = withContext(ioDispatcher) {
        timerHistoryDao.clearHistory()
    }
}