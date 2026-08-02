package com.nerojust.clean_architecture.feature.repolist

import com.nerojust.clean_architecture.domain.model.Repo

sealed interface RepoListIntent {
    data class Search(val query: String) : RepoListIntent
}

sealed interface RepoListUiState {
    data object Idle : RepoListUiState

    data object Loading : RepoListUiState

    data class Success(val repos: List<Repo>) : RepoListUiState

    data class Error(val message: String) : RepoListUiState
}
