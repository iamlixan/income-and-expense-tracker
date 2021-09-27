package com.lixan.fajardo.incomeandexpensetracker.di.modules

import com.lixan.fajardo.incomeandexpensetracker.data.repository.implementation.LoginRepositoryImpl
import com.lixan.fajardo.incomeandexpensetracker.data.repository.implementation.TransactionRepositoryImpl
import com.lixan.fajardo.incomeandexpensetracker.data.repository.source.LoginRepository
import com.lixan.fajardo.incomeandexpensetracker.data.repository.source.TransactionRepository
import com.lixan.fajardo.incomeandexpensetracker.local.source.LoginLocalRepository
import com.lixan.fajardo.incomeandexpensetracker.network.remoterepository.source.LoginRemoteRepository
import com.lixan.fajardo.incomeandexpensetracker.network.remoterepository.source.TransactionRemoteRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun providesLoginRepository(
        remote: LoginRemoteRepository,
        local: LoginLocalRepository
    ): LoginRepository = LoginRepositoryImpl(remote, local)

    @Provides
    @Singleton
    fun providesTransactionRepository(
        local: LoginLocalRepository,
        remote: TransactionRemoteRepository
    ): TransactionRepository = TransactionRepositoryImpl(local, remote)

}