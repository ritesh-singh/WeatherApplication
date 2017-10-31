package com.example.riteshkumarsingh.weatherapplication.network

/**
 * Created by riteshkumarsingh on 21/10/17.
 */
class WeatherApiEndpoint : EndPoint {

  private var BASE_URL = "http://api.apixu.com/v1/"

  override fun getUrl(): String {
    return BASE_URL
  }
}