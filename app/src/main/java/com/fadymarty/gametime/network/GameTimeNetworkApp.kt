package com.fadymarty.gametime.network

import android.app.Application
import com.fadymarty.network.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class GameTimeNetworkApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@GameTimeNetworkApp)
            modules(networkModule)
        }
    }
}