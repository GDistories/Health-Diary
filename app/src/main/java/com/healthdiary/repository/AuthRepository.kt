package com.healthdiary.repository

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

class AuthRepository {
    private lateinit var auth: FirebaseAuth


    fun signUp(email: String, password: String): MutableLiveData<Boolean> {
        val status: MutableLiveData<Boolean> = MutableLiveData()
        auth = FirebaseAuth.getInstance()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                OnCompleteListener { task ->
                    status.value = task.isSuccessful
                })
        return status
    }

    companion object {
        val repository = AuthRepository()
    }
}