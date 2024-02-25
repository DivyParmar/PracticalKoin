package com.divy.practicalkoin.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.divy.practicalkoin.models.DemoRoomDbModel

@Dao
interface AppDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(taskModel: DemoRoomDbModel): Long
}