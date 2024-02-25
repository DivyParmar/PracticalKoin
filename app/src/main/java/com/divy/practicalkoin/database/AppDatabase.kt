package com.divy.practicalkoin.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.divy.practicalkoin.models.DemoRoomDbModel

@Database(entities = [DemoRoomDbModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao
}