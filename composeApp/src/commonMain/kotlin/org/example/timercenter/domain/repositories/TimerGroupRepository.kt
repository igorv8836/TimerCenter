package org.example.timercenter.domain.repositories

import com.example.timercenter.database.model.TimerEntity
import com.example.timercenter.database.model.TimerGroupEntity
import kotlinx.coroutines.flow.Flow

/**
 * Интерфейс репозитория для работы с группами таймеров
 * Определяет основные операции для управления группами таймеров
 */
interface TimerGroupRepository {
    /**
     * Получает список всех групп таймеров
     * @return Flow со списком всех групп
     */
    fun getAllGroups(): Flow<List<TimerGroupEntity>>

    /**
     * Получает группу по идентификатору
     * @param id Идентификатор группы
     * @return Группа или null, если не найдена
     */
    suspend fun getGroup(id: Int): TimerGroupEntity?

    /**
     * Создает новую группу таймеров
     * @param group Сущность группы для создания
     * @param timerIds Список идентификаторов таймеров для добавления в группу
     * @return Идентификатор созданной группы
     */
    suspend fun createGroup(
        group: TimerGroupEntity,
        timerIds: List<Int>,
    ): Int

    /**
     * Удаляет группу по идентификатору
     * @param id Идентификатор группы для удаления
     */
    suspend fun deleteGroup(id: Int)

    /**
     * Получает список таймеров в группе
     * @param id Идентификатор группы
     * @return Flow со списком таймеров в группе
     */
    fun getTimersInGroup(id: Int): Flow<List<TimerEntity>>

    /**
     * Обновляет существующую группу таймеров
     * @param group Обновленная сущность группы
     * @param timerIds Список идентификаторов таймеров для обновления в группе
     */
    suspend fun updateGroup(
        group: TimerGroupEntity,
        timerIds: List<Int>,
    )

    /**
     * Запускает все таймеры в группе
     * @param group Группа таймеров для запуска
     */
    suspend fun startGroup(group: TimerGroupEntity)

    /**
     * Останавливает все таймеры в группе
     * @param group Группа таймеров для остановки
     */
    suspend fun stopGroup(group: TimerGroupEntity)
}
