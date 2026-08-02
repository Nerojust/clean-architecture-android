package com.nerojust.clean_architecture.domain.usecase

import com.nerojust.clean_architecture.domain.model.Repo
import com.nerojust.clean_architecture.domain.repository.RepoRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class SearchReposUseCaseTest {
    private val repository = mockk<RepoRepository>()
    private val useCase = SearchReposUseCase(repository)

    @Test
    fun `returns repos from repository on success`() =
        runTest {
            val repos =
                listOf(
                    Repo(1, "owner/repo", "desc", "https://x", 10, "Kotlin", "owner", "https://avatar"),
                )
            coEvery { repository.searchRepositories("kotlin") } returns Result.success(repos)

            val result = useCase("kotlin")

            assertEquals(Result.success(repos), result)
        }

    @Test
    fun `propagates failure from repository`() =
        runTest {
            val exception = RuntimeException("network error")
            coEvery { repository.searchRepositories("kotlin") } returns Result.failure(exception)

            val result = useCase("kotlin")

            assertEquals(true, result.isFailure)
        }
}
