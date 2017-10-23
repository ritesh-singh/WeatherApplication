package com.example.riteshkumarsingh.weatherapplication.di.modules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.riteshkumarsingh.weatherapplication.Constants
import com.example.riteshkumarsingh.weatherapplication.R
import com.example.riteshkumarsingh.weatherapplication.network.ApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named
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

  @Provides @Singleton
  fun providesApiService(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
  }

}
