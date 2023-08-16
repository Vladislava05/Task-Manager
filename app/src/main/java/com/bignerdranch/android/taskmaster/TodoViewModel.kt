package com.bignerdranch.android.taskmaster

import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDeepLinkRequest
import androidx.room.Query
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class TodoViewModel(private val repository: TaskRepository): ViewModel() {
    private val _navDeepLinkRequest = MutableSharedFlow<NavDeepLinkRequest>()
    val navDeepLinkRequest: SharedFlow<NavDeepLinkRequest> = _navDeepLinkRequest
    fun insert(todo: Todo) = viewModelScope.launch {
        repository.insert(todo)
    }

    fun update(todo: Todo) = viewModelScope.launch{
        repository.update(todo)
    }

    fun delete(todo: Todo) = viewModelScope.launch{
        repository.delete(todo)
    }

    fun onTaskClickListener(todo: Todo) {
        val request = NavDeepLinkRequest.Builder.fromUri(
            (
                    "${TaskListFragment.TASK_DETAIL_URI}?${Constants.TASK_BUNDLE_KEY}=${todo.title}" +
                            "&${Constants.TASK_DATE_BUNDLE_KEY}=${todo.date}"
                    )
                .toUri()
        ).build()
        viewModelScope.launch {
            _navDeepLinkRequest.emit(request)
        }
    }

}
class TodoViewModelFactory(private val repository: TaskRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TodoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}