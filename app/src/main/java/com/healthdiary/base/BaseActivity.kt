package com.healthdiary.base

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.NotificationUtils
import com.blankj.utilcode.util.TimeUtils
import com.blankj.utilcode.util.ToastUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.healthdiary.R
import com.healthdiary.utils.SharedPreferencesUtils
import com.healthdiary.utils.Utils.Companion.changeLang
import com.healthdiary.utils.Utils.Companion.getSysLang
import java.text.SimpleDateFormat
import java.util.*
import kotlin.system.exitProcess

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var language: String = getSysLang(baseContext)
        if (language == "zh_CN_#Hans") {
            language = "zh_CN"
        } else if (language == "zh_TW_#Hant") {
            language = "zh_TW"
        }
        SharedPreferencesUtils.setParam("language", language)
    }

    override fun attachBaseContext(newBase: Context?) {
        val lang = SharedPreferencesUtils.getParam("language", getSysLang(newBase!!)).toString()
        val context: Context = changeLang(newBase, lang)
        super.attachBaseContext(context)
    }

    open fun hasNotificationPermission(): Boolean {
        return NotificationUtils.areNotificationsEnabled()
    }

    open fun canNotify(): Boolean {
        return SharedPreferencesUtils.getBooleanParam("notifications", true)
    }

    /*
      TODO: Find the reason why this code is not working
      Using comments method will cause the app to crash, and now don't know why,
      so I'm going to use the method below, which is deprecated, but it works.

      FATAL EXCEPTION: main
      Process: com.healthdiary, PID: 17264
      java.lang.NoSuchMethodError: No virtual method getWindowInsetsController()
      Landroid/view/WindowInsetsController; in class Landroid/view/View; or its super classes
      (declaration of 'android.view.View' appears in /system/framework/framework.jar!classes3.dex)
 */

//    open fun hideStatusAndActionBar() {
//        val decorView = window.decorView
//        // Hide the status bar.
//        decorView.doOnLayout {
//            decorView.windowInsetsController?.hide(android.view.WindowInsets.Type.statusBars())
//        }
//
//        // Hide the action bar.
//        supportActionBar!!.hide()
//    }

//    open fun showStatusAndActionBar() {
//        val decorView = window.decorView
//        // Hide the status bar.
//        decorView.doOnLayout {
//            decorView.windowInsetsController?.show(android.view.WindowInsets.Type.statusBars())
//        }
//
//        // Show the action bar.
//        supportActionBar!!.show()
//    }

    @Suppress("DEPRECATION")
    open fun hideStatusAndActionBar() {
        val decorView = window.decorView
        // Hide the status bar.
        val uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
        decorView.systemUiVisibility = uiOptions

        // Hide the action bar.
        supportActionBar!!.hide()
    }

    @Suppress("DEPRECATION")
    open fun showStatusAndActionBar() {
        val decorView = window.decorView
        val uiOptions = View.SYSTEM_UI_FLAG_VISIBLE
        decorView.systemUiVisibility = uiOptions

        // Show the action bar.
        supportActionBar!!.show()
    }

    open fun showActionBar() {
        supportActionBar!!.show()
    }

    open fun showBackButton() {
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowCustomEnabled(true)
        }
    }

    open fun hideBackButton() {
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(false)
            actionBar.setDisplayHomeAsUpEnabled(false)
            actionBar.setDisplayShowCustomEnabled(false)
        }
    }

    open fun setActionBarTitle(title: String?) {
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = title
        }
    }

    open fun hasAllPermission(): Boolean? {
        val permission = getPermission()
        for (p in permission!!) {
            if (!hasPermission(this, p)) {
                return false
            }
        }
        return true
    }

    open fun hasPermission(context: Context, permission: String?): Boolean {
        return context.checkCallingOrSelfPermission(permission!!) == PackageManager.PERMISSION_GRANTED
    }

    open fun getPermission(): List<String>? {
        val permission: MutableList<String> = ArrayList()
        if (!hasPermission(this, "android.permission.ACTIVITY_RECOGNITION")) {
            permission.add("android.permission.ACTIVITY_RECOGNITION")
        }
        if (!hasPermission(this, "android.permission.INTERNET")) {
            permission.add("android.permission.INTERNET")
        }
        if (!hasPermission(this, "android.permission.READ_EXTERNAL_STORAGE")) {
            permission.add("android.permission.READ_EXTERNAL_STORAGE")
        }
        if (!hasPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE")) {
            permission.add("android.permission.WRITE_EXTERNAL_STORAGE")
        }
        return permission
    }

    open fun exitApp(delay: Int) {
        Handler(Looper.getMainLooper()).postDelayed({ exitProcess(0) }, delay.toLong())
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

    open fun getToday(): String{
        val yearNow = TimeUtils.getNowString(SimpleDateFormat("yyyy"))
        val monthNow = TimeUtils.getNowString(SimpleDateFormat("MM"))
        val dayNow = TimeUtils.getNowString(SimpleDateFormat("dd"))
        var dateToday = dayNow + monthNow + yearNow
        return dateToday
    }

    open fun getHealthScore(): Int {
        val savedEmail = SharedPreferencesUtils.getParam("healthScoreEmail", "").toString()
        val healthScore = SharedPreferencesUtils.getNumberParam("healthScore", 0)
        if (savedEmail == getUserEmail()) {
            return healthScore.toInt()
        }
        return -1
    }

    open fun restartApp(delay: Int) {
        Handler(Looper.getMainLooper()).postDelayed({
            val launchIntent = packageManager.getLaunchIntentForPackage(application.packageName)
            launchIntent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(launchIntent)
        }, delay.toLong())
    }
}