package com.reggya.github.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.reggya.github.data.local.UsersLocalDataSource
import com.reggya.github.data.remote.pagingdatasource.UsersPagingSource
import com.reggya.github.data.remote.network.ApiService
import com.reggya.github.domain.repositories.UsersRepository
import com.reggya.github.domain.model.GitHubUser
import com.reggya.github.domain.model.GitHubUserDetail
import com.reggya.github.domain.model.Resource
import com.reggya.github.domain.mapper.toDomain
import com.reggya.github.domain.mapper.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersRepositoryImpl @Inject constructor(
	private val apiService: ApiService,
	private val localDataSource: UsersLocalDataSource
) : UsersRepository {
	override fun searchUsers(query: String): Flow<PagingData<GitHubUser>> {
		return Pager(
			config = PagingConfig(
				pageSize = 30,
				prefetchDistance = 1,
				initialLoadSize = 30,
				enablePlaceholders = false
			),
			pagingSourceFactory = {
				UsersPagingSource(apiService, query)
			}
		).flow
	}
	
	override fun getUserDetail(username: String): Flow<Resource<GitHubUserDetail>> {
		return flow {
			try {
				Resource.Loading(null)
				val response = apiService.getUserDetail(username)
				if (response.id > 0) {
					emit(Resource.Success(response.toDomain()))
				}
			}catch (e: Exception){
				emit(Resource.Error(e.message.toString()))
			}
		}.flowOn(Dispatchers.IO)
	}
	
	override suspend fun insertFavoriteUser(user: GitHubUserDetail) {
		localDataSource.insertUser(user.toEntity())
	}
	
	override fun getFavoriteUsers(): Flow<List<GitHubUserDetail>> {
		return flow {
			localDataSource.getUsers().collect { entities ->
				emit(entities.toDomain())
			}
		}
	}
	
	override fun getFavoriteUserById(id: Int): Flow<GitHubUserDetail?> {
		return flow{
			localDataSource.getUserById(id).collect { entity ->
				if (entity != null) emit(entity.toDomain())
				else emit(null)
			}
		}
	}
	
	override suspend fun deleteFavoriteUser(user: GitHubUserDetail) {
		localDataSource.deleteUser(user.toEntity())
	}
}
