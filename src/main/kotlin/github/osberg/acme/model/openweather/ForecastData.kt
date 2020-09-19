package github.osberg.acme.model.openweather

import com.fasterxml.jackson.annotation.JsonProperty

data class ForecastData(@JsonProperty("cod") val cod: String,
                        @JsonProperty("list") val pointInTimeForecastList: List<PointInTimeForecast>,
                        @JsonProperty("city") val city: CityInfo)

data class CityInfo(@JsonProperty("name") val name: String,
                    @JsonProperty("timezone") val timezoneSeconds: Long)