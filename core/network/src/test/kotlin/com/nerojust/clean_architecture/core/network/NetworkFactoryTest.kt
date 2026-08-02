package com.nerojust.clean_architecture.core.network

import org.junit.Assert.assertNotNull
import org.junit.Test

class NetworkFactoryTest {
    @Test
    fun `creates a non-null GitHubApiService`() {
        val service = NetworkFactory.createGitHubApiService()
        assertNotNull(service)
    }
}
