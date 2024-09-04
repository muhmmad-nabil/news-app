package com.news

import android.annotation.SuppressLint
import android.app.Application
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.news.di.dataSourceModule
import com.news.di.repoModule
import com.news.di.useCaseModule
import com.news.di.viewModelModule
import io.reactivex.schedulers.Schedulers
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

        listenToNetworkConnectivity()

    }

    @SuppressLint("CheckResult")
    private fun listenToNetworkConnectivity() {
        ReactiveNetwork.observeInternetConnectivity().subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io()).subscribe { isConnected ->
                isInternetConnected = isConnected
            }
    }

    companion object {
        var isInternetConnected = false
    }
}