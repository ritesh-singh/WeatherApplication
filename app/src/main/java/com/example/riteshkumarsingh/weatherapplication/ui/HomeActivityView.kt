package com.example.riteshkumarsingh.weatherapplication.ui

import com.example.riteshkumarsingh.weatherapplication.db.entities.WeatherData

/**
 * Created by riteshksingh on 1/15/18.
 */
interface HomeActivityView {
    fun bindDataWithUi(weatherData: WeatherData?)
    fun showHideLoader(isActive: Boolean)
}