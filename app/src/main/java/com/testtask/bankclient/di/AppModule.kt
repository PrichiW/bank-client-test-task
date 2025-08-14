package com.testtask.bankclient.di

import com.testtask.bankclient.data.repository.PurchasesRepositoryImpl
import com.testtask.bankclient.data.repository.RegistrationRepositoryImpl
import com.testtask.bankclient.domain.repository.PurchasesRepository
import com.testtask.bankclient.domain.repository.RegistrationRepository
import com.testtask.bankclient.domain.usecase.GetPurchasesUseCase
import com.testtask.bankclient.domain.usecase.GetRegistrationDataUseCase
import com.testtask.bankclient.domain.usecase.SaveRegistrationDataUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindRegistrationRepository(
        impl: RegistrationRepositoryImpl
    ): RegistrationRepository

    @Binds
    @Singleton
    abstract fun bindPurchasesRepository(
        impl: PurchasesRepositoryImpl
    ): PurchasesRepository

    companion object {

        @Provides
        @Singleton
        fun provideSaveRegistrationDataUseCase(
            repository: RegistrationRepository
        ): SaveRegistrationDataUseCase = SaveRegistrationDataUseCase(repository)

        @Provides
        @Singleton
        fun provideGetRegistrationDataUseCase(
            repository: RegistrationRepository
        ): GetRegistrationDataUseCase = GetRegistrationDataUseCase(repository)

        @Provides
        @Singleton
        fun provideGetPurchasesUseCase(
            repository: PurchasesRepository
        ): GetPurchasesUseCase = GetPurchasesUseCase(repository)
    }
}