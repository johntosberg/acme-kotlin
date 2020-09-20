# "Kotlin ACME Customer Engagement Classifier"

The purpose of this application is to determine what communication method to use based on the upcoming five day forecast for Minneapolis.
It uses the [OpenWeatherApi](https://openweathermap.org/forecast5)'s five day forecast API to get the weather.

For ease of use, *my free API key* is exposed directly in the code. Please don't abuse it.

## Rules:

- The best time to engage a customer via a text message is when it is sunny and warmer than 75
degrees Fahrenheit
- The best time to engage a customer via email is when it is between 55 and 75 degrees
Fahrenheit
- The best time to engage a customer via a phone call is when it is less than 55 degrees or when it
is raining.

Missing from the rules are when it is not sunny, not rainy, and wamer than 75 degrees. I've chosen to return a message to refrain from contacting, or consult a product owner.

## Usage

Assumptions: Java 8+ installed on machine. I've tested on a macbook running macOS 10.15.4. I recommend similar setup and installing java using [SDKMAN!](https://sdkman.io/)

Two options for running: 

### Build the shadow jar and run the jar:

(From the project directory)
1. `$ ./gradlew shadowJar`
2. `$ java -jar build/libs/acme-kotlin-all.jar Minneapolis`

### Run directly from gradle

`$ ./gradlew run --args Minneapolis`

### Limitations

I've only tested using the literal string "Minneapolis" as a city input. The Application will accept other cities as input and pass them to the OpenWeather API. 
However, I cannot guarantee predictable behavior with other cities at this time. The Application will print a warning message accordingly. 
See the [OpenWeatherApi](https://openweathermap.org/forecast5) docs for further information on correct city input

## Future improvements

Some initial thoughts on how this could be further improved:

### Exposing service as an API
The Application could be pretty easily converted to an API to get classifications for any given city, and could be further enhanced to even expose time of day communication methods

### Dependency Injection
Running straight from main, it was simple to just build the dependent objects directly. In an application that would run as an API, I probably would've introduced some DI but for the purposes of this app it felt like overkill 

## Personal Notes

In my day jobs until now, I've primarily written Java & Groovy webapps that expose an API, using frameworks like Ratpack, Spring Boot, and Micronaut. 

I decided to stretch my comfort zone a little bit in choosing Kotlin, and tried to use some of the language-specific features like `let` and `when` instead of strictly imperative style programming, but
I know there's room for improvement to make this app even more functional.

I chose Kotlin as the new technology for myself, so in turn I decided to limit this Application to just a command line program and not an API backend for some light SPA frontend.

[![forthebadge](https://forthebadge.com/images/badges/works-on-my-machine.svg)](https://forthebadge.com)

[![forthebadge](https://forthebadge.com/images/badges/winter-is-coming.svg)](https://forthebadge.com)