package com.example.riteshkumarsingh.weatherapplication.di.modules

import com.example.riteshkumarsingh.weatherapplication.BuildConfig
import com.example.riteshkumarsingh.weatherapplication.Constants
import com.example.riteshkumarsingh.weatherapplication.WeatherApplication
import dagger.Module
import dagger.Provides
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by riteshkumarsingh on 31/10/17.
 */
@Module
class InterceptorModule {
  @Provides
  @Singleton
  @Named(Constants.cacheInterceptor)
  fun providesCacheInterceptor(): Interceptor {
    return Interceptor { chain ->
      val response = chain.proceed(chain.request())

      // re-write response header to force use of cache
      val cacheControl = CacheControl.Builder()
          .maxAge(2, TimeUnit.MINUTES)
          .build()

      response.newBuilder()
          .header(Constants.CACHE_CONTROL, cacheControl.toString())
          .build()
    }
  }

  @Provides
  @Singleton
  @Named(Constants.urlAndHeaderInterceptor)
  fun provideUrlAndHeaderInterceptor(): Interceptor {
    return Interceptor { chain ->
      val request = chain.request()
      val builder = request.newBuilder()
          .addHeader(Constants.USER_AGENT, Constants.ADEPT_ANDROID_APP)

      val url = request.url()
          .newBuilder()
          .addQueryParameter(Constants.VERSION, BuildConfig.VERSION_NAME)
          .build()

      builder.url(url)

      chain.proceed(builder.build())
    }
  }


  @Provides
  @Singleton
  @Named(Constants.offlineCacheInterceptor)
  fun provideOfflineCacheInterceptor(): Interceptor {
    return Interceptor { chain ->
      var request = chain.request()
      if (!WeatherApplication.checkIfHasNetwork()) { // If no network
        val cacheControl = CacheControl.Builder()
            .maxStale(7, TimeUnit.DAYS) // Stale for 7 days, with the expired cache
            .build()

        request = request.newBuilder()
            .cacheControl(cacheControl)
            .build()
      }

      chain.proceed(request);
    }
  }

  @Provides
  @Singleton
  fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
    val httpLoggingInterceptor = HttpLoggingInterceptor(
        HttpLoggingInterceptor.Logger { message -> Timber.d(message) })
    httpLoggingInterceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.HEADERS else HttpLoggingInterceptor.Level.NONE
    return httpLoggingInterceptor
  }
}