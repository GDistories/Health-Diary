package com.healthdiary.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.LogUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.healthdiary.data.User

open class BaseFragment : Fragment() {
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

    open fun getUserInfo(): User? {
        if (isLogin()) {
            val currentUser = Firebase.auth.currentUser
            val user: User = User()
            user.email = currentUser?.email
            user.name = currentUser?.displayName
            return user
        }
        return null
    }
}
