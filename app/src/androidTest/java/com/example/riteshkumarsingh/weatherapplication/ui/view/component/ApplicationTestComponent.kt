package com.example.riteshkumarsingh.weatherapplication.ui.view.component

import com.example.riteshkumarsingh.weatherapplication.di.components.ApplicationComponent
import com.example.riteshkumarsingh.weatherapplication.di.modules.ApiModule
import com.example.riteshkumarsingh.weatherapplication.di.modules.ApplicationModule
import com.example.riteshkumarsingh.weatherapplication.di.modules.CacheModule
import com.example.riteshkumarsingh.weatherapplication.di.modules.InterceptorModule
import com.example.riteshkumarsingh.weatherapplication.ui.view.activities.HomeActivityTest
import com.example.riteshkumarsingh.weatherapplication.ui.view.module.MockedModule
import com.example.riteshkumarsingh.weatherapplication.ui.view.module.TestEndPointModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by riteshkumarsingh on 30/10/17.
 */
@Singleton
@Component(
    modules = arrayOf(ApplicationModule::class, MockedModule::class, TestEndPointModule::class,
        CacheModule::class,
        InterceptorModule::class, ApiModule::class))
interface ApplicationTestComponent : ApplicationComponent {
  fun inject(homeActivityTest: HomeActivityTest)
}