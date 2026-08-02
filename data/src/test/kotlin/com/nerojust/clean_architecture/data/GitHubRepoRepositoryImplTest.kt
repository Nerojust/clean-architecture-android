package com.nerojust.clean_architecture.data

import com.nerojust.clean_architecture.core.common.DispatcherProvider
import com.nerojust.clean_architecture.core.network.GitHubApiService
import com.nerojust.clean_architecture.core.network.dto.OwnerDto
import com.nerojust.clean_architecture.core.network.dto.RepoDto
import com.nerojust.clean_architecture.core.network.dto.RepoSearchResponseDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

private object TestDispatcherProvider : DispatcherProvider {
    override val io: CoroutineDispatcher = Dispatchers.Unconfined
    override val main: CoroutineDispatcher = Dispatchers.Unconfined
    override val default: CoroutineDispatcher = Dispatchers.Unconfined
}

class GitHubRepoRepositoryImplTest {
    private val api = mockk<GitHubApiService>()
    private val repository = GitHubRepoRepositoryImpl(api, TestDispatcherProvider)

    private val dto =
        RepoDto(
            id = 1,
            fullName = "owner/repo",
            description = "desc",
            htmlUrl = "https://x",
            stargazersCount = 10,
            language = "Kotlin",
            owner = OwnerDto(login = "owner", avatarUrl = "https://avatar"),
        )

    @Test
    fun `searchRepositories maps DTOs to domain models`() =
        runTest {
            coEvery { api.searchRepositories("kotlin") } returns RepoSearchResponseDto(items = listOf(dto))

            val result = repository.searchRepositories("kotlin")

            assertEquals("owner/repo", result.getOrThrow().first().fullName)
        }

    @Test
    fun `searchRepositories wraps exceptions as failure`() =
        runTest {
            coEvery { api.searchRepositories("kotlin") } throws RuntimeException("boom")

            val result = repository.searchRepositories("kotlin")

            assertTrue(result.isFailure)
        }

    @Test
    fun `getRepository maps DTO to domain model`() =
        runTest {
            coEvery { api.getRepository("owner", "repo") } returns dto

            val result = repository.getRepository("owner", "repo")

            assertEquals("owner/repo", result.getOrThrow().fullName)
            assertEquals(10, result.getOrThrow().stars)
        }
}
