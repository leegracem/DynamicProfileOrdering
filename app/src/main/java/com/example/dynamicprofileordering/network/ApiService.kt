package com.example.dynamicprofileordering.network

import com.example.dynamicprofileordering.model.Config
import com.example.dynamicprofileordering.model.User
import retrofit2.http.GET

interface ApiService {
    @GET("/users")
    suspend fun getUsers(): UsersResponse

    data class UsersResponse(
        val users: List<User>
    )

    @GET("/config")
    suspend fun getConfig(): Config

}