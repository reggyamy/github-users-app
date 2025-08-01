package com.reggya.github.presentation.ui.screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.reggya.github.domain.model.GitHubUser
import com.reggya.github.domain.model.GitHubUserDetail
import com.reggya.github.domain.model.Resource
import com.reggya.github.domain.usecase.UsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersSearchViewModel @Inject constructor(
	private val useCase: UsersUseCase
) : ViewModel() {
	
	private val _users = MutableStateFlow<PagingData<GitHubUser>>(PagingData.empty())
	val users : StateFlow<PagingData<GitHubUser>> = _users.asStateFlow()
	private val _userDetail = MutableStateFlow<Resource<GitHubUserDetail>>(Resource.Loading(null))
	val userDetail : StateFlow<Resource<GitHubUserDetail>> = _userDetail.asStateFlow()
	
	fun searchUsers(query: String) {
		viewModelScope.launch {
			useCase.searchUsers(query).cachedIn(viewModelScope)
				.collect {
					_users.value = it
				}
		}
	}
	
	fun getUserDetail(username: String) {
		viewModelScope.launch {
			useCase.getUserDetail(username).collect {
				_userDetail.value = it
			}
		}
	}
	
}