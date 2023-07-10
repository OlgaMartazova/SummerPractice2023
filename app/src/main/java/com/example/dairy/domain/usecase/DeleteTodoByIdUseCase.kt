package com.example.dairy.domain.usecase

import com.example.dairy.domain.repository.TodoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteTodoByIdUseCase(
    private val todoRepository: TodoRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend operator fun invoke(
        id: Int
    ) {
        return withContext(dispatcher) {
            todoRepository.deleteTodoById(id)
        }
    }
}