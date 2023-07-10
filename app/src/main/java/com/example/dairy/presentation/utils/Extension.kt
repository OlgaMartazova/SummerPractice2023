package com.example.dairy.presentation.utils

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

fun toCalendar(date: LocalDate): Calendar {
    val calendar = Calendar.getInstance()
    calendar.set(date.year, date.monthValue - 1, date.dayOfMonth, 0, 0)
    return calendar
}

fun toCalendar(time: LocalTime): Calendar {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, time.hour)
    calendar.set(Calendar.MINUTE, time.minute)
    return calendar
}

fun createDatePicker(
    context: Context,
    selected: Calendar,
    dateSetListener: DatePickerDialog.OnDateSetListener
): DatePickerDialog {
    return DatePickerDialog(
        context,
        dateSetListener,
        selected.get(Calendar.YEAR),
        selected.get(Calendar.MONTH),
        selected.get(Calendar.DAY_OF_MONTH)
    )
}

fun createTimePicker(
    context: Context,
    selected: Calendar,
    timeSetListener: TimePickerDialog.OnTimeSetListener
): TimePickerDialog {
    return TimePickerDialog(
        context,
        timeSetListener,
        selected.get(Calendar.HOUR_OF_DAY),
        selected.get(Calendar.MINUTE),
        true
    )
}