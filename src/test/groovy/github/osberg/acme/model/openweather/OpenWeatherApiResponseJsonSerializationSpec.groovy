package github.osberg.acme.model.openweather

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification

class OpenWeatherApiResponseJsonSerializationSpec extends Specification {

    void "POJO representations of JSON deserialize correctly"() {
        given:
        String rawJson = removeAllWhiteSpace(new File(getClass().getResource("/sample_response.json").toURI()))
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

        when:
        OpenWeatherApiResponse forecastData = mapper.readValue(rawJson, OpenWeatherApiResponse.class)

        then:
        forecastData
        forecastData.dailyForecastList.size() == 40
        forecastData.city.name == 'Minneapolis'
        forecastData.city.timezoneSeconds == -18000
        forecastData.dailyForecastList.every {
            it.tempInfo.temp && it.utcTime && it.weatherBody.weatherType
        }
    }

    String removeAllWhiteSpace(File file) {
        file.text.replace(' ', '').replace('\n', '')
    }
}
