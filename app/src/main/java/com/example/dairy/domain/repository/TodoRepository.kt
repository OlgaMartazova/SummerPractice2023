package com.example.dairy.domain.repository

import com.example.dairy.domain.entity.TodoEntity
import java.time.LocalDate

interface TodoRepository {
    suspend fun findAllTodoByDay(givenDay: LocalDate): List<TodoEntity>

    suspend fun addTodo(todo: TodoEntity)

    suspend fun getTodoById(id: Int): TodoEntity

    suspend fun deleteTodoById(id: Int)

}