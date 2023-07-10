package com.example.dairy.domain.entity

import java.time.LocalDateTime

data class TodoEntity(
    val id: Int,
    val dateStart: LocalDateTime,
    val dateFinish: LocalDateTime,
    val name: String?,
    val description: String?
)