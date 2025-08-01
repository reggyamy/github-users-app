package com.reggya.github.domain.mapper

import com.reggya.github.data.remote.response.DetailUserResponse
import com.reggya.github.domain.model.GitHubUser
import com.reggya.github.domain.model.GitHubUserDetail
import com.reggya.github.data.local.entities.UserEntity
import com.reggya.github.data.remote.response.UserResponse
import com.reggya.github.data.remote.response.UsersResponse

fun UserResponse.toDomain(): GitHubUser {
	return GitHubUser(
		id = id,
		login = login ?: "",
		avatarUrl = avatarUrl ?: ""
	)
}

fun UsersResponse.toDomain(): List<GitHubUser> {
	val items = ArrayList<GitHubUser>()
	for (item in this.items){
		val user = item.toDomain()
		items.add(user)
	}
	return items
}


fun DetailUserResponse.toDomain(): GitHubUserDetail {
	return  GitHubUserDetail(
		login = login,
		id = id,
		avatarUrl = avatarUrl ?: "",
		name = name ?: "",
		company = company ?: "",
		blog = blog ?: "",
		location = location ?: "",
		email = email ?: "",
		bio = bio ?: "",
		publicRepos = publicRepos,
		followers = followers,
		following = following,
		twitterUsername = twitterUsername ?: "",
		createdAt = createdAt ?: "",
		updatedAt = updatedAt ?: ""
	)
}

fun GitHubUserDetail.toEntity(): UserEntity {
	return UserEntity(
		id = id,
		login = login,
		avatarUrl = avatarUrl,
		name = name,
		company = company,
		blog = blog,
		location = location,
		email = email,
		bio = bio,
		publicRepos = publicRepos,
		followers = followers,
		following = following,
		twitterUsername = twitterUsername,
		createdAt = createdAt,
		updatedAt = updatedAt
	)
}

fun List<UserEntity>.toDomain(): List<GitHubUserDetail> {
	val users = ArrayList<GitHubUserDetail>()
	for (user in this){
		users.add(user.toDomain())
	}
	return users
}

fun UserEntity.toDomain(): GitHubUserDetail {
	return GitHubUserDetail(
		id = id,
		login = login,
		avatarUrl = avatarUrl ?: "",
		name = name ?: "",
		company = company ?: "",
		blog = blog ?: "",
		location = location ?: "",
		email = email ?: "",
		bio = bio ?: "",
		publicRepos = publicRepos ?: 0,
		followers = followers ?: 0,
		following = following ?: 0,
		twitterUsername = twitterUsername ?: "",
		createdAt = createdAt ?: "",
		updatedAt = updatedAt ?: ""
	)
}
