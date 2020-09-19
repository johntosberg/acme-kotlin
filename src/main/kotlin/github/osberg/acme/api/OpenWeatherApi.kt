package github.osberg.acme.api

import github.osberg.acme.model.ForecastData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface OpenWeatherApi {

    @GET("/data/2.5/forecast")
    fun getFiveDayForecastForCity(@Query(value = "q", encoded = true) city: String): Call<ForecastData>

}