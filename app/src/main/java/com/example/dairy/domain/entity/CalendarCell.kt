package com.example.dairy.domain.entity

data class CalendarCell(
    val hour: String,
    val todoListPerHour: List<TodoEntity>
)
