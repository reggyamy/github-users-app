package com.reggya.github.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.reggya.github.domain.model.GitHubUser
import com.reggya.github.presentation.ui.screen.viewmodel.UsersFavoriteViewModel

@Composable
fun FavoriteScreen () {
	
	val navController = rememberNavController()
	val usersFavoriteViewModel: UsersFavoriteViewModel = hiltViewModel()
	val users = usersFavoriteViewModel.favoriteUsers.collectAsState().value
	
	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(16.dp)
	) {
	
	if (users.isNotEmpty()) {
		LazyColumn(
			modifier = Modifier.fillMaxSize(),
			verticalArrangement = Arrangement.spacedBy(8.dp)
		) {
			items(items = users) { user ->
				Column(
					modifier = Modifier
						.padding(16.dp)
				) {
					UserListItem(
						GitHubUser(
							id = user.id,
							login = user.login,
							avatarUrl = user.avatarUrl
						),
						onClick = {
							navController.navigate("detail/${user.login}/${user.id}")
						}
					)
				}
			}
		}
	} else {
		Text(
			text = "No favorite users",
			style = MaterialTheme.typography.bodyMedium,
			color = MaterialTheme.colorScheme.onSurfaceVariant
		)
	}
	
	}

}