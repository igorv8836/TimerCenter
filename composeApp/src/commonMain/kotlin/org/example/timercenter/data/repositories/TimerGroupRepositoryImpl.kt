package org.example.timercenter.data.repositories

import com.example.timercenter.database.dao.TimerDao
import com.example.timercenter.database.dao.TimerGroupCrossRefDao
import com.example.timercenter.database.dao.TimerGroupDao
import com.example.timercenter.database.model.TimerEntity
import com.example.timercenter.database.model.TimerGroupCrossRef
import com.example.timercenter.database.model.TimerGroupEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.example.timercenter.domain.repositories.TimerGroupRepository

/**
 * Реализация репозитория для работы с группами таймеров
 * Обеспечивает доступ к данным групп таймеров и управление ими
 * @property timerGroupDao DAO для работы с группами таймеров
 * @property timerDao DAO для работы с таймерами
 * @property timerGroupRefDao DAO для работы со связями таймеров и групп
 * @property ioDispatcher Диспетчер для выполнения операций ввода-вывода
 */
class TimerGroupRepositoryImpl(
    private val timerGroupDao: TimerGroupDao,
    private val timerDao: TimerDao,
    private val timerGroupRefDao: TimerGroupCrossRefDao,
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
    override fun getTimersInGroup(id: Int): Flow<List<TimerEntity>> =
        timerDao.getTimersByGroupFlow(id)

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
    override suspend fun startGroup(group: TimerGroupEntity) {
        TODO("Not yet implemented")
    }

    /**
     * Останавливает все таймеры в группе
     * @param group Группа таймеров для остановки
     */
    override suspend fun stopGroup(group: TimerGroupEntity) {
        TODO("Not yet implemented")
    }
}