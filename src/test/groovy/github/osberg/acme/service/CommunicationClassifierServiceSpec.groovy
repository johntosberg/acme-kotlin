package github.osberg.acme.service

import github.osberg.acme.BaseSpec
import github.osberg.acme.model.CommunicationMethod
import github.osberg.acme.model.internal.InternalOneDayForecast
import spock.lang.Unroll

class CommunicationClassifierServiceSpec extends BaseSpec {

    CommunicationClassifierService engagementClassifierService

    OpenWeatherService openWeatherService
    ForecastConverterService forecastConverterService

    void setup() {
        openWeatherService = Mock()
        forecastConverterService = Mock()
        engagementClassifierService = new CommunicationClassifierService(forecastConverterService, openWeatherService)
    }

    @Unroll
    void "Properly classifies many combinations of weather"() {
        given:
        InternalOneDayForecast oneDayForecast = getInternalOneDayForecast(temp, weather)

        when:
        def actual = engagementClassifierService.getCommunicationMethod(oneDayForecast)

        then:
        actual == expected

        where:
        expected                    | temp | weather
        //Sunny and warmer than 75
        CommunicationMethod.TEXT    | 77   | "Clear"
        CommunicationMethod.TEXT    | 80   | "Clear"
        CommunicationMethod.TEXT    | 76   | "Clear"

        //Between 55 and 75, regardless of the weather (except rain)
        CommunicationMethod.EMAIL    | 75   | "Clear"
        CommunicationMethod.EMAIL    | 55   | "Clear"
        CommunicationMethod.EMAIL    | 55   | "Clouds"
        CommunicationMethod.EMAIL    | 75   | "Clouds"

        //If it's raining, always call
        CommunicationMethod.PHONE   | 35   | "Rain"
        CommunicationMethod.PHONE   | 55   | "Rain"
        CommunicationMethod.PHONE   | 54   | "Rain"
        CommunicationMethod.PHONE   | 56   | "Rain"
        CommunicationMethod.PHONE   | 74   | "Rain"
        CommunicationMethod.PHONE   | 75   | "Rain"
        CommunicationMethod.PHONE   | 76   | "Rain"

        //When it's over 75, not sunny, and not rainy, undefined
        CommunicationMethod.REFRAIN | 76 | "Clouds"

    }
}
