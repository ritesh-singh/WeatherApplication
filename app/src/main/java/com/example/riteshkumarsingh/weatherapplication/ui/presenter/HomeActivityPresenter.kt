package com.example.riteshkumarsingh.weatherapplication.ui.presenter

import com.example.riteshkumarsingh.weatherapplication.Constants
import com.example.riteshkumarsingh.weatherapplication.models.WeatherForecast
import com.example.riteshkumarsingh.weatherapplication.network.ApiService
import com.example.riteshkumarsingh.weatherapplication.ui.di.PerActivity
import com.example.riteshkumarsingh.weatherapplication.ui.view.HomeActivityView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named


/**
 * Created by riteshkumarsingh on 17/10/17.
 */
@PerActivity
class HomeActivityPresenter @Inject constructor(private val homeActivityView: HomeActivityView,
    private val apiService: ApiService,
    @Named(Constants.weatherApiKey) private val apiKey: String) {


  fun fetchWeatherData() {
    apiService
        .getWeatherData(apiKey, "Bangalore", 4)
        .enqueue(object : Callback<WeatherForecast> {
          override fun onFailure(call: Call<WeatherForecast>?, t: Throwable?) {
            Timber.i(t)
          }

          override fun onResponse(call: Call<WeatherForecast>?,
              response: Response<WeatherForecast>) {
            if (response.isSuccessful) {
              homeActivityView.showWeatherForecast(response.body())
            } else {
              homeActivityView.showErrorView()
            }
          }

        })
  }
}