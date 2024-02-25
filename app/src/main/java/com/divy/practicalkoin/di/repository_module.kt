package com.divy.practicalkoin.di

import com.divy.practicalkoin.repository.HomeApiRepo
import com.divy.practicalkoin.repository.HomeApiRepository
import com.divy.practicalkoin.repository.RoomRepo
import com.divy.practicalkoin.repository.RoomRepository
import org.koin.dsl.module.module

/* Created Koin module for Repository class */

val repositoryModule = module {
    single<RoomRepo> { RoomRepository(get()) }
    single<HomeApiRepo> { HomeApiRepository(get()) }
}