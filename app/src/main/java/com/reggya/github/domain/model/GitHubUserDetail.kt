package com.reggya.github.domain.model

class GitHubUserDetail (
	val login: String,
	val id: Int,
	val avatarUrl: String,
	val name: String,
	val company: String,
	val blog: String,
	val location: String,
	val email: String,
	val bio: String,
	val publicRepos: Int,
	val followers: Int,
	val following: Int,
	val twitterUsername: String,
	val createdAt: String,
	val updatedAt: String,
	var isFavorite: Boolean = false
)