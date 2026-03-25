package com.fadymary.gametime.network

import android.app.Application
import com.fadymary.gametime.network.di.appModule
import com.fadymary.network.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class GameTimeNetworkApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@GameTimeNetworkApp)
            modules(networkModule, appModule)
        }
    }
}