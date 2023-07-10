package com.example.dairy.data.impl

import com.example.dairy.data.local.room.TodoDao
import com.example.dairy.data.mapper.mapToEntity
import com.example.dairy.data.mapper.mapTodo
import com.example.dairy.data.mapper.toListTodo
import com.example.dairy.domain.entity.TodoEntity
import com.example.dairy.domain.repository.TodoRepository
import java.time.LocalDate
import java.time.ZoneOffset

class TodoRepositoryImpl(
    private val todoDao: TodoDao
) : TodoRepository {
    override suspend fun findAllTodoByDay(givenDay: LocalDate): List<TodoEntity> {
        return toListTodo(
            todoDao.getTodoByDay(
                givenDay.atStartOfDay().toEpochSecond(ZoneOffset.UTC),
                givenDay.atStartOfDay().plusDays(1).toEpochSecond(ZoneOffset.UTC)
            )
        )
    }

    override suspend fun addTodo(todo: TodoEntity) {
        return todoDao.insertTodo(mapTodo(todo))
    }

    override suspend fun getTodoById(id: Int): TodoEntity {
        return mapToEntity(todoDao.getTodoById(id))
    }

    override suspend fun deleteTodoById(id: Int) {
        return todoDao.deleteTodoById(id)
    }
}