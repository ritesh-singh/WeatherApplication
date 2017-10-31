package com.example.riteshkumarsingh.weatherapplication.di.modules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by riteshkumarsingh on 21/10/17.
 */

@Module class ApplicationModule(private val application: Application) {

  @Provides @Singleton fun providesContext(): Context {
    return application
  }

  @Provides @Singleton fun providesSharedPref(context: Context): SharedPreferences {
    return PreferenceManager.getDefaultSharedPreferences(context)
  }

}
