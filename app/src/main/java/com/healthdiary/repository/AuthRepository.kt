package com.healthdiary.repository

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class AuthRepository {
    private lateinit var auth: FirebaseAuth

    fun forgotPassword(email: String): MutableLiveData<Boolean> {
        //obtain an instance of this class by calling getInstance().
        val status: MutableLiveData<Boolean> = MutableLiveData()
        auth = FirebaseAuth.getInstance()

        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener( // This interface is used as a method of being notified when an operation has been acknowledged by the Database servers and can be considered complete
                object : OnCompleteListener<Void?> {
                    override fun onComplete(task: Task<Void?>) {
                        status.value = task.isSuccessful
                    }
                })

        return status
    }

    fun login(email: String, password: String): MutableLiveData<Boolean> {
        //obtain an instance of this class by calling getInstance().
        val status: MutableLiveData<Boolean> = MutableLiveData()
        auth = FirebaseAuth.getInstance()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener( // This interface is used as a method of being notified when an operation has been acknowledged by the Database servers and can be considered complete
                object : OnCompleteListener<AuthResult?> {
                    override fun onComplete(task: Task<AuthResult?>) {
                        status.value = task.isSuccessful
                    }
                })

        return status
    }

    fun signUp(email: String, password: String): MutableLiveData<Boolean> {
        val status: MutableLiveData<Boolean> = MutableLiveData()
        auth = FirebaseAuth.getInstance()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(){ task ->
                    status.value = task.isSuccessful
                    LogUtils.e(task.exception.toString())
                }
        return status
    }

    fun updatePassword(password: String): MutableLiveData<Boolean> {
        val status: MutableLiveData<Boolean> = MutableLiveData()
        auth = FirebaseAuth.getInstance()

        auth.currentUser?.updatePassword(password)?.addOnCompleteListener() { task ->
                status.value = task.isSuccessful
            }

        return status
    }

    fun isLoggedIn(): MutableLiveData<Boolean> {
        val status: MutableLiveData<Boolean> = MutableLiveData()

        //obtain an instance of this class by calling getInstance().
        auth = FirebaseAuth.getInstance()

        status.value = auth.currentUser != null

        return status
    }

    fun logout() {
        auth = FirebaseAuth.getInstance()
        auth.signOut()
    }

    companion object {
        val repository = AuthRepository()
    }
}