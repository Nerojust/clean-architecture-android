package com.nerojust.clean_architecture.feature.repodetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.nerojust.clean_architecture.core.ui.component.ErrorMessage
import com.nerojust.clean_architecture.core.ui.component.LoadingIndicator

@Composable
fun RepoDetailScreen(
    owner: String,
    name: String,
    viewModel: RepoDetailViewModel = hiltViewModel(),
) {
    LaunchedEffect(owner, name) {
        viewModel.onIntent(RepoDetailIntent.Load(owner, name))
    }
    val state by viewModel.state.collectAsState()
    RepoDetailContent(
        state = state,
        onRetry = { viewModel.onIntent(RepoDetailIntent.Load(owner, name)) },
    )
}

@Composable
fun RepoDetailContent(
    state: RepoDetailUiState,
    onRetry: () -> Unit,
) {
    when (state) {
        is RepoDetailUiState.Loading -> LoadingIndicator()
        is RepoDetailUiState.Error -> ErrorMessage(message = state.message, onRetry = onRetry)
        is RepoDetailUiState.Success -> {
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                AsyncImage(
                    model = state.repo.ownerAvatarUrl,
                    contentDescription = state.repo.ownerLogin,
                    modifier = Modifier.size(48.dp),
                )
                Text(state.repo.fullName)
                Text(state.repo.description ?: "")
                Text("★ ${state.repo.stars}")
                Text(state.repo.language ?: "")
            }
        }
    }
}
