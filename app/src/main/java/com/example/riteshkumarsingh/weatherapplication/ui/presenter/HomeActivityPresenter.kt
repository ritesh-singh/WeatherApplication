package com.example.riteshkumarsingh.weatherapplication.ui.presenter

import com.example.riteshkumarsingh.weatherapplication.Constants
import com.example.riteshkumarsingh.weatherapplication.models.WeatherForecast
import com.example.riteshkumarsingh.weatherapplication.network.ApiService
import com.example.riteshkumarsingh.weatherapplication.ui.di.PerActivity
import com.example.riteshkumarsingh.weatherapplication.ui.view.HomeActivityView
import com.example.riteshkumarsingh.weatherapplication.util.RxUtil
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
    apiService.getWeatherData(apiKey, "Bangalore", 4)
        .compose(RxUtil.applySchedulersToSingleObservable())
        .subscribe({ t: WeatherForecast? ->
          homeActivityView.showWeatherForecast(t)
        }, { t: Throwable? ->
          homeActivityView.showErrorView()
        })

  }
}