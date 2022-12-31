package com.healthdiary.utils

import android.content.Context
import android.content.SharedPreferences

open class SharedPreferencesUtils {
    companion object{
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

        fun setNumberParam(key: String?, value: Number) {
            editor!!.putInt(key, value.toInt())
            editor!!.apply()
        }

        fun getNumberParam(key: String?, defaultValue: Number): Number {
            return sharedPreferences!!.getInt(key, 0)
        }

        fun getParam(key: String?, defaultValue: String?): String? {
            return sharedPreferences!!.getString(key, defaultValue)
        }

        fun getBooleanParam(key: String?, defaultValue: Boolean): Boolean {
            return sharedPreferences!!.getBoolean(key, defaultValue)
        }

        fun setBooleanParam(key: String?, value: Boolean) {
            editor!!.putBoolean(key, value)
            editor!!.apply()
        }

        fun clear() {
            editor!!.clear()
            editor!!.apply()
        }
    }


}