package org.example.timercenter.data.repositories

import com.example.timercenter.database.dao.TimerDao
import com.example.timercenter.database.dao.TimerHistoryDao
import com.example.timercenter.database.model.TimerEntity
import com.example.timercenter.database.model.TimerStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import org.example.timercenter.data.scheduler.TimerScheduler
import org.example.timercenter.domain.repositories.TimerRepository

/**
 * Реализация репозитория для работы с таймерами
 * Обеспечивает доступ к данным таймеров и управление их состоянием
 * @property timerDao DAO для работы с таймерами в базе данных
 * @property timerHistoryDao DAO для работы с историей таймеров
 * @property timerScheduler Планировщик для управления таймерами
 * @property ioDispatcher Диспетчер для выполнения операций ввода-вывода
 */
class TimerRepositoryImpl(
    private val timerDao: TimerDao,
    private val timerHistoryDao: TimerHistoryDao,
    private val timerScheduler: TimerScheduler,
    private val ioDispatcher: CoroutineDispatcher
) : TimerRepository {

    /**
     * Получает список всех таймеров
     * @return Flow со списком всех таймеров
     */
    override fun getAllTimers(): Flow<List<TimerEntity>> = timerDao.getAllTimersFlow()

    /**
     * Получает таймер по идентификатору
     * @param id Идентификатор таймера
     * @return Таймер или null, если не найден
     */
    override suspend fun getTimer(id: Int): TimerEntity? = withContext(ioDispatcher) {
        timerDao.getTimerById(id)
    }

    /**
     * Создает новый таймер
     * @param timer Сущность таймера для создания
     * @return Идентификатор созданного таймера
     */
    override suspend fun createTimer(timer: TimerEntity): Int = withContext(ioDispatcher) {
        timerDao.insertTimer(timer).toInt()
    }

    /**
     * Обновляет существующий таймер
     * @param timer Обновленная сущность таймера
     */
    override suspend fun updateTimer(timer: TimerEntity) = withContext(ioDispatcher) {
        timerDao.updateTimer(timer)
    }

    /**
     * Обновляет идентификатор группы для таймера
     * @param timerId Идентификатор таймера
     * @param groupId Идентификатор группы
     */
    override suspend fun updateTimerInGroupId(timerId: Int, groupId: Int) {
//        timerDao.updateTimerInGroupId(timerId = timerId, groupId = groupId)
    }

    /**
     * Сбрасывает идентификатор группы для таймера
     * @param timerId Идентификатор таймера
     * @param groupId Идентификатор группы
     */
    override suspend fun resetTimerInGroupId(timerId: Int, groupId: Int) {
//        timerDao.resetTimerInGroupId(timerId = timerId, groupId = groupId)
    }

    /**
     * Удаляет таймер по идентификатору
     * @param id Идентификатор таймера для удаления
     */
    override suspend fun deleteTimer(id: Int): Unit = withContext(ioDispatcher) {
        timerDao.getTimerById(id)?.let { timerDao.deleteTimer(it) }
    }

    /**
     * Запускает таймер
     * @param id Идентификатор таймера
     */
    override suspend fun startTimer(id: Int) = withContext(ioDispatcher) {
        val currentTime = Clock.System.now().toEpochMilliseconds()
        val timer = timerDao.getTimerById(id) ?: return@withContext

        val updatedTimer = when (timer.status) {
            TimerStatus.NOT_STARTED -> {
                timer.copy(
                    isRunning = true,
                    startTime = currentTime,
                    remainingMillis = timer.durationMillis,
                    status = TimerStatus.RUNNING,
                )
            }
            TimerStatus.PAUSED -> {
                timer.copy(
                    isRunning = true,
                    startTime = currentTime,
                    remainingMillis = timer.remainingMillis - (currentTime - timer.startTime!!),
                    status = TimerStatus.RUNNING,
                )
            }
            else -> {
                null
            }
        } ?: timer

        timerDao.updateTimer(updatedTimer)
        timerScheduler.scheduleTimer(updatedTimer.id, updatedTimer.durationMillis)
    }

    /**
     * Останавливает таймер
     * @param id Идентификатор таймера
     */
    override suspend fun stopTimer(id: Int) = withContext(ioDispatcher) {
        val timer = timerDao.getTimerById(id) ?: return@withContext
        timerScheduler.cancelTimer(id)

        val updatedTimer = timer.copy(
            isRunning = false,
            startTime = null,
            status = TimerStatus.NOT_STARTED,
            remainingMillis = timer.durationMillis,
        )

        timerDao.updateTimer(updatedTimer)
    }

    /**
     * Приостанавливает таймер
     * @param id Идентификатор таймера
     */
    override suspend fun pauseTimer(id: Int) = withContext(ioDispatcher) {
        val currentTime = Clock.System.now().toEpochMilliseconds()
        val timer = timerDao.getTimerById(id) ?: return@withContext
        timerScheduler.cancelTimer(id)

        val updatedTimer = when (timer.status) {
            TimerStatus.RUNNING -> {
                timer.startTime?.let { startTime ->
                    val elapsed = currentTime - startTime
                    val newRemaining = maxOf(0, timer.remainingMillis - elapsed)

                    timer.copy(
                        isRunning = false,
                        status = TimerStatus.PAUSED,
                        remainingMillis = newRemaining,
                        startTime = currentTime
                    )
                } ?: timer
            }
            else -> timer
        }

        timerDao.updateTimer(updatedTimer)
    }

    /**
     * Создает копию существующего таймера
     * @param id Идентификатор таймера для копирования
     */
    override suspend fun copyTimer(id: Int) {
        val currentTime = Clock.System.now().toEpochMilliseconds()
        timerDao.getTimerById(id)?.let {
            timerDao.insertTimer(
                it.copy(
                    id = 0,
                    remainingMillis = it.durationMillis,
                    isRunning = true,
                    startTime = currentTime,
                    status = TimerStatus.RUNNING,
                )
            )
        }
    }
}