package com.beautymnl.exam.core.networking.services

import com.beautymnl.exam.core.networking.entities.Developer
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("developers")
    suspend fun getDevelopers(): List<Developer>

    @GET("repos/{owner}/{repo}/contributors?per_page=100")
    fun getRepositoryContributors(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): List<Any>
}