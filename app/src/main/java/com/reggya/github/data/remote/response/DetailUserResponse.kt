package com.reggya.github.data.remote.response

import com.google.gson.annotations.SerializedName

data class DetailUserResponse(
	val login: String,
	val id: Int,
	@SerializedName("avatar_url")
	val avatarUrl: String? = null,
	val name: String? = null,
	val company: String? = null,
	val blog: String? = null,
	val location: String? = null,
	val email: String? = null,
	val bio: String? = null,
	@SerializedName("public_repos")
	val publicRepos: Int = 0,
	val followers: Int = 0,
	val following: Int = 0,
	@SerializedName("twitter_username")
	val twitterUsername: String? = null,
	@SerializedName("created_at")
	val createdAt: String? = null,
	@SerializedName("updated_at")
	val updatedAt: String? = null
)