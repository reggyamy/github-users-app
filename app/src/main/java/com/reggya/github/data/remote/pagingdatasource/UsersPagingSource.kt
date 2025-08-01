package com.reggya.github.data.remote.pagingdatasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.reggya.github.data.remote.network.ApiService
import com.reggya.github.domain.model.GitHubUser
import com.reggya.github.domain.mapper.toDomain
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersPagingSource  @Inject constructor(
	private val apiService: ApiService,
	private val query: String
) : PagingSource<Int, GitHubUser>() {
	
	override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GitHubUser> {
		val pageIndex = params.key ?: 1
		return try {
			val client = apiService.searchUsers( query, pageIndex)
			val response = client.toDomain()
			LoadResult.Page(
				data = response,
				prevKey = null,
				nextKey = if (response.isEmpty()) null else pageIndex + 1
			)
		} catch (exception: IOException) {
			return LoadResult.Error(exception)
		} catch (exception: HttpException) {
			return LoadResult.Error(exception)
		}
	}
	
	override fun getRefreshKey(state: PagingState<Int, GitHubUser>): Int? {
		return state.anchorPosition?.let { anchorPosition ->
			state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
				?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
		}
	}
}