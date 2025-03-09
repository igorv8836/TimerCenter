package com.example.timercenter.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.timercenter.database.model.TimerGroupEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TimerGroupDao {
    @Query("SELECT * FROM timer_groups")
    suspend fun getAllGroups(): List<TimerGroupEntity>

    @Query("SELECT * FROM timer_groups")
    fun getAllGroupsFlow(): Flow<List<TimerGroupEntity>>

    @Query("SELECT * FROM timer_groups WHERE id = :id")
    suspend fun getGroupById(id: Int): TimerGroupEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroup(group: TimerGroupEntity): Long

    @Update
    suspend fun updateGroup(group: TimerGroupEntity)

    @Delete
    suspend fun deleteGroup(group: TimerGroupEntity)
}
