package com.healthdiary.base

import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

open class BaseFragment : Fragment() {

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
