package com.example.riteshkumarsingh.weatherapplication.service

import com.example.riteshkumarsingh.weatherapplication.BuildConfig
import com.example.riteshkumarsingh.weatherapplication.Constants
import com.example.riteshkumarsingh.weatherapplication.WeatherApplication
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.File
import java.lang.Exception
import okhttp3.CacheControl
import java.util.concurrent.TimeUnit


/**
 * Created by riteshkumarsingh on 13/10/17.
 */
class Injection {
  companion object {

    private val USER_AGENT = "User-Agent"
    private val ADEPT_ANDROID_APP = "Adept-Android-App"
    private val VERSION = "version"
    private val CACHE_CONTROL = "Cache-Control"


    fun getOkHttpClient(): OkHttpClient {
      val okHttpClient: OkHttpClient =
          OkHttpClient.Builder()
              .addInterceptor(provideHttpLoggingInterceptor())
              .addInterceptor(provideUrlAndHeaderInterceptor())
              .addInterceptor(provideOfflineCacheInterceptor())
              .cache(provideCache())
              .addNetworkInterceptor(provideCacheInterceptor())
              .build()

      return okHttpClient
    }


    private fun provideCache(): Cache? {
      var cache: Cache? = null
      try {
        cache = Cache(File(WeatherApplication.instance.cacheDir, "http-cache"),
            1024 * 1024 * 10)
      } catch (e: Exception) {
        Timber.e(e, "couldn't  create cache")
      }

      return cache
    }


    private fun provideCacheInterceptor(): Interceptor {
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

    private fun provideUrlAndHeaderInterceptor(): Interceptor {
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

    private fun provideOfflineCacheInterceptor(): Interceptor {
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

    private fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
      val httpLoggingInterceptor = HttpLoggingInterceptor(
          HttpLoggingInterceptor.Logger { message -> Timber.d(message) })
      httpLoggingInterceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.HEADERS else HttpLoggingInterceptor.Level.NONE
      return httpLoggingInterceptor
    }

    fun provideRetrofit(): Retrofit {
      val httpClient = getOkHttpClient()
      val retrofit = Retrofit.Builder()
          .baseUrl(Constants.BASE_URL)
          .addConverterFactory(GsonConverterFactory.create())
          .client(httpClient)
          .build()

      return retrofit
    }

    fun provideApiService(): ApiService {
      return provideRetrofit()
          .create(ApiService::class.java)
    }
  }
}