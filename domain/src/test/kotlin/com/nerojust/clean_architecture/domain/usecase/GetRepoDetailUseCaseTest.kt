package com.nerojust.clean_architecture.domain.usecase

import com.nerojust.clean_architecture.domain.model.Repo
import com.nerojust.clean_architecture.domain.repository.RepoRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetRepoDetailUseCaseTest {
    private val repository = mockk<RepoRepository>()
    private val useCase = GetRepoDetailUseCase(repository)

    @Test
    fun `returns repo detail from repository on success`() =
        runTest {
            val repo = Repo(1, "owner/repo", "desc", "https://x", 10, "Kotlin", "owner", "https://avatar")
            coEvery { repository.getRepository("owner", "repo") } returns Result.success(repo)

            val result = useCase("owner", "repo")

            assertEquals(Result.success(repo), result)
        }
}
