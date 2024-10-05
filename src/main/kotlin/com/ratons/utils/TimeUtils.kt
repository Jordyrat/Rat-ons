package com.ratons.utils

import kotlin.time.Duration

object TimeUtils {

    fun Duration.shortFormat(): String {
        val millis = inWholeMilliseconds
        val unit = TimeUnit.entries.find { millis >= it.factor } ?: TimeUnit.SECOND
        val amount = millis / unit.factor
        val remainder = millis % unit.factor
        val decimal = (remainder * 10 / unit.factor).toInt()
        return "$amount.$decimal ${unit.longName.lowercase()}" + if (amount != 1L) "s" else ""
    }
}

private const val FACTOR_SECONDS = 1000L
private const val FACTOR_MINUTES = FACTOR_SECONDS * 60
private const val FACTOR_HOURS = FACTOR_MINUTES * 60
private const val FACTOR_DAYS = FACTOR_HOURS * 24
private const val FACTOR_WEEKS = FACTOR_DAYS * 7
private const val FACTOR_MONTH = FACTOR_DAYS * 31
private const val FACTOR_YEARS = (FACTOR_DAYS * 365.25).toLong()

enum class TimeUnit(val factor: Long, val shortName: String, val longName: String) {
    YEAR(FACTOR_YEARS, "y", "Year"),
    MONTH(FACTOR_MONTH, "mo", "Month"),
    WEEK(FACTOR_WEEKS, "w", "Week"),
    DAY(FACTOR_DAYS, "d", "Day"),
    HOUR(FACTOR_HOURS, "h", "Hour"),
    MINUTE(FACTOR_MINUTES, "m", "Minute"),
    SECOND(FACTOR_SECONDS, "s", "Second"),
}
