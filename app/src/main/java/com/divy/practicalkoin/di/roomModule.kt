package com.divy.practicalkoin.di

import androidx.room.Room
import com.divy.practicalkoin.database.AppDatabase
import com.divy.practicalkoin.utility.AppConstant

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module

val roomModule = module {

    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            AppConstant.DB_NAME
        ).build()
    }

    single { get<AppDatabase>().appDao() }
}