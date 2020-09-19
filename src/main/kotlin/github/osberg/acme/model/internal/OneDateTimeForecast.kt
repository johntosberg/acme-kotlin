package github.osberg.acme.model.internal

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.*

data class OneDateTimeForecast(val city: String,
                               val cityTimeZoneOffset: Long,
                               val utcTime: Long,
                               val localTime: ZonedDateTime,
                               val temperature: Int,
                               val weatherType: String)