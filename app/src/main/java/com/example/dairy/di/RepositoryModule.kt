package com.example.dairy.di

import com.example.dairy.data.impl.TodoRepositoryImpl
import com.example.dairy.domain.repository.TodoRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val repositoryModule by lazy {
    module {
        factoryOf(::TodoRepositoryImpl) { bind<TodoRepository>() }
    }
}