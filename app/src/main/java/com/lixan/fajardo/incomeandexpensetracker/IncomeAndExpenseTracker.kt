package com.lixan.fajardo.incomeandexpensetracker

import android.app.Activity
import android.app.Application
import com.lixan.fajardo.incomeandexpensetracker.di.modules.injector.ApplicationInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

class IncomeAndExpenseTracker : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        ApplicationInjector.init(this)
    }

}