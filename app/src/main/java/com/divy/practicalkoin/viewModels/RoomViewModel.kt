package com.divy.practicalkoin.viewModels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.divy.practicalkoin.models.DemoRoomDbModel
import com.divy.practicalkoin.repository.RoomRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoomViewModel(application: Application, private val roomRepo: RoomRepo) : ViewModel() {

    val insertLiveData: MutableLiveData<Long> = MutableLiveData()

    fun add(taskModel: DemoRoomDbModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val insert = roomRepo.insertTask(taskModel)
            insertLiveData.postValue(insert)
        }
    }
}