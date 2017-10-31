package com.example.riteshkumarsingh.weatherapplication.ui.view.module

import com.example.riteshkumarsingh.weatherapplication.Constants
import com.example.riteshkumarsingh.weatherapplication.di.modules.ApiModule
import com.example.riteshkumarsingh.weatherapplication.network.EndPoint
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Named

/**
 * Created by riteshkumarsingh on 30/10/17.
 */

class TestApiModule : ApiModule() {

  override fun provideRetrofit(httpClient: OkHttpClient, @Named(Constants.mockedEndPoint) endPoint: EndPoint): Retrofit {
    return super.provideRetrofit(httpClient, endPoint)
  }
}