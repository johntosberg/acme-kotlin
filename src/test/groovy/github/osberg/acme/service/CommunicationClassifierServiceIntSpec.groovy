package github.osberg.acme.service

import github.osberg.acme.config.RetrofitClientConfig
import spock.lang.Specification

class CommunicationClassifierServiceIntSpec extends Specification {

    CommunicationClassifierService engagementClassifierService

    OpenWeatherService openWeatherService
    ForecastConverterService forecastConverterService

    void setup() {
        openWeatherService = new OpenWeatherService(new RetrofitClientConfig().openWeatherApi)
        forecastConverterService = new ForecastConverterService()
        engagementClassifierService = new CommunicationClassifierService(forecastConverterService, openWeatherService)
    }

    void 'Can get a classification for next five days'() {
        when:
        List<String> messages = engagementClassifierService.getClassificationsForCity("Minneapolis")

        then:
        messages
        messages.size() == 5
        messages.every { it != "Something went wrong when calling Open Weather API. See stacktrace." }
    }

}
