package com.divy.practicalkoin.repository

import com.divy.practicalkoin.models.DemoRoomDbModel


interface RoomRepo {
    suspend fun insertTask(taskModel: DemoRoomDbModel): Long
}
