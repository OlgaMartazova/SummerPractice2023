package com.example.dairy.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dairy.domain.entity.TodoEntity
import com.example.dairy.domain.usecase.DeleteTodoByIdUseCase
import com.example.dairy.domain.usecase.GetTodoByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class InfoViewModel(
    private val getTodoByIdUseCase: GetTodoByIdUseCase,
    private val deleteTodoByIdUseCase: DeleteTodoByIdUseCase
) : ViewModel() {

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

    fun onDeleteTodo(id: Int) {
        viewModelScope.launch {
            deleteTodoByIdUseCase(id)
        }
    }
}