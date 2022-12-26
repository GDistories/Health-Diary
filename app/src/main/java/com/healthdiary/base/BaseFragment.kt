package com.healthdiary.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.blankj.utilcode.util.LogUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.healthdiary.data.User
import com.healthdiary.repository.AuthRepository
import com.healthdiary.repository.UserRepository
import com.healthdiary.viewmodel.AuthViewModel
import com.healthdiary.viewmodel.UserViewModel

open class BaseFragment : Fragment() {
    private val userViewModel: UserViewModel by viewModels {
        UserViewModel.Provider(UserRepository.repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    open fun isLogin(): Boolean {
        val auth = FirebaseAuth.getInstance()
        return auth.currentUser != null
    }

    open fun getUserEmail(): String? {
        if (isLogin()) {
            val currentUser = Firebase.auth.currentUser
            return currentUser?.email
        }
        return null
    }
}
