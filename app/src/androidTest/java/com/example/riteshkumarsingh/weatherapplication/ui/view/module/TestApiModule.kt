package com.example.riteshkumarsingh.weatherapplication.ui.view.module

import com.example.riteshkumarsingh.weatherapplication.Constants
import com.example.riteshkumarsingh.weatherapplication.network.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.annotations.Nullable
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by riteshkumarsingh on 01/11/17.
 */
@Module
class TestApiModule {

  @Provides
  @Singleton
  fun providesOkHttpClient(@Nullable cache: Cache,
      httpLoggingInterceptor: HttpLoggingInterceptor,
      @Named(Constants.cacheInterceptor) cacheInterceptor: Interceptor,
      @Named(Constants.urlAndHeaderInterceptor) urlAndHeaderInterceptor: Interceptor,
      @Named(
          Constants.offlineCacheInterceptor) offlineCacheInterceptor: Interceptor): OkHttpClient {

    return OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(urlAndHeaderInterceptor)
        .addInterceptor(offlineCacheInterceptor)
        .cache(cache)
        .addNetworkInterceptor(cacheInterceptor)
        .build()
  }


  @Provides
  fun provideRetrofit(httpClient: OkHttpClient): Retrofit {
    return Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build()
  }

  @Provides
  fun providesApiService(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
  }
}