package com.example.riteshkumarsingh.weatherapplication.di.modules

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
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by riteshkumarsingh on 21/10/17.
 */

@Module
class ApiModule {

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
  @Singleton
  fun provideRetrofit(httpClient: OkHttpClient): Retrofit {
    return Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(httpClient)
        .build()
  }

  @Provides
  @Singleton
  fun providesApiService(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
  }

}
