package com.example.riteshkumarsingh.weatherapplication.db.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.example.riteshkumarsingh.weatherapplication.db.entities.WeatherData

/**
 * Created by riteshksingh on 1/2/18.
 */
@Dao
interface WeatherDataDao {

    @Query("SELECT * from weatherData")
    fun getAll(): List<WeatherData>

    @Insert(onConflict = REPLACE)
    fun insert(weatherData: WeatherData)

    @Query("DELETE from weatherData")
    fun deleteAll()
}