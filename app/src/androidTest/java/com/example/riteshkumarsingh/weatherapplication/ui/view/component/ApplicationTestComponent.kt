package com.example.riteshkumarsingh.weatherapplication.ui.view.component

import com.example.riteshkumarsingh.weatherapplication.di.components.ApplicationComponent
import com.example.riteshkumarsingh.weatherapplication.di.modules.ApiKeyModule
import com.example.riteshkumarsingh.weatherapplication.di.modules.ApplicationModule
import com.example.riteshkumarsingh.weatherapplication.di.modules.CacheModule
import com.example.riteshkumarsingh.weatherapplication.di.modules.InterceptorModule
import com.example.riteshkumarsingh.weatherapplication.ui.view.activities.HomeActivityTest
import com.example.riteshkumarsingh.weatherapplication.ui.view.module.TestApiModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by riteshkumarsingh on 30/10/17.
 */

@Singleton
@Component(modules = arrayOf(
    ApplicationModule::class, ApiKeyModule::class, CacheModule::class, InterceptorModule::class,
    TestApiModule::class))
interface ApplicationTestComponent : ApplicationComponent {
  fun inject(homeActivityTest: HomeActivityTest)
}