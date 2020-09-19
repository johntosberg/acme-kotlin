package github.osberg.acme.model

import com.fasterxml.jackson.annotation.JsonProperty

data class DailyForecast(@JsonProperty("dt_txt") val dtTxt: String)