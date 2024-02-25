package com.divy.practicalkoin.di

import com.divy.practicalkoin.viewModels.HomeApiViewModel
import com.divy.practicalkoin.viewModels.RoomViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {
    viewModel { RoomViewModel(androidApplication(),get()) }
    viewModel { HomeApiViewModel(get()) }
}