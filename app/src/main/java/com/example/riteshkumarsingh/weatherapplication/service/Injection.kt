package com.example.riteshkumarsingh.weatherapplication.service

import android.support.v4.BuildConfig
import com.example.riteshkumarsingh.weatherapplication.Constants
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by riteshkumarsingh on 13/10/17.
 */
class Injection {
    companion object {
        fun getOkHttpClient(): OkHttpClient {
            val logger: HttpLoggingInterceptor =
                    HttpLoggingInterceptor()

            if (BuildConfig.DEBUG)
                logger.level = HttpLoggingInterceptor.Level.BODY
            else
                logger.level = HttpLoggingInterceptor.Level.NONE

            val okHttpClient: OkHttpClient =
                    OkHttpClient.Builder()
                            .addInterceptor(logger)
                            .addNetworkInterceptor(StethoInterceptor())
                            .build()

            return okHttpClient
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