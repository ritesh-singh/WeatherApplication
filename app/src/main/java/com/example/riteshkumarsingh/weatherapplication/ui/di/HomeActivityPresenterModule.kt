package com.example.riteshkumarsingh.weatherapplication.ui.di

import com.example.riteshkumarsingh.weatherapplication.ui.view.HomeActivityView
import dagger.Module
import dagger.Provides

/**
 * Created by riteshkumarsingh on 24/10/17.
 */
@Module
class HomeActivityPresenterModule(private val homeActivityView: HomeActivityView) {

  @Provides @PerActivity
  fun providesHomeActivityView(): HomeActivityView = homeActivityView
}