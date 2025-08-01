package com.reggya.github.presentation.ui.screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reggya.github.domain.model.GitHubUserDetail
import com.reggya.github.domain.usecase.UsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersFavoriteViewModel @Inject constructor(
		private val useCase: UsersUseCase
) : ViewModel() {
	
	init {
		getFavoriteUsers()
	}
	
	// Local
	private val _favoriteUsers = MutableStateFlow<List<GitHubUserDetail>>(emptyList())
	val favoriteUsers : StateFlow<List<GitHubUserDetail>> = _favoriteUsers
	
	private val _detailFavoriteUser = MutableStateFlow<GitHubUserDetail?>(null)
	val detailFavoriteUser : StateFlow<GitHubUserDetail?> = _detailFavoriteUser
	
	private fun getFavoriteUsers() {
		viewModelScope.launch {
			useCase.getFavoriteUsers().collect {
				_favoriteUsers.value = it
			}
		}
	}
	
	fun insertFavoriteMovie(user: GitHubUserDetail) = viewModelScope.launch {
		useCase.insertFavoriteUser(user)
	}
	
	fun deleteFavoriteMovie(user: GitHubUserDetail) = viewModelScope.launch {
		useCase.deleteFavoriteUser(user)
	}
	
	fun getFavoriteUserById(id: Int) {
		viewModelScope.launch {
			useCase.getFavoriteUserById(id).collect {
				_detailFavoriteUser.value = it
			}
		}
	}

}