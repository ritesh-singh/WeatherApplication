package com.example.riteshkumarsingh.weatherapplication.ui.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.riteshkumarsingh.gojek.data.models.WeatherForecast
import com.example.riteshkumarsingh.weatherapplication.R
import com.example.riteshkumarsingh.weatherapplication.service.Injection
import com.example.riteshkumarsingh.weatherapplication.ui.presenter.HomeActivityPresenter


class HomeActivity : AppCompatActivity(), HomeActivityView {

  override fun showWeatherForecast(weatherForecast: WeatherForecast?) {
  }

  override fun showErrorView() {
  }

  lateinit var presenter: HomeActivityPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_home)

    presenter = HomeActivityPresenter(this,Injection
        .provideApiService())

    presenter.fetchWeatherData()

  }
}
