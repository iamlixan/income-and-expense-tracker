package com.lixan.fajardo.incomeandexpensetracker.di.component

import android.app.Application
import com.lixan.fajardo.incomeandexpensetracker.IncomeAndExpenseTracker
import com.lixan.fajardo.incomeandexpensetracker.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AppModule::class,
        AndroidSupportInjectionModule::class,
        ActivityBuilderModule::class,
        NetworkModule::class,
        SchedulerModule::class,
        DatabaseModule::class,
        ApiServiceModule::class,
        RemoteRepositoryModule::class,
        LocalRepositoryModule::class,
        RepositoryModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: IncomeAndExpenseTracker)
}