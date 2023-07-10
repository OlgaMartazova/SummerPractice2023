package com.example.dairy.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dairy.domain.entity.TodoEntity
import com.example.dairy.domain.usecase.GetTodoListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class CalendarViewModel(
    private val getTodoListUseCase: GetTodoListUseCase
) : ViewModel() {

    private var _todoList = MutableStateFlow<List<TodoEntity>>(mutableListOf())
    val todoList: StateFlow<List<TodoEntity>>
        get() = _todoList.asStateFlow()

    fun onGetTodoListByDay(givenDate: LocalDate) {
        viewModelScope.launch {
            getTodoListUseCase(givenDate).also {
                _todoList.emit(it)
            }
        }
    }
}