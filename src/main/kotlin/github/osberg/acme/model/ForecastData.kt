package github.osberg.acme.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue

data class ForecastData(@JsonProperty("cod") val cod: String,
                        @JsonProperty("list") val dailyForecastList: List<DailyForecast>)