package org.example.timercenter.data.repositories

import com.example.timercenter.database.dao.TimerHistoryDao
import com.example.timercenter.database.model.TimerHistoryEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.example.timercenter.domain.repositories.TimerHistoryRepository

/**
 * Реализация репозитория для работы с историей таймеров
 * Обеспечивает доступ к данным истории выполнения таймеров
 * @property timerHistoryDao DAO для работы с историей таймеров
 * @property ioDispatcher Диспетчер для выполнения операций ввода-вывода
 */
class TimerHistoryRepositoryImpl(
    private val timerHistoryDao: TimerHistoryDao,
    private val ioDispatcher: CoroutineDispatcher
) : TimerHistoryRepository {

    /**
     * Получает историю для конкретного таймера
     * @param timerId Идентификатор таймера
     * @return Flow со списком записей истории для указанного таймера
     */
    override fun getHistoryForTimer(timerId: Int): Flow<List<TimerHistoryEntity>> =
        timerHistoryDao.getHistoryForTimerFlow(timerId)

    /**
     * Получает всю историю таймеров
     * @return Flow со списком всех записей истории
     */
    override fun getAllHistory(): Flow<List<TimerHistoryEntity>> = timerHistoryDao.getAllHistoryFlow()

//    override suspend fun addRecord(name: String, lastStartedTime: Long) =
//        withContext(ioDispatcher) {
//            timerHistoryDao.insertRecord(
//                TimerHistoryEntity(
//                    name = name,
//                    lastStartedTime = lastStartedTime
//                )
//            )
//        }

    /**
     * Очищает всю историю таймеров
     */
    override suspend fun clearAll() = withContext(ioDispatcher) {
        timerHistoryDao.clearHistory()
    }
}