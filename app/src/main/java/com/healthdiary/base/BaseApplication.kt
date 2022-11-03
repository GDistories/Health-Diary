package com.healthdiary.base

import android.content.Context
import com.healthdiary.utils.SharedPreferencesUtils

class BaseApplication : android.app.Application() {

    override fun onCreate() {
        super.onCreate()
        SharedPreferencesUtils.init(baseContext)
    }
}