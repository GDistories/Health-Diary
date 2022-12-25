package com.healthdiary.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.healthdiary.repository.AuthRepository

class AuthViewModel(val repository: AuthRepository): ViewModel() {
    private lateinit var auth: FirebaseAuth



    fun signUp(email: String, password: String): LiveData<Boolean> =
        repository.signUp(email, password)

    class Provider(private val repository: AuthRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
                return AuthViewModel(repository) as T
            }

            throw IllegalArgumentException("Invalid viewmodel")
        }
    }
}