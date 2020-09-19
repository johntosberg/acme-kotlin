package github.osberg.acme.model.internal

data class FivePlusDayForecast(val city: String,
                               val forecastList: List<OneDateTimeForecast>) {
}