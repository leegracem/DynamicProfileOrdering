package com.example.dynamicprofileordering.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dynamicprofileordering.model.Config
import com.example.dynamicprofileordering.model.User
import com.example.dynamicprofileordering.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(val repository: UserRepository): ViewModel() {
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    private val _config = MutableStateFlow(Config(emptyList()))
    val config: StateFlow<Config> = _config

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex

    init {
        viewModelScope.launch {
            fetchUsers()
            fetchConfig()
        }
    }

    suspend fun fetchUsers() {
        try {
            _users.value = repository.getUsers()
        } catch (e: Exception) {
            Log.e("UserViewModel", "Error fetching users", e)
        }
    }

    suspend fun fetchConfig() {
        try {
            _config.value = repository.getConfig()
        } catch (e: Exception) {
            Log.e("UserViewModel", "Error fetching config", e)
        }
    }

    fun nextUser() {
        if (_currentIndex.value < _users.value.size - 1) {
            _currentIndex.value += 1
            _users.value = _users.value
        }
    }
}