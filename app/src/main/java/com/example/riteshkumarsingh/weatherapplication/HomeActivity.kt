package com.example.riteshkumarsingh.weatherapplication


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import android.widget.Toast
import com.example.riteshkumarsingh.gojek.data.models.WeatherForecast
import com.example.riteshkumarsingh.weatherapplication.db.WeatherDataBase
import com.example.riteshkumarsingh.weatherapplication.db.entities.WeatherData
import com.example.riteshkumarsingh.weatherapplication.service.Injection
import retrofit2.Response
import timber.log.Timber

class HomeActivity : AppCompatActivity() {

    private var mDb: WeatherDataBase? = null

    private lateinit var mTempInC: TextView
    private lateinit var mTempInF: TextView
    private lateinit var mLatitude: TextView
    private lateinit var mLongitude: TextView
    private lateinit var mName: TextView
    private lateinit var mRegion: TextView

    private lateinit var mDbWorkerThread: DbWorkerThread

    private val mUiHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        mDbWorkerThread = DbWorkerThread("dbWorkerThread")
        mDbWorkerThread.start()

        mTempInC = findViewById(R.id.temp_in_c)
        mTempInF = findViewById(R.id.temp_in_f)
        mLatitude = findViewById(R.id.lat)
        mLongitude = findViewById(R.id.lon)
        mName = findViewById(R.id.name)
        mRegion = findViewById(R.id.region)


        mDb = WeatherDataBase.getInstance(this)

        if (isConnectedToInternet()) {
            Injection.provideApiService()
                    .getWeatherData("Bangalore", 4)
                    .enqueue(object : retrofit2.Callback<WeatherForecast> {
                        override fun onFailure(call: retrofit2.Call<WeatherForecast>?,
                                t: Throwable?) {
                            Timber.d(t?.message)
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

                            bindDataWithUi(weatherData)

                            insertWeatherDataInDb(weatherData = weatherData)
                        }

                    })
        } else {
            fetchWeatherDataFromDb()
        }

    }

    private fun bindDataWithUi(weatherData: WeatherData?) {
        mTempInC.text = weatherData?.tempInC.toString()
        mTempInF.text = weatherData?.tempInF.toString()
        mLatitude.text = weatherData?.lat.toString()
        mLongitude.text = weatherData?.lon.toString()
        mName.text = weatherData?.name
        mRegion.text = weatherData?.region
    }

    private fun fetchWeatherDataFromDb() {
        val task = Runnable {
            val weatherData =
                    mDb?.weatherDataDao()?.getAll()
            mUiHandler.post({
                if (weatherData == null || weatherData?.size == 0) {
                    showToast("No data in cache..!!", Toast.LENGTH_SHORT)
                } else {
                    bindDataWithUi(weatherData = weatherData?.get(0))
                }
            })
        }
        mDbWorkerThread.postTask(task)
    }

    private fun insertWeatherDataInDb(weatherData: WeatherData) {
        val task = Runnable { mDb?.weatherDataDao()?.insert(weatherData) }
        mDbWorkerThread.postTask(task)
    }

    override fun onDestroy() {
        WeatherDataBase.destroyInstance()
        mDbWorkerThread.quit()
        super.onDestroy()
    }
}
