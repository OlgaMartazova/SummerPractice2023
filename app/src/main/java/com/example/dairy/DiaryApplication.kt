package com.example.dairy

import android.app.Application
import android.content.Context
import com.example.dairy.di.databaseModule
import com.example.dairy.di.repositoryModule
import com.example.dairy.di.useCaseModule
import com.example.dairy.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DiaryApplication : Application() {

    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        startKoin {
            allowOverride(false)
            androidContext(this@DiaryApplication)
            modules(
                listOf(
                    databaseModule,
                    useCaseModule,
                    viewModelModule,
                    repositoryModule,
                )
            )
        }
    }
}