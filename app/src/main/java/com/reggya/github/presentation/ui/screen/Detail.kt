package com.reggya.github.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.People
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.reggya.github.domain.model.GitHubUserDetail
import com.reggya.github.domain.model.Resource
import com.reggya.github.presentation.ui.screen.viewmodel.UsersFavoriteViewModel
import com.reggya.github.presentation.ui.screen.viewmodel.UsersSearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
	user: GitHubUserDetail? = null,
	username: String
) {
	val navController = rememberNavController()
	val usersSearchViewModel: UsersSearchViewModel = hiltViewModel()
	val usersFavoriteViewModel: UsersFavoriteViewModel = hiltViewModel()
	var isFavorite by remember {  mutableStateOf(false) }
	val userDetailResource by usersSearchViewModel.userDetail.collectAsStateWithLifecycle()
	var userDetail by remember { mutableStateOf(user) }
	
	LaunchedEffect(username){
		if (userDetail != null) return@LaunchedEffect
		usersSearchViewModel.getUserDetail(username)
	}
	
	LaunchedEffect(userDetailResource) {
		if (userDetailResource is Resource.Success) {
			userDetail = (userDetailResource as Resource.Success<GitHubUserDetail>).data
		}
	}
	
	LaunchedEffect(userDetail) {
		usersFavoriteViewModel.getFavoriteUserById(userDetail?.id ?: 0)
		isFavorite = usersFavoriteViewModel.detailFavoriteUser.value != null
	}
	
	Scaffold(
		topBar = {
			TopAppBar(
				title = { Text("Detail User") },
				navigationIcon = {
					IconButton(onClick = {navController.popBackStack()}) {
						Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
					}
				},
				actions = {
					IconButton(
						onClick = {
							if (userDetail == null) return@IconButton
							if (isFavorite) {
								usersFavoriteViewModel.deleteFavoriteMovie(userDetail!!)
							} else {
								usersFavoriteViewModel.insertFavoriteMovie(userDetail!!)
							}
							isFavorite = !isFavorite
						}
					) {
						Icon(
							imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
							contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites"
						)
					}
				}
			)
		}
	) { innerPadding ->
		if (userDetail == null) return@Scaffold
		Column(
			modifier = Modifier
				.padding(innerPadding)
				.padding(16.dp)
				.fillMaxSize(),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			// Avatar
			AsyncImage(
				model = userDetail?.avatarUrl,
				contentDescription = null,
				modifier = Modifier
					.size(100.dp)
					.clip(CircleShape)
					.background(Color.Gray),
				contentScale = ContentScale.Crop
			)
			
			Spacer(modifier = Modifier.height(16.dp))
			
			// Name
			Text(text = userDetail?.name!!, style = MaterialTheme.typography.titleLarge)
			
			// Username
			Surface(
				color = MaterialTheme.colorScheme.surfaceVariant,
				shape = MaterialTheme.shapes.small,
				modifier = Modifier.padding(top = 8.dp)
			) {
				Text(
					text = userDetail?.login!!,
					modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
					style = MaterialTheme.typography.bodyMedium
				)
			}
			
			Spacer(modifier = Modifier.height(16.dp))
			
			// Followers and Following
			Row(
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.Center
			) {
				Icon(Icons.Outlined.People, contentDescription = null)
				Spacer(modifier = Modifier.width(4.dp))
				Text("${userDetail?.followers} followers")
				Text(" • ")
				Text("${userDetail?.following} following")
			}
			
			Spacer(modifier = Modifier.height(24.dp))
			
			// Additional Info
			Column(
				modifier = Modifier
					.fillMaxWidth()
					.padding(horizontal = 8.dp),
				horizontalAlignment = Alignment.Start
			) {
				InfoText("Company", userDetail?.company)
				InfoText("Location", userDetail?.location)
				InfoText("Blog", userDetail?.blog)
				InfoText("Email", userDetail?.email)
				InfoText("Twitter", userDetail?.twitterUsername)
				InfoText("Bio", userDetail?.bio)
			}
		}
	}
}

@Composable
fun InfoText(label: String, value: String?) {
	if (!value.isNullOrEmpty()) {
		Text(
			text = "$label: $value",
			style = MaterialTheme.typography.bodySmall,
			modifier = Modifier.padding(vertical = 4.dp)
		)
	}
}