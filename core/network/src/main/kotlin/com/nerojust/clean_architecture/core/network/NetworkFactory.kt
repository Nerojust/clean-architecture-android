package com.nerojust.clean_architecture.core.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

object NetworkFactory {
    private const val BASE_URL = "https://api.github.com/"

    private val json = Json { ignoreUnknownKeys = true }

    fun createOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    fun createRetrofit(client: OkHttpClient = createOkHttpClient()): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType()),
            )
            .build()

    fun createGitHubApiService(retrofit: Retrofit = createRetrofit()): GitHubApiService =
        retrofit.create(GitHubApiService::class.java)
}
