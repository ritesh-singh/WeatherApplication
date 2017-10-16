package com.example.riteshkumarsingh.weatherapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.riteshkumarsingh.gojek.data.models.WeatherForecast
import com.example.riteshkumarsingh.weatherapplication.service.Injection
import retrofit2.Response


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        Injection.provideApiService()
                .getWeatherData("Bangalore", 4)
                .enqueue(object : retrofit2.Callback<WeatherForecast> {
                    override fun onFailure(call: retrofit2.Call<WeatherForecast>?, t: Throwable?) {
                        println(t?.message)
                    }

                    override fun onResponse(call: retrofit2.Call<WeatherForecast>?, response: Response<WeatherForecast>?) {
                        println(response?.body())
                    }

                })
    }
}
