package com.nerojust.clean_architecture.feature.repolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nerojust.clean_architecture.domain.usecase.SearchReposUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoListViewModel
    @Inject
    constructor(
        private val searchRepos: SearchReposUseCase,
    ) : ViewModel() {
        private val _state = MutableStateFlow<RepoListUiState>(RepoListUiState.Idle)
        val state: StateFlow<RepoListUiState> = _state.asStateFlow()

        fun onIntent(intent: RepoListIntent) {
            when (intent) {
                is RepoListIntent.Search -> search(intent.query)
            }
        }

        private fun search(query: String) {
            viewModelScope.launch {
                _state.value = RepoListUiState.Loading
                val result = searchRepos(query)
                _state.value =
                    result.fold(
                        onSuccess = { RepoListUiState.Success(it) },
                        onFailure = { RepoListUiState.Error(it.message ?: "Unknown error") },
                    )
            }
        }
    }
