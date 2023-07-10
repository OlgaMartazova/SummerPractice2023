package com.example.dairy.presentation.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

class DateConverter {

    fun dateAsString(
        givenDate: LocalDate,
        setYear: Boolean = true,
        fullDayOfWeek: Boolean = true
    ): String {
        var dayOfWeek = givenDate.dayOfWeek.toString().lowercase(Locale.ROOT)
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
        if (!fullDayOfWeek) dayOfWeek = dayOfWeek.take(3)
        val month = givenDate.month.getDisplayName(TextStyle.FULL, Locale("en"))
        val result = "${dayOfWeek} $month ${givenDate.dayOfMonth}"
        return if (setYear) "$result, ${givenDate.year}" else result
    }

    fun formatTime(givenTime: LocalTime): String {
        val timeFormat = DateTimeFormatter.ofPattern("HH:mm")
        return givenTime.format(timeFormat)
    }

    fun toLocalDate(date: String?): LocalDate {
        val format = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return LocalDate.parse(date, format)
    }

    fun formatDateInfo(start: LocalDateTime, finish: LocalDateTime): String {
        val today = LocalDate.now()
        val timeStart = formatTime(start.toLocalTime())
        val timeFinish = formatTime(finish.toLocalTime())
        if (start.toLocalDate() != finish.toLocalDate()) {
            return "${
                dateAsString(
                    start.toLocalDate(),
                    setYear = false,
                    fullDayOfWeek = false
                )
            }, $timeStart - ${
                dateAsString(
                    finish.toLocalDate(),
                    setYear = false,
                    fullDayOfWeek = false
                )
            }, $timeFinish"
        }
        val date = when (start.toLocalDate()) {
            today -> "Today at"
            today.plusDays(1) -> "Tomorrow at"
            today.minusDays(1) -> "Yesterday at"
            else -> dateAsString(start.toLocalDate(), false) + ","
        }
        return "$date $timeStart-$timeFinish"
    }
}