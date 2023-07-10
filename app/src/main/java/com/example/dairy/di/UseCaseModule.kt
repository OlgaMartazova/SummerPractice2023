package com.example.dairy.di

import com.example.dairy.domain.usecase.*
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
    factoryOf(::CreateTodoUseCase)
    factoryOf(::GetTodoListUseCase)
    factoryOf(::GetTodoByIdUseCase)
    factoryOf(::DeleteTodoByIdUseCase)
    factory { Dispatchers.Default }
}