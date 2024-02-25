package com.divy.practicalkoin.repository

import com.divy.practicalkoin.database.AppDao
import com.divy.practicalkoin.models.DemoRoomDbModel


class RoomRepository(private val appDao: AppDao) : RoomRepo {

    override suspend fun insertTask(taskModel: DemoRoomDbModel): Long {
        return appDao.insertTask(taskModel)
    }
}
