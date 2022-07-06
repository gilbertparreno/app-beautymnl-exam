package com.beautymnl.exam.core.networking.services

import com.beautymnl.exam.core.networking.entities.Developer
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("developers")
    suspend fun getDevelopers(): List<Developer>

    @DELETE("developer/{id}")
    suspend fun deleteDeveloper(@Path("id") id: Int): Response<Void>

    @POST("add-developer")
    suspend fun addDeveloper(@Body developer: Developer): Response<Void>

    @POST("update-developer")
    suspend fun updateDeveloper(@Body developer: Developer): Response<Void>
}