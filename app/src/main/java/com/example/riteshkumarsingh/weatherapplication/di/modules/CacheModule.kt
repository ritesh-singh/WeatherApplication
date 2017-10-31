package com.example.riteshkumarsingh.weatherapplication.di.modules

import com.example.riteshkumarsingh.weatherapplication.WeatherApplication
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import timber.log.Timber
import java.io.File
import java.lang.Exception
import javax.inject.Singleton

/**
 * Created by riteshkumarsingh on 31/10/17.
 */
@Module
class CacheModule {
  @Provides
  @Singleton
  fun provideCache(): Cache? {
    var cache: Cache? = null
    try {
      cache = Cache(File(WeatherApplication.instance.cacheDir, "http-cache"),
          1024 * 1024 * 10)
    } catch (e: Exception) {
      Timber.e(e, "couldn't  create cache")
    }

    return cache
  }
}