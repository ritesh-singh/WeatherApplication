package com.example.riteshkumarsingh.weatherapplication

import android.app.Application
import timber.log.Timber.DebugTree
import timber.log.Timber


/**
 * Created by riteshkumarsingh on 16/10/17.
 */
class WeatherApplication : Application() {

  companion object {
    lateinit var instance: WeatherApplication
  }


  override fun onCreate() {
    super.onCreate()
    instance = this
    initTimber()
  }

  fun initTimber() {
    if (BuildConfig.DEBUG) {
      Timber.plant(DebugTree())
    }
  }
}