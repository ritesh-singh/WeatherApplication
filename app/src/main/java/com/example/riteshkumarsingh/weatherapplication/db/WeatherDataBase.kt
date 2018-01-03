package com.example.riteshkumarsingh.weatherapplication.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.riteshkumarsingh.weatherapplication.db.daos.WeatherDataDao
import com.example.riteshkumarsingh.weatherapplication.db.entities.WeatherData
import android.arch.persistence.room.Room
import android.content.Context

/**
 * Created by riteshksingh on 1/3/18.
 */

@Database(entities = arrayOf(WeatherData::class), version = 1)
abstract class WeatherDataBase : RoomDatabase() {

    abstract fun weatherDataDao(): WeatherDataDao

    companion object {
        private var INSTANCE: WeatherDataBase? = null

        fun getInstance(context: Context): WeatherDataBase? {
            if (INSTANCE == null) {
                synchronized(WeatherDataBase::class) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WeatherDataBase::class.java, "weather.db")
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}