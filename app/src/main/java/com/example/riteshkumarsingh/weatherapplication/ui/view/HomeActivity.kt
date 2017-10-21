package com.example.riteshkumarsingh.weatherapplication.ui.view

import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.riteshkumarsingh.weatherapplication.R
import com.example.riteshkumarsingh.weatherapplication.WeatherApplication
import com.example.riteshkumarsingh.weatherapplication.models.WeatherForecast
import com.example.riteshkumarsingh.weatherapplication.service.Injection
import com.example.riteshkumarsingh.weatherapplication.ui.presenter.HomeActivityPresenter
import timber.log.Timber
import javax.inject.Inject


class HomeActivity : AppCompatActivity(), HomeActivityView {

  @Inject
  lateinit var sharedPreferences: SharedPreferences

  override fun showWeatherForecast(weatherForecast: WeatherForecast?) {
  }

  override fun showErrorView() {
  }

  lateinit var presenter: HomeActivityPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_home)

    initDi()

    presenter = HomeActivityPresenter(this, Injection
        .provideApiService())

    presenter.fetchWeatherData()

    if (sharedPreferences != null){
      Timber.d("Not null man")
    }

  }

  private fun initDi() {
    (application as WeatherApplication).applicationComponent
        .inject(this)
  }
}
