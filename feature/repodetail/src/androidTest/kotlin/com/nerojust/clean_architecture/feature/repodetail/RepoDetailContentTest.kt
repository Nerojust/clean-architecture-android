package com.nerojust.clean_architecture.feature.repodetail

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.nerojust.clean_architecture.domain.model.Repo
import org.junit.Rule
import org.junit.Test

class RepoDetailContentTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun successStateShowsRepoDescription() {
        val repo = Repo(1, "octocat/hello", "A greeting repo", "https://x", 5, "Kotlin", "octocat", "https://a")
        composeRule.setContent {
            RepoDetailContent(state = RepoDetailUiState.Success(repo))
        }
        composeRule.onNodeWithText("A greeting repo").assertIsDisplayed()
    }

    @Test
    fun errorStateShowsMessage() {
        composeRule.setContent {
            RepoDetailContent(state = RepoDetailUiState.Error("Not found"))
        }
        composeRule.onNodeWithText("Not found").assertIsDisplayed()
    }
}
