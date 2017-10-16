package com.example.riteshkumarsingh.gojek.data.models

data class Day(
	val avgvis_km: Double? = null,
	val uv: Double? = null,
	val avgtemp_f: Double? = null,
	val avgtemp_c: Double? = null,
	val maxtemp_c: Double? = null,
	val maxtemp_f: Double? = null,
	val mintemp_c: Double? = null,
	val avgvis_miles: Double? = null,
	val mintemp_f: Double? = null,
	val totalprecip_in: Double? = null,
	val avghumidity: Double? = null,
	val condition: Condition? = null,
	val maxwind_kph: Double? = null,
	val maxwind_mph: Double? = null,
	val totalprecip_mm: Double? = null
)
