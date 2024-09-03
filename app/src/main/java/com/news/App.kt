package com.news

import android.app.Application
import com.news.di.dataSourceModule
import com.news.di.repoModule
import com.news.di.useCaseModule
import com.news.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(repoModule, dataSourceModule, useCaseModule, viewModelModule)
        }
    }
}