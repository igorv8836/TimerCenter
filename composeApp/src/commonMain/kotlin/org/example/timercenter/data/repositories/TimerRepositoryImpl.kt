package org.example.timercenter.data.repositories

import com.example.timercenter.database.dao.TimerDao
import com.example.timercenter.database.dao.TimerHistoryDao
import com.example.timercenter.database.model.TimerEntity
import com.example.timercenter.database.model.TimerHistoryEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import org.example.timercenter.data.scheduler.TimerScheduler
import org.example.timercenter.domain.repositories.TimerRepository

class TimerRepositoryImpl(
    private val timerDao: TimerDao,
    private val timerHistoryDao: TimerHistoryDao,
    private val timerScheduler: TimerScheduler,
    private val ioDispatcher: CoroutineDispatcher
) : TimerRepository {

    override fun getAllTimers(): Flow<List<TimerEntity>> = timerDao.getAllTimersFlow()

    override suspend fun getTimer(id: Int): TimerEntity? = withContext(ioDispatcher) {
        timerDao.getTimerById(id)
    }

    override suspend fun createTimer(timer: TimerEntity): Int = withContext(ioDispatcher) {
        timerDao.insertTimer(timer).toInt()
    }

    override suspend fun updateTimer(timer: TimerEntity) = withContext(ioDispatcher) {
        timerDao.updateTimer(timer)
    }

    override suspend fun deleteTimer(id: Int): Unit = withContext(ioDispatcher) {
        timerDao.getTimerById(id)?.let { timerDao.deleteTimer(it) }
    }

    override suspend fun startTimer(timer: TimerEntity) = withContext(ioDispatcher) {
        val currentTime = Clock.System.now().toEpochMilliseconds()
        val updatedTimer = timer.copy(isRunning = true, startTime = currentTime)
        timerDao.updateTimer(updatedTimer)
        timerHistoryDao.insertRecord(
            TimerHistoryEntity(
                name = timer.name,
                lastStartedTime = currentTime
            )
        )
        timerScheduler.scheduleTimer(updatedTimer.id, updatedTimer.durationMillis)
    }

    override suspend fun stopTimer(timer: TimerEntity) = withContext(ioDispatcher) {
        timerScheduler.cancelTimer(timer.id)
        val updatedTimer = timer.copy(isRunning = false, startTime = null)
        timerDao.updateTimer(updatedTimer)
    }
}