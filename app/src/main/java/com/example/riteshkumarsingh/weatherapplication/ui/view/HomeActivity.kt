package com.example.riteshkumarsingh.weatherapplication.ui.view

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.example.riteshkumarsingh.weatherapplication.R
import com.example.riteshkumarsingh.weatherapplication.WeatherApplication
import com.example.riteshkumarsingh.weatherapplication.models.WeatherForecast
import com.example.riteshkumarsingh.weatherapplication.ui.di.DaggerHomeActivityComponent
import com.example.riteshkumarsingh.weatherapplication.ui.di.HomeActivityComponent
import com.example.riteshkumarsingh.weatherapplication.ui.di.HomeActivityPresenterModule
import com.example.riteshkumarsingh.weatherapplication.ui.presenter.HomeActivityPresenter
import timber.log.Timber
import javax.inject.Inject


class HomeActivity : AppCompatActivity(), HomeActivityView {


  private var homeActivityComponent: HomeActivityComponent? = null

  private lateinit var textView: TextView

  @Inject lateinit var sharedPreferences: SharedPreferences

  @Inject lateinit var presenter: HomeActivityPresenter



  private fun initDi() {
    homeActivityComponent = DaggerHomeActivityComponent.builder()
        .applicationComponent(WeatherApplication.applicationComponent)
        .homeActivityPresenterModule(HomeActivityPresenterModule(this))
        .build()

    homeActivityComponent?.inject(this)
  }

  override fun showWeatherForecast(weatherForecast: WeatherForecast?) {
    textView.text = weatherForecast?.current?.temp_c?.toString()
  }

  override fun showErrorView() {
    textView.text = getString(R.string.error_msg)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_home)

    initDi()

    textView = findViewById(R.id.tv_sample_text)

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
