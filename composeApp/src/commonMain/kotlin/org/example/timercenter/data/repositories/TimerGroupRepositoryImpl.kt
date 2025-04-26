package org.example.timercenter.data.repositories

import com.example.timercenter.database.dao.TimerDao
import com.example.timercenter.database.dao.TimerGroupCrossRefDao
import com.example.timercenter.database.dao.TimerGroupDao
import com.example.timercenter.database.dao.TimerRunDao
import com.example.timercenter.database.model.TimerEntity
import com.example.timercenter.database.model.TimerGroupCrossRef
import com.example.timercenter.database.model.TimerGroupEntity
import com.example.timercenter.database.model.TimerRunEntity
import com.example.timercenter.database.model.TimerStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import org.example.timercenter.data.scheduler.TimerScheduler
import org.example.timercenter.domain.repositories.TimerGroupHistoryRepository
import org.example.timercenter.domain.repositories.TimerGroupRepository
import org.example.timercenter.ui.model.GroupType
import org.example.timercenter.ui.model.TimerUiModel
import org.example.timercenter.ui.model.toGroupType

/**
 * Реализация репозитория для работы с группами таймеров
 * Обеспечивает доступ к данным групп таймеров и управление ими
 * @property timerGroupDao DAO для работы с группами таймеров
 * @property timerDao DAO для работы с таймерами
 * @property timerGroupRefDao DAO для работы со связями таймеров и групп
 * @property timerRunDao DAO для работы с записями о запусках таймеров
 * @property ioDispatcher Диспетчер для выполнения операций ввода-вывода
 */
class TimerGroupRepositoryImpl(
    private val timerGroupDao: TimerGroupDao,
    private val timerDao: TimerDao,
    private val timerGroupRefDao: TimerGroupCrossRefDao,
    private val timerRunDao: TimerRunDao,
    private val timerGroupHistoryRepository: Lazy<TimerGroupHistoryRepository>,
    private val timerScheduler: TimerScheduler,
    private val ioDispatcher: CoroutineDispatcher
) : TimerGroupRepository {

    /**
     * Получает список всех групп таймеров
     * @return Flow со списком всех групп таймеров
     */
    override fun getAllGroups(): Flow<List<TimerGroupEntity>> = timerGroupDao.getAllGroupsFlow()

    /**
     * Получает группу по идентификатору
     * @param id Идентификатор группы
     * @return Группа или null, если не найдена
     */
    override suspend fun getGroup(id: Int): TimerGroupEntity? = withContext(ioDispatcher) {
        timerGroupDao.getGroupById(id)
    }

    /**
     * Создает новую группу
     * @param group Сущность группы для создания
     * @return Идентификатор созданной группы
     */
    override suspend fun createGroup(
        group: TimerGroupEntity,
        timerIds: List<Int>,
    ): Int = withContext(ioDispatcher) {
        val id = timerGroupDao.insertGroup(group).toInt()
        timerIds.forEach {
            timerGroupRefDao.insert(
                TimerGroupCrossRef(
                    timerId = it,
                    groupId = id
                )
            )
        }
        return@withContext id
    }

    /**
     * Удаляет группу по идентификатору
     * @param id Идентификатор группы для удаления
     */
    override suspend fun deleteGroup(id: Int): Unit = withContext(ioDispatcher) {
        timerGroupDao.getGroupById(id)?.let { timerGroupDao.deleteGroup(it) }
    }

    /**
     * Получает список таймеров в группе
     * @param id Идентификатор группы
     * @return Flow со списком таймеров в группе
     */
    override fun getTimersInGroup(id: Int): Flow<List<TimerUiModel>> =
        timerDao.getTimersByGroupFlow(id).map {
            it.map { timer ->
                val timerRun = timer.currentRunId?.let { timerRun ->
                    timerRunDao.getRunById(timerRun)
                }

                TimerUiModel(
                    id = timer.id,
                    timerName = timer.name,
                    totalTime = timer.durationMillis,
                    groupId = id,
                    isRunning = timerRun?.isRunning ?: false,
                    lastStartedTime = timerRun?.startTime ?: 0L,
                    remainingMillis = timerRun?.remainingMillis ?: timer.durationMillis,
                )
            }
        }

    /**
     * Обновляет существующую группу таймеров
     * @param group Обновленная сущность группы
     * @param timerIds Список идентификаторов таймеров для обновления в группе
     */
    override suspend fun updateGroup(
        group: TimerGroupEntity,
        timerIds: List<Int>,
    ) = withContext(ioDispatcher) {
        timerGroupDao.updateGroup(group)
        timerGroupRefDao.deleteForGroup(groupId = group.id)

        timerIds.forEach {
            timerGroupRefDao.insert(
                TimerGroupCrossRef(
                    timerId = it,
                    groupId = group.id
                )
            )
        }
    }

    /**
     * Запускает все таймеры в группе
     * @param group Группа таймеров для запуска
     */
    override suspend fun startGroup(groupId: Int) = withContext(ioDispatcher) {
        val timerGroup = timerGroupDao.getGroupById(groupId) ?: return@withContext
        val currentTime = Clock.System.now().toEpochMilliseconds()
        timerGroupDao.updateGroup(timerGroup.copy(isRunning = true, lastStartedTime = currentTime))
        val timers = timerDao.getTimersByGroup(groupId)
        var sum = 0L
        timers.forEach { timer ->
            timer.currentRunId?.let { runId ->
                val run = timerRunDao.getRunById(runId = runId) ?: return@forEach
                timerRunDao.updateRun(
                    run.copy(
                        startTime = currentTime,
                        isRunning = true,
                        status = TimerStatus.RUNNING,
                    )
                )
            } ?: run {
                val runId = timerRunDao.insertRun(
                    TimerRunEntity(
                        timerId = timer.id,
                        groupId = groupId,
                        startTime = currentTime,
                        remainingMillis = timer.durationMillis,
                        isRunning = true,
                        status = TimerStatus.RUNNING
                    )
                ).toInt()
                timerDao.updateTimer(timer.copy(currentRunId = runId))
            }
            timerScheduler.scheduleTimer(
                timerId = timerDao.getTimerById(timer.id)?.getTimerSchedulerId() ?: 0,
                timerName = timer.name,
                delayMillis = when (timerGroup.groupType.toGroupType()) {
                    GroupType.DELAY -> timer.remainingMillis + timerGroup.delayTime
                    GroupType.PARALLEL -> timer.remainingMillis
                    GroupType.CONSISTENT -> {
                        sum += timer.remainingMillis
                        sum
                    }
                }
            )
        }

        timerGroupHistoryRepository.value.addRecord(
            groupTimerId = timerGroup.id,
            lastStartedTime = currentTime,
        )
    }

    /**
     * Останавливает все таймеры в группе
     * @param group Группа таймеров для остановки
     */
    override suspend fun stopGroup(groupId: Int) = withContext(ioDispatcher) {
        val timerGroup = timerGroupDao.getGroupById(groupId) ?: return@withContext
        timerGroupDao.updateGroup(timerGroup.copy(isRunning = false))
        val timers = timerDao.getTimersByGroup(groupId)
        timers.forEach { timer ->
            timerRunDao.getRunById(timer.currentRunId ?: return@forEach)?.let { run ->
                timerRunDao.updateRun(
                    run.copy(
                        isRunning = false,
                        status = TimerStatus.NOT_STARTED,
                        remainingMillis = timer.durationMillis,
                    )
                )
            }
            timerScheduler.cancelTimer(timer.getTimerSchedulerId())
            timerDao.updateTimer(timer.copy(currentRunId = null))
        }
    }

    /**
     * Приостанавливает все таймеры в группе
     * @param groupId Идентификатор группы таймеров для паузы
     */
    override suspend fun pauseGroup(groupId: Int) = withContext(ioDispatcher) {
        val group = timerGroupDao.getGroupById(groupId) ?: return@withContext
        timerGroupDao.updateGroup(group.copy(isRunning = false))
        val currentTime = Clock.System.now().toEpochMilliseconds()
        val timers = timerDao.getTimersByGroup(groupId)

        when (group.groupType.toGroupType()) {
            GroupType.CONSISTENT -> {
                var elapsedTime = currentTime - group.lastStartedTime

                timers.forEach { timer ->
                    timerScheduler.cancelTimer(timer.getTimerSchedulerId())
                    val run = timerRunDao.getRunById(timer.currentRunId ?: return@forEach)
                    run?.let {
                        timerRunDao.updateRun(
                            run.copy(
                                remainingMillis = maxOf(
                                    0L,
                                    it.remainingMillis - elapsedTime,
                                ),
                                status = TimerStatus.PAUSED,
                            )
                        )
                        elapsedTime -= timer.remainingMillis
                        if (elapsedTime <= 0) return@withContext
                    }
                }
            }

            else -> {
                timers.forEach { timer ->
                    val run = timerRunDao.getRunById(timer.currentRunId ?: return@forEach)
                    run?.let {
                        val elapsed = currentTime - it.startTime
                        val newRemaining = maxOf(0L, it.remainingMillis - elapsed)
                        timerRunDao.updateRun(
                            it.copy(
                                isRunning = false,
                                status = TimerStatus.PAUSED,
                                startTime = currentTime,
                                remainingMillis = newRemaining
                            )
                        )
                    }
                    timerScheduler.cancelTimer(timer.getTimerSchedulerId())
                }
            }
        }
    }

    /**
     * Создает копию существующуй группы
     * @param groupId Идентификатор группы для копирования
     */
    override suspend fun copyGroup(groupId: Int) {
        getGroup(groupId)?.let { group ->
            val newId = timerGroupDao.insertGroup(
                group.copy(
                    id = 0,
                    isRunning = false,
                )
            ).toInt()

            timerGroupRefDao.getTimersForGroup(groupId).map { timer ->
                timerGroupRefDao.insert(
                    TimerGroupCrossRef(
                        timerId = timer.timerId,
                        groupId = newId,
                    )
                )
            }

            timerRunDao.getRunsForGroup(groupId).first().map { run ->
                timerRunDao.insertRun(
                    run.copy(
                        id = 0,
                        groupId = newId,
                        isRunning = false,
                        status = TimerStatus.NOT_STARTED,
                    )
                )
            }

            startGroup(newId)
        }
    }

    private fun TimerEntity.getTimerSchedulerId(): Int {
        return currentRunId?.let { id * 100000 + it } ?: id
    }
}