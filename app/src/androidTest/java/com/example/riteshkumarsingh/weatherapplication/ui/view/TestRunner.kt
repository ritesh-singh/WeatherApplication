package com.example.riteshkumarsingh.weatherapplication.ui.view

import android.app.Application
import android.content.Context
import android.support.test.runner.AndroidJUnitRunner

/**
 * Created by riteshkumarsingh on 30/10/17.
 */
class TestRunner : AndroidJUnitRunner() {
  override fun newApplication(cl: ClassLoader?, className: String?,
      context: Context?): Application {
    return super.newApplication(cl, WeatherApplicationTest::class.java.name, context)
  }
}