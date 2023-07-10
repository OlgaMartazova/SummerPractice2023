package com.example.dairy.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.*

@Entity(tableName = "todo_db")
data class Todo(
    @ColumnInfo(name = "date_start") val dateStart: LocalDateTime,
    @ColumnInfo(name = "date_finish") val dateFinish: LocalDateTime,
    val name: String?,
    val description: String?,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)
