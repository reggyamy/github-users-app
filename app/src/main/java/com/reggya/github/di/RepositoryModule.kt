package com.reggya.github.di

import com.reggya.github.data.local.UsersLocalDataSource
import com.reggya.github.data.UsersRepositoryImpl
import com.reggya.github.data.remote.network.ApiService
import com.reggya.github.domain.repositories.UsersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
	
	@Provides
	@Singleton
	fun provideUsersRepository(
		apiService: ApiService,
		usersLocalDataSource: UsersLocalDataSource
	): UsersRepository {
		return UsersRepositoryImpl(apiService, usersLocalDataSource)
	}
	
}