package com.example.riteshkumarsingh.gojek.data.models

data class Current(
	val feelslike_c: Double? = null,
	val last_updated: String? = null,
	val feelslike_f: Double? = null,
	val wind_degree: Int? = null,
	val last_updated_epoch: Int? = null,
	val is_day: Int? = null,
	val precip_in: Double? = null,
	val wind_dir: String? = null,
	val temp_c: Double? = null,
	val pressure_in: Double? = null,
	val temp_f: Double? = null,
	val precip_mm: Double? = null,
	val cloud: Int? = null,
	val wind_kph: Double? = null,
	val condition: Condition? = null,
	val wind_mph: Double? = null,
	val vis_km: Double? = null,
	val humidity: Int? = null,
	val pressure_mb: Double? = null,
	val vis_miles: Double? = null
)
