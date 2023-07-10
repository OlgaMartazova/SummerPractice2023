package com.example.dairy.di

import com.example.dairy.presentation.viewmodel.CalendarViewModel
import com.example.dairy.presentation.viewmodel.InfoViewModel
import com.example.dairy.presentation.viewmodel.EditViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::CalendarViewModel)
    viewModelOf(::InfoViewModel)
    viewModelOf(::EditViewModel)
}