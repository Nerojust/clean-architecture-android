package com.nerojust.clean_architecture.feature.repolist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nerojust.clean_architecture.core.ui.component.ErrorMessage
import com.nerojust.clean_architecture.core.ui.component.LoadingIndicator
import com.nerojust.clean_architecture.domain.model.Repo

@Composable
fun RepoListScreen(
    onRepoClick: (owner: String, name: String) -> Unit,
    viewModel: RepoListViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    RepoListContent(state = state, onIntent = viewModel::onIntent, onRepoClick = onRepoClick)
}

@Composable
fun RepoListContent(
    state: RepoListUiState,
    onIntent: (RepoListIntent) -> Unit,
    onRepoClick: (owner: String, name: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        var query by remember { mutableStateOf("") }
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Search GitHub repos") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onIntent(RepoListIntent.Search(query)) }),
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
        )

        when (state) {
            is RepoListUiState.Idle -> Unit
            is RepoListUiState.Loading -> LoadingIndicator(modifier = Modifier.testTag("repo_list_loading"))
            is RepoListUiState.Error ->
                ErrorMessage(
                    message = state.message,
                    onRetry = { onIntent(RepoListIntent.Search(query)) },
                )
            is RepoListUiState.Success -> RepoList(state.repos, onRepoClick)
        }
    }
}

@Composable
private fun RepoList(
    repos: List<Repo>,
    onRepoClick: (String, String) -> Unit,
) {
    LazyColumn {
        items(repos) { repo ->
            ListItem(
                headlineContent = { Text(repo.fullName) },
                supportingContent = { Text(repo.description ?: "") },
                modifier =
                    Modifier.clickable {
                        onRepoClick(repo.ownerLogin, repo.fullName.substringAfter('/'))
                    },
            )
        }
    }
}
