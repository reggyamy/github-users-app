package com.reggya.github.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.reggya.github.presentation.ui.screen.FavoriteScreen
import com.reggya.github.presentation.ui.screen.DetailScreen
import com.reggya.github.presentation.ui.screen.HomeScreen
import com.reggya.github.presentation.ui.screen.viewmodel.UsersFavoriteViewModel


@Composable
fun UsersApp(
) {
	val navController = rememberNavController()
	val usersFavoriteViewModel: UsersFavoriteViewModel = hiltViewModel()
	val navBackStackEntry by navController.currentBackStackEntryAsState()
	val currentDestination = navBackStackEntry?.destination
	
	val showBottomBar = currentDestination?.route?.startsWith("detail/") != true
	
	Scaffold(
		bottomBar = {
			if (showBottomBar) {
				NavigationBar {
					val items = listOf(
						NavigationItem("home", "Home", Icons.Default.Home),
						NavigationItem("favorites", "Favorites", Icons.Default.Favorite)
					)
					items.forEach { item ->
						NavigationBarItem(
							icon = { Icon(item.icon, contentDescription = item.label) },
							label = { Text(item.label) },
							selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
							onClick = {
								navController.navigate(item.route) {
									popUpTo(navController.graph.findStartDestination().id) {
										saveState = true
									}
									launchSingleTop = true
									restoreState = true
								}
							}
						)
					}
				}
			}
		}
	) { innerPadding ->
		NavHost(
			navController = navController,
			startDestination = "home",
			modifier = Modifier
				.fillMaxSize()
				.padding(innerPadding)
		) {
			composable("home") {
				HomeScreen(
					onNavigateToDetail = { username, userId ->
						navController.navigate("detail/$username/$userId")
					}
				)
			}
			composable("favorites") {
				FavoriteScreen(
					onNavigateToDetail = { username, userId ->
						navController.navigate("detail/$username/$userId")
					}
				)
			}
			composable("detail/{username}/{userId}") { backStackEntry ->
				val username = backStackEntry.arguments?.getString("username") ?: ""
				val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull()
				DetailScreen(
					userId = userId,
					username = username,
					onNavigateBack = {navController.popBackStack()}
				)
			}
		}
	}
}

