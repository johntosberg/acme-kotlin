package github.osberg.acme.model.internal

import java.time.ZonedDateTime

data class InternalFiveDayForecast(val city: String,
                                   val forecastListInternal: List<InternalOneDayForecast>)

data class InternalOneDayForecast(val city: String,
                                  val cityTimeZoneOffset: Long,
                                  val localTime: ZonedDateTime,
                                  val temperature: Int,
                                  val weatherType: String)