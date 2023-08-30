package com.bignerdranch.android.taskmaster

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDAO) {
    val allUsers: LiveData<List<User>> = userDao.getAllUsers()
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(user: User) {
        userDao.insert(user)
    }

    fun loadSingle(email: String, password: String, callback: (User?) -> Unit) {
        userDao.loadSingle(email, password).observeForever { user ->
            callback(user)
        }
    }


    suspend fun update(user: User){
        userDao.update(user)
    }

    suspend fun delete(user: User){
        userDao.delete(user)
    }

}