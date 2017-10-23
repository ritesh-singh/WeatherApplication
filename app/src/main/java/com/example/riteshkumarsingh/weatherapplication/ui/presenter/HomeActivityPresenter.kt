package com.example.riteshkumarsingh.weatherapplication.ui.presenter

import android.os.AsyncTask
import com.example.riteshkumarsingh.weatherapplication.models.WeatherForecast
import com.example.riteshkumarsingh.weatherapplication.network.ApiService
import com.example.riteshkumarsingh.weatherapplication.ui.view.HomeActivityView
import timber.log.Timber


/**
 * Created by riteshkumarsingh on 17/10/17.
 */
class HomeActivityPresenter(private val homeActivityView: HomeActivityView,
    private val apiService: ApiService,
    private val apiKey: String) {
  fun fetchWeatherData() {

    object : AsyncTask<Void, Void, WeatherForecast>() {

      var e: Exception? = null

      override fun doInBackground(vararg p0: Void?): WeatherForecast? {
        try {
          return apiService.getWeatherData(apiKey, "Bangalore", 4)
        } catch (e: Exception) {
          this.e = e
          return null
        }
      }

      override fun onPostExecute(result: WeatherForecast?) {
        if (result == null) {
          homeActivityView.showErrorView()
          Timber.e(e)
          return
        }

        homeActivityView.showWeatherForecast(result)
        Timber.i(result.toString())

      }

    }
  }
}