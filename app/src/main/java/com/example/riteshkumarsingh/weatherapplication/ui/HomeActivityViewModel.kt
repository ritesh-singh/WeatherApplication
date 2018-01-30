package com.example.riteshkumarsingh.weatherapplication.ui

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.os.Handler
import com.example.riteshkumarsingh.gojek.data.models.WeatherForecast
import com.example.riteshkumarsingh.weatherapplication.DbWorkerThread
import com.example.riteshkumarsingh.weatherapplication.db.WeatherDataBase
import com.example.riteshkumarsingh.weatherapplication.db.entities.WeatherData
import com.example.riteshkumarsingh.weatherapplication.service.Injection
import retrofit2.Response
import timber.log.Timber

/**
 * Created by riteshksingh on 1/15/18.
 */
class HomeActivityViewModel(lifecycleOwner: LifecycleOwner,
        private var weatherDataBase: WeatherDataBase?,
        private var homeActivityView: HomeActivityView) : LifecycleObserver {

    private var mDbWorkerThread: DbWorkerThread

    private val mUiHandler = Handler()

    private var weatherData: WeatherData? = null

    fun getWeatherData(): WeatherData? {
        return this.weatherData
    }

    init {
        lifecycleOwner.lifecycle.addObserver(this)

        mDbWorkerThread = DbWorkerThread("dbWorkerThread")
        mDbWorkerThread.start()

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun onCreate() {
        Timber.d("on Create")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
        Timber.d("on Start")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        Timber.d("on Stop")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onDestroy() {
        mDbWorkerThread.quit()
    }

    fun fetchWeatherData() {
        homeActivityView.showHideLoader(true)
        Injection.provideApiService()
                .getWeatherData("Bangalore", 4)
                .enqueue(object : retrofit2.Callback<WeatherForecast> {
                    override fun onFailure(call: retrofit2.Call<WeatherForecast>?,
                            t: Throwable?) {
                        Timber.d(t?.message)
                        homeActivityView.showHideLoader(false)
                    }

                    override fun onResponse(call: retrofit2.Call<WeatherForecast>?,
                            response: Response<WeatherForecast>?) {
                        Timber.d(response?.body().toString())
                        val weatherForecast = response?.body()

                        var weatherData = WeatherData()
                        weatherData.humidity = weatherForecast?.current?.humidity ?: 0
                        weatherData.tempInC = weatherForecast?.current?.temp_c ?: 0.0
                        weatherData.tempInF = weatherForecast?.current?.temp_f ?: 0.0
                        weatherData.lat = weatherForecast?.location?.lat ?: 0.0
                        weatherData.lon = weatherForecast?.location?.lon ?: 0.0
                        weatherData.name = weatherForecast?.location?.name ?: ""
                        weatherData.region = weatherForecast?.location?.region ?: ""


                        this@HomeActivityViewModel.weatherData = weatherData

                        homeActivityView.showHideLoader(false)
                        homeActivityView.bindDataWithUi(weatherData)

                        insertWeatherDataInDb(weatherData = weatherData)
                    }

                })
    }

    fun fetchWeatherDataFromDb() {
        homeActivityView.showHideLoader(true)
        val task = Runnable {
            val weatherData =
                    weatherDataBase?.weatherDataDao()?.getAll()
            mUiHandler.post({
                homeActivityView.showHideLoader(false)
                if (weatherData == null || weatherData?.size == 0) {
                    Timber.d("No data in cache..!!")
                } else {
                    homeActivityView.bindDataWithUi(weatherData = weatherData?.get(0))
                }
            })
        }
        mDbWorkerThread.postTask(task)
    }

    private fun insertWeatherDataInDb(weatherData: WeatherData) {
        val task = Runnable { weatherDataBase?.weatherDataDao()?.insert(weatherData) }
        mDbWorkerThread.postTask(task)
    }

}