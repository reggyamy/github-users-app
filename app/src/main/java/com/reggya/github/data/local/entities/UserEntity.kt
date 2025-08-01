package com.reggya.github.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
	@PrimaryKey val id: Int,
	val login: String,
	val avatarUrl: String? = null,
	val htmlUrl: String? = null,
	val name: String? = null,
	val bio: String? = null,
	val publicRepos: Int? = null,
	val followers: Int? = null,
	val following: Int? = null,
	val location: String? = null,
	val company: String? = null,
	val blog: String? = null,
	val email: String? = null,
	val twitterUsername: String? = null,
	val createdAt: String? = null,
	val updatedAt: String? = null
)