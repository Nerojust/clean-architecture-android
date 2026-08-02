package com.nerojust.clean_architecture.di

import com.nerojust.clean_architecture.core.common.DefaultDispatcherProvider
import com.nerojust.clean_architecture.core.common.DispatcherProvider
import com.nerojust.clean_architecture.data.GitHubRepoRepositoryImpl
import com.nerojust.clean_architecture.domain.repository.RepoRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindRepoRepository(impl: GitHubRepoRepositoryImpl): RepoRepository

    companion object {
        @Provides
        @Singleton
        fun provideDispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider()
    }
}
