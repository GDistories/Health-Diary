package com.healthdiary.utils

import android.content.Context
import android.content.ContextWrapper
import java.util.*

class Utils {
    companion object{
        fun changeLang(context: Context, lang: String): ContextWrapper {
            var context = context
            val langs = lang.split("_").toTypedArray()
            val lang_code = langs[0]
            val country_code = langs[1]
            val sysLocale: Locale
            val rs = context.resources
            val config = rs.configuration
            sysLocale = config.locales[0]
            if (lang_code != "" && sysLocale.country != country_code) {
                val locale = Locale(lang_code, country_code)
                Locale.setDefault(locale)
                config.setLocale(locale)
                context = context.createConfigurationContext(config)
            }
            return ContextWrapper(context)
        }

        fun getSysLang(context: Context): String {
            val sysLocale: Locale
            val rs = context.resources
            val config = rs.configuration
            sysLocale = config.locales[0]
            return sysLocale.toString()
        }

        fun getSysCountry(context: Context): String {
            val sysLocale: Locale
            val rs = context.resources
            val config = rs.configuration
            sysLocale = config.locales[0]
            return sysLocale.country.toString()
        }
    }

}