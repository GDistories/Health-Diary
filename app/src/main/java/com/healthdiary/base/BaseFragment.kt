package com.healthdiary.base

import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.NotificationUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.healthdiary.utils.SharedPreferencesUtils

open class BaseFragment : Fragment() {

    open fun isLogin(): Boolean {
        val auth = FirebaseAuth.getInstance()
        return auth.currentUser != null
    }

    open fun hasNotificationPermission(): Boolean {
        return NotificationUtils.areNotificationsEnabled()
    }

    open fun canNotify(): Boolean {
        return SharedPreferencesUtils.getBooleanParam("notifications", true)
    }

    open fun getUserEmail(): String? {
        if (isLogin()) {
            val currentUser = Firebase.auth.currentUser
            return currentUser?.email
        }
        return null
    }
}
