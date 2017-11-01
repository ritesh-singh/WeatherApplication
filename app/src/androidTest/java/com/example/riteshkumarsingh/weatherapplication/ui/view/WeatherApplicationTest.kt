package com.example.riteshkumarsingh.weatherapplication.ui.view

import com.example.riteshkumarsingh.weatherapplication.WeatherApplication
import com.example.riteshkumarsingh.weatherapplication.di.modules.ApiKeyModule
import com.example.riteshkumarsingh.weatherapplication.di.modules.ApplicationModule
import com.example.riteshkumarsingh.weatherapplication.di.modules.CacheModule
import com.example.riteshkumarsingh.weatherapplication.di.modules.InterceptorModule
import com.example.riteshkumarsingh.weatherapplication.ui.view.component.DaggerApplicationTestComponent
import com.example.riteshkumarsingh.weatherapplication.ui.view.module.TestApiModule

/**
 * Created by riteshkumarsingh on 30/10/17.
 */
class WeatherApplicationTest : WeatherApplication() {
  override fun initDi() {
    applicationComponent = DaggerApplicationTestComponent
        .builder()
        .applicationModule(ApplicationModule(this))
        .apiKeyModule(ApiKeyModule())
        .cacheModule(CacheModule())
        .interceptorModule(InterceptorModule())
        .testApiModule(TestApiModule())
        .build()
  }
}