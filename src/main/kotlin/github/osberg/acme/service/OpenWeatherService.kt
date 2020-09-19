package github.osberg.acme.service

import github.osberg.acme.api.OpenWeatherApi
import github.osberg.acme.config.RetrofitClientConfig
import github.osberg.acme.model.openweather.ForecastData

class OpenWeatherService(val openWeatherApi: OpenWeatherApi) {

    fun getFiveDayForecastFor(city: String): ForecastData? {
        val response = openWeatherApi.getFiveDayForecastForCity("$city,us").execute()
        if (response.isSuccessful) {
            return response.body()
        } else {
            println(response.message())
            return null
        }
    }
}