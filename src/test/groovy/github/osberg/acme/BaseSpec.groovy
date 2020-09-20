package github.osberg.acme

import github.osberg.acme.model.internal.InternalOneDayForecast
import github.osberg.acme.model.openweather.CityInfo
import github.osberg.acme.model.openweather.OpenWeatherApiResponse
import github.osberg.acme.model.openweather.DailyForecast
import github.osberg.acme.model.openweather.TemperatureInfo
import github.osberg.acme.model.openweather.WeatherBody
import spock.lang.Specification

import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime

class BaseSpec extends Specification {

    OpenWeatherApiResponse getTestForecastData() {
        List<DailyForecast> forecastList = [
                new DailyForecast(
                        1600538400,
                        new TemperatureInfo(300L),
                        [new WeatherBody("Clear")]
                ),
                new DailyForecast(
                        1600538500,
                        new TemperatureInfo(302L),
                        [new WeatherBody("Rain")]
                )
        ]
        return new OpenWeatherApiResponse(
                "200",
                forecastList,
                new CityInfo("Minneapolis", -18000)
        )
    }

    InternalOneDayForecast getInternalOneDayForecast(Integer temperature, String weatherType) {
        return new InternalOneDayForecast(
                "Minneapolis",
                -18000,
                Instant.ofEpochSecond(1600538400).atZone(ZoneOffset.UTC),
                temperature,
                weatherType
        )
    }
}
