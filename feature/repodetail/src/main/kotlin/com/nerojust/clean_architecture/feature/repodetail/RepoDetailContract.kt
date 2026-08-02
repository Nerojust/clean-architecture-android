package com.nerojust.clean_architecture.feature.repodetail

import com.nerojust.clean_architecture.domain.model.Repo

sealed interface RepoDetailIntent {
    data class Load(val owner: String, val name: String) : RepoDetailIntent
}

sealed interface RepoDetailUiState {
    data object Loading : RepoDetailUiState

    data class Success(val repo: Repo) : RepoDetailUiState

    data class Error(val message: String) : RepoDetailUiState
}
