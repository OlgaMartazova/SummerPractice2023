package com.example.dairy.domain.usecase

import com.example.dairy.domain.entity.TodoEntity
import com.example.dairy.domain.repository.TodoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetTodoByIdUseCase(
    private val todoRepository: TodoRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend operator fun invoke(
        id: Int
    ): TodoEntity {
        return withContext(dispatcher) {
            todoRepository.getTodoById(id)
        }
    }
}