package com.example.riteshkumarsingh.weatherapplication

import android.app.Application
import com.facebook.stetho.Stetho
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber.DebugTree
import timber.log.Timber

/**
 * Created by riteshkumarsingh on 16/10/17.
 */
class WeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initLeakCanary()
        initTimber()
        initStetho()
    }

    private fun initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this);
    }

    private fun initStetho() {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}