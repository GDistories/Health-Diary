package com.healthdiary.base

import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.NotificationUtils
import com.blankj.utilcode.util.TimeUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.healthdiary.utils.SharedPreferencesUtils
import java.text.SimpleDateFormat

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

    open fun getToday(): String{
        val yearNow = TimeUtils.getNowString(SimpleDateFormat("yyyy"))
        val monthNow = TimeUtils.getNowString(SimpleDateFormat("MM"))
        val dayNow = TimeUtils.getNowString(SimpleDateFormat("dd"))
        var dateToday = dayNow + monthNow + yearNow
        return dateToday
    }

    open fun getUserEmail(): String? {
        if (isLogin()) {
            val currentUser = Firebase.auth.currentUser
            return currentUser?.email
        }
        return null
    }

    open fun getHealthScore(): Int {
        val savedEmail = SharedPreferencesUtils.getParam("healthScoreEmail", "").toString()
        val healthScore = SharedPreferencesUtils.getNumberParam("healthScore", 0)
        if (savedEmail == getUserEmail()) {
            return healthScore.toInt()
        }
        return -1
    }
}
