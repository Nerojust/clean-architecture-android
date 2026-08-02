package com.nerojust.clean_architecture.feature.repolist

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.nerojust.clean_architecture.domain.model.Repo
import org.junit.Rule
import org.junit.Test

class RepoListContentTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun loadingStateShowsProgressIndicator() {
        composeRule.setContent {
            RepoListContent(state = RepoListUiState.Loading, onIntent = {}, onRepoClick = { _, _ -> })
        }
        composeRule.onNodeWithTag("repo_list_loading").assertIsDisplayed()
    }

    @Test
    fun successStateShowsRepoFullName() {
        val repos = listOf(Repo(1, "octocat/hello", null, "https://x", 5, "Kotlin", "octocat", "https://a"))
        composeRule.setContent {
            RepoListContent(state = RepoListUiState.Success(repos), onIntent = {}, onRepoClick = { _, _ -> })
        }
        composeRule.onNodeWithText("octocat/hello").assertIsDisplayed()
    }
}
