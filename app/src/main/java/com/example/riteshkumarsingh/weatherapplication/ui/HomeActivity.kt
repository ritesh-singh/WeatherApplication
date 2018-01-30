package com.example.riteshkumarsingh.weatherapplication.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.example.riteshkumarsingh.weatherapplication.R
import com.example.riteshkumarsingh.weatherapplication.R.id
import com.example.riteshkumarsingh.weatherapplication.R.layout
import com.example.riteshkumarsingh.weatherapplication.db.WeatherDataBase
import com.example.riteshkumarsingh.weatherapplication.db.entities.WeatherData
import com.example.riteshkumarsingh.weatherapplication.isConnectedToInternet

class HomeActivity : AppCompatActivity(), HomeActivityView {

    private var mDb: WeatherDataBase? = null

    private lateinit var mTempInC: TextView
    private lateinit var mTempInF: TextView
    private lateinit var mLatitude: TextView
    private lateinit var mLongitude: TextView
    private lateinit var mName: TextView
    private lateinit var mRegion: TextView
    private lateinit var mPbLoader: ProgressBar

    private lateinit var homeActivityViewModel: HomeActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_home)

        mDb = WeatherDataBase.getInstance(this)



        homeActivityViewModel = HomeActivityViewModel(this, mDb, this)


        initUI()

        if (isConnectedToInternet()) {
            homeActivityViewModel.fetchWeatherData()
        } else {
            homeActivityViewModel.fetchWeatherDataFromDb()
        }

    }

    private fun initUI() {
        mTempInC = findViewById(id.temp_in_c)
        mTempInF = findViewById(id.temp_in_f)
        mLatitude = findViewById(id.lat)
        mLongitude = findViewById(id.lon)
        mName = findViewById(id.name)
        mRegion = findViewById(id.region)
        mPbLoader = findViewById(R.id.pb_loader)
    }

    override fun bindDataWithUi(weatherData: WeatherData?) {
        mTempInC.text = weatherData?.tempInC.toString()
        mTempInF.text = weatherData?.tempInF.toString()
        mLatitude.text = weatherData?.lat.toString()
        mLongitude.text = weatherData?.lon.toString()
        mName.text = weatherData?.name
        mRegion.text = weatherData?.region
    }

    override fun showHideLoader(isActive: Boolean) {
        if (isActive) {
            mPbLoader.visibility = View.VISIBLE
            return
        }
        mPbLoader.visibility = View.INVISIBLE
    }

    override fun onDestroy() {
        WeatherDataBase.destroyInstance()
        super.onDestroy()
    }
}
