package com.nerojust.clean_architecture.domain.usecase

import com.nerojust.clean_architecture.domain.model.Repo
import com.nerojust.clean_architecture.domain.repository.RepoRepository
import javax.inject.Inject

class SearchReposUseCase
    @Inject
    constructor(
        private val repository: RepoRepository,
    ) {
        suspend operator fun invoke(query: String): Result<List<Repo>> = repository.searchRepositories(query)
    }
