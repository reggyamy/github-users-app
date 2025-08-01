package com.reggya.github.domain.usecase

import androidx.paging.PagingData
import com.reggya.github.domain.repositories.UsersRepository
import com.reggya.github.domain.model.GitHubUser
import com.reggya.github.domain.model.GitHubUserDetail
import com.reggya.github.domain.model.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersUseCase @Inject constructor(
	private val repository: UsersRepository
) : UsersRepository {
	
	override fun searchUsers(query: String): Flow<PagingData<GitHubUser>> {
		return repository.searchUsers(query)
	}
	
	override fun getUserDetail(username: String): Flow<Resource<GitHubUserDetail>> {
		return  repository.getUserDetail(username)
	}
	
	override suspend fun insertFavoriteUser(user: GitHubUserDetail) {
		repository.insertFavoriteUser(user)
	}
	
	override fun getFavoriteUsers(): Flow<List<GitHubUserDetail>> {
		return repository.getFavoriteUsers()
	}
	
	override fun getFavoriteUserById(id: Int): Flow<GitHubUserDetail?> {
		return repository.getFavoriteUserById(id)
	}
	
	override suspend fun deleteFavoriteUser(user: GitHubUserDetail) {
		repository.deleteFavoriteUser(user)
	}
}