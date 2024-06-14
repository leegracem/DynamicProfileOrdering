package com.example.dynamicprofileordering.repository

import com.example.dynamicprofileordering.model.Config
import com.example.dynamicprofileordering.model.User

interface UserRepository {
    suspend fun getUsers(): List<User>
    suspend fun getConfig(): Config
}
