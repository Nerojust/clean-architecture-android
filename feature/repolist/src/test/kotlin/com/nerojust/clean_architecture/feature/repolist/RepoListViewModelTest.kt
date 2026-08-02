package com.nerojust.clean_architecture.feature.repolist

import app.cash.turbine.test
import com.nerojust.clean_architecture.domain.model.Repo
import com.nerojust.clean_architecture.domain.usecase.SearchReposUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.yield
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RepoListViewModelTest {
    private val searchRepos = mockk<SearchReposUseCase>()
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `search emits Loading then Success`() =
        runTest(testDispatcher) {
            val repos = listOf(Repo(1, "owner/repo", null, "https://x", 1, "Kotlin", "owner", "https://a"))
            // yield() forces a real suspension point, like a real network call would; without it,
            // the mock resolves synchronously and the StateFlow's Loading value is conflated away
            // before the collector gets a chance to observe it.
            coEvery { searchRepos("kotlin") } coAnswers {
                yield()
                Result.success(repos)
            }
            val viewModel = RepoListViewModel(searchRepos)

            viewModel.state.test {
                assertEquals(RepoListUiState.Idle, awaitItem())
                viewModel.onIntent(RepoListIntent.Search("kotlin"))
                assertEquals(RepoListUiState.Loading, awaitItem())
                assertEquals(RepoListUiState.Success(repos), awaitItem())
            }
        }

    @Test
    fun `search emits Loading then Error on failure`() =
        runTest(testDispatcher) {
            coEvery { searchRepos("kotlin") } coAnswers {
                yield()
                Result.failure(RuntimeException("boom"))
            }
            val viewModel = RepoListViewModel(searchRepos)

            viewModel.state.test {
                assertEquals(RepoListUiState.Idle, awaitItem())
                viewModel.onIntent(RepoListIntent.Search("kotlin"))
                assertEquals(RepoListUiState.Loading, awaitItem())
                assertEquals(RepoListUiState.Error("boom"), awaitItem())
            }
        }
}
