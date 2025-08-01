package com.reggya.github.data.remote.network

import com.reggya.github.data.remote.response.DetailUserResponse
import com.reggya.github.data.remote.response.UsersResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
	
	@GET("search/users")
	suspend fun searchUsers(
		@Query("q") query: String,
		@Query("page") page: Int,
	): UsersResponse
	
	@GET("users/{username}")
	suspend fun getUserDetail(@Path("username") username: String): DetailUserResponse
}