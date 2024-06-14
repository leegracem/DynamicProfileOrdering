package com.example.dynamicprofileordering.di

import com.example.dynamicprofileordering.network.ApiService
import com.example.dynamicprofileordering.network.RetrofitInstance
import com.example.dynamicprofileordering.repository.UserRepository
import com.example.dynamicprofileordering.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return RetrofitInstance.retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRepository(apiService: ApiService): UserRepository {
        return UserRepositoryImpl(apiService)
    }
}