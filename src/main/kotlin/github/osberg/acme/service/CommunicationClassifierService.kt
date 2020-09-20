package github.osberg.acme.service

import github.osberg.acme.model.CommunicationMethod
import github.osberg.acme.model.internal.InternalFiveDayForecast
import github.osberg.acme.model.internal.InternalOneDayForecast
import java.time.ZonedDateTime

class CommunicationClassifierService(val forecastConverterService: ForecastConverterService,
                                     val openWeatherService: OpenWeatherService) {

    fun getClassificationsForCity(city: String): List<String> {
        return openWeatherService.getFiveDayForecastFor(city)?.let { forecastData ->
            selectRepresentativeForecastForDay(forecastConverterService.convertToInternal(forecastData)).forecastListInternal.map {
                "On ${getPrettyDateString(it.localTime)}, " +
                "The temperature will be: ${it.temperature}F, " +
                getPrettyWeatherString(it.weatherType) +
                getPrettyCommunicationMethod(getCommunicationMethod(it))
            }
        } ?: run {
            //Probably redundant since the stacktrace will be there.
            listOf("Something went wrong when calling Open Weather API. See stacktrace.")
        }
    }

    fun printClassifiersForCity(city: String) {
        getClassificationsForCity(city).forEach { println(it) }
    }

    fun getPrettyDateString(dateTime: ZonedDateTime): String {
        return "${dateTime.dayOfWeek.name}, ${dateTime.month.name} ${dateTime.dayOfMonth}"
    }

    fun getPrettyWeatherString(weatherType: String): String {
        return when(weatherType) {
            "Clouds" -> "with cloudy skies. "
            "Clear" -> "with clear skies. "
            "Rain" -> "with expected rain. "
            else -> "a new type of unknown weather: ${weatherType}. "
        }
    }

    fun getPrettyCommunicationMethod(method: CommunicationMethod): String {
        return when(method) {
            CommunicationMethod.REFRAIN -> "Undefined weather combination. Refrain from communicating on this day, or consult product owner."
            else -> "The suggested engagement method is: $method."
        }
    }

    //Openweather returns multiple hours for each day. We only care about the overall day's forecast.
    //For now, we're taking the data point that contains the high temperature of the day as our representative value
    fun selectRepresentativeForecastForDay(internalFiveDayForecast: InternalFiveDayForecast): InternalFiveDayForecast {
        val filteredList = emptyMap<Int, InternalOneDayForecast>().toMutableMap()

        //Iterate through all of the forecast points. If we haven't saved one for that day yet, save the current value.
        //Otherwise, only save the one the hottest point of the day.
        internalFiveDayForecast.forecastListInternal.forEach { currentForecastValue ->
            val key = currentForecastValue.localTime.dayOfMonth

            //Requirements specify "Next five days". The API also returns today, so ignore those values
            if (key != ZonedDateTime.now().dayOfMonth) {
                filteredList[key]?.let {
                    if (it.temperature < currentForecastValue.temperature) {
                        filteredList[key] = currentForecastValue
                    }
                } ?: run {
                    filteredList[key] = currentForecastValue
                }
            }
        }
        return InternalFiveDayForecast(internalFiveDayForecast.city, filteredList.values.toList())
    }

    fun getCommunicationMethod(internalOneDayForecast: InternalOneDayForecast): CommunicationMethod {
        val temperature = internalOneDayForecast.temperature
        val weatherType = internalOneDayForecast.weatherType
        return when {
            temperature < 55 || weatherType == "Rain" -> CommunicationMethod.PHONE
            temperature > 75 && weatherType == "Clear" -> CommunicationMethod.TEXT
            temperature in 55..75 -> CommunicationMethod.EMAIL
            else -> CommunicationMethod.REFRAIN
        }
    }
}