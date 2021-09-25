package com.lixan.fajardo.incomeandexpensetracker.di.modules

import com.lixan.fajardo.incomeandexpensetracker.network.ApiService
import com.lixan.fajardo.incomeandexpensetracker.network.remoterepository.implementation.LoginRemoteRepositoryImpl
import com.lixan.fajardo.incomeandexpensetracker.network.remoterepository.source.LoginRemoteRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RemoteRepositoryModule {

    @Provides
    @Singleton
    fun provideLoginRemoteRepository(
        apiService: ApiService
    ): LoginRemoteRepository = LoginRemoteRepositoryImpl(apiService)

}