package com.nerojust.clean_architecture.feature.repodetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nerojust.clean_architecture.domain.usecase.GetRepoDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoDetailViewModel
    @Inject
    constructor(
        private val getRepoDetail: GetRepoDetailUseCase,
    ) : ViewModel() {
        private val _state = MutableStateFlow<RepoDetailUiState>(RepoDetailUiState.Loading)
        val state: StateFlow<RepoDetailUiState> = _state.asStateFlow()

        fun onIntent(intent: RepoDetailIntent) {
            when (intent) {
                is RepoDetailIntent.Load -> load(intent.owner, intent.name)
            }
        }

        private fun load(
            owner: String,
            name: String,
        ) {
            viewModelScope.launch {
                _state.value = RepoDetailUiState.Loading
                val result = getRepoDetail(owner, name)
                _state.value =
                    result.fold(
                        onSuccess = { RepoDetailUiState.Success(it) },
                        onFailure = { RepoDetailUiState.Error(it.message ?: "Unknown error") },
                    )
            }
        }
    }
