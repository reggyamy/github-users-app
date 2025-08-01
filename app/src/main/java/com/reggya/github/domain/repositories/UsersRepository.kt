package com.reggya.github.domain.repositories

import androidx.paging.PagingData
import com.reggya.github.domain.model.GitHubUser
import com.reggya.github.domain.model.GitHubUserDetail
import com.reggya.github.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
	fun searchUsers(query: String): Flow<PagingData<GitHubUser>>
	fun getUserDetail(username: String): Flow<Resource<GitHubUserDetail>>
	suspend fun insertFavoriteUser(user: GitHubUserDetail)
	fun getFavoriteUsers(): Flow<List<GitHubUserDetail>>
	fun getFavoriteUserById(id: Int): Flow<GitHubUserDetail?>
	suspend fun deleteFavoriteUser(user: GitHubUserDetail)
}