package com.example.riteshkumarsingh.weatherapplication.di.modules

import android.content.Context
import com.example.riteshkumarsingh.weatherapplication.BuildConfig
import com.example.riteshkumarsingh.weatherapplication.Constants
import com.example.riteshkumarsingh.weatherapplication.R
import com.example.riteshkumarsingh.weatherapplication.WeatherApplication
import com.example.riteshkumarsingh.weatherapplication.network.EndPoint
import com.example.riteshkumarsingh.weatherapplication.network.WeatherApiEndpoint
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.annotations.Nullable
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.File
import java.lang.Exception
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by riteshkumarsingh on 21/10/17.
 */

@Module
class ApiModule {

  private val USER_AGENT = "User-Agent"
  private val ADEPT_ANDROID_APP = "Adept-Android-App"
  private val VERSION = "version"
  private val CACHE_CONTROL = "Cache-Control"


  @Provides @Singleton
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

  @Provides @Singleton
  @Named(Constants.cacheInterceptor)
  fun providesCacheInterceptor(): Interceptor {
    return Interceptor { chain ->
      val response = chain.proceed(chain.request())

      // re-write response header to force use of cache
      val cacheControl = CacheControl.Builder()
          .maxAge(2, TimeUnit.MINUTES)
          .build()

      response.newBuilder()
          .header(CACHE_CONTROL, cacheControl.toString())
          .build()
    }
  }

  @Provides @Singleton
  @Named(Constants.urlAndHeaderInterceptor)
  fun provideUrlAndHeaderInterceptor(): Interceptor {
    return Interceptor { chain ->
      val request = chain.request()
      val builder = request.newBuilder()
          .addHeader(USER_AGENT, ADEPT_ANDROID_APP)

      val url = request.url()
          .newBuilder()
          .addQueryParameter(VERSION, BuildConfig.VERSION_NAME)
          .build()

      builder.url(url)

      chain.proceed(builder.build())
    }
  }


  @Provides @Singleton
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

  @Provides @Singleton
  fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
    val httpLoggingInterceptor = HttpLoggingInterceptor(
        HttpLoggingInterceptor.Logger { message -> Timber.d(message) })
    httpLoggingInterceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.HEADERS else HttpLoggingInterceptor.Level.NONE
    return httpLoggingInterceptor
  }

  @Provides @Singleton
  fun providesOkHttpClient(@Nullable cache: Cache,
      httpLoggingInterceptor: HttpLoggingInterceptor,
      @Named(Constants.cacheInterceptor) cacheInterceptor: Interceptor,
      @Named(Constants.urlAndHeaderInterceptor) urlAndHeaderInterceptor: Interceptor,
      @Named(
          Constants.offlineCacheInterceptor) offlineCacheInterceptor: Interceptor): OkHttpClient {
    val okHttpClient: OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(urlAndHeaderInterceptor)
            .addInterceptor(offlineCacheInterceptor)
            .cache(cache)
            .addNetworkInterceptor(cacheInterceptor)
            .build()

    return okHttpClient
  }

  @Provides @Singleton
  @Named(Constants.weatherApiKey)
  fun providesWeatherApiKey(context: Context): String = context.getString(R.string.api_key)

  @Provides @Singleton
  fun providesEndPoint(@Named(Constants.weatherApiKey) apiKey: String): EndPoint =
      WeatherApiEndpoint()

  @Provides @Singleton
  fun provideRetrofit(httpClient: OkHttpClient, endPoint: EndPoint): Retrofit {
    val retrofit = Retrofit.Builder()
        .baseUrl(endPoint.getUrl())
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build()
    return retrofit
  }

}
