package com.example.riteshkumarsingh.weatherapplication.ui.view

import com.example.riteshkumarsingh.weatherapplication.WeatherApplication
import com.example.riteshkumarsingh.weatherapplication.di.modules.ApiModule
import com.example.riteshkumarsingh.weatherapplication.di.modules.ApplicationModule
import com.example.riteshkumarsingh.weatherapplication.di.modules.CacheModule
import com.example.riteshkumarsingh.weatherapplication.di.modules.InterceptorModule
import com.example.riteshkumarsingh.weatherapplication.ui.view.component.DaggerApplicationTestComponent
import com.example.riteshkumarsingh.weatherapplication.ui.view.module.MockedModule
import com.example.riteshkumarsingh.weatherapplication.ui.view.module.TestEndPointModule

/**
 * Created by riteshkumarsingh on 30/10/17.
 */
class WeatherApplicationTest : WeatherApplication() {
  override fun initDi() {
    applicationComponent = DaggerApplicationTestComponent
        .builder()
        .applicationModule(ApplicationModule(this))
        .mockedModule(MockedModule())
        .testEndPointModule(TestEndPointModule())
        .cacheModule(CacheModule())
        .interceptorModule(InterceptorModule())
        .apiModule(ApiModule())
        .build()
  }
}