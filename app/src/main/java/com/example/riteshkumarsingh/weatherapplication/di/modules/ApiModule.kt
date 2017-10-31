package com.example.riteshkumarsingh.weatherapplication.di.modules

import android.content.Context
import com.example.riteshkumarsingh.weatherapplication.Constants
import com.example.riteshkumarsingh.weatherapplication.R
import com.example.riteshkumarsingh.weatherapplication.network.ApiService
import com.example.riteshkumarsingh.weatherapplication.network.EndPoint
import com.example.riteshkumarsingh.weatherapplication.network.WeatherApiEndpoint
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.annotations.Nullable
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by riteshkumarsingh on 21/10/17.
 */

@Module
open class ApiModule {

  @Provides
  @Singleton
  @Named(Constants.weatherApiKey)
  fun providesWeatherApiKey(context: Context): String = context.getString(R.string.api_key)

  @Provides @Named(Constants.unMockedEndPoint)
  @Singleton
  fun providesEndPoint(): EndPoint =
      WeatherApiEndpoint()

  @Provides
  @Singleton
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


  @Provides
  @Singleton
  open fun provideRetrofit(httpClient: OkHttpClient, @Named(
      Constants.unMockedEndPoint) endPoint: EndPoint): Retrofit {
    val retrofit = Retrofit.Builder()
        .baseUrl(endPoint.getUrl())
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build()
    return retrofit
  }

  @Provides
  @Singleton
  fun providesApiService(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
  }

}