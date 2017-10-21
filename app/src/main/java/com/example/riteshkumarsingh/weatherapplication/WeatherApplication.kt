package com.example.riteshkumarsingh.weatherapplication

import android.app.Application
import android.content.Context
import timber.log.Timber.DebugTree
import timber.log.Timber
import android.net.ConnectivityManager


/**
 * Created by riteshkumarsingh on 16/10/17.
 */
class WeatherApplication : Application() {

  companion object {
    lateinit var instance: WeatherApplication

    fun checkIfHasNetwork(): Boolean {
      val cm = this.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
      val networkInfo = cm.activeNetworkInfo
      return networkInfo != null && networkInfo.isConnected
    }
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