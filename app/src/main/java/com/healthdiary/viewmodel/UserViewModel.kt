package com.healthdiary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.healthdiary.data.User
import com.healthdiary.repository.UserRepository


class UserViewModel(val repository: UserRepository): ViewModel() {
    fun addUser(user: User): LiveData<Boolean> =
        repository.addUser(user)

    fun updateUser(user: User): LiveData<Boolean> =
        repository.updateUser(user)

    fun deleteUser(email: String): LiveData<Boolean> =
        repository.deleteUser(email)

    fun getUser(email: String): LiveData<User> =
        repository.getUser(email)

    class Provider(private val repository: UserRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
                return UserViewModel(repository) as T
            }

            throw IllegalArgumentException("Invalid viewmodel")
        }
    }
}