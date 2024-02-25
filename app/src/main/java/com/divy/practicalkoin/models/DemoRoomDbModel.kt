package com.divy.practicalkoin.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.divy.practicalkoin.utility.AppConstant

@Entity(tableName = AppConstant.TABLE_TASK)
data class DemoRoomDbModel(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var name: String,
)