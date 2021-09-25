package com.lixan.fajardo.incomeandexpensetracker.di.modules

import com.lixan.fajardo.incomeandexpensetracker.di.SchedulerProvider
import com.lixan.fajardo.incomeandexpensetracker.di.base.BaseSchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SchedulerModule {

    @Provides
    @Singleton
    fun providesSchedulerProvider(): BaseSchedulerProvider = SchedulerProvider.getInstance()
}