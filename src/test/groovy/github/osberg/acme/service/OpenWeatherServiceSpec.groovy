package github.osberg.acme.service

import github.osberg.acme.api.OpenWeatherApi
import github.osberg.acme.model.openweather.CityInfo
import github.osberg.acme.model.openweather.DailyForecast
import github.osberg.acme.model.openweather.OpenWeatherApiResponse
import github.osberg.acme.model.openweather.TemperatureInfo
import github.osberg.acme.model.openweather.WeatherBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import spock.lang.Specification

class OpenWeatherServiceSpec extends Specification {

    OpenWeatherService openWeatherService

    OpenWeatherApi weatherApi

    void setup() {
        weatherApi = Mock()
        openWeatherService = new OpenWeatherService(weatherApi)
    }

    void 'Can get an external Five Day Forecast object'() {
        given:
        String city = "Minneapolis"
        Call mockCall = Mock(Call)
        OpenWeatherApiResponse returnedForecastData = new OpenWeatherApiResponse(
                "200",
                [new DailyForecast(1600538400,
                    new TemperatureInfo(300),
                    [new WeatherBody("Clear")])
                ],
                new CityInfo("Minneapolis", -18000)
        )

        when:
        OpenWeatherApiResponse forecastData = openWeatherService.getFiveDayForecastFor(city)

        then:
        1 * weatherApi.getFiveDayForecastForCity("$city,us") >> mockCall
        1 * mockCall.execute() >> Response.success(returnedForecastData)

        and:
        forecastData == returnedForecastData
    }

}
