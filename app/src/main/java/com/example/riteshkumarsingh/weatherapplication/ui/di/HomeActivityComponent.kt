package com.example.riteshkumarsingh.weatherapplication.ui.di

import com.example.riteshkumarsingh.weatherapplication.di.components.ApplicationComponent
import com.example.riteshkumarsingh.weatherapplication.ui.view.HomeActivity
import dagger.Component

/**
 * Created by riteshkumarsingh on 24/10/17.
 */
@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(HomeActivityPresenterModule::class))
interface HomeActivityComponent {
  fun inject(homeActivity: HomeActivity)
}