package com.example.dairy.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dairy.domain.entity.TodoEntity
import com.example.dairy.domain.usecase.CreateTodoUseCase
import com.example.dairy.domain.usecase.GetTodoByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EditViewModel(
    private val createTodoUseCase: CreateTodoUseCase,
    private val getTodoByIdUseCase: GetTodoByIdUseCase,
): ViewModel() {

    fun onCreateTodo(todo: TodoEntity) {
        viewModelScope.launch {
            createTodoUseCase(todo)
        }
    }

    private var _todo = MutableStateFlow<TodoEntity?>(null)
    val todo: StateFlow<TodoEntity?>
        get() = _todo.asStateFlow()

    fun onGetTodo(id: Int) {
        viewModelScope.launch {
            getTodoByIdUseCase(id).also {
                _todo.emit(it)
            }
        }
    }
}