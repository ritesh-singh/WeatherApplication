package com.example.riteshkumarsingh.weatherapplication.network


import com.example.riteshkumarsingh.weatherapplication.models.WeatherForecast
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by riteshkumarsingh on 13/10/17.
 */
interface ApiService {
  @GET("forecast.json")
  fun getWeatherData(
      @Query("key") key:String,
      @Query("q") city: String,
      @Query("days") totalCount: Int): WeatherForecast
}