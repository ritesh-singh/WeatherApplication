package com.example.riteshkumarsingh.weatherapplication

import android.app.Application
import android.content.Context
import timber.log.Timber.DebugTree
import timber.log.Timber
import android.net.ConnectivityManager
import com.example.riteshkumarsingh.weatherapplication.di.components.ApplicationComponent
import com.example.riteshkumarsingh.weatherapplication.di.components.DaggerApplicationComponent
import com.example.riteshkumarsingh.weatherapplication.di.modules.ApplicationModule


/**
 * Created by riteshkumarsingh on 16/10/17.
 */
class WeatherApplication : Application() {

  lateinit var applicationComponent: ApplicationComponent

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
    initDi()
  }

  private fun initDi() {
    applicationComponent = DaggerApplicationComponent
        .builder()
        .applicationModule(ApplicationModule(this))
        .build()
  }

  fun initTimber() {
    if (BuildConfig.DEBUG) {
      Timber.plant(DebugTree())
    }
  }

}