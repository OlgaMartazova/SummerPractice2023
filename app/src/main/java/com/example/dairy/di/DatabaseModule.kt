package com.example.dairy.di

import com.example.dairy.data.local.room.TodoDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single { TodoDatabase.getInstance(androidApplication()) }
    single { get<TodoDatabase>().todoDao() }
}