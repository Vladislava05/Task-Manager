package com.bignerdranch.android.taskmaster

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository): ViewModel() {
    fun getAllUsers(): LiveData<List<User>> {
        return userRepository.allUsers
    }
    fun loadSingle(email: String, password: String, callback: (User?) -> Unit) = viewModelScope.launch{
        userRepository.loadSingle(email, password, callback)
    }
    fun insert(user: User) = viewModelScope.launch {
        userRepository.insert(user)
    }

    fun update(user: User) = viewModelScope.launch{
        userRepository.update(user)
    }

    fun delete(user: User) = viewModelScope.launch{
        userRepository.delete(user)
    }

}
class UserViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}