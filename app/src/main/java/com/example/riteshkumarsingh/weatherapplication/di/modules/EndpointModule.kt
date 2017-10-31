package com.example.riteshkumarsingh.weatherapplication.di.modules

import android.content.Context
import com.example.riteshkumarsingh.weatherapplication.Constants
import com.example.riteshkumarsingh.weatherapplication.R
import com.example.riteshkumarsingh.weatherapplication.network.EndPoint
import com.example.riteshkumarsingh.weatherapplication.network.WeatherApiEndpoint
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by riteshkumarsingh on 31/10/17.
 */
@Module
class EndpointModule {

  @Provides
  @Singleton
  @Named(Constants.weatherApiKey)
  fun providesWeatherApiKey(context: Context): String = context.getString(R.string.api_key)

  @Provides
  @Singleton
  fun providesEndPoint(): EndPoint =
      WeatherApiEndpoint()
}