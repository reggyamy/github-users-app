package com.reggya.github.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.reggya.github.domain.model.GitHubUser
import com.reggya.github.presentation.ui.screen.viewmodel.UsersSearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
	val navController = rememberNavController()
	val usersSearchViewModel: UsersSearchViewModel = hiltViewModel()
	var query by remember { mutableStateOf("") }
	val users = usersSearchViewModel.users.collectAsLazyPagingItems()
	
	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(16.dp)
	) {
		
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.height(IntrinsicSize.Min),
			verticalAlignment = Alignment.CenterVertically
		) {
			OutlinedTextField(
				value = query,
				onValueChange = { query = it },
				modifier = Modifier
					.weight(1f)
					.fillMaxHeight(),
				placeholder = { Text("Search GitHub users") },
				singleLine = true,
				keyboardOptions = KeyboardOptions.Default.copy(
					imeAction = ImeAction.Search
				),
				keyboardActions = KeyboardActions(
					onSearch = {
						if (query.isNotBlank()) {
							usersSearchViewModel.searchUsers(query)
						}
					}
				)
			)
			
			Spacer(modifier = Modifier.width(8.dp))
			
			IconButton(
				onClick = {
					navController.navigate("favorites")
				},
				modifier = Modifier
					.size(56.dp)
					.background(
						color = MaterialTheme.colorScheme.secondaryContainer,
						shape = CircleShape
					)
			) {
				Icon(
					imageVector = Icons.Default.Favorite,
					contentDescription = "Go to Favorites",
					tint = MaterialTheme.colorScheme.onSecondaryContainer
				)
			}
		}
		
		Spacer(modifier = Modifier.height(16.dp))

		if (users.itemCount > 0) {
			LazyColumn(
				modifier = Modifier.fillMaxSize(),
				verticalArrangement = Arrangement.spacedBy(8.dp)
			) {
				items(users.itemCount) { index ->
					users[index]?.let {
						UserListItem(
							user = it,
							onClick = { userId ->
								navController.navigate("detail/${it.login}/${userId}")
							}
						)
					}
				}
				
				when (users.loadState.append) {
					is LoadState.Loading -> {
						item { CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally)) }
					}
					is LoadState.Error -> {
						item { Text("Error loading more data", color = Color.Red) }
					}
					else -> {}
				}
			}
		} else if (users.loadState.refresh is LoadState.NotLoading && query.isNotBlank()) {
			Text(
				text = "No users found for \"$query\"",
				style = MaterialTheme.typography.bodyMedium,
				color = MaterialTheme.colorScheme.onSurfaceVariant
			)
		}
	}
}

@Composable
fun UserListItem(
	user: GitHubUser,
	onClick: (userId: Int) -> Unit
) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.clickable { onClick(user.id) }
			.padding(vertical = 12.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		
		Box(
			modifier = Modifier
				.size(48.dp)
				.clip(CircleShape)
				.background(Color.LightGray)
		) {
			if (user.avatarUrl.isBlank()) {
				Icon(
					imageVector = Icons.Default.Person,
					contentDescription = "Default User Icon",
					modifier = Modifier
						.size(48.dp)
						.clip(CircleShape)
						.background(Color.Gray)
						.padding(8.dp),
					tint = Color.White
				)
			} else {
				AsyncImage(
					model = user.avatarUrl,
					contentDescription = "User Avatar",
					modifier = Modifier
						.size(48.dp)
						.clip(CircleShape),
					contentScale = ContentScale.Crop
				)
			}
			
		}
		
		Spacer(modifier = Modifier.width(16.dp))
		
		Text(
			text = user.login,
			style = MaterialTheme.typography.titleMedium,
			fontWeight = FontWeight.Medium
		)
	}
}