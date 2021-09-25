package com.lixan.fajardo.incomeandexpensetracker.di.modules

import com.lixan.fajardo.incomeandexpensetracker.local.database.AppDatabase
import com.lixan.fajardo.incomeandexpensetracker.local.implementation.LoginLocalRepositoryImpl
import com.lixan.fajardo.incomeandexpensetracker.local.source.LoginLocalRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class LocalRepositoryModule {

    @Provides
    @Singleton
    fun providesLoginLocalRepository(database : AppDatabase): LoginLocalRepository =
        LoginLocalRepositoryImpl(database)
}