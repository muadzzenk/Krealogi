package com.example.krealogi.network.repository

import com.example.krealogi.datamodel.UserModel
import retrofit2.http.*

interface UserService {
    @GET("/users")
    suspend fun getUsers(
        @QueryMap parameter: UserRequest
    ): ArrayList<UserModel>

    @GET("search/users")
    suspend fun searchUsers(
        @QueryMap parameter: SearchRequest
    ): SearchResponse

}