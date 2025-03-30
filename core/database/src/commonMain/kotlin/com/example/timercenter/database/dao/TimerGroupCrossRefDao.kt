package com.example.timercenter.database.dao

import androidx.room.*
import com.example.timercenter.database.model.TimerGroupCrossRef

@Dao
interface TimerGroupCrossRefDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(crossRef: TimerGroupCrossRef)

    @Delete
    suspend fun delete(crossRef: TimerGroupCrossRef)

    @Query("DELETE FROM timer_group_cross_ref WHERE timerId = :timerId AND groupId = :groupId")
    suspend fun deleteByIds(timerId: Int, groupId: Int)

    @Query("DELETE FROM timer_group_cross_ref WHERE groupId = :groupId")
    suspend fun deleteForGroup(groupId: Int)

    @Query("SELECT * FROM timer_group_cross_ref WHERE timerId = :timerId")
    suspend fun getGroupsForTimer(timerId: Int): List<TimerGroupCrossRef>

    @Query("SELECT * FROM timer_group_cross_ref WHERE groupId = :groupId")
    suspend fun getTimersForGroup(groupId: Int): List<TimerGroupCrossRef>
}