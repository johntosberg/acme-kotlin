package github.osberg.acme.service

import github.osberg.acme.model.internal.FivePlusDayForecast
import github.osberg.acme.model.internal.OneDateTimeForecast
import github.osberg.acme.model.openweather.ForecastData
import java.time.Instant
import java.time.ZoneOffset
import java.time.ZonedDateTime

class ForecastConverterService() {

    fun convertToInternal(forecastData: ForecastData): FivePlusDayForecast {
        return FivePlusDayForecast(forecastData.city.name, forecastData.pointInTimeForecastList.map { pointInTimeForecast ->
            OneDateTimeForecast(
                    forecastData.city.name,
                    forecastData.city.timezoneSeconds,
                    pointInTimeForecast.utcTime,
                    buildZonedDateTime(forecastData.city.timezoneSeconds, pointInTimeForecast.utcTime),
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
