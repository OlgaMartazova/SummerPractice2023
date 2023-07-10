package com.example.dairy.data.mapper

import com.example.dairy.data.model.Todo
import com.example.dairy.domain.entity.TodoEntity


fun toListTodo(list: List<Todo>) = list.map {
    mapToEntity(it)
}

fun mapToEntity(todo: Todo) = TodoEntity(
    todo.id,
    todo.dateStart,
    todo.dateFinish,
    todo.name,
    todo.description
)

fun mapTodo(todo: TodoEntity) = Todo(
    todo.dateStart,
    todo.dateFinish,
    todo.name,
    todo.description,
    todo.id,
)
