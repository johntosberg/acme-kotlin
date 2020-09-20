package github.osberg.acme.model.openweather

import com.fasterxml.jackson.annotation.JsonProperty

data class OpenWeatherApiResponse(@JsonProperty("cod") val cod: String,
                                  @JsonProperty("list") val dailyForecastList: List<DailyForecast>,
                                  @JsonProperty("city") val city: CityInfo)

data class CityInfo(@JsonProperty("name") val name: String,
                    @JsonProperty("timezone") val timezoneSeconds: Long)

data class DailyForecast(@JsonProperty("dt") val utcTime: Long,
                         @JsonProperty("main") val tempInfo: TemperatureInfo,
                         @JsonProperty("weather") val weatherBody: List<WeatherBody>)


data class TemperatureInfo(@JsonProperty("temp") val temp: Double)

data class WeatherBody(@JsonProperty("main") val weatherType: String)