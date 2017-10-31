package com.example.riteshkumarsingh.weatherapplication

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.example.riteshkumarsingh.weatherapplication.di.components.ApplicationComponent
import com.example.riteshkumarsingh.weatherapplication.di.components.DaggerApplicationComponent
import com.example.riteshkumarsingh.weatherapplication.di.modules.ApiModule
import com.example.riteshkumarsingh.weatherapplication.di.modules.ApplicationModule
import com.example.riteshkumarsingh.weatherapplication.di.modules.CacheModule
import com.example.riteshkumarsingh.weatherapplication.di.modules.InterceptorModule
import timber.log.Timber
import timber.log.Timber.DebugTree


/**
 * Created by riteshkumarsingh on 16/10/17.
 */
open class WeatherApplication : Application() {

  companion object {
    lateinit var applicationComponent: ApplicationComponent

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

  open fun initDi() {
    applicationComponent = DaggerApplicationComponent
        .builder()
        .applicationModule(ApplicationModule(this))
        .cacheModule(CacheModule())
        .interceptorModule(InterceptorModule())
        .apiModule(ApiModule())
        .build()
  }

  fun initTimber() {
    if (BuildConfig.DEBUG) {
      Timber.plant(DebugTree())
    }
  }

}