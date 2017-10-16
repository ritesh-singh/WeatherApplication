package com.example.riteshkumarsingh.weatherapplication

import android.app.Application
import timber.log.Timber.DebugTree
import timber.log.Timber



/**
 * Created by riteshkumarsingh on 16/10/17.
 */
class WeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    fun initTimber(){
        if(BuildConfig.DEBUG){
            Timber.plant(DebugTree())
        }
    }
}