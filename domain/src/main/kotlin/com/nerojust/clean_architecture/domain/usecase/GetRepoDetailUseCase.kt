package com.nerojust.clean_architecture.domain.usecase

import com.nerojust.clean_architecture.domain.model.Repo
import com.nerojust.clean_architecture.domain.repository.RepoRepository
import javax.inject.Inject

class GetRepoDetailUseCase
    @Inject
    constructor(
        private val repository: RepoRepository,
    ) {
        suspend operator fun invoke(
            owner: String,
            name: String,
        ): Result<Repo> = repository.getRepository(owner, name)
    }
