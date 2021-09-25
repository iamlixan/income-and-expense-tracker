package com.lixan.fajardo.incomeandexpensetracker.di.modules

import com.lixan.fajardo.incomeandexpensetracker.di.scope.ActivityScope
import com.lixan.fajardo.incomeandexpensetracker.main.home.HomeActivity
import com.lixan.fajardo.incomeandexpensetracker.main.login.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributeLoginActivity(): LoginActivity

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributeHomeActivity(): HomeActivity

}