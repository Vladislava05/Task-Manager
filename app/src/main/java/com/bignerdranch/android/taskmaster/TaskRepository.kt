package com.bignerdranch.android.taskmaster

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class TaskRepository(private val todoDao: TodoDao) {
    val allTasks: LiveData<List<Todo>> = todoDao.getAllTasks()
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(todo: Todo) {
        todoDao.insert(todo)
    }

    suspend fun update(todo: Todo){
        todoDao.update(todo)
    }

    suspend fun delete(todo: Todo){
        todoDao.delete(todo)
    }
}