package github.osberg.acme.service

import github.osberg.acme.BaseSpec
import github.osberg.acme.model.internal.InternalFiveDayForecast
import github.osberg.acme.model.internal.InternalOneDayForecast
import github.osberg.acme.model.openweather.OpenWeatherApiResponse
import github.osberg.acme.model.openweather.DailyForecast
import spock.lang.Unroll

import java.time.ZonedDateTime

class ForecastConverterServiceSpec extends BaseSpec {

    ForecastConverterService forecastConverterService = new ForecastConverterService()

    void "Properly converts time information to ZonedTime"() {
        given:
        Long minneapolisOffsetSeconds = -18000L
        Long utcSeconds = 1600538400

        when:
        ZonedDateTime zonedDateTime = forecastConverterService.buildZonedDateTime(minneapolisOffsetSeconds, utcSeconds)

        then:
        zonedDateTime
    }

    void "Properly converters to internal model"() {
        given:
        OpenWeatherApiResponse deserializedApiResponse = getTestForecastData()

        when:
        InternalFiveDayForecast internalFiveDayForecast = forecastConverterService.convertToInternal(deserializedApiResponse)

        then:
        internalFiveDayForecast
        internalFiveDayForecast.city == deserializedApiResponse.city.name
        internalFiveDayForecast.forecastListInternal.eachWithIndex { InternalOneDayForecast entry, int i ->
            DailyForecast apiResponse = deserializedApiResponse.dailyForecastList[i]
            assert entry.weatherType == apiResponse.weatherBody[0].weatherType
            assert entry.cityTimeZoneOffset == deserializedApiResponse.city.timezoneSeconds
        }
    }

    @Unroll
    void "Properly converts a few kelvin temperatures according to google"() {
        when:
        Integer convertedTemp = forecastConverterService.convertToFahrenheit(kelvinTemp)

        then:
        convertedTemp == expectedTemp

        where:
        kelvinTemp | expectedTemp
        300        | 80
        350        | 170
        280        | 44
    }
}
