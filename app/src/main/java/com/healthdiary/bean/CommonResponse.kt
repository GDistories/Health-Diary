package com.healthdiary.bean

import com.google.gson.JsonObject

class CommonResponse {
    var success: Boolean? = null
    var content: JsonObject? = null
    var message: String? = null
}