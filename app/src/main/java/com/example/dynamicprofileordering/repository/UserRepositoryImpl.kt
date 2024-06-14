package com.example.dynamicprofileordering.repository

import com.example.dynamicprofileordering.model.Config
import com.example.dynamicprofileordering.model.User
import com.example.dynamicprofileordering.network.ApiService
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val apiService: ApiService): UserRepository {
    override suspend fun getUsers(): List<User> {
       return apiService.getUsers().users
    }

    override suspend fun getConfig(): Config {
        return apiService.getConfig()

    }
}