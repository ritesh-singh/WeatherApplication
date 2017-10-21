package com.example.riteshkumarsingh.weatherapplication.ui.view

import com.example.riteshkumarsingh.weatherapplication.models.WeatherForecast


/**
 * Created by riteshkumarsingh on 17/10/17.
 */
interface HomeActivityView {
    fun showWeatherForecast(weatherForecast: WeatherForecast?)
    fun showErrorView()
}