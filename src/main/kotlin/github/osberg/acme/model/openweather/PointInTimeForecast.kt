package github.osberg.acme.model.openweather

import com.fasterxml.jackson.annotation.JsonProperty

data class PointInTimeForecast(@JsonProperty("dt") val utcTime: Long,
                               @JsonProperty("main") val tempInfo: TemperatureInfo,
                               @JsonProperty("weather") val weatherBody: List<WeatherBody>)


data class TemperatureInfo(@JsonProperty("temp") val temp: Double)

data class WeatherBody(@JsonProperty("main") val weatherType: String)