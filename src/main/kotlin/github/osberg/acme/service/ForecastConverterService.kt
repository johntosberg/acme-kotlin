package github.osberg.acme.service

import github.osberg.acme.model.internal.InternalFiveDayForecast
import github.osberg.acme.model.internal.InternalOneDayForecast
import github.osberg.acme.model.openweather.OpenWeatherApiResponse
import java.time.Instant
import java.time.ZoneOffset
import java.time.ZonedDateTime

open class ForecastConverterService() {

    fun convertToInternal(openWeatherApiResponse: OpenWeatherApiResponse): InternalFiveDayForecast {
        return InternalFiveDayForecast(openWeatherApiResponse.city.name, openWeatherApiResponse.dailyForecastList.map { pointInTimeForecast ->
            InternalOneDayForecast(
                    openWeatherApiResponse.city.name,
                    openWeatherApiResponse.city.timezoneSeconds,
                    buildZonedDateTime(openWeatherApiResponse.city.timezoneSeconds, pointInTimeForecast.utcTime),
                    convertToFahrenheit(pointInTimeForecast.tempInfo.temp),
                    pointInTimeForecast.weatherBody[0].weatherType //just get the first in this list, API appears to only have one element anyway
            )
        })
    }

    fun convertToFahrenheit(kelvinTemp: Double): Int {
        return ((kelvinTemp * (9.0 / 5.0)) - 459.67).toInt()
    }

    fun buildZonedDateTime(timeZoneOffsetSeconds: Long, utcTimeSeconds: Long): ZonedDateTime {
        val zoneOffset: ZoneOffset = ZoneOffset.ofHours(
                (timeZoneOffsetSeconds / 3600L).toInt()
        )
        return Instant.ofEpochSecond(utcTimeSeconds).atZone(zoneOffset)
    }
}
