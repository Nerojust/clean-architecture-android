package com.nerojust.clean_architecture.di

import com.nerojust.clean_architecture.data.GitHubRepoRepositoryImpl
import com.nerojust.clean_architecture.domain.repository.RepoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindRepoRepository(impl: GitHubRepoRepositoryImpl): RepoRepository
}
