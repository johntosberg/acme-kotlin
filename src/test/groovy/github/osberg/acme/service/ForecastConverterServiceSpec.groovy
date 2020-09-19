package github.osberg.acme.service

import spock.lang.Specification
import spock.lang.Unroll

import java.time.ZonedDateTime

class ForecastConverterServiceSpec extends Specification {

    ForecastConverterService forecastConverterService = new ForecastConverterService()

    void "Properly converts time information to ZonedTime"() {
        given:
        Long minneapolisOffsetSeconds = -18000L
        Long utcSeconds = 1600538400

        when:
        ZonedDateTime zonedDateTime = forecastConverterService.buildZonedDateTime(minneapolisOffsetSeconds, utcSeconds)

        then:
        zonedDateTime
    }

    @Unroll
    void "Properly converts a few kelvin temperatures according to google"() {
        when:
        Integer convertedTemp = forecastConverterService.convertToFahrenheit(kelvinTemp)

        then:
        convertedTemp == expectedTemp

        where:
        kelvinTemp | expectedTemp
        300        | 80
        350        | 170
        280        | 44
    }
}
