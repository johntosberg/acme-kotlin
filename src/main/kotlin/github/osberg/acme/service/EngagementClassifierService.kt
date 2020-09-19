package github.osberg.acme.service

import github.osberg.acme.api.OpenWeatherApi
import github.osberg.acme.model.CommunicationMethod
import github.osberg.acme.model.internal.FivePlusDayForecast
import github.osberg.acme.model.internal.OneDateTimeForecast
import github.osberg.acme.openWeatherService
import java.time.ZonedDateTime

class EngagementClassifierService(val forecastConverterService: ForecastConverterService,
                                  val openWeatherService: OpenWeatherService) {

    fun printClassifiersForCity(city: String) {
        openWeatherService.getFiveDayForecastFor(city)?.let { forecastData ->
            filterFiveDayForecastToOnlyFiveElements(forecastConverterService.convertToInternal(forecastData)).forecastList.forEach {
                println("Date: ${it.localTime}, Temperature: ${it.temperature}, Weather Type: ${it.weatherType} Suggested Engagement: ${getEngagementMethod(it).toString()}")
            }
        }
    }

    fun filterFiveDayForecastToOnlyFiveElements(fivePlusDayForecast: FivePlusDayForecast): FivePlusDayForecast {
        //I'm sure there's a more clever way to do this, I'm not coming up with it right now.
        //Iterate through all of the forecast points, and only save one per date
        var filteredList = emptyMap<Int, OneDateTimeForecast>().toMutableMap()

        fivePlusDayForecast.forecastList.forEach {
            if (!filteredList.containsKey(it.localTime.dayOfMonth)) {
                filteredList[it.localTime.dayOfMonth] = it
            }
        }
        return FivePlusDayForecast(fivePlusDayForecast.city, filteredList.values.toList())
    }

    fun getEngagementMethod(oneDateTimeForecast: OneDateTimeForecast): CommunicationMethod {
        if (oneDateTimeForecast.temperature < 55 || oneDateTimeForecast.weatherType == "Rain") {
            return CommunicationMethod.PHONE
        } else if (oneDateTimeForecast.temperature > 75 && oneDateTimeForecast.weatherType == "Clear") {
            return CommunicationMethod.TEXT
        } else if (oneDateTimeForecast.temperature in 56..74) {
            return CommunicationMethod.EMAIL
        } else {
            //TODO: Ask Product owner what to do if it's warmer than 75 and not clear, but not raining
            println ("ERROR: Too hot to communicate")
            return CommunicationMethod.TOO_HOT_FOR_COMMUNICATING
        }
    }
}