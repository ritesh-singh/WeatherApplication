package com.example.riteshkumarsingh.weatherapplication.ui.view

import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.riteshkumarsingh.weatherapplication.R
import com.example.riteshkumarsingh.weatherapplication.WeatherApplication
import com.example.riteshkumarsingh.weatherapplication.models.WeatherForecast
import com.example.riteshkumarsingh.weatherapplication.toast
import com.example.riteshkumarsingh.weatherapplication.ui.di.DaggerHomeActivityComponent
import com.example.riteshkumarsingh.weatherapplication.ui.di.HomeActivityComponent
import com.example.riteshkumarsingh.weatherapplication.ui.di.HomeActivityPresenterModule
import com.example.riteshkumarsingh.weatherapplication.ui.presenter.HomeActivityPresenter
import timber.log.Timber
import javax.inject.Inject


class HomeActivity : AppCompatActivity(), HomeActivityView {


  var homeActivityComponent: HomeActivityComponent? = null

  @Inject lateinit var sharedPreferences: SharedPreferences

  @Inject lateinit var presenter: HomeActivityPresenter

  private fun initDi() {
    homeActivityComponent = DaggerHomeActivityComponent.builder()
        .applicationComponent((application as WeatherApplication).applicationComponent)
        .homeActivityPresenterModule(HomeActivityPresenterModule(this))
        .build()

    homeActivityComponent?.inject(this)
  }

  override fun showWeatherForecast(weatherForecast: WeatherForecast?) {
    this.toast("Success")
  }

  override fun showErrorView() {
    this.toast("error view called")
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_home)

    initDi()

    presenter.fetchWeatherData()

    if (sharedPreferences != null) {
      Timber.d("Not null man")
    }

  }

  override fun onDestroy() {
    super.onDestroy()
    homeActivityComponent = null
  }

}
