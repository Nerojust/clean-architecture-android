package com.nerojust.clean_architecture.feature.repodetail

import app.cash.turbine.test
import com.nerojust.clean_architecture.domain.model.Repo
import com.nerojust.clean_architecture.domain.usecase.GetRepoDetailUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RepoDetailViewModelTest {
    private val getRepoDetail = mockk<GetRepoDetailUseCase>()

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `load emits Loading then Success`() =
        runTest {
            val repo = Repo(1, "owner/repo", "desc", "https://x", 5, "Kotlin", "owner", "https://a")
            coEvery { getRepoDetail("owner", "repo") } returns Result.success(repo)
            val viewModel = RepoDetailViewModel(getRepoDetail)

            viewModel.state.test {
                assertEquals(RepoDetailUiState.Loading, awaitItem())
                viewModel.onIntent(RepoDetailIntent.Load("owner", "repo"))
                assertEquals(RepoDetailUiState.Success(repo), awaitItem())
            }
        }

    @Test
    fun `load emits Loading then Error on failure`() =
        runTest {
            coEvery { getRepoDetail("owner", "repo") } returns Result.failure(RuntimeException("boom"))
            val viewModel = RepoDetailViewModel(getRepoDetail)

            viewModel.state.test {
                assertEquals(RepoDetailUiState.Loading, awaitItem())
                viewModel.onIntent(RepoDetailIntent.Load("owner", "repo"))
                assertEquals(RepoDetailUiState.Error("boom"), awaitItem())
            }
        }
}
