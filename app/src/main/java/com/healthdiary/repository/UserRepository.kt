package com.healthdiary.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.healthdiary.data.User

class UserRepository {
    private val db = Firebase.firestore

    fun addUser(user: User) : MutableLiveData<Boolean> {
        val status: MutableLiveData<Boolean> = MutableLiveData()
        db.collection("User").document(user.email.toString()).set(user)
            .addOnSuccessListener {
                status.value = true
            }
            .addOnFailureListener {
                status.value = false
            }
        return status
    }

    fun updateUser(user: User) : MutableLiveData<Boolean> {
        val status: MutableLiveData<Boolean> = MutableLiveData()
        db.collection("User").document(user.email.toString()).set(user)
            .addOnSuccessListener {
                status.value = true
            }
            .addOnFailureListener {
                status.value = false
            }
        return status
    }

    fun deleteUser(email: String) : MutableLiveData<Boolean> {
        val status: MutableLiveData<Boolean> = MutableLiveData()
        db.collection("User").document(email).delete()
            .addOnSuccessListener {
                status.value = true
            }
            .addOnFailureListener {
                status.value = false
            }
        return status
    }

    fun getUser(email: String) : MutableLiveData<User> {
        val userLiveData: MutableLiveData<User> = MutableLiveData()
        db.collection("User").document(email).get()
            .addOnSuccessListener { result ->
                val user = User(
                    result.data?.get("name").toString(),
                    result.id,
                    result.data?.get("phone").toString(),
                    result.data?.get("birthday").toString(),
                    result.data?.get("gender").toString(),
                    result.data?.get("height") as Number?,
                    result.data?.get("weight") as Number?
                )
                userLiveData.value = user
                LogUtils.e(user.email)
            }
            .addOnFailureListener {
                userLiveData.value = null
            }
        return userLiveData
    }

    companion object {
        val repository = UserRepository()
    }
}