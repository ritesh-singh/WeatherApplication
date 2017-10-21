package com.example.riteshkumarsingh.weatherapplication.ui.presenter

import com.example.riteshkumarsingh.weatherapplication.models.WeatherForecast
import com.example.riteshkumarsingh.weatherapplication.service.ApiService
import com.example.riteshkumarsingh.weatherapplication.service.Injection
import com.example.riteshkumarsingh.weatherapplication.ui.view.HomeActivityView
import retrofit2.Response
import timber.log.Timber

/**
 * Created by riteshkumarsingh on 17/10/17.
 */
class HomeActivityPresenter(private val homeActivityView: HomeActivityView,
    private val apiService: ApiService) {
  fun fetchWeatherData() {
    apiService
        .getWeatherData("Bangalore", 4)
        .enqueue(object : retrofit2.Callback<WeatherForecast> {
          override fun onFailure(call: retrofit2.Call<WeatherForecast>?, t: Throwable) {
            homeActivityView.showErrorView()
            Timber.e(t)
          }

          override fun onResponse(call: retrofit2.Call<WeatherForecast>?,
              response: Response<WeatherForecast>) {
            if (response.isSuccessful) {
              homeActivityView.showWeatherForecast(response.body())
              Timber.i(response.body().toString())
            }

          }

        })
  }
}