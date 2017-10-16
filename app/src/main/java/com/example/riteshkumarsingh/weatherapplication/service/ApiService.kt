package com.example.riteshkumarsingh.weatherapplication.service

import com.example.riteshkumarsingh.gojek.data.models.WeatherForecast
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by riteshkumarsingh on 13/10/17.
 */
interface ApiService {
    @GET("/v1/forecast.json?key=f308568837cd42d793d123416170610")
    fun getWeatherData(@Query("q") city: String, @Query("days") totalCount: Int): Call<WeatherForecast>
}