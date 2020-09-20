package github.osberg.acme.service

import github.osberg.acme.api.OpenWeatherApi
import github.osberg.acme.model.openweather.OpenWeatherApiResponse

open class OpenWeatherService(val openWeatherApi: OpenWeatherApi) {

    fun getFiveDayForecastFor(city: String): OpenWeatherApiResponse? {
        val response = openWeatherApi.getFiveDayForecastForCity("$city,us").execute()
        return if (response.isSuccessful) {
            response.body()
        } else {
            println("Something went wrong in Open Weather API Call. " +
                    "Error code=${response.code()} " +
                    "Error body=${response.errorBody()}")
            null
        }
    }
}