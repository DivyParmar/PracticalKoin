package com.divy.practicalkoin.utility

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import com.divy.practicalkoin.di.networkModule
import com.divy.practicalkoin.di.repositoryModule
import com.divy.practicalkoin.di.roomModule
import com.divy.practicalkoin.di.viewModelModule
import com.pixplicity.easyprefs.library.Prefs
import org.koin.android.ext.android.startKoin

class MyApplication : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        var context: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        context = this
        
        startKoin(
            this, arrayListOf(
                roomModule,
                viewModelModule,
                networkModule,
                repositoryModule
            )
        )

        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()
    }
}