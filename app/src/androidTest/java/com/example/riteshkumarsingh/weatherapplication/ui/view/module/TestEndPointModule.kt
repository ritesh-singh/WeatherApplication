package com.example.riteshkumarsingh.weatherapplication.ui.view.module

import android.content.Context
import com.example.riteshkumarsingh.weatherapplication.Constants
import com.example.riteshkumarsingh.weatherapplication.R
import com.example.riteshkumarsingh.weatherapplication.network.EndPoint
import dagger.Module
import dagger.Provides
import okhttp3.mockwebserver.MockWebServer
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by riteshkumarsingh on 31/10/17.
 */
@Module
class TestEndPointModule {
  @Provides
  @Singleton
  @Named(Constants.weatherApiKey)
  fun providesWeatherApiKey(context: Context): String = context.getString(R.string.api_key)

  @Provides
  @Singleton
  fun providesEndPoint(mockWebServer: MockWebServer): EndPoint =
      object : EndPoint {
        override fun getUrl(): String {
          return mockWebServer.url("/").toString()
        }

      }
}