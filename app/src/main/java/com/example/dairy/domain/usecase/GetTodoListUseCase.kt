package com.example.dairy.domain.usecase

import com.example.dairy.domain.entity.TodoEntity
import com.example.dairy.domain.repository.TodoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate

class GetTodoListUseCase(
    private val todoRepository: TodoRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend operator fun invoke(
        date: LocalDate
    ): List<TodoEntity> {
        return withContext(dispatcher) {
            todoRepository.findAllTodoByDay(date)
        }
    }
}