package com.nerojust.clean_architecture.di

import com.nerojust.clean_architecture.core.network.GitHubApiService
import com.nerojust.clean_architecture.core.network.NetworkFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideGitHubApiService(): GitHubApiService = NetworkFactory.createGitHubApiService()
}
