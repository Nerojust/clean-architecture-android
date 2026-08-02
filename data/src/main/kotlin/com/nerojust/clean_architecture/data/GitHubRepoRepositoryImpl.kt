package com.nerojust.clean_architecture.data

import com.nerojust.clean_architecture.core.network.GitHubApiService
import com.nerojust.clean_architecture.data.mapper.toDomain
import com.nerojust.clean_architecture.domain.model.Repo
import com.nerojust.clean_architecture.domain.repository.RepoRepository
import javax.inject.Inject

class GitHubRepoRepositoryImpl
    @Inject
    constructor(
        private val api: GitHubApiService,
    ) : RepoRepository {
        override suspend fun searchRepositories(query: String): Result<List<Repo>> =
            runCatching { api.searchRepositories(query).items.map { it.toDomain() } }

        override suspend fun getRepository(
            owner: String,
            name: String,
        ): Result<Repo> = runCatching { api.getRepository(owner, name).toDomain() }
    }
