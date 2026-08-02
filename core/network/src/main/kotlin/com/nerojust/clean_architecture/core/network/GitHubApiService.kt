package com.nerojust.clean_architecture.core.network

import com.nerojust.clean_architecture.core.network.dto.RepoDto
import com.nerojust.clean_architecture.core.network.dto.RepoSearchResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApiService {
    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") query: String,
    ): RepoSearchResponseDto

    @GET("repos/{owner}/{name}")
    suspend fun getRepository(
        @Path("owner") owner: String,
        @Path("name") name: String,
    ): RepoDto
}
