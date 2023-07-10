package com.example.dairy.domain.usecase

import com.example.dairy.domain.entity.TodoEntity
import com.example.dairy.domain.repository.TodoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CreateTodoUseCase(
    private val todoRepository: TodoRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend operator fun invoke(
        todo: TodoEntity
    ) {
        return withContext(dispatcher) {
            todoRepository.addTodo(todo)
        }
    }
}