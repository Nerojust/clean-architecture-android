package com.nerojust.clean_architecture.domain.repository

import com.nerojust.clean_architecture.domain.model.Repo

interface RepoRepository {
    suspend fun searchRepositories(query: String): Result<List<Repo>>

    suspend fun getRepository(
        owner: String,
        name: String,
    ): Result<Repo>
}
