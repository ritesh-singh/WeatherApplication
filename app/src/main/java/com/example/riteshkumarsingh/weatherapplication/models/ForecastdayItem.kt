package com.example.riteshkumarsingh.weatherapplication.models

data class ForecastdayItem(
	val date: String? = null,
	val astro: Astro? = null,
	val date_epoch: Int? = null,
	val hour: List<HourItem?>? = null,
	val day: Day? = null
)
