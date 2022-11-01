package com.healthdiary.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesUtils {
    private var sharedPreferences: SharedPreferences? = null

    private var editor: SharedPreferences.Editor? = null

    fun init(context: Context) {
        sharedPreferences =
            context.getSharedPreferences("com.healthdiary_preferences", Context.MODE_PRIVATE)
        editor = sharedPreferences!!.edit()
    }

    fun setParam(key: String?, value: String?) {
        editor!!.putString(key, value)
        editor!!.apply()
    }

    fun getParam(key: String?, defaultValue: String?): String? {
        return sharedPreferences!!.getString(key, defaultValue)
    }
    
    fun clear() {
        editor!!.clear()
        editor!!.apply()
    }
}