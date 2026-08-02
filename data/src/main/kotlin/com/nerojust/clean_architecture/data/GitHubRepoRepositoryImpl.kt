package com.nerojust.clean_architecture.data

import com.nerojust.clean_architecture.core.common.DispatcherProvider
import com.nerojust.clean_architecture.core.network.GitHubApiService
import com.nerojust.clean_architecture.data.mapper.toDomain
import com.nerojust.clean_architecture.domain.model.Repo
import com.nerojust.clean_architecture.domain.repository.RepoRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GitHubRepoRepositoryImpl
    @Inject
    constructor(
        private val api: GitHubApiService,
        private val dispatcherProvider: DispatcherProvider,
    ) : RepoRepository {
        override suspend fun searchRepositories(query: String): Result<List<Repo>> =
            withContext(dispatcherProvider.io) {
                runCatching { api.searchRepositories(query).items.map { it.toDomain() } }
                    .onFailure { if (it is CancellationException) throw it }
            }

        override suspend fun getRepository(
            owner: String,
            name: String,
        ): Result<Repo> =
            withContext(dispatcherProvider.io) {
                runCatching { api.getRepository(owner, name).toDomain() }
                    .onFailure { if (it is CancellationException) throw it }
            }
    }
