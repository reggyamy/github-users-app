package com.reggya.github.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponse (
	val id: Int,
	val login: String? = null,
	@SerializedName("avatar_url")
	val avatarUrl: String? = null,
	@SerializedName("html_url")
	val htmlUrl: String,
	val name: String? = null,
	val bio: String? = null,
	@SerializedName("public_repos")
	val publicRepos: Int? = null,
	val followers: Int? = null,
	val following: Int? = null,
	val location: String? = null,
	val company: String? = null,
	val blog: String? = null,
	val email: String? = null,
	@SerializedName("twitter_username")
	val twitterUsername: String? = null,
	@SerializedName("created_at")
	val createdAt: String? = null,
	@SerializedName("updated_at")
	val updatedAt: String? = null
): Parcelable
